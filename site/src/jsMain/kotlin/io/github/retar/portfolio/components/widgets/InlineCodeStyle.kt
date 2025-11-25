package io.github.retar.portfolio.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.OverflowWrap
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.overflowWrap
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.styles.sitePalette
import org.jetbrains.compose.web.dom.Code
import org.jetbrains.compose.web.dom.Text

val InlineCodeStyle = CssStyle {
    val palette = sitePalette()
    Modifier
        .backgroundColor(palette.codeBackground)
        .color(palette.codeText)
        .overflowWrap(OverflowWrap.BreakWord)
}

@Composable
fun InlineCode(text: String, modifier: Modifier = Modifier) {
    Code(attrs = InlineCodeStyle.toModifier().then(modifier).toAttrs()) {
        Text(text)
    }
}