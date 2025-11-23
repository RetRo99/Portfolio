package io.github.retar.portfolio.styles

import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import org.jetbrains.compose.web.css.px

val TitleStyle = CssStyle {
    base {
        Modifier
            .color(AppColors.TextPrimary)
            .fontSize(64.px)
            .fontWeight(FontWeight.Bold)
            .fontFamily("Outfit", "sans-serif")
    }

    Breakpoint.ZERO {
        Modifier.fontSize(36.px)
    }
}

val SubtitleStyle = CssStyle {
    base {
        Modifier
            .color(AppColors.TextSecondary)
            .fontSize(18.px)
            .fontWeight(FontWeight.Medium)
            .fontFamily("Inter", "sans-serif")
            .maxWidth(650.px)
    }
}

val DescriptorStyle = CssStyle {
    base {
        Modifier
            .color(AppColors.TextSecondary)
            .fontSize(18.px)
            .fontFamily("Inter", "sans-serif")
    }
}

val ImageTextHoverStyle = CssStyle {
    base {
        Modifier
            .color(AppColors.ImageTextHover)
            .fontSize(18.px)
            .padding(leftRight = 8.px)
            .textAlign(TextAlign.Center)
    }
}
