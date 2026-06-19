package io.github.retar.portfolio.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.gridTemplateColumns
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.dom.Div
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.styles.BodyStyle
import io.github.retar.portfolio.styles.H3Style
import io.github.retar.portfolio.styles.MonoAccentStyle
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.fr
import org.jetbrains.compose.web.css.px

data class FeatureItem(
    val index: String,
    val title: String,
    val description: String,
)

val FeatureGridStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .display(DisplayStyle.Grid)
            .gridTemplateColumns {
                size(1.fr)
            }
            .gap(32.px)
    }

    Breakpoint.MD {
        Modifier.gridTemplateColumns {
            size(1.fr)
            size(1.fr)
        }
    }
}

@Composable
fun FeatureGrid(
    items: List<FeatureItem>,
    modifier: Modifier = Modifier,
) {
    Div(
        attrs = FeatureGridStyle.toModifier()
            .then(modifier)
            .toAttrs(),
    ) {
        items.forEach { item ->
            FeatureCell(item)
        }
    }
}

@Composable
private fun FeatureCell(item: FeatureItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .gap(12.px)
            .padding(topBottom = 8.px),
    ) {
        SpanText(
            text = item.index,
            modifier = MonoAccentStyle.toModifier(),
        )
        SpanText(
            text = item.title,
            modifier = H3Style.toModifier()
                .fillMaxWidth(),
        )
        SpanText(
            text = item.description,
            modifier = BodyStyle.toModifier()
                .fillMaxWidth(),
        )
    }
}
