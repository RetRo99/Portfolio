package io.github.retar.portfolio.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.AnimationIterationCount
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.WhiteSpace
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.animation
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.translateX
import com.varabyte.kobweb.compose.ui.modifiers.whiteSpace
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.AnimationPlayState
import org.jetbrains.compose.web.css.AnimationTimingFunction
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.s

@Composable
fun <T> InfiniteCarousel(
    items: List<T>,
    modifier: Modifier = Modifier,
    itemContent: @Composable (T) -> Unit,
) {
    val baseRepeatCount = if (items.size < 4) 3 else 1
    val baseItems = List(baseRepeatCount) { items }.flatten()

    Box(
        modifier = InfiniteCarouselContainerStyle
            .toModifier()
            .then(modifier)
    ) {
        Box(
            modifier = InfiniteCarouselTrackStyle
                .toModifier()
        ) {
            repeat(2) {
                baseItems.forEach { item ->
                    itemContent(item)
                }
            }
        }
    }
}

val InfiniteCarouselScrollKeyframes = Keyframes {
    from {
        Modifier.translateX(0.px)
    }

    to {
        Modifier.translateX((-50).percent)
    }
}

val InfiniteCarouselContainerStyle = CssStyle {
    base {
        Modifier.overflow { x(Overflow.Hidden) }
    }
}

val InfiniteCarouselTrackStyle = CssStyle {
    base {
        Modifier
            .display(DisplayStyle.LegacyInlineFlex)
            .gap(24.px)
            .whiteSpace(WhiteSpace.NoWrap)
            .animation(
                InfiniteCarouselScrollKeyframes.toAnimation(
                    duration = 40.s,
                    iterationCount = AnimationIterationCount.Infinite,
                    timingFunction = AnimationTimingFunction.Linear,
                ),
            )
    }

    hover {
        Modifier
            .animation(
                InfiniteCarouselScrollKeyframes.toAnimation(
                    duration = 40.s,
                    iterationCount = AnimationIterationCount.Infinite,
                    timingFunction = AnimationTimingFunction.Linear,
                    playState = AnimationPlayState.Paused,
                ),
            )
    }
}


