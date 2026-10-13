---
routeOverride: "/blog/opds-catalogue-client-kotlin-multiplatform"
title: "Every catalogue is hostile: an OPDS client in Kotlin Multiplatform"
description: "Building Parrot's OPDS catalogue browser in shared Kotlin: parsing two feed formats, keeping passwords on their origin, and the nine bugs a security review found."
date: "2026-10-13"
cover: "/articles/opds_catalogue.jpeg"
---
# Every catalogue is hostile: an OPDS client in Kotlin Multiplatform

OPDS is how ebook servers publish their catalogues. Project Gutenberg has one, and so do self-hosted servers like Calibre, Kavita and Komga. A client that speaks OPDS can browse any of them, search them and download books. I wanted that in Parrot, so people can fill their library from the servers they already use.

The protocol is simple. The hard part is everything around it: Parrot fetches XML and JSON from servers chosen by the user, follows links those servers choose, sends a password to some of them, and writes files they provide to disk. Each of those is a place where a catalogue can do something you didn't intend. So I designed the client as if every catalogue were hostile, and then had it reviewed as if it were.

---

## Two formats, one model

There are two OPDS versions in the wild. OPDS 1.x is Atom XML and is what almost every server speaks today. OPDS 2 is JSON. Both are parsed in common Kotlin into one model, so the screens never know which one they're showing:

- **OPDS 1** uses xmlutil's streaming reader with a small hand-written walker, rather than a DOM or a serializer, so the parser can count and stop at any point.
- **OPDS 2** uses `kotlinx.serialization`'s JSON tree, with a depth check and an item count check before anything is read.

The feed type is decided by media type plus structure, never by "try one parser, and if it throws, try the other". An HTML error page, an RSS feed or an authentication document is rejected as what it is, with a matching message, instead of failing somewhere deep inside a parser.

Some plumbing had no maintained Kotlin Multiplatform library, so I wrote it: an RFC 3986 resolver for relative links, an RFC 6570 subset for OpenSearch templates (`{var}`, `{+var}`, `{?list}`, `{&list}`, and an explicit error for anything else), and `xml:base` handling, because the XML library doesn't do it. One detail mattered more than expected: relative links resolve against the **final URL after redirects**, not the URL you asked for or the feed's `self` link. Get that wrong and covers and next pages point at the wrong server.

---

## The parser stops before the entities

xmlutil accepts internal DTDs and doesn't limit entity expansion. That's the classic "billion laughs" setup: a few hundred bytes of XML that expand to gigabytes in memory. No OPDS feed needs a DTD, so the parser refuses any document that declares one, before a single entity can be read:

```kotlin
when (val typeName = reader.next().name) {
    "DOCDECL", "DTD" -> {
        // Phase 1 REQUIRED mitigation: stop before any entity
        // text can be read (Phase 0 record).
        throw StructuralAbort(OpdsRejection.DocumentTypeDeclarationRejected())
    }
```

Everything else is bounded by one budget object:

| Limit | Value |
|---|---|
| Feed or description response | 5 MiB |
| Nesting depth | 64 |
| Items per page | 2,000 |
| Redirects | 5 |
| A downloaded book | 512 MiB |
| Pages saved for offline use | 25 MiB |

Nesting is counted without recursion, so a feed nested 100,000 levels deep gets a clean rejection instead of a stack overflow. That's a test, not a guess.

---

## A password never leaves its origin

Parrot supports HTTP Basic sign-in, and that's a password sent with every request. The rule: credentials go out only if the request's scheme, host and port all match the catalogue's, the scheme is https, and no earlier redirect in the chain has left that origin.

To make that rule enforceable, each catalogue gets its own HTTP client with automatic redirects turned off, no auth plugin, no cookies and no shared default headers:

```kotlin
/** One isolated client per source, owning its engine. No auth/cookie/logging plugins or shared default headers. */
class KtorOpdsTransport(
    private val engine: HttpClientEngine,
    private val catalogueRoot: String,
    ...
    private val client = HttpClient(engine) {
        followRedirects = false
        expectSuccess = false
        install(HttpTimeout) { requestTimeoutMillis = 30_000; connectTimeoutMillis = 15_000; socketTimeoutMillis = 30_000 }
    }
```

Redirects are followed by hand, at most five hops, with loop detection. https to http is refused, and so are URLs with a user name or password embedded in them. Because every hop goes through code I wrote, the `Authorization` header is attached per request, and only when that hop passes the origin check. With a library's redirect handling, I'd have had to trust its rules for when to drop the header.

