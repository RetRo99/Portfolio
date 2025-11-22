package io.github.retar.portfolio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.scrollBehavior
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.silk.SilkApp
import com.varabyte.kobweb.silk.components.layout.Surface
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.init.registerStyleBase
import com.varabyte.kobweb.silk.style.common.SmoothColorStyle
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.styles.AppColors
import org.jetbrains.compose.web.css.px

@InitSilk
fun initStyles(ctx: InitSilkContext) {
    ctx.stylesheet.registerStyleBase("html, body") {
        Modifier
            .fillMaxHeight()
            .fontFamily("Roboto", "system-ui", "sans-serif")
    }

    ctx.stylesheet.registerStyle("html") {
        base {
            Modifier.scrollBehavior(ScrollBehavior.Smooth)
        }
    }

    ctx.stylesheet.registerStyleBase("pre") {
        Modifier
            .padding(12.px)
            .backgroundColor(AppColors.CodeBackground)
            .borderRadius(8.px)
            .fontFamily("monospace")
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
        Surface(SmoothColorStyle.toModifier().fillMaxHeight()) {
            var active by remember { mutableStateOf<PortfolioSectionId?>(null) }

            CompositionLocalProvider(
                LocalActiveSection provides active,
                LocalSetActiveSection provides { active = it }
            ) {
                content()
            }
        }
    }
}
