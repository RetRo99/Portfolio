package io.github.retar.portfolio.components.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.varabyte.kobweb.compose.css.functions.blur
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.modifiers.backdropFilter
import com.varabyte.kobweb.compose.ui.modifiers.borderBottom
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.attr
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.components.footer.Footer
import io.github.retar.portfolio.components.header.NavHeader
import io.github.retar.portfolio.resources.StringRes
import io.github.retar.portfolio.styles.sitePalette
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Text
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgba
import org.jetbrains.compose.web.css.vh
import org.w3c.dom.HTMLElement
import kotlin.js.json

val PageContentStyle = CssStyle {
    base {
        Modifier.styleModifier {
            property("width", "100%")
            property("max-width", "1200px")
            property("padding-inline", "clamp(1rem, 4vw, 2.5rem)")
            property("box-sizing", "border-box")
        }
    }
}

const val Header_domId = "header"
const val Main_domId = "main"
val NavHeaderStyle = CssStyle {
    base {
        Modifier
            .borderBottom(1.px, LineStyle.Solid, sitePalette().headerBorder)
            .backdropFilter(blur(15.px))
            .styleModifier {
                property("background-color", "color-mix(in srgb, ${sitePalette().background} 84%, transparent)")
            }
    }
}

@Layout
@Composable
fun PageLayout(content: @Composable () -> Unit) {
    val ctx = rememberPageContext()
    val currentPath = ctx.route.path

    DisposableEffect(currentPath) {
        val isPush = ScrollHistory.consumePushFlag()
        val saved = if (isPush) null else ScrollHistory.restore(currentPath)
        val target = saved ?: 0.0

        val html = document.documentElement as? HTMLElement
        val style = html?.style?.asDynamic()
        val previousBehavior = style?.scrollBehavior as? String
        style?.scrollBehavior = "auto"
        html?.scrollTop = target

        // A link to a section ("/#contact" from /parrot, or a shared retar.app/#notes) lands on that
        // section, unless going back to a page whose scroll position we remember.
        val anchor = window.location.hash.removePrefix("#").takeIf { it.isNotEmpty() && saved == null }

        window.requestAnimationFrame {
            val section = anchor?.let { document.getElementById(it) }
            if (section != null) section.scrollIntoView() else html?.scrollTop = target
            style?.scrollBehavior = previousBehavior ?: ""
        }

        onDispose {
            ScrollHistory.save(currentPath, window.scrollY)
        }
    }

    // Browsers with scroll-driven animations fill the reading bar in CSS (editorial.css);
    // the rest get a rAF-throttled listener that sets the transform directly, without recomposing.
    DisposableEffect(Unit) {
        val scrollDriven = window.asDynamic().CSS?.supports("animation-timeline: scroll()") == true
        var frame = 0
        val onScroll: (org.w3c.dom.events.Event) -> Unit = { _ ->
            if (frame == 0) {
                frame = window.requestAnimationFrame {
                    frame = 0
                    val root = document.documentElement
                    val maxScroll = ((root?.scrollHeight ?: 0) - (root?.clientHeight ?: 0)).toDouble()
                    val progress = if (maxScroll > 0) (window.scrollY / maxScroll).coerceIn(0.0, 1.0) else 0.0
                    (document.querySelector(".scroll-progress") as? HTMLElement)
                        ?.style?.transform = "scaleX($progress)"
                }
            }
        }
        if (!scrollDriven) {
            window.addEventListener("scroll", onScroll)
            onScroll(org.w3c.dom.events.Event("scroll"))
        }
        onDispose { window.removeEventListener("scroll", onScroll) }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .minHeight(100.vh),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        A(href = "#$Main_domId", attrs = { classes("skip-link") }) {
            Text(StringRes.SkipToContent.value)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.px)
                .position(Position.Fixed)
                .top(0.px)
                .zIndex(2)
        ) {
            Box(
                modifier = Modifier
                    .classNames("scroll-progress", "scroll-linked")
                    .height(2.px)
                    .fillMaxWidth()
                    .backgroundColor(sitePalette().accent),
            )
        }

        Box(
            modifier = NavHeaderStyle
                .toModifier()
                .id(Header_domId)
                .fillMaxWidth()
                .position(Position.Sticky)
                .top(0.px)
                .zIndex(1)
        ) {
            NavHeader()
        }

        Box(
            modifier = PageContentStyle.toModifier()
                .weight(1f)
                .id(Main_domId)
                .attr("role", "main")
                .attr("tabindex", "-1"),
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
        Footer()
    }
}

private external interface ScrollStore {
    var positions: dynamic
}

private object ScrollHistory {
    private val window: dynamic get() = kotlinx.browser.window.asDynamic()

    fun consumePushFlag(): Boolean {
        val isPush = window.__portfolioNav === "push"
        window.__portfolioNav = null
        return isPush
    }

    fun save(path: String, scrollY: Double) {
        if (window.__portfolioScroll == null) {
            window.__portfolioScroll = json("positions" to json())
        }
        window.__portfolioScroll.positions[path] = scrollY
    }

    fun restore(path: String): Double? {
        val store = window.__portfolioScroll as? ScrollStore ?: return null
        return store.positions[path] as? Double
    }
}

