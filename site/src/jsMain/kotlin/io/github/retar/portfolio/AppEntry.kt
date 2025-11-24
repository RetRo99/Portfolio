package io.github.retar.portfolio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.css.WhiteSpace
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.scrollBehavior
import com.varabyte.kobweb.compose.ui.modifiers.whiteSpace
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.silk.SilkApp
import com.varabyte.kobweb.silk.components.layout.Surface
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.init.registerStyleBase
import com.varabyte.kobweb.silk.style.common.SmoothColorStyle
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.loadFromLocalStorage
import com.varabyte.kobweb.silk.theme.colors.palette.background
import com.varabyte.kobweb.silk.theme.colors.palette.color
import com.varabyte.kobweb.silk.theme.colors.systemPreference
import io.github.retar.portfolio.components.PortfolioSectionId
import io.github.retar.portfolio.styles.SitePalettes
import org.jetbrains.compose.web.css.px

@InitSilk
fun initStyles(ctx: InitSilkContext) {
    ctx.config.initialColorMode =
        ColorMode.loadFromLocalStorage() ?: ColorMode.systemPreference

    Language.initializeFromLocalStorage()

    ctx.theme.palettes.light.background = SitePalettes.light.background
    ctx.theme.palettes.light.color = SitePalettes.light.textPrimary

    ctx.theme.palettes.dark.background = SitePalettes.dark.background
    ctx.theme.palettes.dark.color = SitePalettes.dark.textPrimary

    ctx.stylesheet.registerStyleBase("html, body") {
        Modifier
            .fillMaxHeight()
            .fontFamily("Inter", "system-ui", "sans-serif")
            .scrollBehavior(ScrollBehavior.Smooth)
            .overflow { x(Overflow.Hidden) }
    }

    ctx.stylesheet.registerStyleBase("pre") {
        Modifier
            .padding(12.px)
            .backgroundColor(SitePalettes.dark.codeBackground)
            .color(SitePalettes.dark.codeText)
            .borderRadius(8.px)
            .fontFamily("monospace")
            .whiteSpace(WhiteSpace.PreWrap)
    }

    ctx.stylesheet.registerStyleBase("code") {
        Modifier.fontFamily("monospace")
    }
}

val LocalActiveSection = compositionLocalOf<PortfolioSectionId?> { null }
val LocalSetActiveSection = compositionLocalOf<(PortfolioSectionId?) -> Unit> { {} }

@App
@Composable
fun AppEntry(content: @Composable () -> Unit) {
    SilkApp {
        Surface(SmoothColorStyle.toModifier()) {
            var active by remember { mutableStateOf<PortfolioSectionId?>(null) }

            CompositionLocalProvider(
                LocalActiveSection provides active,
                LocalSetActiveSection provides { active = it },
            ) {
                content()
            }
        }
    }
}