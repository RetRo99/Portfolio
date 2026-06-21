package io.github.retar.portfolio.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.browser.dom.observers.IntersectionObserver
import com.varabyte.kobweb.compose.dom.ref
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.ColumnScope
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.scrollMargin
import io.github.retar.portfolio.LocalSetActiveSection
import io.github.retar.portfolio.components.header.NavHeaderHeight
import io.github.retar.portfolio.utils.trackEvent
import org.jetbrains.compose.web.css.CSSLengthValue
import org.jetbrains.compose.web.css.px
import org.w3c.dom.HTMLElement
import kotlin.js.json

@Composable
fun PortfolioSection(
    section: PortfolioSectionId,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val setActive = LocalSetActiveSection.current
    var element by remember { mutableStateOf<HTMLElement?>(null) }
    var hasTrackedView by remember { mutableStateOf(false) }

    DisposableEffect(section, element) {
        val currentElement = element
        if (currentElement != null) {
            val options = IntersectionObserver.Options(
                rootMargin = "-50% 0px -50% 0px"
            )

            val observer = IntersectionObserver(options) { entries ->
                entries.forEach { entry ->
                    if (entry.isIntersecting) {
                        setActive(section)
                        if (!hasTrackedView) {
                            hasTrackedView = true
                            trackEvent("section-view", json("section" to section.domId))
                        }
                    }
                }
            }

            observer.observe(currentElement)

            onDispose { observer.disconnect() }
        } else {
            onDispose { }
        }
    }

    Column(
        modifier = modifier.id(section.domId)
            .scrollMargin(top = NavHeaderHeight.value()),
        ref = ref { htmlElement ->
            element = htmlElement
        },
        content = content
    )
}