There's a product side effect. A password-protected Calibre server on a home network uses Digest sign-in over plain http. Parrot won't send a password over http, so that setup isn't supported. I'd rather say "not supported" than quietly send a password in the clear.

---

## What the security review found

When the client worked, I ran a dedicated security pass over it: transport, parser, downloads, images, logs. It found nine real bugs. A few of them are worth knowing about for any client that fetches URLs on someone else's behalf:

- **The catalogue could reach your home network.** A catalogue on the internet could list a cover image at `http://192.168.1.1/...`, and Parrot would fetch it. That's server-side request forgery, from a phone. Requests from a public catalogue to local addresses, including through a redirect, are now refused. The known gap: host names aren't resolved first, so a public name that points at a private address isn't caught yet.
- **A missing host became `localhost`.** An address like `https:///feed` was requested as `https://localhost/feed`. It is now refused.
- **A download could be bigger than it said.** A server that sent more bytes than its `Content-Length` got the whole file written before the size check failed. Now the stream stops at the declared length and writes none of the excess:

```kotlin
if (count + read > maxBytes) { exceeded = true; break }
// More than was declared is not the file that was offered: stop, write none of it.
if (declared != null && count + read > declared) { overrun = true; break }
```

- **Endless paging.** A feed whose `next` link pointed back at itself loaded forever. Lists now end when the next link loops, and auto-loading stops after three pages in a row with no books.

The others were smaller: relative cover addresses that were never resolved, a stale sign-in status, a preset's search address surviving an edit of its server address, and an exception message that could carry a URL into a log. The transport now logs a fixed set of words and never an address, because for some servers (Kavita puts an API key in the URL path) the address is the secret.

---

## Downloads are checked by their bytes

A downloaded book streams to a staging file in 64 KiB chunks, with free space checked before the download and again every 4 MiB. The file name is a random UUID. Nothing the server sends, not the title, not `Content-Disposition`, ever becomes part of a path.

Then the file is identified by its bytes, never by the server's content type or the URL's extension. The EPUB check refuses ZIP entries whose paths escape the book's folder, caps entry count and size, and detects DRM: encrypted entries, or a `META-INF/encryption.xml` that encrypts content, mark the book as protected. Font obfuscation alone still counts as a normal book. Protected books are refused with an explanation, because Parrot can't open them, and an import that fails later is worse than one that says no up front. Links that are clearly not free downloads (buy, borrow, subscribe, sample, LCP or Adobe DRM) are shown as a link to the provider instead of a download button.

---

## The iOS surprise: a cache you didn't ask for

On iOS, Ktor uses the Darwin engine, which uses `NSURLSession`, which by default uses the shared `NSURLCache`. That means feed pages, search queries and whole book files were being written to `Library/Caches` by the operating system, outside every limit above, with nothing in the shared code to show it. The fix is a small `expect`/`actual` seam that wraps the Darwin engine and switches the cache off last, so no caller configuration can turn it back on:

```kotlin
private object CatalogueDarwinEngines : HttpClientEngineFactory<DarwinClientEngineConfig> {
    override fun create(block: DarwinClientEngineConfig.() -> Unit): HttpClientEngine =
        Darwin.create {
            block()
            // Appended last, so nothing a caller configured can put the cache back.
            configureSession {
                URLCache = null
                requestCachePolicy = NSURLRequestReloadIgnoringLocalCacheData
            }
        }
}
```

The test for it was committed failing first. If you use Ktor on iOS for anything sensitive, check what `NSURLCache` is doing for you.

---

## Honest status

Shipping a client for "any OPDS server" means being honest about which servers you've actually tried. So far that's Project Gutenberg, on Android and in the iOS Simulator, plus a local test server that can misbehave on purpose. Calibre, Calibre-Web, Kavita, Komga and Standard Ebooks are still untested on a device, and OPDS 2 has only been tested against fixtures. Those runs come before the catalogue browser is called finished.

Gutenberg alone produced two surprises. Its search description advertises an http-only address, which Parrot rightly refuses, so the preset carries an https search address of its own. And it recently started sending `Vary: Accept-Language`, which Parrot's offline cache treats as "don't store", so Gutenberg pages are no longer saved for offline reading. Real servers will keep doing things the spec didn't plan for. The budgets and the origin rule are what make that survivable.
