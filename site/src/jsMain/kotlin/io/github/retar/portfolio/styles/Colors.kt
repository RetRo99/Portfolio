package io.github.retar.portfolio.styles

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.silk.style.CssStyleScope
import com.varabyte.kobweb.silk.theme.colors.ColorMode

data class SitePalette(
    val background: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val primary: Color,
    val secondary: Color,
    val accent: Color,
    val codeBackground: Color,
    val codeText: Color,
    val drawerBackground: Color,
    val headerBorder: Color,
    val buttonText: Color,
    val dropdownBackground: Color,
)

object SitePalettes {
    val light =
        SitePalette(
            background = Color.rgb(250, 250, 250),
            textPrimary = Color.rgb(24, 24, 27),
            textSecondary = Color.rgb(82, 82, 91),
            primary = Color.rgb(122, 136, 254),
            secondary = Color.rgb(255, 107, 107),
            accent = Color.rgb(245, 158, 11),
            codeBackground = Color.rgb(240, 240, 244),
            codeText = Color.rgb(24, 24, 27),
            drawerBackground = Color.rgb(250, 250, 250),
            headerBorder = Colors.LightGray,
            buttonText = Color.rgb(255, 255, 255),
            dropdownBackground = Color.rgb(255, 255, 255),
        )

    val dark =
        SitePalette(
            background = Color.rgb(24, 24, 27),
            textPrimary = Color.rgb(250, 250, 250),
            textSecondary = Color.rgb(161, 161, 170),
            primary = Color.rgb(122, 136, 254),
            secondary = Color.rgb(255, 107, 107),
            accent = Color.rgb(245, 158, 11),
            codeBackground = Color.rgb(39, 39, 42),
            codeText = Color.rgb(236, 239, 244),
            drawerBackground = Color.rgb(39, 39, 42),
            headerBorder = Color.rgb(63, 63, 70),
            buttonText = Color.rgb(255, 255, 255),
            dropdownBackground = Color.rgb(39, 39, 42),
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


