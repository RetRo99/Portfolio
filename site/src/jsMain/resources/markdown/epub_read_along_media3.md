---
routeOverride: "/blog/epub-read-along-media3"
title: "Highlighting the sentence the narrator is reading: EPUB read-along with Media3"
description: "How Parrot keeps the text and the narration in step: SMIL media overlays, one ExoPlayer playlist per chapter, page turns for split sentences, and the Media3 update that silently killed playback."
date: "2026-10-13"
---
# Highlighting the sentence the narrator is reading: EPUB read-along with Media3

Read-along is the feature Parrot started with. You open an ebook that comes with its narration, press play, and the sentence being read lights up. When the narrator reaches the bottom of the page, the page turns. It sounds simple, and the first version was. Making it hold up for a whole book, in the background, on slow e-ink hardware, took a lot longer.

This note is about how it works today and the bugs that taught me the most.

---

## What a read-along book contains

A read-along EPUB uses EPUB 3 **media overlays**. Next to the chapters and the audio files, the book holds SMIL files: XML that pairs a fragment of text with a span of audio.

```xml
<par>
  <text src="chapter1.xhtml#s1"/>
  <audio src="audio.mp3" clipBegin="0s" clipEnd="2.5s"/>
</par>
```

Each `<par>` is one clip: "sentence `s1` is the first 2.5 seconds of `audio.mp3`". Parrot turns these into a short pipeline of models. The parser reads `SmilClip`s, keeping the text and audio sources with begin and end times, and drops any `<par>` that is missing either source. Those become `MediaOverlayClip`s, keyed by the sentence's fragment id, and finally `SchedulableClip`s with plain millisecond times for the player.

For a sense of scale, my main test book is a 617 MB EPUB with 38 SMIL files and 29 audio files. Its first chapter alone has 308 clips.

---

## Finding the SMIL for a chapter, quickly

The book's manifest doesn't directly say which SMIL file belongs to which chapter in a way you can use cheaply. The first version found out lazily, scanning SMIL files when a chapter was opened. On a big book that was slow enough to make it into the changelog as a bug: "Slow audio loading".

The fix was to index every SMIL file up front, without parsing it. All the index needs is the first `<text src>` in each file, and a regex finds that:

```kotlin
/**
 * This scanner uses regex to quickly find the first textSrc reference
 * without fully parsing the XML. This is much faster than full parsing
 * and sufficient for building the SMIL→chapter index.
 */
```

The full parse then happens once per chapter, when it's needed, with a cache that makes sure two callers never parse the same chapter twice. The next chapter is parsed in the background while you listen, and that work is cancelled if you jump somewhere else. I didn't measure the before and after, which I regret. The difference was obvious, but "obvious" isn't a number.

---

## One playlist per chapter

A chapter's narration can span several audio files. Parrot builds one ExoPlayer playlist per chapter, with one `ProgressiveMediaSource` per audio file the chapter uses, ordered by first appearance. Moving from one file to the next is then `seekTo(trackIndex, positionMs)` instead of preparing a new source, so the switch is seamless and ExoPlayer only buffers the file it's playing.

The player and its `MediaLibrarySession` live in a `MediaPlaybackService`, not in the reader screen. That's what makes background playback and the lock screen controls work, and it means the narration doesn't care if the reader UI is destroyed and rebuilt.

---

## Two ways to know which sentence is playing

The audio side has to tell the reader "sentence `s47` started now". Parrot does it two ways at once.

**Player messages.** For every clip, the service schedules an ExoPlayer `PlayerMessage` at the clip's start position in its track. When it fires, the service broadcasts a custom session command, `com.retro99.CLIP_CHANGED`, with the fragment id to every connected controller. The messages are kept after delivery, so seeking backward fires them again:

```kotlin
player.createMessage { messageType, payload ->
    val clipPayload = payload as SchedulableClip
    onClipStarted(clipPayload)
}.apply {
    setLooper(player.applicationLooper)
    setPosition(trackIndex, clip.startTimeMs)
    setPayload(clip)
    setDeleteAfterDelivery(false)
    send()
}
```

**Polling.** Every 100 ms, a coroutine reads the player position and binary-searches the current file's clips for the one that contains it. It publishes a new location only when the sentence changes.

Messages give exact timing; polling recovers from anything messages miss, like a reconnecting UI. That `setLooper` line wasn't always there. It's the subject of the worst bug in this note.

