---
routeOverride: "/blog/syncing-reading-positions"
title: "Never lose someone's place: syncing reading positions across devices and formats"
description: "How Parrot syncs where you are in a book: local-first writes, compare-and-set revisions on the server, when to settle a conflict and when to ask, and moving a position between an ebook and its audiobook."
date: "2026-10-13"
---
# Never lose someone's place: syncing reading positions across devices and formats

A reading app has one job that matters more than any feature: when you open a book, it should be where you left it. Parrot runs on phones, tablets and e-ink readers, and the same book can exist as an EPUB, a read-along EPUB and an audiobook on a different server. So "where you left it" turns into a distributed systems problem with several devices, several servers, unreliable clocks and a person who reads offline on a train.

This note is about how Parrot syncs reading positions, and the rules I ended up with after getting it wrong a few times.

---

## The rule everything else follows

Early on I wrote down the thing I was most tempted to do, so I wouldn't do it:

> Never resolve by maximum percentage. Never replace the original reading time with upload/retry time. Device wall clocks and server arrival order alone are not reliable proof of causality.

"Furthest wins" sounds safe until you open a book on an old tablet where you once skimmed ahead to the last chapter. Then every device jumps to the end. "Newest upload wins" sounds safe until a phone that was offline for a day uploads a stale position and overwrites the one you just made. Both rules are simple, and both lose someone's place.

---

## Local first

Every position is written to the device's own database first, in a SQLDelight table, before any network call. The commit is the moment it's safe. Syncing happens later, from an outbox.

The position row carries more than a percentage. Three fields do most of the work:

- `observed_at`: the schema comment says it best, "When the reading happened, not when it was saved." An upload that retries for an hour keeps the original time.
- `origin`: one of `user`, `restore`, `remote`, `linked_copy` or `manual`. Only user, remote and manual positions count as real reading. A position restored from a backup or copied from another format of the book is useful, but it's not evidence that you read there.
- `text_anchor`: about 20 words before and 30 after the position. The comment on that column is "Never sent to a server." It's the reader's text, and it stays on the device.

Each change also goes into a `sync_outbox` row with a `mutation_id` and the server revision it was based on.

---

## The server: compare-and-set, not last writer wins

On Parrot's own server (Supabase, Postgres), every book's position has a `revision`. A write says which revision it was based on, and the server function only accepts it if that's still the current one:

```sql
select revision, payload into current_revision_value, current_payload
from public.reading_positions where cloud_book_id = cloud_book_id_value
for update;
if current_revision_value is not null
    and (base_revision_value is null or base_revision_value <> current_revision_value)
then
    result := jsonb_build_object('mutation_id', mutation_id_value,
        'status', 'conflict', 'cloud_book_id', cloud_book_id_value,
        'revision', current_revision_value, 'payload', current_payload);
else
    new_revision_value := coalesce(current_revision_value, 0) + 1;
```

A rejected write gets the current position back, so the client can decide what to do without another round trip.

Writes are also idempotent. The server stores its first answer for each `mutation_id` and replays it if the same id comes again, so a retry after a dropped connection can't apply a change twice. That has a consequence I learned the hard way: an answer is final for its id. A write that was rejected once and then rebased onto the new revision must go out under a **new** mutation id, or the server just replays the old rejection.

---

## When to settle, when to ask

A conflict happens when the device and the server disagree about where you are. Parrot has three possible responses, decided by one small function:

```kotlin
if (kotlin.math.abs(localPercent - remotePercent) * 100.0 < POSITION_PERCENT_TOLERANCE) {
    return ConflictDecision.Silence
}
val localMillis = localPosition.epochObservedAtMillis() ?: return ConflictDecision.Ask
val remoteMillis = remotePosition.epochObservedAtMillis() ?: return ConflictDecision.Ask
...
return when {
    localNewer && localFurther -> ConflictDecision.KeepThis(remotePosition)
    remoteNewer && remoteFurther -> ConflictDecision.MoveToOther(localPosition)
    else -> ConflictDecision.Ask
}
```

- **Less than 1% apart:** say nothing.
- **One side is both newer and further:** that's the normal case of reading on one device and then picking up another. Parrot settles it and shows a bar with Undo, holding on to the position it didn't pick.
- **Anything else:** newer but behind, missing a timestamp, missing a percentage. Parrot asks.

