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
import com.varabyte.kobweb.compose.ui.styleModifier
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
            .fluidFontSize(3.5.cssRem, lengthCalc("9vw + 1rem"), 8.5.cssRem)
            .lineHeight(0.92)
            .fontWeight(FontWeight.Normal)
            .letterSpacing((-0.02).em)
            .fontFamily("Instrument Serif", "Iowan Old Style", "Georgia", "serif")
            .color(palette.textPrimary)
            .styleModifier { property("text-wrap", "balance") }
    }
}

val H1Style = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fluidFontSize(1.25.cssRem, lengthCalc("1.2vw + 0.9rem"), 1.75.cssRem)
            .lineHeight(1.3)
            .fontWeight(FontWeight.Medium)
            .letterSpacing((-0.01).em)
            .fontFamily("Geist", "system-ui", "sans-serif")
            .color(palette.textPrimary)
            .styleModifier { property("text-wrap", "balance") }
    }
}

val H2Style = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fluidFontSize(2.25.cssRem, lengthCalc("4vw + 0.75rem"), 4.cssRem)
            .lineHeight(1.0)
            .fontWeight(FontWeight.Normal)
            .letterSpacing((-0.015).em)
            .fontFamily("Instrument Serif", "Iowan Old Style", "Georgia", "serif")
            .color(palette.textPrimary)
            .styleModifier { property("text-wrap", "balance") }
    }
}

val H3Style = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fluidFontSize(1.0625.cssRem, lengthCalc("0.6vw + 0.9rem"), 1.25.cssRem)
            .lineHeight(1.3)
            .fontWeight(FontWeight.SemiBold)
            .letterSpacing((-0.01).em)
            .fontFamily("Geist", "system-ui", "sans-serif")
            .color(palette.textPrimary)
    }
}

val BodyStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fluidFontSize(1.cssRem, lengthCalc("0.4vw + 0.9rem"), 1.0625.cssRem)
            .lineHeight(1.65)
            .fontFamily("Geist", "system-ui", "sans-serif")
            .color(palette.textSecondary)
            .styleModifier { property("text-wrap", "pretty") }
    }
}

val BodySmallStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fontSize(13.px)
            .lineHeight(1.5)
            .fontFamily("Geist", "system-ui", "sans-serif")
            .color(palette.textSecondary)
    }
}

val MonoEyebrowStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fontSize(12.px)
            .lineHeight(1.4)
            .fontWeight(FontWeight.Medium)
            .fontFamily("Geist Mono", "ui-monospace", "monospace")
            .letterSpacing(0.02.em)
            .color(palette.textSecondary)
    }
}

val MonoLabelStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fontSize(13.px)
            .lineHeight(1.4)
            .fontFamily("Geist Mono", "ui-monospace", "monospace")
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
            .fontFamily("Geist Mono", "ui-monospace", "monospace")
            .color(palette.textSecondary)
    }
}

val LabelStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fontSize(16.px)
            .fontWeight(FontWeight.Bold)
            .fontFamily("Geist", "system-ui", "sans-serif")
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
            .fontFamily("Geist", "system-ui", "sans-serif")
            .color(palette.textSecondary)
    }
}

val DescriptorStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fontSize(14.px)
            .lineHeight(1.4)
            .fontFamily("Geist", "system-ui", "sans-serif")
            .color(palette.textSecondary)
    }
}

val MonoAccentStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier
            .fontFamily("Geist Mono", "ui-monospace", "monospace")
            .fontSize(12.px)
            .letterSpacing(0.04.em)
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

/**
 * Eyebrow copy in the string resources is written as `// 01 — label`. The editorial design drops the
 * code-comment prefix and the section number (the order changed), and sets the label in sentence case.
 */
fun String.eyebrowText(): String =
    removePrefix("//").trim()
        .replace(Regex("^\\d+\\s*—\\s*"), "")
        .replaceFirstChar { it.uppercase() }
