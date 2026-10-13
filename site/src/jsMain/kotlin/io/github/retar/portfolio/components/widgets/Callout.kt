package io.github.retar.portfolio.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.borderLeft
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.styles.BodyStyle
import io.github.retar.portfolio.styles.MonoEyebrowStyle
import io.github.retar.portfolio.styles.eyebrowText
import io.github.retar.portfolio.styles.sitePalette
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px

val CalloutStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fillMaxWidth()
            .backgroundColor(palette.surface)
            .borderLeft(2.px, LineStyle.Solid, palette.accent)
            .borderRadius(0.px, 8.px, 8.px, 0.px)
            .padding(leftRight = 24.px, topBottom = 20.px)
    }
}

@Composable
fun Callout(
    label: String,
    body: String,
    modifier: Modifier = Modifier,
) {
    val palette = sitePalette()
    Column(
        modifier = CalloutStyle.toModifier()
            .then(modifier)
            .gap(8.px),
    ) {
        SpanText(
            text = label.eyebrowText(),
            modifier = MonoEyebrowStyle.toModifier().color(palette.accent),
        )
        SpanText(
            text = body,
            modifier = BodyStyle.toModifier()
                .fillMaxWidth(),
        )
    }
}
