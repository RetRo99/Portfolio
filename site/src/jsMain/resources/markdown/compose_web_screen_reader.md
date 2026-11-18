---
routeOverride: "/blog/compose-multiplatform-web-screen-reader"
title: "A canvas has no buttons: screen readers and Compose for the web"
description: "Compose Multiplatform draws a web app onto one canvas, so a screen reader finds nothing to read. How Parrot's Kotlin/Wasm app got a usable accessibility tree: Compose's hidden semantics DOM, a layer bug fixed by moving to an alpha, browser focus instead of canvas focus, checked and selected states read from Compose internals, and spoken page turns."
date: "2026-11-18"
cover: "/articles/compose_web_screen_reader.jpeg"
---
# A canvas has no buttons: screen readers and Compose for the web

Parrot is an ebook and audiobook reader, and it runs in a browser through Kotlin/Wasm and Compose Multiplatform ([how that started](/blog/android-app-in-the-browser-kotlin-wasm)). In [the last web note](/blog/password-managers-compose-multiplatform-web) the problem was password managers: Compose draws everything onto one canvas, so there was no form for them to fill. A screen reader has the same problem, only bigger. A password manager needs two inputs. A screen reader needs every button, every tab, every switch and some idea of what just changed.

On Android and iPhone, Parrot already works with TalkBack and VoiceOver because Compose hands its semantics tree to the platform. This note is about getting that same tree to mean something in a browser.

---

## One flag, half a tree

With the default setup, the page Parrot served had a canvas and nothing else a screen reader could use. Compose for the web has an accessibility bridge, but it's off unless you ask for it:

```kotlin
ComposeViewport(viewportContainerId = "root", configure = { isA11YEnabled = true }) {
    ...
}
```

With the flag on, Compose keeps a hidden DOM tree in the viewport's shadow root (`#cmp_a11y_root`), one element per semantics node, placed over the spot where that node is drawn. Each element gets a role, an `aria-label` from `contentDescription`, and its text. A DOM click on an element runs the node's `onClick`, so a screen reader's "activate" works. On the library screen, the browser's accessibility tree went from one canvas to this:

```
button "Books"
button "Browse"
button "Statistics"
button "Settings"
button "Add books"
button "Sort, currently Title, A → Z"
button "Filters, none active"
button "Pride and Prejudice"
textbox "Search your library"
button "List"
button "Grid"
button "Add to favorites"
```

Those names came for free, because the shared Compose code already had them for TalkBack. That part was a good day.

Reading the Compose 1.12.1 source showed what the bridge didn't do yet:

- **No focus.** No element had a `tabindex`, so Tab couldn't land on anything a screen reader would hear.
- **No state.** A switch had no `aria-checked` and a tab had no `aria-selected`. "List" and "Grid" both read as unchecked, including the one that was chosen.
- **No live regions.** Turning a page changed the canvas and nothing else.
- **One layer at a time.** That one turned out to be the worst.

---

## The menu that never closed

In Compose, a dropdown menu or a bottom sheet is its own layer with its own semantics owner. The 1.12.1 web bridge only showed the most recent one. When a menu closed, its owner was removed, the bridge set its current owner to null, and nothing redrew. The tree kept showing the closed menu's buttons until some other layer opened.

I saw it three times in the browser. Open the library's Sort menu, pick "Author", and the menu goes away on screen. In the accessibility tree, "Title", "Author" and the rest were still there, and the library under them was gone. A screen reader user would hear the menu's options, press one, and nothing would happen. The Listening sheet did the same thing.

That isn't something to patch around from app code. Compose 1.13.0-alpha01 had already rewritten that part: it keeps a list of owners, marks a modal one, and makes the layers under it inert. So the fix was one line in `libs.versions.toml`, and the real question was whether to put an alpha under an app that ships on phones. I ran the full host test suite (3,542 tests) on the alpha, built the apps and reran the web checks. Nothing failed that hadn't failed before, the closed layer left the tree, and the bump stayed.

---

## A Tab nobody heard

Compose has its own keyboard focus, and on the web Tab does move it: a focus ring appears on the canvas. But that focus lives inside Compose. The browser's focus is still on the canvas, so a screen reader announces nothing.

The fix was to make the browser's focus the only one. A small script finds Compose's tree once it exists and gives every pressable element a `tabindex`:

```js
const PRESSABLE = new Set(["button", "tab", "checkbox", "switch", "radio", "menu"]);
...
if (PRESSABLE.has(el.getAttribute("role"))) { if (!el.hasAttribute("tabindex")) el.tabIndex = 0; }
```

After that it's mostly bookkeeping:

- **Tab and Shift+Tab** move through the tree's elements in semantics order. The event is stopped before Compose sees it, so its own focus doesn't move in parallel, invisibly.
- **Enter and Space** click the focused element, which runs the same `onClick` a tap would.
- **Escape** is the odd one. Compose doesn't read keys from the tree's elements, only from the canvas. So an Escape on a tree element is given again on the canvas as a synthetic `KeyboardEvent`, where Compose closes the menu or sheet, and then focus goes back to the tree.
- **A visible outline** on the focused element, since Compose's own focus ring no longer follows.

Compose's text fields needed care. When one takes focus, Compose creates its own hidden input for the keyboard, which takes focus away from the textbox element in the tree. Tab from that input has to continue from the textbox it belongs to, or the field keeps grabbing focus back.

