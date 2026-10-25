---
routeOverride: "/blog/password-managers-compose-multiplatform-web"
title: "Password managers can't see a canvas: real sign-in forms in Compose for the web"
description: "Compose Multiplatform draws its text fields on a canvas, so browser password managers have nothing to fill or save. How Parrot slides a real HTML form under the canvas without changing how the fields look, from sign-in to new-password forms, and the focus fight, Tab order and font path it took to get there."
cover: "/articles/password_managers_web.jpeg"
date: "2026-10-25"
---
# Password managers can't see a canvas: real sign-in forms in Compose for the web

Parrot runs in a browser now, through Kotlin/Wasm and Compose Multiplatform ([the first note on that](/blog/android-app-in-the-browser-kotlin-wasm)). One of the first things people do in it is connect their own book server, like Audiobookshelf or Storyteller, with a username and a password. On a phone the system's autofill offers the saved login. In the browser nothing happened: no key icon in the field, no "Save password?" after signing in, nothing for 1Password or Bitwarden to fill.

This note is about why, and the small piece of shared Kotlin that fixed it for every sign-in form without making those forms look any different.

---

## There was no form

I opened the production build on the "Add server" screen and searched the page for anything a password manager could use. With no field focused there was no `input`, no `textarea` and no `form` element anywhere, shadow roots included.

That's how Compose works on the web. It draws the whole UI, text fields included, onto one canvas. When a field gets focus, Compose creates a hidden element to receive keyboard input, and in Parrot's build that element was this:

```html
<span contenteditable="true" class="compose-backing-field" autocomplete="off"></span>
```

It has no `type`, no `name`, it isn't in a form, and it disappears when the field loses focus. A password manager looks for a `<form>` with a username input and an `<input type="password">`, then watches for that form to be submitted. Parrot gave it neither.

Labels and accessibility hints don't help here. The password manager isn't reading the screen. It's reading the DOM, and the DOM had nothing in it.

---

## The idea: real inputs, Compose decoration

The obvious fix is an HTML form drawn over the Compose UI. That's also the obvious way to get it wrong. Parrot's text fields have their own look: the fill, the border, the focus outline, the error state, a "Show password" button, Day and Night themes. An HTML form on top would need all of that rebuilt in CSS, and it would cover every dialog, sheet and tooltip that Compose draws in the same place.

The reader had the same problem, and I'd already solved it there. The HTML goes **under** the canvas, and the canvas is cleared in the one spot where the HTML should show through. So the plan for sign-in forms was:

- Compose keeps drawing every field exactly as before: the box, the border, the outline, the error text.
- Where the field's **text** would be, it draws a transparent hole.
- A real `<input>` sits under that hole, in the same font, size and colours.

Only the text and the caret belong to the browser. Everything around them is still the same Compose code that runs on Android and iPhone.

---

## One shared API, three platforms

Screens shouldn't need to know any of this. So the piece lives in Parrot's shared UI module as an `expect`/`actual` pair:

```kotlin
@Composable
expect fun rememberCredentialForm(
    username: String,
    onUsernameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
): CredentialForm

@Composable
expect fun CredentialFormArea(form: CredentialForm, spacing: Dp = 0.dp, inPopup: Boolean = false, content: @Composable () -> Unit)
```

A screen wraps the rows that hold its two fields in `CredentialFormArea`, and its Sign in button calls `form.submit()` instead of signing in directly. That's the whole change on the screen's side.

On Android and iPhone both functions do nothing. The area just draws its content, and `submit()` calls the screen's sign-in:

```kotlin
actual fun CredentialFormArea(form: CredentialForm, spacing: Dp, inPopup: Boolean, content: @Composable () -> Unit) = content()
```

No reference image changed and no Android test was edited. The phones don't know the web version exists.

On the web, `CredentialFormArea` puts a `WebElementView` holding one `<form>` under the rows. The fields find that form through a `CompositionLocal`, and each one draws a `Slot` where its text used to go:

```kotlin
Spacer(
    modifier
        .onGloballyPositioned { state.coordinates = it; state.look?.invoke() }
        // The canvas is cleared here, so the input under it shows through.
        .drawBehind { drawRect(Color.Transparent, blendMode = BlendMode.Clear) },
)
```

Every time the slot moves, Kotlin measures where it is relative to the form, converts that to CSS pixels and tells the page where to put the input, along with the font size, text colour, caret colour and background. So the input follows the field through scrolling and resizing, and switches between Day and Night with the rest of the screen.

The form itself is as plain as I could make it, because plain is what password managers recognise:

```js
form.method = "post"; // never a GET: a submit that got through would put the password in the address
const inputs = {
    username: make("username", "text", "username"),
    password: make("password", "password", "current-password"),
};
```

Each input also gets the field's own label as its `aria-label`, and `aria-invalid` in the error state.

---

## Submitting without leaving the page

A password manager offers to save a login when it sees the form submitted. So Parrot's Sign in button can't just call a Kotlin function any more. It has to go through a real submit.

On the web, `form.submit()` calls `requestSubmit()`, which fires the browser's submit event. Parrot catches that event, cancels the navigation and runs the sign-in:

```js
form.addEventListener("submit", (event) => {
    event.preventDefault();
    onSubmit();
}, { capture: true });
```

Enter in either input does the same, so the button and the keyboard take one path. After a successful sign-in the screen closes and the form leaves the page, in that order, which is the sequence password managers watch for.

