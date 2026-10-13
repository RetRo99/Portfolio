---
routeOverride: "/blog/on-device-neural-tts-android"
title: "Neural voices on an e-ink reader: on-device TTS in Parrot with sherpa-onnx"
description: "How Parrot reads any ebook aloud with Kokoro and Supertonic running on the phone: downloading 150 MB voices safely, preparing chapters ahead, and the lambda that crashed every synthesis."
date: "2026-10-13"
---
# Neural voices on an e-ink reader: on-device TTS in Parrot with sherpa-onnx

Parrot started as a read-along app. You give it an ebook and the matching audiobook, and it highlights each sentence as the narrator reads it. But most ebooks don't have an audiobook. So I wanted Parrot to be able to read any book aloud, with a voice that doesn't sound like a GPS from 2009, without sending the text to a server.

That means running a neural text-to-speech model on the device. Here's how it works in Parrot today, what the numbers look like on real hardware, and the bugs that cost me the most time.

---

## The engine: sherpa-onnx, two models

Parrot uses [sherpa-onnx](https://github.com/k2-fsa/sherpa-onnx), which wraps ONNX Runtime and ships ready-made TTS pipelines (phonemizer, acoustic model, vocoder) behind one `OfflineTts` class. It comes as an Android AAR with JNI bindings, so the Kotlin side stays small.

There are two neural voice families:

- **Kokoro** (Kokoro-82M, the INT8 multi-language pack). Parrot exposes the 28 US and UK English speakers. A UK voice only needs a different eSpeak phonemizer language per call, not a second engine.
- **Supertonic 3**, a flow-matching model by Supertone. It is faster and sounds great, but its license requires users to accept terms before downloading, and the app labels its speech as AI-generated.

Next to those sits the Android system `TextToSpeech` engine, wrapped behind the same synthesizer interface. A small router picks the engine by voice id, so the rest of the reader never knows which one is talking.

Generation itself is one suspending call. It runs on `Dispatchers.IO` behind a mutex, because a native engine instance isn't safe to call from two threads at once:

```kotlin
val audio = generationMutex.withLock {
    withContext(Dispatchers.IO) {
        val activeEngine = engine
            ?: throw CancellationException("Kokoro engine released")
        activeEngine.generateWithConfigAndCallback(
            text,
            GenerationConfig(
                sid = speakerId,
                speed = speed,
                extra = mapOf("lang" to kokoroPhonemizerLanguage(voiceId)),
            ),
            neuralGenerationCallback {
                requestJob?.isActive == false ||
                        cancellationGeneration.get() != requestGeneration
            },
        )
```

That callback is how cancellation reaches native code: sherpa-onnx calls it with each batch of samples, and returning `1` aborts generation. It is also the line that crashed the app on every single synthesis. More on that below.

---

## Getting 150 MB of model onto a phone

The Kokoro pack is about 149 MB and Supertonic about 145 MB. Neither ships in the APK. Voices are downloaded on demand from GitHub Releases, described by a `manifest.json` that lists every file with its URL, size and SHA-256.

The manifest isn't signed, so the downloader treats it as untrusted input:

- It only accepts URLs under the project's own releases path, and follows redirects by hand, requiring HTTPS on `github.com` or `*.githubusercontent.com` at every hop.
- It rejects path traversal in file names and caps file and model sizes.
- Every file is verified against its SHA-256 before the new version becomes active. The switch is a marker file that moves only after the whole set has been verified, so a half-downloaded voice can never be loaded.
- Downloads resume with HTTP `Range` into `.part` files, retry with backoff, and check free disk space before starting.

The biggest win here was boring. The first version downloaded the upstream `.tar.bz2` and unpacked it on the device. On an Onyx Boox Nova Air, an Android e-ink reader, the tap-to-ready time was **135.9 seconds**, and **124 of those seconds were single-threaded bzip2**. Switching to plain per-file downloads, with only the small eSpeak data still zipped, cut it to **21.6 seconds**. The network was never the bottleneck.

---

## The real problem: e-readers are slow

Speech synthesis speed is best expressed as a real-time factor, *r*: seconds spent generating per second of audio. Below 1.0 you can synthesize live. Above 1.0, playback eventually catches up with generation and stops to wait.

I benchmarked both models on two devices:

| Device | Supertonic (8 steps) | Kokoro |
|---|---|---|
| Onyx Boox Nova Air (e-ink) | r ≈ 2.3 | r ≈ 5 |
| Xiaomi, MediaTek MT6991 | r ≈ 0.6 | r ≈ 1.15 |

Two takeaways. First, even a fast 2026 phone can't run Kokoro in real time. Second, on the e-reader, the device Parrot cares most about, nothing is real-time. Ten minutes of chapter at r = 2.3 needs about six minutes of audio generated before playback can start and never stall.

Threads didn't save me either. Going from 2 to 4 threads gave 7 to 10 percent, and 8 threads was no better than 4 and sometimes worse. Parrot uses `availableProcessors().coerceIn(2, 4)`. Fewer Supertonic flow steps help more: going from 8 to 6 steps is 20 to 25 percent faster, and 4 steps about 45 percent. But in a listening test 4 steps sounded clearly worse, so the quality floor is 6, and even 4 steps stays above r = 1 on the e-reader.

So live synthesis alone could never be the whole answer.

---

## Preparing chapters ahead of time

Parrot can **prepare** a chapter: synthesize every sentence in the background and store it, so playback later is just file playback.

**What gets prepared.** The reader splits the chapter into sentences, and long sentences into chunks of at most 280 characters (with care for abbreviations, decimals and ellipses). Preparation starts at the sentence you're on, runs to the end of the chapter, then wraps around to the beginning, so the part you're about to hear is ready first.

**The queue survives the app.** Jobs live in a SQLDelight table and run in a `dataSync` foreground service, so killing the app doesn't lose them. The notification never shows the book or chapter title on the lock screen.

**Live speech always wins.** There is one synthesis permit. When you press play while a chapter is preparing in the background, your sentence jumps the queue:

```kotlin
withContext(NonCancellable) {
    mutex.withLock {
        if (request.granted) {
            val next = if (live.isNotEmpty()) live.removeFirst() else background.removeFirstOrNull()
            if (next == null) busy = false else {
                next.granted = true
                next.signal.complete(Unit)
            }
```

The gate never interrupts a native call already in progress. It just decides who goes next.

**Storage is AAC, not WAV.** Each sentence is encoded with `MediaCodec` to AAC-LC, mono, about 48 kbit/s, in an `.m4a`. That's roughly seven times smaller than the WAV. The cost is 87 to 127 ms of encoder padding per sentence. I tried remuxing with encoder-delay metadata to remove it, that didn't work, and at sentence boundaries the padding reads as a natural pause. Prepared audio is capped at 1 GiB.

**File names say nothing.** Each clip is keyed by `sha256(voiceId | modelVersion | rate | pitch | text)`, and folders are hashes of the server, book and chapter. Nothing in the file system reveals what you're reading. A side effect is that changing the voice or speed makes a clean cache miss instead of playing stale audio.

When playback needs a sentence, it looks in the prepared store first, then a small live WAV cache, and only then synthesizes.

---

## Playback and highlighting

Playback is Media3. Every sentence is one ExoPlayer `MediaItem`, appended to the playlist as soon as its audio exists. The engine keeps four sentences synthesized ahead.

Highlighting falls out of that structure. Each item's media id is `tts:<book>:<sentenceIndex>`. When ExoPlayer moves to the next item, Parrot parses the index back and highlights that sentence in the reader, turning the page if needed. There is no forced alignment or word timing, just one sentence per media item. That's coarser than word-level highlighting, but it's robust and it costs nothing at runtime.

---

## The bugs that cost the most

**A lambda that killed the process (TTS-F22).** The cancellation callback above was originally a plain Kotlin lambda. Every Kokoro synthesis crashed the app with no Kotlin stack trace. sherpa-onnx's native code looks up the callback method by its exact JNI signature, `invoke([F)Ljava/lang/Integer;`. A Kotlin lambda compiles to a synthetic class that only has the erased bridge `invoke(Object)Object`, so the lookup failed and ART aborted the process. The fix is an explicit object, which declares the specialized method:

```kotlin
internal fun neuralGenerationCallback(shouldCancel: () -> Boolean): (FloatArray) -> Int =
    object : (FloatArray) -> Int {
        override fun invoke(samples: FloatArray): Int = if (shouldCancel()) 1 else 0
    }
```

A host test now guards it, and an R8 keep rule makes sure the minifier doesn't undo it. If you call into a JNI library that takes a Kotlin function type, check how it looks the method up.

**The 5 ms race (TTS-F26).** Narration sometimes just went silent. A sentence appended about 5 ms before ExoPlayer reported reaching the end of the queue was never played: the player had already decided it was done. The engine now notices this and restarts the next sentence itself.

**Chapter handover (TTS-F30).** At the end of a chapter, the engine stopped the playback service and started it again for the next chapter. But a foreground service started while the app is in the background can't be promoted to the foreground on modern Android, so the system stopped it for being idle. Now the service and its player survive the handover, and only the engine's state resets.

**Warm-up.** The first sentence after loading a model was always slow, because ONNX Runtime does one-time initialization on the first run. Parrot now synthesizes the text `"a"` once right after loading, so that cost lands while you're still choosing a voice.

---

## What's next

The iPhone version. A spike ran Kokoro in the iOS simulator with sherpa-onnx's static XCFrameworks. It works, but it adds 38 MB to the app, takes 4.5 seconds for a cold first synthesis, and peaks at 487 MB of memory. Getting that down is the next problem, and probably the next note.

The lessons so far: measure on the slowest device you care about, because that's where the design decisions get made. Treat downloaded models like any other untrusted input. And when a native library calls back into Kotlin, check exactly which method it's looking for.
