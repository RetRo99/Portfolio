package io.github.retar.portfolio.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.scale
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.styles.AppColors
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Text

@Composable
fun PortfolioButton(url: String, label: String) {
    A(url, attrs = LinkButtonStyle.toModifier().toAttrs()) {
        Text(label)
    }
}

val LinkButtonStyle = CssStyle {
    base {
        Modifier
            .padding(topBottom = 10.px, leftRight = 18.px)
            .border(1.px, LineStyle.Solid, AppColors.Primary)
            .borderRadius(999.px)
            .backgroundColor(AppColors.Primary)
            .color(AppColors.ButtonTextHover)
            .textDecorationLine(TextDecorationLine.None)
    }

    hover {
        Modifier
            .backgroundColor(AppColors.Primary)
            .color(AppColors.ButtonTextHover)
            .scale(1.03)
    }
}