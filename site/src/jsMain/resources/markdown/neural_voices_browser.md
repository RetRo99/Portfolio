---
routeOverride: "/blog/neural-tts-browser-onnxruntime-web"
title: "From 14 seconds to 1.5: fast neural voices in the browser with onnxruntime-web"
description: "How Parrot's web app went from a 14-second wait after pressing Play to under two seconds: a worker per voice so memory comes back, cross-origin isolation for four threads, and the 7-second wait that had nothing to do with the model."
cover: "/articles/neural_voices_browser.jpeg"
date: "2026-11-13"
---
# From 14 seconds to 1.5: fast neural voices in the browser with onnxruntime-web

Parrot runs in a browser through Kotlin/Wasm ([how that started](/blog/android-app-in-the-browser-kotlin-wasm)), and its neural voices came along: Kokoro and Supertonic, the same models that read books aloud on Android and iPhone ([part two of the voices notes](/blog/kokoro-tts-shared-kotlin-onnx-runtime)). On the web they run on onnxruntime-web, ONNX Runtime compiled to WebAssembly.

The first version worked, and it was slow in every way you could measure. Pressing Play waited 14 to 30 seconds for the first sound. Speech was made slower than it played, so the reader caught up with the voice and waited again. And after you switched the voice off, the tab still held almost a gigabyte.

This note is about the three fixes. None of them made the model itself faster. Each one came from measuring what the page was actually waiting for.

---

## How a sentence gets spoken

The pieces, in order:

- The **voice pack** is downloaded once and kept in Cache Storage. For Kokoro that's a 114 MB model plus its pronunciation dictionaries and voice styles.
- The **pronunciation front end** (text to phonemes) is the shared Kotlin from the iPhone work, compiled to Wasm. It runs on the page.
- The **model** runs in onnxruntime-web, in a worker. The page sends phoneme ids, the worker sends back float samples, and the page wraps them in a WAV and plays it.

To see where the time went, I added temporary timing marks to every step, from the Kotlin controller to the worker, and a script that drives a production build in a headless browser: fresh app, a ten-sentence bench book from *Pride and Prejudice*, choose a voice, press Play, record. Every number below comes from those runs on my Mac, with before and after builds run in the same minutes where it mattered, because the machine was busy with other work.

One aside about the browser: the headless browser was Brave, and Brave randomises `navigator.hardwareConcurrency`. It told me 4 cores, then 13. The Mac has 18. Never trust "cores" in Brave.

---

## The gigabyte that never came back

First the memory. I measured the renderer's footprint around the steps of reading:

| Step | Footprint |
|---|---|
| Reader open, no voice | 248-323 MB |
| Kokoro reading for 60 s | 865 MB (peak 1.0 GB) |
| Switched to the browser's own voice, every session released | 823-845 MB |

Releasing every ONNX session gave nothing back. To find out who held it, I wrapped `Worker` in a QA-only build so I could find onnxruntime's proxy worker and end it from the page. With nothing else changed, the footprint dropped from 823 to 326 MB at once.

The reason is a property of WebAssembly: **a WebAssembly memory can grow, but it never shrinks.** `session.release()` frees memory inside the heap, not back to the device. The heap stays at its peak until the worker that owns it is gone. And onnxruntime-web 1.30 has no way to end its proxy worker: it starts once per module and nothing exported stops it.

So Parrot stopped using onnxruntime's proxy and owns the worker instead. Each neural voice runs in a worker of its own, which imports onnxruntime with `proxy` off. The page transfers the model bytes to it, so there's no second copy. Closing a voice calls `terminate()`, and the browser gets the whole heap back.

```javascript
// voice-worker.mjs
async function runtime(ortDir) {
    if (ort) return ort;
    ort = await import(new URL(ortDir + "ort.wasm.min.mjs", self.location.href).href);
    ort.env.wasm.wasmPaths = new URL(ortDir, self.location.href).href;
    // Already in a worker: onnxruntime's own proxy worker would only add a second one.
    ort.env.wasm.proxy = false;
    ...
}
```

The JavaScript API the Kotlin side calls (open, run, close) didn't change, so no Kotlin changed for this.

| Step | Before | After |
|---|---|---|
| Kokoro reading for 60 s | 865 MB | 848 MB |
| Switched to Supertonic | 863 MB | 631 MB |
| Browser voice, sessions closed | 823-845 MB | 342-347 MB |

The steady state never holds two models any more. A switch between voices still peaks for a moment (1.2 GB), because the browser frees the old worker's memory a little after the new one starts loading.

---

## One thread is slower than speech

Next, the speed of speech itself. With the timing marks in, Kokoro took about 7.5 seconds to make 5 seconds of audio. That's a **real-time factor of 1.4 to 1.6**: slower than real time. Parrot prepares two sentences ahead, but with a factor above 1, no lead is ever enough.

onnxruntime-web can use several threads, but only with `SharedArrayBuffer`, and browsers only give you that on a **cross-origin isolated** page. The page was running on one thread because the server didn't send the headers that make it isolated.

There are two ways to ask for isolation, and Parrot sends both:

```
Document-Isolation-Policy: isolate-and-credentialless
Cross-Origin-Opener-Policy: same-origin
Cross-Origin-Embedder-Policy: credentialless
```

