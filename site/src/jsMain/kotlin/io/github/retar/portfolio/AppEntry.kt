package io.github.retar.portfolio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.silk.SilkApp
import com.varabyte.kobweb.silk.components.layout.Surface
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.init.registerStyleBase
import com.varabyte.kobweb.silk.style.common.SmoothColorStyle
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.browser.document
import org.w3c.dom.HTMLLinkElement

@InitSilk
fun initStyles(ctx: InitSilkContext) {
    ctx.stylesheet.registerStyleBase("html, body") { Modifier.fillMaxHeight() }
}

@App
@Composable
fun AppEntry(content: @Composable () -> Unit) {
    SilkApp {
        Surface(SmoothColorStyle.toModifier().fillMaxHeight()) {
            SideEffect {
                val head = document.head
                if (head != null) {
                    fun upsert(rel: String, type: String?, href: String) {
                        var link =
                            head.querySelector("link[rel='${'$'}rel']") as? HTMLLinkElement
                        if (link == null) {
                            link = document.createElement("link") as HTMLLinkElement
                            link.rel = rel
                            head.appendChild(link)
                        }
                        if (type != null) {
                            link.type = type
                        } else {
                            link.removeAttribute("type")
                        }
                        link.href = href
                    }
                    // Ensure favicon works with basePath in export and adds explicit
                    // type for better browser support
                    upsert("icon", "image/svg+xml", "favicon.svg")
                    upsert("shortcut icon", "image/svg+xml", "favicon.svg")
                }
            }
            content()
        }
    }
}
