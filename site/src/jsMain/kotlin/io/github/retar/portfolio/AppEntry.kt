package io.github.retar.portfolio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.scrollBehavior
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.core.AppGlobals
import com.varabyte.kobweb.core.init.InitKobweb
import com.varabyte.kobweb.core.init.InitKobwebContext
import com.varabyte.kobweb.core.isExporting
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
import kotlinx.browser.document
import kotlinx.browser.window

const val COLOR_MODE_KEY = "portfolio:colorMode"

@InitSilk
fun initStyles(ctx: InitSilkContext) {
    ctx.config.initialColorMode =
        ColorMode.loadFromLocalStorage(COLOR_MODE_KEY) ?: ColorMode.systemPreference

    // Script to fix color mode and hide content until language is ready on exported sites
    if (AppGlobals.isExporting) {
        document.head!!.appendChild(
            document.createElement("script").apply {
                textContent = """
                    {
                        // Fix color mode
                        const storedColor = localStorage.getItem('$COLOR_MODE_KEY');
                        const isDark = storedColor
                            ? storedColor.toLowerCase() === 'dark'
                            : window.matchMedia('(prefers-color-scheme: dark)').matches;
                        const desiredColor = isDark ? 'silk-dark' : 'silk-light';
                        const oppositeColor = isDark ? 'silk-light' : 'silk-dark';
                        document.documentElement.classList.replace(oppositeColor, desiredColor);

                        // Hide content until language is initialized (with correct background)
                        const style = document.createElement('style');
                        style.id = 'initial-hide';
                        style.textContent = 'body { visibility: hidden; background: ' + (isDark ? '${SitePalettes.dark.background}' : '${SitePalettes.light.background}') + '; }';
                        document.head.appendChild(style);
                    }
                """.trimIndent()
            }
        )
    }

    Language.initializeFromLocalStorage()

    ctx.theme.palettes.light.background = SitePalettes.light.background
    ctx.theme.palettes.light.color = SitePalettes.light.textPrimary

    ctx.theme.palettes.dark.background = SitePalettes.dark.background
    ctx.theme.palettes.dark.color = SitePalettes.dark.textPrimary

    ctx.stylesheet.registerStyleBase("body") {
        Modifier
            .fontFamily("Inter", "system-ui", "sans-serif")
    }

    ctx.stylesheet.registerStyleBase("html") {
        Modifier.scrollBehavior(ScrollBehavior.Smooth)
    }

    ctx.stylesheet.registerStyleBase("code") {
        Modifier.fontFamily("monospace")
    }
}

@InitKobweb
fun initRouter(ctx: InitKobwebContext) {
    val history = window.history.asDynamic()
    history.scrollRestoration = "manual"

    val originalPushState = history.pushState.bind(history)
    history.pushState = { state: dynamic, title: String, url: dynamic ->
        window.asDynamic().__portfolioNav = "push"
        originalPushState(state, title, url)
    }
}

val LocalActiveSection = compositionLocalOf<PortfolioSectionId?> { null }
val LocalSetActiveSection = compositionLocalOf<(PortfolioSectionId?) -> Unit> { {} }

@App
@Composable
fun AppEntry(content: @Composable () -> Unit) {
    // Remove the initial hide style once the app is ready (language is initialized)
    LaunchedEffect(Unit) {
        document.getElementById("initial-hide")?.remove()
    }

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