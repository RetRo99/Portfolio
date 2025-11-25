package io.github.retar.portfolio.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.browser.dom.observers.ResizeObserver
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.ScrollbarWidth
import com.varabyte.kobweb.compose.css.Width
import com.varabyte.kobweb.compose.dom.ref
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.onFocusIn
import com.varabyte.kobweb.compose.ui.modifiers.onFocusOut
import com.varabyte.kobweb.compose.ui.modifiers.onMouseEnter
import com.varabyte.kobweb.compose.ui.modifiers.onMouseLeave
import com.varabyte.kobweb.compose.ui.modifiers.onTouchCancel
import com.varabyte.kobweb.compose.ui.modifiers.onTouchEnd
import com.varabyte.kobweb.compose.ui.modifiers.onTouchStart
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.scrollbarWidth
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.browser.window
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.px
import org.w3c.dom.HTMLElement

@Composable
fun <T> InfiniteCarousel(
    items: List<T>,
    modifier: Modifier = Modifier,
    itemContent: @Composable (T) -> Unit,
) {
    val baseRepeatCount = if (items.isEmpty()) 1 else (15 / items.size).coerceAtLeast(2)
    val baseItems = List(baseRepeatCount) { items }.flatten()

    var containerElement by remember { mutableStateOf<HTMLElement?>(null) }

    var isHovered by remember { mutableStateOf(false) }
    var isUserInteracting by remember { mutableStateOf(false) }

    // Timers
    var touchTimerId by remember { mutableStateOf<Int?>(null) }

    fun resumePlayAfterDelay(timerId: Int?, setTimerId: (Int?) -> Unit) {
        timerId?.let { window.clearTimeout(it) }
        val newId = window.setTimeout({
            isUserInteracting = false
        }, 400)
        setTimerId(newId)
    }

    DisposableEffect(Unit) {
        var lastTime = 0.0
        val speed = 0.08
        var animId = 0

        fun step(time: Double) {
            val delta = if (lastTime == 0.0) 0.0 else (time - lastTime)
            lastTime = time

            containerElement?.let { element ->
                val loopWidth = element.scrollWidth / 3.0
                val endPos = loopWidth * 2

                if (element.scrollLeft < 1) element.scrollLeft = loopWidth

                if (!isHovered && !isUserInteracting) {
                    element.scrollLeft += (delta * speed)
                }

                if (element.scrollLeft >= endPos) {
                    element.scrollLeft -= loopWidth
                } else if (element.scrollLeft <= 0) {
                    element.scrollLeft += loopWidth
                }
            }
            animId = window.requestAnimationFrame { step(it) }
        }

        animId = window.requestAnimationFrame { step(it) }
        onDispose { window.cancelAnimationFrame(animId) }
    }

    DisposableEffect(containerElement) {
        val element = containerElement ?: return@DisposableEffect onDispose { }

        var resizeTimerId: Int? = null

        var isInitialLayout = true

        val observer = ResizeObserver { _, _ ->
            if (isInitialLayout) {
                isInitialLayout = false
                return@ResizeObserver
            }

            isUserInteracting = true

            resizeTimerId?.let { window.clearTimeout(it) }
            resizeTimerId = window.setTimeout({
                isUserInteracting = false
            }, 400)
        }

        observer.observe(element)

        onDispose {
            resizeTimerId?.let { window.clearTimeout(it) }
            observer.disconnect()
        }
    }
    Box(
        modifier = InfiniteCarouselContainerStyle
            .toModifier()
            .then(modifier)
            .onMouseEnter { isHovered = true }
            .onMouseLeave { isHovered = false }
            .onFocusIn { isUserInteracting = true }
            .onFocusOut { isUserInteracting = false }
            .onTouchStart {
                isUserInteracting = true
                touchTimerId?.let { window.clearTimeout(it) }
            }
            .onTouchEnd {
                resumePlayAfterDelay(touchTimerId) { touchTimerId = it }
            }
            .onTouchCancel {
                resumePlayAfterDelay(touchTimerId) { touchTimerId = it }
            },
        ref = ref { containerElement = it },
    ) {
        Box(modifier = InfiniteCarouselTrackStyle.toModifier()) {
            repeat(3) {
                baseItems.forEach { item -> itemContent(item) }
            }
        }
    }
}
val InfiniteCarouselContainerStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .overflow {
                x(Overflow.Auto)
                y(Overflow.Hidden)
            }
            .scrollbarWidth(ScrollbarWidth.None)
    }
}

val InfiniteCarouselTrackStyle = CssStyle {
    base {
        Modifier
            .display(DisplayStyle.Flex)
            .flexDirection(FlexDirection.Row)
            .gap(24.px)
            .width(Width.MaxContent)
    }
}