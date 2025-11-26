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

// Large heading (H1 equivalent)
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

// Medium heading (H2 equivalent)
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

// Body text style
val BodyStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fontSize(16.px)
            .fontFamily("Inter", "sans-serif")
            .color(palette.textSecondary)
    }
}

// Small body text style
val BodySmallStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fontSize(12.px)
            .fontFamily("monospace")
            .color(palette.textSecondary)
    }
}

// Label/title style (bold, primary color)
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

// Monospace/code style with accent color
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
        Modifier
            .fontSize(18.px)
            .fontFamily("Inter", "sans-serif")
    }
}