---

## Turning the page at the right moment

The naive rule is "if the highlighted sentence isn't on screen, turn the page". That looks bad for sentences split across two pages. The narrator starts the sentence at the bottom of page 9, the reader jumps to page 10 immediately, and you lose the start of the sentence you're listening to.

So the reader measures the sentence in the WebView with `getClientRects()`. A page turn is needed if the sentence is entirely off the page, less than half visible, or starts in the last 10% of the page width. If a turn is needed, it waits as long as the narrator will spend on the visible part before turning:

```kotlin
val visibility = checkSentenceVisibility(fragmentId)
if (visibility.needsPageTurn) {
    // Calculate delay based on visible fraction
    val delayMs = (visibility.visibleFraction * sentenceDurationMs).toLong()
        .coerceAtLeast(MIN_PAGE_TURN_DELAY_MS)
    lastPageTurnSentenceId = fragmentId
    pendingPageTurnJob = controllerScope.launch {
        delay(delayMs)
        withNavigator { it.goForward() }
    }
}
```

If 40% of the sentence is on this page, the page turns 40% of the way through it, which is roughly when the narrator reaches the page break. The minimum delay is 200 ms.

Chapter ends are handled separately. The service pauses just after the last clip ends, and the reader navigates to the next chapter explicitly instead of relying on a page turn, because a chapter can be a single page and a page turn would never happen.

---

## The update that killed narration in 320 ms

In October, narration stopped working. You pressed play and nothing happened: the button stayed on Play, the time stayed at 0:00, and no error appeared. In the log, the whole failure took 320 ms:

```
Caused by: java.lang.IllegalStateException: Player callback method is called from a wrong thread.
    at androidx.media3.session.MediaSessionImpl.verifyApplicationThread(...)
```

The cause had been in the code since February. `ExoPlayer.createMessage` delivers on the playback thread by default, and the clip callback reached into the `MediaSession` from there. That was always wrong, but it worked, until a dependency update moved Media3 to 1.11.0, which added a `verifyApplicationThread()` guard to that method. ExoPlayer treats an exception thrown inside message delivery as a fatal playback error. The first clip starts at 0 ms, so narration died before its first sample.

How I found it is the part I'd repeat. The obvious move was to bisect the commits, but each test build took about 20 minutes, and the range needed about twenty of them. Instead I compared the source jars: the guard is absent in Media3 1.9.2 and 1.10.0 and present in 1.11.0. The app code involved was byte-identical across the whole range, and the only commit that changed anything relevant was the version bump. That pinned the cause without a single bisect build.

The fix is the one line you saw above, `setLooper(player.applicationLooper)`, plus a comment explaining why it must stay. I couldn't write a host test for it, because the project has no Robolectric and no mocking library, so it was proven on a device instead. If your app schedules `PlayerMessage`s that touch a `MediaSession`, check which thread they're delivered on before you update to 1.11.

---

## A stale playlist

A smaller bug with a cleaner lesson. Parrot can hand playback from the device's own voice back to the narration. That handover creates a new service and a new player, but the cached playlist still described the old player, so narration sat at 0:00 forever.

The fix was to make the decision a pure function, so it could be tested without Media3 or Android, and to remember which player a playlist was built for with a `WeakReference`:

```kotlin
internal fun <T> playlistNeedsRebuild(
    cachedAudioHrefs: List<T>,
    chapterAudioHrefs: List<T>,
    cachedTrackIndexes: Map<T, Int>,
    playlistBelongsToCurrentPlayer: Boolean,
): Boolean = !playlistBelongsToCurrentPlayer ||
    cachedAudioHrefs != chapterAudioHrefs ||
    cachedTrackIndexes.isEmpty()
```

The failing test went in first, then the fix.

---

## What's still rough

- The notification shows the current audio file's duration, not the chapter's, so it can say 20:13 of 59:18 while the reader says 24:53 of 41:44.
- Sections that are only one page long don't advance on their own yet.
- Android Auto plays the narration through a headless player, which has no screen and so no highlighting.
- The iPhone version uses `AVPlayer` with its own 100 ms observer, and still indexes SMIL files the old, lazy way. The two platforms should converge.

The lessons: give the player and session to a service and let the UI come and go. Run a precise mechanism and a forgiving one side by side. And when a dependency update breaks something, reading what changed in the dependency can be much faster than bisecting your own history.