Asking is the fallback on purpose. If you read further on your phone yesterday but went back to re-read a chapter on your tablet today, the app can't know which one you meant. You do. One tap is cheap; losing your place isn't.

---

## The device that conflicted with itself

The best bug in this area: a phone kept asking you to choose between two positions, and both had come from that phone.

The cause was the outbox. When the server accepted write A, the client moved the base revision forward only for the row it had just sent. Write B, already queued behind it, still carried the old base revision, so the server rejected it as a conflict, against A, from the same device.

The fix has three parts:

- When the server reports a conflict whose current position came from this same device, and the queued write is not older, it's not a conflict. The client rebases onto the server's revision and resends in the same push, under a new mutation id.
- An acknowledgement now moves the base revision of every queued write behind it.
- A one-time repair step reopens rows that were already stuck as conflicts.

A follow-up bug hid behind it. Some of those stored conflicts were less than 1% apart, so the decision function chose Silence, and the prompt never appeared. The conflict was preserved but never shown. Now any preserved conflict forces Ask.

---

## Signing in shouldn't move you backwards

Another bug: signing in pulled the server's position and replaced a newer one on the device. One report had a book drop from 9.6% to 4.8%. The pull only checked whether the device had unsent outbox rows for that account, and when it didn't, the server value won. Nothing compared reading times.

Now there's a guard: a remote position never replaces the person's own newer reading. The sync engine compares reading times here, but only for positions that were real reading on this device, never for restored or copied ones. I could fix the logic, but I couldn't prove what triggered it in the original report. I've written that down instead of pretending otherwise.

---

## One book, many copies

The harder half is moving a position between formats. If you listen to chapter 4 of the audiobook in the car, the ebook should offer to open at the same sentence. Parrot tries a series of strategies and stops at the first one that works:

```kotlin
val translated = sameFile(request)
    ?: textAnchor(request)
    ?: smil(request)
    ?: trackToText(request).takeIf { _ -> !target.isAudio }
    ?: proportional(request)
```

1. **Same file.** The content hash matches, so the position copies over as is. Confidence: exact.
2. **Text anchor.** Search the target for the words around the position. First an exact search on the normalized text, with the words before the position breaking ties; then a fuzzy Levenshtein match within 10% of where the percentage says it should be, which needs a score of at least 92. Confidence: high.
3. **Media overlays.** A read-along book already maps every sentence to a time in its audio. That map works as a bridge between text and audio. An audiobook from another server can use it only if its total duration matches within 1%. Confidence: high.
4. **Percentage.** The fallback, using chapters when both copies have the same number. Confidence: approximate.

Confidence decides what happens next. Only exact and high results from real reading are copied to other formats automatically, and they're written with origin `linked_copy`, so they never count as reading evidence. Approximate matches are shown in the positions panel unticked. Guards refuse any match that collapses to the very start or end of the book, which is what a failed match usually looks like.

Switching between copies always asks first. The reasoning I wrote down was "A wrong jump loses the reader's place. A prompt costs one tap." The clock rule follows from that too: another copy has to be newer by 60 seconds before Parrot even offers to move. A prompt is harmless when it's wrong; an override isn't.

---

## Small rules that held up

- **Device names are labels only.** Positions show which device they came from, but the name never takes part in comparisons. A rename must not create a conflict.
- **The device is recorded when you read, not when it uploads**, so a delayed upload can't be credited to the wrong device.
- **Choosing "use the server's position" doesn't post anything back.** "Keep this one" queues exactly one write on the revision you were shown. If someone read further in the meantime, the choice fails instead of overwriting newer reading.
- **Each backend gets honest limits.** Parrot's own server does compare-and-set. Storyteller rejects stale writes by timestamp. Audiobookshelf has no conditional writes at all, so there the client can't guarantee ordering, and I say so in the docs instead of claiming it can.

---

## What's not done

The app says "saved, syncing" but not yet "synced", because it doesn't show the server's acknowledgement. Two writes racing to create a position for a book that has none yet aren't locked against each other, since `FOR UPDATE` can't lock a row that doesn't exist. And a newer local position kept by the sign-in guard is only uploaded back for Parrot's own server, not for Storyteller or Audiobookshelf.

The lesson underneath all of it: a sync design is a list of things you won't do. Don't trust the clock alone, don't trust the biggest number, don't let a retry change history, and when you really can't tell, ask the person who knows.
