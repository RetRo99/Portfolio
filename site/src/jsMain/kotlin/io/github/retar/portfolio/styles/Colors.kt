package io.github.retar.portfolio.styles

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.silk.style.CssStyleScope
import com.varabyte.kobweb.silk.theme.colors.ColorMode

data class SitePalette(
    val background: Color,
    val surface: Color,
    val footerBg: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val border: Color,
    val accent: Color,
    val accentHover: Color,
    val accentPressed: Color,
    val codeBackground: Color,
    val codeText: Color,
    val drawerBackground: Color,
    val headerBorder: Color,
    val buttonText: Color,
    val dropdownBackground: Color,
)

object SitePalettes {
    // Warm paper and ink. One accent (a muted vermilion) carries every highlight.
    val light =
        SitePalette(
            background = Color.rgb(246, 243, 236),
            surface = Color.rgb(237, 232, 221),
            footerBg = Color.rgb(237, 232, 221),
            textPrimary = Color.rgb(28, 26, 23),
            textSecondary = Color.rgb(94, 88, 78),
            border = Color.rgb(221, 213, 199),
            accent = Color.rgb(178, 69, 34),
            accentHover = Color.rgb(154, 58, 27),
            accentPressed = Color.rgb(128, 48, 22),
            codeBackground = Color.rgb(237, 232, 221),
            codeText = Color.rgb(28, 26, 23),
            drawerBackground = Color.rgb(246, 243, 236),
            headerBorder = Color.rgb(221, 213, 199),
            buttonText = Color.rgb(251, 248, 242),
            dropdownBackground = Color.rgb(251, 248, 242),
        )

    val dark =
        SitePalette(
            background = Color.rgb(21, 19, 16),
            surface = Color.rgb(31, 28, 24),
            footerBg = Color.rgb(31, 28, 24),
            textPrimary = Color.rgb(238, 232, 220),
            textSecondary = Color.rgb(167, 159, 144),
            border = Color.rgb(52, 48, 42),
            accent = Color.rgb(226, 124, 84),
            accentHover = Color.rgb(236, 146, 110),
            accentPressed = Color.rgb(204, 104, 66),
            codeBackground = Color.rgb(31, 28, 24),
            codeText = Color.rgb(238, 232, 220),
            drawerBackground = Color.rgb(31, 28, 24),
            headerBorder = Color.rgb(52, 48, 42),
            buttonText = Color.rgb(21, 19, 16),
            dropdownBackground = Color.rgb(31, 28, 24),
        )
}

fun ColorMode.toSitePalette(): SitePalette =
    when (this) {
        ColorMode.LIGHT -> SitePalettes.light
        ColorMode.DARK -> SitePalettes.dark
    }

@Composable
fun sitePalette(): SitePalette = ColorMode.current.toSitePalette()
fun CssStyleScope.sitePalette(): SitePalette = colorMode.toSitePalette()
