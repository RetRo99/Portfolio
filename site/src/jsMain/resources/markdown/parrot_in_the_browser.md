---
routeOverride: "/blog/android-app-in-the-browser-kotlin-wasm"
title: "My Android app in a browser: Parrot on Kotlin/Wasm"
description: "How Parrot's shared Kotlin code went from phones to the browser: 57 of 59 modules compiling with two build files changed, a SQLite database saved into IndexedDB, Readium under a Compose canvas, and what the first screen costs to download."
date: "2026-10-16"
cover: "/articles/kotlin_wasm_browser.jpeg"
---
# My Android app in a browser: Parrot on Kotlin/Wasm

Parrot is an ebook and audiobook reader for Android and iPhone. Almost all of it is shared Kotlin: the database, sync, the reader logic and every screen in Compose Multiplatform. Compose also runs in a browser through Kotlin/Wasm, so I wanted to know how much of Parrot would follow it there, and how much existing code would have to change.

The first answer came the same day. **57 of the 59 shared modules compiled for the browser, the reader and every screen module included, and the only existing files that changed were two Gradle files.** Getting from "compiles" to "you can read a book in it" took a lot longer, and that's what this note is about.

---

## One flag, no module changes

I didn't want the web experiment to touch the Android or iPhone builds at all. So the browser target is added from the root build file, only when Gradle is run with `-Pweb`:

```kotlin
// Web spike: -Pweb adds a browser target to every multiplatform module, so no module's own
// build file changes and Android and iPhone builds never see it.
if (providers.gradleProperty("web").isPresent) {
    // Their shared code needs Firebase, which has no browser build; the web app wires its own.
    val notOnWeb = setOf(":composeApp", ":lib:analytics:implementation")
    ...
    subprojects {
        pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
            extensions.configure<KotlinMultiplatformExtension> {
                wasmJs { browser() }
```

Without the flag nothing changes, and the Android test suite (3,513 tests at the time) ran the same before and after.

The two modules left out explain themselves. The analytics module's shared code uses Firebase, which has no browser build, and the app's root module depends on it. The web app got its own analytics module instead, reusing seven of the nine shared files and replacing the two that touch Firebase.

---

## Shims for what a browser doesn't have

Shared code that compiles for Android and iOS still assumes a few things a browser lacks. Four of them came up: `Dispatchers.IO`, `runBlocking`, `FileSystem.SYSTEM` from Okio, and `Map.putIfAbsent`. A small browser-only module provides stand-ins, so the shared code resolves them without being edited:

```kotlin
// shortcut: kotlinx.coroutines has no Dispatchers.IO for the browser (one thread), so shared code's
// `import kotlinx.coroutines.IO` resolves here. Delete if the library adds its own.
val Dispatchers.IO: CoroutineDispatcher get() = Dispatchers.Default

// shortcut: a browser cannot block. This only finishes work that never really waits; the four shared
// callers need a real change (make them suspend) before the web app can rely on them.
fun <T> runBlocking(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> T,
): T {
    var result: Result<T>? = null
    block.startCoroutine(CoroutineScope(context), Continuation(context) { result = it })
    return (result ?: error("runBlocking cannot wait in the browser")).getOrThrow()
}
```

One detail cost time: these stand-ins have to live in a **separate** module. Declared inside the same module as the shared code that uses them, they're invisible to it. As a dependency of every module, they work.

The biggest saving came from the iPhone port. On iOS, the reader is drawn by native code behind a Kotlin interface: 46 functions that the Swift side implements. Nineteen iPhone source files (1,476 lines), including the reader controller, the audio controller and the download manager, compiled for the browser unchanged, and the bridge interface needed one type changed. The web reader implements the same bridge that Swift does.

---

## What "it compiles" doesn't tell you

Getting the real app to start showed the problems the compiler couldn't see:

- **Five services registered only in platform folders**: file import, EPUB metadata, the transfer file store, catalogue staging files and font import. The phones register them in Android or iOS code, so nothing in shared code says a browser needs them. Each one showed up as a crash at start.
- **Two classes with the same short name.** On the web, the dependency injection library told classes apart by their short name, so the settings module's `LogoutUseCase` clashed with the auth module's. Renaming one was the first edit to shared code, and it's the kind of thing a real port needs a check for.

