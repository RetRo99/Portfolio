package io.github.retar.portfolio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.silk.SilkApp
import com.varabyte.kobweb.silk.components.layout.Surface
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.init.registerStyleBase
import com.varabyte.kobweb.silk.style.common.SmoothColorStyle
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.styles.AppColors
import kotlinx.browser.document
import org.jetbrains.compose.web.css.px
import org.w3c.dom.HTMLLinkElement

@InitSilk
fun initStyles(ctx: InitSilkContext) {
    ctx.stylesheet.registerStyleBase("html, body") {
        Modifier.fillMaxHeight()
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

@App
@Composable
fun AppEntry(content: @Composable () -> Unit) {
    SilkApp {
        Surface(SmoothColorStyle.toModifier().fillMaxHeight()) {
            content()
        }
    }
}