---

## Checked or not

The missing states were the part I liked least, because the information was right there. The shared code sets `selected` on tabs and `toggleableState` on switches and radios, and TalkBack reads them. The web bridge just never wrote them to the DOM.

So `AriaStates.kt` listens to Compose's semantics and writes the attributes itself. It wraps the app's content once, registers a `SemanticsListener` on the window's semantics owner, and after each burst of changes walks the nodes and sets `aria-selected`, `aria-checked`, slider and progress values, and `aria-live`.

Two details make it work. First, popups. Menus and sheets create layers with their own owners, so the scene context is wrapped to catch every layer as it's created:

```kotlin
private class WatchedSceneContext(
    private val base: ComposeSceneContext,
    private val bridge: AriaStateBridge,
) : ComposeSceneContext by base {
    override fun createLayer(
        density: Density,
        layoutDirection: LayoutDirection,
        focusable: Boolean,
        consumePointerInputOutside: Boolean,
    ): ComposeSceneLayer =
        WatchedLayer(base.createLayer(density, layoutDirection, focusable, consumePointerInputOutside), bridge)
}
```

Each layer reports its owner while it's on screen. That also tells the bridge which element had focus when a popup opened, so closing it gives focus back to the button that opened it.

Second, matching. Compose keeps its node-to-element map private, so an element is matched to its semantics node by role and position: the node's bounds in CSS pixels against the element's place over the canvas, within two pixels. Two controls of the same role in exactly the same spot would share a state. None do today.

None of this is public API. The file starts with `@file:Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")` and a comment saying to recheck it on every Compose upgrade and delete it once Compose writes these states itself. It's a shortcut, and it's marked as one.

---

## Saying what changed

A screen reader on a phone hears Parrot's page turns because the reader announces them. In the browser, the reader is an HTML page under the canvas, and Compose's `liveRegion` modifier doesn't reach the DOM in 1.12.1 or 1.13.0-alpha01. So announcements go through two hidden live regions made at startup: `role="status"` for events and `role="alert"` for errors.

They're made before anything is said. A region created and filled in the same moment can be missed. And the same text twice in a row is no change at all to a screen reader, so every announcement clears the region first and sets the text a frame later:

```js
export function announce(text, isUrgent = false) {
    const target = isUrgent ? urgent : polite;
    target.textContent = "";
    setTimeout(() => { target.textContent = text; }, 50);
}
```

The events themselves were already there. The web reader reports every book opened, page turn and play or pause to the browser's Media Session (that's what puts the book on the lock screen). The screen reader script wraps that object instead of adding new hooks, so it says "Opened Pride and Prejudice", "Page 4 of 22" in the reader footer's own words, the chapter's name when the chapter changes, and "Read aloud started" or "paused". Only the last page of a quick burst of turns is spoken. The wrapper also catches its own errors, because an announcement must never break playback.

---

## Checking it without a screen reader

The checks don't use a screen reader. They read what a screen reader would be given: a script drives a headless browser over DevTools and reads the browser's own accessibility tree, focus and properties, with real key events. One command runs the keyboard path through the library, its Sort menu and the Filters sheet:

```
11: menu "Sort, currently Title, A → Z" focusable=true focused=true
Enter -> radio "Title" focusable=true focused=true checked=true
1: radio "Author" focusable=true focused=true checked=false
Escape -> menu "Sort, currently Title, A → Z" focusable=true focused=true
1: button "Filters, none active" focusable=true focused=true
Enter -> button "Reset" focusable=true focused=true
Escape -> button "Filters, none active" focusable=true focused=true
keyboard: all passed
```

On the code from before these fixes it stopped at the first line it could: `FAILED: no tab is selected`. It passes at a wide desktop width and at 800 px, where the layout changes.

The same audit turned up problems that had nothing to do with Compose. At 800 px, Tab from the navigation rail landed in the search field, and focus alone opened search mode, which removed the shelf controls you were tabbing towards. On a phone a TalkBack double-tap only focuses a field, so focus opening search was fine there. On the web it now takes a press or typed text.

---

## Honest status

The library, its menus and sheets, and the reader's controls are reachable by keyboard, named, and report their state. Some things are still open:

- **No real screen reader yet.** Everything above was checked through the browser's accessibility tree, which is what VoiceOver and NVDA read, but that isn't the same as listening to them. Whether page announcements cut into the reader's own speech, and how browse mode and Tab agree, need a person with VoiceOver and NVDA.
- **Tab order on the book page is wrong.** Material's `Scaffold` puts the body before the top bar, so Tab reaches "Read" before "Back". The web ignores `traversalIndex`, so the fix is moving the top bar out of the `Scaffold`, which changes the layout on every platform. Not done yet.
- **Announcements are in English.** The texts live in the web glue, not in the shared strings, and the page number is read from the footer's English text.
- **It leans on Compose internals.** Scene layers, semantics listeners, the shadow root's element ids. All marked, all first in line to break on an upgrade.

The part I didn't expect was how much was already done. The names, the roles and the click handling all came from semantics written for TalkBack, and Compose's web bridge passed them on. What was missing was the browser's half of the deal: focus, state and change. A canvas can look like any app you want. A screen reader, like a password manager, only reads the DOM, and on Compose for the web you have to make sure there's a DOM worth reading.
