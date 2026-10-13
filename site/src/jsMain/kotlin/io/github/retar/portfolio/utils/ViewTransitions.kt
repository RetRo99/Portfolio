package io.github.retar.portfolio.utils

import com.varabyte.kobweb.navigation.Router
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLElement
import kotlin.js.json
import kotlin.math.hypot

/** Authored motion runs only when the visitor hasn't asked for reduced motion. */
fun motionAllowed(): Boolean = !window.matchMedia("(prefers-reduced-motion: reduce)").matches

private fun canViewTransition(): Boolean =
    document.asDynamic().startViewTransition != undefined && motionAllowed()

/**
 * Switches the theme so the new palette spreads out as a circle from ([x], [y]) (see editorial.css).
 * The browser pauses frames while it captures the change, so Compose can't re-render inside the
 * transition: the root class is swapped by hand, and [apply] updates the app state alongside it.
 * Returns false, after just calling [apply], when the switch can't be animated.
 */
fun spillTheme(x: Double, y: Double, toDark: Boolean, apply: () -> Unit): Boolean {
    val root = document.documentElement as? HTMLElement
    if (root == null || !canViewTransition()) {
        apply()
        return false
    }
    val radius = hypot(maxOf(x, window.innerWidth - x), maxOf(y, window.innerHeight - y))
    root.style.setProperty("--spill-x", "${x}px")
    root.style.setProperty("--spill-y", "${y}px")
    root.style.setProperty("--spill-r", "${radius}px")
    root.classList.add("theme-spill")
    val transition = document.asDynamic().startViewTransition {
        root.classList.remove(if (toDark) "silk-light" else "silk-dark")
        root.classList.add(if (toDark) "silk-dark" else "silk-light")
        apply()
    }
    val done: (dynamic) -> Unit = { root.classList.remove("theme-spill") }
    transition.finished.then(done, done)
    return true
}

/**
 * Opens a note so its [title] glides from the link into the article heading (a FLIP animation),
 * while the rest of the article rises in behind it. Without a title, or with reduced motion, it
 * just navigates.
 */
fun openNote(router: Router, route: String, title: HTMLElement?) {
    val from = title?.getBoundingClientRect()
    val fromSize = title?.let { fontSizeOf(it) }
    val oldHeading = document.querySelector(".prose-body h1")
    val oldText = oldHeading?.textContent
    router.navigateTo(route)
    if (from == null || fromSize == null || !motionAllowed()) return

    val started = window.performance.now()
    fun glide() {
        val heading = document.querySelector(".prose-body h1") as? HTMLElement
        if (heading == null || heading === oldHeading || heading.textContent == oldText) {
            if (window.performance.now() - started < 600) window.requestAnimationFrame { glide() }
            return
        }
        val toSize = fontSizeOf(heading) ?: return
        val to = heading.getBoundingClientRect()
        (heading.parentElement as? HTMLElement)?.classList?.add("carrying-title")
        val start = "translate(${from.left - to.left}px, ${from.top - to.top}px) scale(${fromSize / toSize})"
        heading.asDynamic().animate(
            arrayOf(
                json("transform" to start, "transformOrigin" to "0 0"),
                json("transform" to "none", "transformOrigin" to "0 0"),
            ),
            json("duration" to 480, "easing" to "cubic-bezier(0.23, 1, 0.32, 1)"),
        )
    }
    window.requestAnimationFrame { glide() }
}

private fun fontSizeOf(element: HTMLElement): Double? =
    window.getComputedStyle(element).fontSize.removeSuffix("px").toDoubleOrNull()?.takeIf { it > 0 }

/** True for a plain left click, the only kind a link should take over; modified clicks open tabs. */
fun isPlainClick(event: dynamic): Boolean =
    event.button == 0 && event.ctrlKey != true && event.metaKey != true && event.shiftKey != true && event.altKey != true
