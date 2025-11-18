package io.github.retar.portfolio.styles

import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import io.github.retar.portfolio.styles.AppColors
import org.jetbrains.compose.web.css.px

val TitleStyle = CssStyle {
    base {
        Modifier
            .fontSize(55.px)
            .fontWeight(FontWeight.Normal)
    }

    Breakpoint.ZERO {
        Modifier.fontSize(32.px)
    }
}

val SubtitleStyle = CssStyle {
    base {
        Modifier
            .fontSize(18.px)
    }
}

val DescriptorStyle = CssStyle {
    base {
        Modifier
            .color(AppColors.TextSecondary)
            .fontSize(18.px)
    }
}

val ImageTextHoverStyle = CssStyle {
    base {
        Modifier
            .color(AppColors.ImageTextHover)
            .fontSize(18.px)
    }
}