By the end of that first day, the app's own root started in a browser, the home screen and Settings opened, and a sample book could be opened and paged through.

---

## A database without blocking calls

Parrot's database is SQLDelight on SQLite. The browser drivers for SQLDelight are asynchronous, and the app isn't. I measured what switching would mean: turning on SQLDelight's asynchronous mode gave 43 compile errors in 10 files in the database module alone, plus 267 blocking read calls that would each need to change.

So the web app runs SQLite synchronously in the page instead, through [sql.js](https://github.com/sql-js/sql.js), SQLite compiled to WebAssembly. The app's blocking calls work as they are. The cost is persistence: sql.js keeps the database in memory, so the web app saves the whole database into IndexedDB as bytes:

- one second after a write, or after the outermost transaction ends (a burst of writes is one save),
- at once when the page is hidden or closed,
- never inside a transaction.

In the persistence test the database was 573,440 bytes, and a save took about 2 ms. A page turned 0.15 seconds before a reload was kept.

The web database opens through the same shared code as the phones, with the same rules for migrating an old version and setting aside one it can't read. I tested the second case by rewriting the saved bytes to claim version 99. The app logged "Saved database is version 99, the app is at 45", kept the old bytes under a recovery key and started a fresh database, the same as a phone would.

---

## The reader: an HTML page under a Compose canvas

Readium, the EPUB engine Parrot uses on the phones, has a web navigator. The web reader hosts it behind the same 46-function bridge. Wiring the basics (open, turn pages, go to a chapter, report positions, taps) was quick. Drawing Compose on top of it wasn't.

On the web, Compose draws to a canvas, and an embedded HTML element is always drawn **above** that canvas and takes every click in its area. On the phones, the reader's bars, sheets and dialogs are drawn over the page. In the browser, they'd sit hidden behind it.

The first fix cut holes in the page wherever a bar was. It worked, but every floating thing in shared code would have had to report where it was. The second approach copied iOS: put the page **under** the canvas and clear the canvas where the page should show through. Everything Compose draws lands on top, with nothing handled per element.

That broke text selection and links, because the page no longer got any clicks. The version Parrot uses now lets presses fall through the canvas to the page, then copies each press back to Compose first. If a Compose control is under the pointer, Compose takes the whole gesture. Otherwise the page keeps it, and selection, links and long presses on pictures work natively.

It needed no change to shared code. The cost is that it relies on Compose internals in three places: how its HTML layer and canvas are nested, which event fields it reads, and that it handles a press before the event call returns. A Compose update can break any of them, and the code says so where it does it.

---

## What the browser downloads

On the release build served with Brotli compression, the first screen is **6.9 MB** across 27 files. Almost all of that is two WebAssembly files: the app (3.0 MB) and Skia, the graphics engine Compose draws with (2.6 MB). On a throttled 10 Mbit/s connection, that's 5 seconds to the first screen, 4.5 of them spent on the two `.wasm` files. A second visit takes about 0.25 seconds.

Without compression on the server, the same first screen is about 27 MB. Compression is the single biggest thing in the whole setup.

After the first screen, a service worker downloads the rest of the build in the background (17 MB compressed, including a 22 MB English dictionary that compresses to 3.7 MB). From then on, the app starts and reads the books already in the browser with no network, and the browser offers to install it.

---

## Honest status

The web version is merged into the main codebase and being prepared to go live. Some things are still open:

- **Your own servers.** A web page may only read another server's answer if that server allows it, and self-hosted book servers like Storyteller and Audiobookshelf don't. The phones have no such rule. Parrot Cloud works in the browser; your own servers need a browser extension, which is a story for another note.
- **Browsers.** Most checks ran in Brave and Chrome. Safari and Firefox have been tested for the book-safety checks, not for everything.
- **Fragility.** The reader's layering depends on Compose internals, and the in-page SQLite database depends on saving the whole file. Both are fine at today's sizes and both are marked as shortcuts in the code.

The thing I didn't expect: the hardest parts weren't Kotlin at all. They were the browser's own rules about blocking, layering and who may read what, and none of them showed up until something ran.
