---
routeOverride: "/blog/kokoro-tts-shared-kotlin-onnx-runtime"
title: "Neural voices in shared Kotlin: Kokoro on ONNX Runtime, without sherpa-onnx or espeak"
description: "Part two of Parrot's on-device voices: replacing sherpa-onnx with ONNX Runtime behind one Kotlin interface, calling its C API from Kotlin on iPhone, and porting an English pronunciation front end so Kokoro needs no espeak-ng."
date: "2026-10-20"
cover: "/articles/tts_shared_kotlin.jpeg"
---
# Neural voices in shared Kotlin: Kokoro on ONNX Runtime, without sherpa-onnx or espeak

In [the first note on Parrot's voices](/blog/on-device-neural-tts-android), the neural voices ran on [sherpa-onnx](https://github.com/k2-fsa/sherpa-onnx), an Android library that wraps ONNX Runtime and a ready-made text-to-speech pipeline. That note ended with "the iPhone version is next".

The iPhone version is what made me take sherpa-onnx out. Parrot's Kokoro and Supertonic voices now run on ONNX Runtime directly, from shared Kotlin, on Android and iPhone. Kokoro gets its pronunciation from a Kotlin port of an English front end instead of espeak-ng. This note is about why, how, and what it cost.

---

## Why replace a library that worked

Two reasons, and the second one decided it.

**The iPhone.** sherpa-onnx ships an iOS build, so the first iPhone version used it from Swift: a Swift class implemented a Kotlin interface, took text in and handed float audio back. It worked, but the voice pipeline then lived in two places, Kotlin on Android and Swift on iPhone, and nothing about the iPhone side could be tested from Kotlin.

**espeak-ng.** Kokoro doesn't read letters. It reads phonemes, so every sentence first goes through a pronunciation front end. In sherpa-onnx, that front end is espeak-ng, which is GPL-3.0. Reading sherpa-onnx's source showed three things:

- the Kokoro English path always initialises espeak-ng, even though the model pack ships its own pronunciation dictionaries;
- when a language is given, which it is for English voices, the whole text goes to espeak-ng and the dictionaries aren't used at all;
- there's no build switch to leave espeak-ng out. Turning on text-to-speech compiles it in.

Not calling it doesn't help; the code is in the binary either way. The options were to keep it, maintain a patched fork of sherpa-onnx for four Android ABIs and iOS on every upgrade, or drop sherpa-onnx and write the small part of it Parrot actually uses. The model itself needs no espeak: it takes a list of token ids. So I took the third option.

---

## One interface over ONNX Runtime

Everything platform-specific about the neural voices is now one interface:

```kotlin
/**
 * One loaded ONNX model, the only platform seam of the neural voices (Kokoro, Supertonic).
 */
interface OnnxSession : AutoCloseable {

    /** The model's custom metadata (Kokoro keeps sample_rate, n_speakers and style_dim here). */
    val metadata: Map<String, String>

    /**
     * Runs the model and returns its first output. [inputs] are positional, in the model's
     * own input order, as sherpa-onnx passed them.
     */
    fun run(inputs: List<OnnxTensorData>): OnnxTensorData.Floats

    /** Ends a [run] in progress on another thread (it then throws); nothing when none runs. */
    fun terminate() {}
}

expect fun openOnnxSession(modelPath: String, numThreads: Int): OnnxSession
```

There are four implementations:

- **Android** uses the official `onnxruntime-android` package.
- **Host tests** use ONNX Runtime's desktop jar, so the real Kokoro and Supertonic models run in ordinary unit tests on a laptop.
- **iPhone** calls ONNX Runtime's C API directly from Kotlin, through cinterop. The whole definition file is five lines:

```
package = com.retro99.onnxruntime
headers = onnxruntime_c_api.h
headerFilter = onnxruntime_*.h
```

No Swift class, no registration at app start. The iPhone test suite can load the real runtime and synthesize real speech, and it does: 1,235 tests in that module, including real Kokoro and Supertonic runs when the voice packs are on the machine.

- **The web** throws "not wired in the browser" for now. The browser version of Parrot uses the browser's own voices.

Above that seam, everything is common code: the Kokoro and Supertonic pipelines, the read-aloud engine, the audio cache, chapter preparation and their tests.

---

## Kokoro without the wrapper

Once sherpa-onnx is gone, running Kokoro turns out to be small. The model takes three inputs: the token ids with a 0 at each end, a "style" vector for the voice, and the speed. The style comes from `voices.bin`, which holds one row per voice **per sentence length**, so the row is picked by how many tokens the sentence has:

```kotlin
internal fun run(ids: IntArray, sid: Int, speed: Float): FloatArray {
    val x = LongArray(ids.size + 2)
    for (i in ids.indices) x[i + 1] = ids[i].toLong()
    val rowStart = (sid * maxTokens + ids.size) * styleWidth
    val style = styles.copyOfRange(rowStart, rowStart + styleWidth)
    return session.run(
        listOf(
            OnnxTensorData.Longs(x, longArrayOf(1, x.size.toLong())),
            OnnxTensorData.Floats(style, longArrayOf(1, styleWidth.toLong())),
            OnnxTensorData.Floats(floatArrayOf(speed), longArrayOf(1)),
        ),
    ).data
}
```

The table has 510 rows per voice, so longer sentences are cut into pieces of at most 508 tokens, after the last space where possible. The sample rate, speaker count and table shape come from the model's own metadata, the same way sherpa-onnx read them. I kept sherpa's silence scaling too, so the pacing of the speech didn't change.

Cancellation got simpler as well. In part one, cancelling a sentence meant a callback that native code called with each batch of samples, and a Kotlin lambda in that position crashed the app. Now a cancelled coroutine just calls `terminate()` on the session, and ONNX Runtime ends the run in progress:

```kotlin
internal suspend fun <T> runTerminable(context: CoroutineContext, terminate: () -> Unit, block: () -> T): T = coroutineScope {
    val watcher = launch(start = CoroutineStart.UNDISPATCHED) {
        try { awaitCancellation() } finally { terminate() }
    }
    try {
        withContext(context) { block() }
    } catch (error: Exception) {
        currentCoroutineContext().ensureActive()
        throw error
    } finally {
        watcher.cancel()
    }
}
```

That's what lets a sentence you're waiting for interrupt a chapter being prepared in the background.

---

## The hard part: pronouncing English

Kokoro v1.0 was trained on phonemes from [misaki](https://github.com/hexgrad/misaki), its author's own Python front end. misaki looks words up in dictionaries and falls back to espeak-ng for words it doesn't know. So the target was clear: port misaki's rules to Kotlin, and replace the espeak fallback with something that isn't GPL.

The new module, `lib/g2p`, is common Kotlin for Android, iPhone and the web. For each word it tries, in order:

1. the pack's lexicon plus misaki's extra entries (special cases like "a", "the", "to", and capitalised words like "US" and "NASA");
2. misaki's rules for suffixes and possessives (-s, -ed, -ing, 's);
3. the word again with accents stripped;
4. the CMU Pronouncing Dictionary;
5. the parts of a hyphenated compound;
6. a small neural model, g2p_en's GRU encoder-decoder, ported to plain Kotlin, for words nothing else knows.

Before any of that, a text normaliser does the things espeak-ng did for free: numbers, ordinals, years ("eighteen hundred eleven"), decimals, currency, abbreviations like "Mr." and "No. 5", and Roman numerals. One deliberate difference: misaki spells "IV" as letters, and Parrot reads "Chapter IV" as "Chapter four".

The numbers I tested against:

- **Coverage.** Pride and Prejudice has 130,576 words and numbers. Before the neural fallback, 585 of them (0.45%) had no pronunciation from any dictionary or rule. With it, none.
- **Agreement with misaki.** On a recorded list of words, 95.9% of comparable words match misaki exactly, and 97.8% of words inside whole sentences do. The misses are mostly heteronyms: "live", "close", "present", and "I read" in past or present. misaki tells those apart with a part-of-speech tagger; Parrot guesses from the word before.
- **The g2p_en port** gives the same phonemes as the original Python on all 41 test words.

Nothing GPL or LGPL is in the module. The ported code is Apache-2.0 and CMUdict is BSD-2-Clause, each file names its source and licence in its header, and the licence texts ship with the voice pack. On the iPhone release build, `strings` finds no "espeak-ng" and no "sherpa".

There was a bonus. The old espeak path cut sentences short at "Mr.": one 212-character sentence came out as 1.4 seconds of audio. The new front end reads all of it, in 10.7 seconds.

---

## Updating the voice on people's phones

The pronunciation data is about 7.6 MB of new files (4.1 MB compressed): misaki's extras, CMUdict and the g2p_en weights. The 114 MB model and the 28 MB voice table didn't change, so an update downloads only the new files and hard-links the rest from the old pack.

The new front end produces different phonemes, so the same sentence sounds slightly different. The cache key for every sentence's audio includes the model version, and that now ends in `+misaki-g2p1`. Audio prepared by the old version is a clean cache miss instead of a mix of two pronunciations in one chapter.

---

## What it cost

**Size.** The Android APK grew by 1.1 MB. The official ONNX Runtime package replaced sherpa-onnx's own copy of ONNX Runtime and its JNI layer.

**Speed, at first.** On a Samsung Galaxy S24, the new Kokoro path was 15 to 25% slower per second of audio than sherpa-onnx in the first test. That test wasn't clean, though: the phone was warm and charging, and the old and new runs weren't interleaved.

So the next step was a proper benchmark on three phones, a Galaxy Z Fold 3, the S24 and a Lenovo device, with 40 sentences per setting. Two findings:

- **Using more threads than the phone has big cores cost 30 to 40%.** Parrot now counts the cores with at least half the capacity of the fastest one and uses that many, between 2 and 4.
- **Heat mattered more than any setting.** On the S24, one cool run with 2 threads beat every 4-thread run. Pinning threads, turning off spinning, a pre-optimised model and the XNNPACK backend either didn't help or changed the audio.

The honest result: on the S24, no setting keeps Kokoro ahead of playback at normal speed for ten minutes of live reading. That's why Parrot prepares chapters ahead of time, as described in part one, and now does it more carefully while the phone is cool or charging. Supertonic keeps up comfortably.

**iPhone.** In the iOS simulator, Kokoro generates 4 seconds of audio in about 2 seconds, a little faster than the sherpa-onnx build did in the same test. Supertonic generates 5.8 seconds in 1.2 seconds. Both now download, prepare and play in the iPhone app.

---

## Honest status

- Everything on iPhone so far has run in the simulator. Speed, memory and heat on a real iPhone haven't been measured.
- The run reports don't record anyone comparing the old and new Kokoro by ear. The side-by-side recordings exist, and that listening test is still to do.
- Heteronyms remain a guess. "I read the book" is past or present depending on context, and the front end doesn't have the grammar to know.
- The web version doesn't have neural voices. ONNX Runtime has a WebAssembly build, and the interface is where it would plug in.

The lesson I'd keep: a library that does everything for you also decides everything for you, including what's in your binary. Here, the part Parrot needed from sherpa-onnx was one model call and a front end. Owning those cost a module of Kotlin, and it gave back one pipeline instead of two, tests that run the real model on every platform, and a binary I can explain.
