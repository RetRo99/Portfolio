package io.github.retar.portfolio.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.ColumnScope
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Composable
fun PortfolioSection(
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = SectionContainerStyle.toModifier(),
        content = content
    )
}

val SectionContainerStyle = CssStyle {
    base {
        Modifier
            .padding(topBottom = 32.px, leftRight = 16.px)
            .width(100.percent)
    }
}