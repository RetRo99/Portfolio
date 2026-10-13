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
import org.jetbrains.compose.web.dom.Text
import kotlin.js.json

@Composable
fun OutlineButton(
    url: String,
    label: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    val ctx = rememberPageContext()

    Button(
        onClick = {
            onClick?.invoke()
            ctx.router.navigateTo(url)
        },
        modifier = OutlineButtonStyle.toModifier()
            .then(modifier),
        size = ButtonSize.MD,
    ) {
        Text(label)
    }
}

@Composable
fun PrimaryButton(
    url: String,
    label: String,
    modifier: Modifier = Modifier,
    openExternally: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    val ctx = rememberPageContext()

    Button(
        onClick = {
            onClick?.invoke()
            if (openExternally) {
                window.open(url, OpenLinkStrategy.IN_PLACE)
            } else {
                ctx.router.navigateTo(url)
            }
        },
        modifier = PrimaryButtonStyle.toModifier()
            .then(modifier),
        size = ButtonSize.MD,
    ) {
        Text(label)
    }
}

@Composable
fun DownloadButton(
    url: String,
    label: String,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = {
            val fileName = url.substringAfterLast("/")
            val version = fileName.removeSuffix(".apk")
            trackEvent("apk-download", json("version" to version, "file" to fileName))

            window.open(url, OpenLinkStrategy.IN_PLACE)
        },
        modifier = OutlineButtonStyle.toModifier()
            .then(modifier),
        size = ButtonSize.MD,
    ) {
        Text(label)
    }
}

@Composable
fun ResumeDownloadButton(
    url: String,
    label: String,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = {
            trackEvent("resume-download")
            window.open(url, OpenLinkStrategy.IN_PLACE)
        },
        modifier = OutlineButtonStyle.toModifier()
            .then(modifier),
        size = ButtonSize.MD,
    ) {
        Text(label)
    }
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

val OutlineButtonStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .setVariable(ButtonVars.BackgroundDefaultColor, Colors.Transparent)
            .setVariable(ButtonVars.BackgroundHoverColor, palette.surface)
            .setVariable(ButtonVars.BackgroundPressedColor, palette.surface)
            .setVariable(ButtonVars.Color, palette.textPrimary)
            .border(1.px, LineStyle.Solid, palette.border)
            .borderRadius(6.px)
            .styleModifier {
                property("font-weight", "500")
                property("transition", PRESS_TRANSITION)
            }
    }

    hover {
        val palette = sitePalette()
        Modifier.border(1.px, LineStyle.Solid, palette.textSecondary)
    }

    active {
        Modifier.scale(0.97)
    }
}

val PrimaryButtonStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .setVariable(ButtonVars.BackgroundDefaultColor, palette.accent)
            .setVariable(ButtonVars.BackgroundHoverColor, palette.accentHover)
            .setVariable(ButtonVars.BackgroundPressedColor, palette.accentPressed)
            .setVariable(ButtonVars.Color, palette.buttonText)
            .border(1.px, LineStyle.Solid, Colors.Transparent)
            .borderRadius(6.px)
            .styleModifier {
                property("font-weight", "500")
                property("transition", PRESS_TRANSITION)
            }
    }

    active {
        Modifier.scale(0.97)
    }
}
