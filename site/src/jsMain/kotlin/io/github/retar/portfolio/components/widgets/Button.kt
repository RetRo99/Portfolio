package io.github.retar.portfolio.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.scale
import com.varabyte.kobweb.compose.ui.modifiers.setVariable
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.forms.ButtonSize
import com.varabyte.kobweb.silk.components.forms.ButtonVars
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.styles.sitePalette
import io.github.retar.portfolio.utils.trackEvent
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Text

@Composable
fun OutlineButton(
    url: String,
    label: String,
    modifier: Modifier = Modifier,
) {
    val ctx = rememberPageContext()

    Button(
        onClick = { ctx.router.navigateTo(url) },
        modifier = OutlineButtonStyle.toModifier()
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
            // Track download event with Umami analytics
            val fileName = url.substringAfterLast("/")
            val version = fileName.removeSuffix(".apk")
            val eventData = js("{}")
            eventData["version"] = version
            eventData["file"] = fileName
            trackEvent("apk-download", eventData)

            // Trigger download
            js("window.open")(url, "_self")
        },
        modifier = OutlineButtonStyle.toModifier()
            .then(modifier),
        size = ButtonSize.MD,
    ) {
        Text(label)
    }
}

val OutlineButtonStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .setVariable(ButtonVars.BackgroundDefaultColor, Colors.Transparent)
            .setVariable(ButtonVars.BackgroundHoverColor, palette.accent)
            .setVariable(ButtonVars.BackgroundPressedColor, palette.accent)
            .setVariable(ButtonVars.Color, palette.accent)
            .border(2.px, LineStyle.Solid, palette.accent)
            .borderRadius(4.px)
    }

    hover {
        val palette = sitePalette()
        Modifier
            .color(palette.background)
            .scale(1.02)
    }
}