Values go both ways too. Typing, pasting and a password manager's fill all fire `input` or `change`, and those call the screen's setters. A password manager often fills a field **without** focusing it, so listening for focus or key presses alone would miss the fill. In the other direction, when the app sets a value (a saved server's username, for example), it's written into the input whenever the two differ.

---

## Where it fought back

The layout part worked on the first try. The parts after that didn't.

### Focus bounced between the two worlds

Compose and the browser each have their own idea of what's focused. My first version synced them both ways: Compose focus on the slot focused the input, and focus in the input focused the slot. But Compose's `WebElementView` also takes Compose focus when something inside it gets DOM focus. So the input focused the view, the view moved focus off the slot, the slot refocused the input, and around it went. Tab went nowhere.

The fix was to make the view never focusable and keep one direction as the rule. Compose focusing the slot focuses the input. When a click puts focus in the input, Compose is only asked to follow, so that its own Tab order carries on from the right place.

### Tab skipped the inputs

Parrot's web app has its own screen-reader layer, which also handles Tab by walking Compose's accessibility tree. The inputs aren't in that tree, because they sit under the canvas. So Tab went from the server address straight past both inputs.

Now the Tab order puts each visible input in by **position**: before the first stop that's below it, or to its right on the same row. A second bug turned up later. With a dialog open over the form, Tab could land on an input under the dialog's scrim. Inputs covered by a popup are now skipped, and a form that is itself inside a popup stays in that popup's Tab loop.

### Clicks had to get through the canvas

The canvas sits on top, so it receives every press. While Compose sees the pointer over a slot, and therefore knows nothing of its own is drawn there, it tells the page to let presses through to the input. Pass-through ends when the pointer leaves, a wheel turns, or the input is disabled or removed. That last case matters, because a disabled input may never send `pointerleave`, and without it the whole screen stopped taking clicks.

### The font loaded from the wrong address

The inputs use Figtree, the same font as the Compose fields, loaded from the app's resources. `Res.getUri` returned a path with the current route stuck in front, `/settings/servers./composeResources/...`, so the font failed and the inputs quietly fell back to the system font. The page script now keeps only the `composeResources/` part and resolves it against the script's own address.

---

## Signing up is a different form

The first version covered signing in to your own servers. Writing this note made me notice the forms it didn't cover: Parrot Cloud's sign-in, its "Create an account" screen, the page that sets a new password after a reset link, and the sign-in sheet for a preset catalogue like Standard Ebooks. So those got the same treatment, and the shared piece grew three options:

- **`emailUsername`**: Parrot Cloud signs in with an email, so the username input becomes `type="email"`.
- **`newPassword`**: on "Create an account" and the reset page, the password says `autocomplete="new-password"`. That's the signal for a password manager to offer a generated password instead of filling a saved one. Switching between Sign in and Create an account changes the attribute in place and keeps what was typed.
- **A repeat-password input** (`name="confirm-password"`, also `new-password`), so the reset page's "New password again" gets the same generated value.

The defaults keep the server forms exactly as they were, and on Android and iPhone the new parameters are accepted and ignored.

The Parrot Cloud screens don't use Parrot's own field component. They use Material 3's `OutlinedTextField`. So they got a small web-only field that draws Material's decoration (outline, label, colours, shape) around a slot instead of around Compose's own text. The first build drew those fields 4 dp short, with a doubled red line in the error state. The inner content wasn't being stretched to the decorator's minimum height, and `propagateMinConstraints` fixed it. The check compared screenshots of the new fields against the old ones in Day and Night, and that's how this got caught.

---

## Checking it without a password manager

I didn't want "it worked when I tried it" to be the test, so there's a script that drives a production build in a headless browser. It checks seven things on the four server and catalogue sign-in forms. The Parrot Cloud forms get the first five:

1. Each form has both inputs with the right `name`, `type`, `autocomplete` and `aria-label`.
2. A fill done the way a password manager does it, with values set, events fired and no focus, enables Sign in, and the sign-in reaches a test Audiobookshelf server.
3. Enter in the password signs in, and the page neither reloads nor navigates.
4. Tab and Shift+Tab visit address, username, password and Show password in order, with no trap.
5. Day and Night, empty, focused and error states all match, down to the computed caret colour.
6. With a dialog open over the form, Tab stays inside the dialog.
7. A disabled or removed input gives the clicks back to the page.

Every new check was written to fail on the old code first, and all of them did.

One detail of the setup is worth knowing. Self-hosted servers like Audiobookshelf don't send CORS headers, so a web page on another address can't read their answers. The check puts a small local proxy in front of the test server that adds those headers and nothing else. That's the same thing Parrot's web app tells self-hosters to do.

---

## Honest status

Every sign-in form in the web app now uses it: your own servers, catalogues, preset catalogue sheets, and Parrot Cloud's sign-in, account creation and password reset. Some things are still open:

- **No real password manager in the automated checks.** The script fills fields the way one does, but that isn't the same as running 1Password, Bitwarden or a browser's own manager. Trying them by hand is the next step.
- **The text looks slightly different.** The browser draws typed text and password dots a little heavier than Compose's canvas did, in the same font and size. That's browser text rendering, and I've left it.
- **It leans on Compose internals.** Finding the canvas depends on how Compose 1.13 nests its HTML layer, the same shortcut the reader takes. The code marks it, and it's the first thing to recheck after a Compose update.

What surprised me is how little of this was about passwords. The form itself is about ten lines of HTML. Everything else came from two UI systems sharing one screen: whose focus it is, whose click it is, whose Tab it is. Compose on the web is a canvas pretending to be a page, and a password manager is one of the few visitors that can tell.
