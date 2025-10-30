package io.github.retar.portfolio.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgb
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Text

@Composable
fun PortfolioButton(href: String, label: String) {
    A(href, attrs = LinkButtonStyle.toModifier().toAttrs()) {
        Text(label)
    }
}

val LinkButtonStyle = CssStyle {
    base {
        Modifier
            .padding(8.px)
            .border(1.px, LineStyle.Solid, Color.lightgray)
            .borderRadius(16.px)
            .backgroundColor(Color.transparent)
            .color(rgb(74, 74, 69))
            .textDecorationLine(TextDecorationLine.None)
    }

    hover {
        Modifier.backgroundColor(rgb(122, 136, 254)).color(Color.white)
    }
}