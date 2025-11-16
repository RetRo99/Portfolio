package io.github.retar.portfolio.styles

import com.varabyte.kobweb.compose.css.AnimationIterationCount
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.WhiteSpace
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.animation
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.translateX
import com.varabyte.kobweb.compose.ui.modifiers.whiteSpace
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.animation.toAnimation
import com.varabyte.kobweb.silk.style.selectors.hover
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.s

val ProjectsCarouselScrollKeyframes = Keyframes {
    from {
        Modifier.translateX(0.px)
    }

    to {
        Modifier.translateX((-50).percent)
    }
}

val ProjectsCarouselContainerStyle = CssStyle {
    base {
        Modifier.overflow { x(Overflow.Hidden) }
    }
}

val ProjectsCarouselTrackStyle = CssStyle {
    base {
        Modifier
            .gap(24.px)
            .styleModifier {
                // Use inline-flex so the track width matches its content, which is
                // required for the 50% marquee shift trick to be seamless.
                property("display", "inline-flex")
            }
            .whiteSpace(WhiteSpace.NoWrap)
            .animation(
                ProjectsCarouselScrollKeyframes.toAnimation(
                    colorMode = colorMode,
                    duration = 20.s,
                    iterationCount = AnimationIterationCount.Infinite,
                ),
            )
            .styleModifier {
                property("animation-timing-function", "linear")
            }
    }

    hover {
        Modifier.styleModifier {
            property("animation-play-state", "paused")
        }
    }
}

