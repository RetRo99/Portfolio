package io.github.retar.portfolio.styles

import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import org.jetbrains.compose.web.css.px

val HeadingXLStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fontSize(64.px)
            .fontWeight(FontWeight.Bold)
            .fontFamily("Outfit", "sans-serif")
            .color(palette.textPrimary)
    }

    Breakpoint.ZERO {
        Modifier.fontSize(40.px)
    }
}

val HeadingLStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fontSize(48.px)
            .fontWeight(FontWeight.Bold)
            .fontFamily("Outfit", "sans-serif")
            .color(palette.textSecondary)
    }

    Breakpoint.ZERO {
        Modifier.fontSize(28.px)
    }
}

val HeadingMStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fontSize(36.px)
            .fontWeight(FontWeight.Bold)
            .fontFamily("Outfit", "sans-serif")
            .color(palette.textPrimary)
    }

    Breakpoint.ZERO {
        Modifier.fontSize(28.px)
    }
}

val BodyStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fontSize(16.px)
            .fontFamily("Inter", "sans-serif")
            .color(palette.textSecondary)
    }
}

val BodySmallStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fontSize(12.px)
            .fontFamily("monospace")
            .color(palette.textSecondary)
    }
}

val LabelStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fontSize(16.px)
            .fontWeight(FontWeight.Bold)
            .fontFamily("Outfit", "sans-serif")
            .color(palette.textPrimary)
    }
}

val MonoAccentStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fontFamily("monospace")
            .fontSize(16.px)
            .color(palette.accent)
    }
}

val SubtitleStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fontSize(18.px)
            .fontWeight(FontWeight.Medium)
            .fontFamily("Inter", "sans-serif")
            .color(palette.textSecondary)
            .maxWidth(650.px)
    }
}

val DescriptorStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fontSize(14.px)
            .fontFamily("Inter", "sans-serif")
            .color(palette.textSecondary)
    }
}