Chrome 137 and later take `Document-Isolation-Policy`. Firefox doesn't know it and takes the COOP and COEP pair instead. I checked both report `crossOriginIsolated` as true. Safari supports neither `credentialless` nor DIP, so it can't be isolated this way and stays on one thread.

The worry with COOP `same-origin` is that it breaks pop-ups that talk back to their opener, which is how a lot of sign-in flows work. I went through every case before turning it on. Every `window.open` in the app is already `noopener`. Google sign-in is a same-tab redirect. Dropbox's sign-in tab reports back over a `BroadcastChannel`, not through `window.opener`. Then I signed in for real with Google under the new headers and walked the Dropbox flow up to its return. Both worked.

How many threads? I forced each count on the same ten sentences:

| Threads | Model run per sentence | Real-time factor |
|---|---|---|
| 1 | 7.5 s | 1.48 |
| 2 | 4.9 s | 0.97 |
| **4** | **3.8 s** | **0.74** |
| 8 | 3.3 s | 0.65 |
| 12 | 3.1 s | 0.62 |

Two threads still aren't faster than speech. Four are the first count clearly under 1, with margin. Going to eight buys another 12 percent, but `hardwareConcurrency` on phones and laptops counts efficiency cores and hyperthreads, and the page thread also needs a core for the pronunciation front end, drawing and audio. So the cap is four:

```javascript
ort.env.wasm.numThreads = self.crossOriginIsolated ? Math.min(4, navigator.hardwareConcurrency || 1) : 1;
```

I also timed the Kotlin pronunciation front end while I was there, in case it was the next problem. It wasn't: about 35 ms per sentence, under 1 percent of the model's time.

---

## The 7 seconds that weren't the model

Faster synthesis helped once speech was going, but pressing Play still took about 14 seconds to the first sound. So I split that wait into every step it contained:

| Step | Time |
|---|---|
| **Look for prepared audio in Parrot Cloud** | **7,010 ms** |
| Write the front-end files | 100 ms |
| Read the model from Cache Storage | 50-110 ms |
| `InferenceSession.create` | 630-740 ms |
| First sentence: pronunciation | 320-380 ms |
| First sentence: model run | 5,600 ms |
| **Total, Play to first sound** | **about 14 s** |

Half of the wait wasn't about voices at all. Parrot can pre-render a chapter's audio and keep it in Parrot Cloud, so before speaking, the player checks whether that audio exists. For a profile with no cloud session, that request was held for 7 seconds and then failed with "No Parrot Cloud session". Then the voice started loading. It happened in five of five runs, on my local setup and on the real project.

The model itself needed about one second to get ready. The rest was the first sentence being synthesised.

Three changes, all in the web read-aloud controller:

1. **Look up prepared audio when the chapter loads, not when Play is pressed.** The result is kept for that chapter and its sentences, and Play uses it if it's under two minutes old. A lookup that gets replaced is cancelled rather than left running.
2. **Load the model when read-aloud opens.** If the chosen voice is a neural one and its pack is installed, the model loads in a job of its own. It never starts a download, and it does nothing for the browser's own voice or a voice whose terms haven't been accepted.
3. **Don't wait for a cloud session that can't come.** A profile that isn't linked to a cloud account can't have prepared audio there, so the lookup returns at once:

```kotlin
internal suspend fun cloudPreparedAudioPossible(
    activeProfileId: () -> String,
    linkFor: suspend (String) -> CloudProfileLink?,
): Boolean = try {
    linkFor(activeProfileId()) != null
} catch (cancelled: CancellationException) {
    throw cancelled
} catch (_: Exception) {
    true
}
```

The auth state couldn't make this call. A profile with no cloud client reports "restoring session", which is exactly what a signed-in user reports while their session starts. The profile's cloud link is what tells them apart. A linked profile keeps the wait, and if the check itself fails, the old path runs.

The result, before and after builds interleaved in the same minutes:

| | Run 1 | Run 2 | Run 3 |
|---|---|---|---|
| Before | 13.9 s | 19.3 s | 14.3 s |
| After | 1.56 s | 1.65 s | 1.81 s |

Everything apart from the model run went from 8.3 seconds to under half a second.

There's a part of this I can't fully explain. The first sentence's model run itself also fell, from about 5.7 seconds to about 1.2, on the same four threads and the same sentence. The only difference is that the model had been loaded a second or two earlier and sat idle. My best guess is that the browser finished optimising onnxruntime's WebAssembly in the background before the first run instead of during it. I didn't measure that directly, so treat the drop as real and the explanation as a guess.

The same early load now covers every neural voice, not only Kokoro. Supertonic, which loads four models, went from about 3.7 seconds to 0.9 from Play to first sound.

---

## Honest status

- **Safari is still on one thread.** Without `credentialless` or Document-Isolation-Policy there's no isolation, so Kokoro there is slower than speech.
- **A fast Play can still wait, if you're signed in.** For a profile linked to Parrot Cloud, the lookup still waits for the session. Press Play within a couple of seconds of opening read-aloud and you get the rest of that wait. I haven't measured the signed-in path yet.
- **One machine.** Every number here comes from one Mac in a headless Chromium browser with other work running. Phones and low-end laptops will be slower. The ratios are what I'd trust, more than the milliseconds.

What I keep coming back to is that the model was never the slow part people waited on. The memory was a WebAssembly rule nobody had told me about, the threads were three response headers, and the 7 seconds were a network request that couldn't succeed. Before this, I would have tried to make the model faster. The timing marks are why I didn't.
