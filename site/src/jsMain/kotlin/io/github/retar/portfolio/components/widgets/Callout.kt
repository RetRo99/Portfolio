package io.github.retar.portfolio.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.borderLeft
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.styles.BodyStyle
import io.github.retar.portfolio.styles.MonoEyebrowStyle
import io.github.retar.portfolio.styles.sitePalette
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px

val CalloutStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fillMaxWidth()
            .borderLeft(3.px, LineStyle.Solid, palette.accent)
            .padding(leftRight = 24.px, topBottom = 20.px)
    }
}

@Composable
fun Callout(
    label: String,
    body: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = CalloutStyle.toModifier()
            .then(modifier)
            .gap(8.px),
    ) {
        SpanText(
            text = label,
            modifier = MonoEyebrowStyle.toModifier(),
        )
        SpanText(
            text = body,
            modifier = BodyStyle.toModifier()
                .fillMaxWidth(),
        )
    }
}
