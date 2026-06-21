package io.github.retar.portfolio.styles

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.CSSLengthNumericValue
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.functions.clamp
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.letterSpacing
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Text
import kotlin.js.unsafeCast

private fun fluidLength(
    min: CSSLengthNumericValue,
    preferred: CSSLengthNumericValue,
    max: CSSLengthNumericValue,
): CSSLengthNumericValue = clamp(min, preferred, max)

private fun Modifier.fluidFontSize(
    min: CSSLengthNumericValue,
    preferred: CSSLengthNumericValue,
    max: CSSLengthNumericValue,
): Modifier = fontSize(fluidLength(min, preferred, max))

private fun lengthCalc(expr: String): CSSLengthNumericValue = "calc($expr)".unsafeCast<CSSLengthNumericValue>()

val DisplayStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fluidFontSize(2.75.cssRem, lengthCalc("6vw + 1rem"), 4.5.cssRem)
            .lineHeight(1.05)
            .fontWeight(FontWeight.Bold)
            .fontFamily("Outfit", "sans-serif")
            .color(palette.textPrimary)
    }
}

val H1Style = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fluidFontSize(1.875.cssRem, lengthCalc("4vw + 0.5rem"), 3.cssRem)
            .lineHeight(1.1)
            .fontWeight(FontWeight.SemiBold)
            .fontFamily("Outfit", "sans-serif")
            .color(palette.textPrimary)
    }
}

val H2Style = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fluidFontSize(1.625.cssRem, lengthCalc("3vw + 0.5rem"), 2.25.cssRem)
            .lineHeight(1.15)
            .fontWeight(FontWeight.Bold)
            .fontFamily("Outfit", "sans-serif")
            .color(palette.textPrimary)
    }
}

val H3Style = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fluidFontSize(1.25.cssRem, lengthCalc("2vw + 0.5rem"), 1.5.cssRem)
            .lineHeight(1.2)
            .fontWeight(FontWeight.SemiBold)
            .fontFamily("Outfit", "sans-serif")
            .color(palette.textPrimary)
    }
}

val BodyStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fluidFontSize(1.cssRem, lengthCalc("0.5vw + 0.875rem"), 1.0625.cssRem)
            .lineHeight(1.6)
            .fontFamily("Inter", "sans-serif")
            .color(palette.textSecondary)
    }
}

val BodySmallStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fontSize(13.px)
            .lineHeight(1.5)
            .fontFamily("Inter", "sans-serif")
            .color(palette.textSecondary)
    }
}

val MonoEyebrowStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fontSize(13.px)
            .lineHeight(1.4)
            .fontFamily("JetBrains Mono", "monospace")
            .letterSpacing(0.08.em)
            .color(palette.accent)
    }
}

val MonoLabelStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fontSize(13.px)
            .lineHeight(1.4)
            .fontFamily("JetBrains Mono", "monospace")
            .letterSpacing(0.04.em)
            .color(palette.textSecondary)
    }
}

val CaptionStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fontSize(12.px)
            .lineHeight(1.5)
            .fontFamily("JetBrains Mono", "monospace")
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

val SubtitleStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fluidFontSize(1.0625.cssRem, lengthCalc("1vw + 0.75rem"), 1.25.cssRem)
            .lineHeight(1.5)
            .fontWeight(FontWeight.Medium)
            .fontFamily("Inter", "sans-serif")
            .color(palette.textSecondary)
    }
}

val DescriptorStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fontSize(14.px)
            .lineHeight(1.4)
            .fontFamily("Inter", "sans-serif")
            .color(palette.textSecondary)
    }
}

val MonoAccentStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fontFamily("JetBrains Mono", "monospace")
            .fontSize(16.px)
            .color(palette.accent)
    }
}

@Composable
fun H1Text(text: String, modifier: Modifier = Modifier) {
    H1(attrs = modifier.toAttrs()) { Text(text) }
}

@Composable
fun H2Text(text: String, modifier: Modifier = Modifier) {
    H2(attrs = modifier.toAttrs()) { Text(text) }
}

@Composable
fun H3Text(text: String, modifier: Modifier = Modifier) {
    H3(attrs = modifier.toAttrs()) { Text(text) }
}
