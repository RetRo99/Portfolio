package io.github.retar.portfolio.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.AnimationIterationCount
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.scale
import com.varabyte.kobweb.compose.ui.modifiers.setVariable
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.navigation.OpenLinkStrategy
import com.varabyte.kobweb.navigation.open
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.forms.ButtonSize
import com.varabyte.kobweb.silk.components.forms.ButtonVars
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.animation.toAnimation
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.selectors.active
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.styles.DescriptorStyle
import io.github.retar.portfolio.styles.sitePalette
import io.github.retar.portfolio.utils.trackEvent
import kotlinx.browser.window
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgba
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Text
import com.varabyte.kobweb.compose.ui.toAttrs
import kotlin.js.json

/**
 * Buttons are real links, so they can be opened in a new tab, copied or saved like any other link.
 * Internal paths go through the client-side router; files and mailto links load directly.
 */
@Composable
private fun ButtonLink(
    url: String,
    label: String,
    style: Modifier,
    modifier: Modifier,
    download: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    val linkModifier = ButtonLinkStyle.toModifier()
        .then(style)
        .then(modifier)
        .onClick { onClick?.invoke() }
    if (isPageRoute(url)) {
        Link(path = url, modifier = linkModifier) { Text(label) }
    } else {
        // Files, mailto and other sites skip the router, which only knows about pages.
        A(href = url, attrs = linkModifier.toAttrs { if (download) attr("download", "") }) { Text(label) }
    }
}

/** A path the site renders itself, as opposed to a file (".pdf", ".apk") or a link with a scheme. */
private fun isPageRoute(url: String): Boolean =
    (url.startsWith("/") || url.startsWith("#")) &&
        !url.substringBefore('#').substringAfterLast('/').contains('.')

@Composable
fun OutlineButton(
    url: String,
    label: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    ButtonLink(url, label, OutlineButtonStyle.toModifier(), modifier, onClick = onClick)
}

@Composable
fun PrimaryButton(
    url: String,
    label: String,
    modifier: Modifier = Modifier,
    download: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    ButtonLink(url, label, PrimaryButtonStyle.toModifier(), modifier, download = download, onClick = onClick)
}

@Composable
fun DownloadButton(
    url: String,
    label: String,
    modifier: Modifier = Modifier,
) {
    ButtonLink(url, label, OutlineButtonStyle.toModifier(), modifier, download = true) {
        val fileName = url.substringAfterLast("/")
        val version = fileName.removeSuffix(".apk")
        trackEvent("apk-download", json("version" to version, "file" to fileName))
    }
}

@Composable
fun ResumeDownloadButton(
    url: String,
    label: String,
    modifier: Modifier = Modifier,
) {
    ButtonLink(url, label, OutlineButtonStyle.toModifier(), modifier) { trackEvent("resume-download") }
}

@Composable
fun TextLink(
    url: String,
    label: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    Link(
        path = url,
        modifier = DescriptorStyle.toModifier()
            .then(TextLinkStyle.toModifier())
            .cursor(Cursor.Pointer)
            .textDecorationLine(TextDecorationLine.None)
            .transition(Transition.all(duration = 150.ms))
            .then(modifier)
            .onClick { onClick?.invoke() },
    ) {
        SpanText(
            text = label,
            modifier = Modifier,
        )
    }
}

// Press feedback: a quick scale-down on :active, eased out so it responds instantly.
private const val PRESS_TRANSITION =
    "transform 160ms cubic-bezier(0.23, 1, 0.32, 1), background-color 160ms ease, color 160ms ease, border-color 160ms ease"

val TextLinkStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier.styleModifier {
            property("color", palette.textPrimary.toString())
            property("text-decoration", "underline")
            property("text-decoration-color", palette.border.toString())
            property("text-underline-offset", "0.3em")
            property("text-decoration-thickness", "1px")
        }
    }

    hover {
        val palette = sitePalette()
        Modifier.styleModifier {
            property("color", palette.accent.toString())
            property("text-decoration-color", palette.accent.toString())
        }
    }
}

/** Shared button shape: a 44px-tall target with centred text and no link underline. */
val ButtonLinkStyle = CssStyle {
    base {
        Modifier.styleModifier {
            property("display", "inline-flex")
            property("align-items", "center")
            property("justify-content", "center")
            property("min-height", "2.75rem")
            property("padding", "0 1.125rem")
            property("box-sizing", "border-box")
            property("border-radius", "6px")
            property("font-family", "Geist, system-ui, sans-serif")
            property("font-size", "1rem")
            property("font-weight", "500")
            property("line-height", "1.2")
            property("text-decoration", "none")
            property("white-space", "nowrap")
            property("cursor", "pointer")
            property("user-select", "none")
            property("transition", PRESS_TRANSITION)
        }
    }

    active {
        Modifier.scale(0.97)
    }
}

val OutlineButtonStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier.styleModifier {
            property("background-color", "transparent")
            property("color", palette.textPrimary.toString())
            property("border", "1px solid ${palette.border}")
        }
    }

    hover {
        val palette = sitePalette()
        Modifier.styleModifier {
            property("background-color", palette.surface.toString())
            property("border-color", palette.textSecondary.toString())
        }
    }
}

val PrimaryButtonStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier.styleModifier {
            property("background-color", palette.accent.toString())
            property("color", palette.buttonText.toString())
            property("border", "1px solid transparent")
        }
    }

    hover {
        Modifier.styleModifier { property("background-color", sitePalette().accentHover.toString()) }
    }

    active {
        Modifier.styleModifier { property("background-color", sitePalette().accentPressed.toString()) }
    }
}
