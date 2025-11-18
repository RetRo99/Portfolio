package io.github.retar.portfolio.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.PointerEvents
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.functions.brightness
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.filter
import com.varabyte.kobweb.compose.ui.modifiers.objectFit
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.onMouseEnter
import com.varabyte.kobweb.compose.ui.modifiers.onMouseLeave
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.pointerEvents
import com.varabyte.kobweb.compose.ui.modifiers.scale
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.resources.ImageRes
import io.github.retar.portfolio.styles.ImageTextHoverStyle
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px

val CardImageStyle = CssStyle {
    base {
        Modifier
            .width(220.px)
            .borderRadius(16.px)
            .overflow(Overflow.Hidden)
            .transition(Transition.all(duration = 500.ms))
    }

    hover {
        Modifier
            .filter(brightness(0.6))
            .scale(1.1)
    }
}

@Composable
fun ImageCard(
    image: ImageRes,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    onClick: (() -> Unit)? = null,
    overlay: (@Composable () -> Unit)? = null,
) {
    var hasMouse by remember { mutableStateOf(false) }

    Box(
        modifier = CardImageStyle
            .toModifier()
            .then(modifier)
            .onMouseEnter { hasMouse = true }
            .onMouseLeave { hasMouse = false },
        contentAlignment = Alignment.Center,
    ) {
        val imageWrapperModifier = if (onClick != null) {
            Modifier
                .fillMaxSize()
                .cursor(Cursor.Pointer)
                .onClick { onClick() }
        } else {
            Modifier.fillMaxSize()
        }

        Box(modifier = imageWrapperModifier) {
            Image(
                src = image.path,
                description = contentDescription ?: "",
                modifier = Modifier
                    .fillMaxSize()
                    .objectFit(ObjectFit.Cover)
            )
        }

        if (hasMouse && overlay != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerEvents(PointerEvents.None),
                contentAlignment = Alignment.Center,
            ) {
                overlay()
            }
        }
    }
}

@Composable
fun ProjectCard(
    image: ImageRes,
    modifier: Modifier = Modifier,
) {
    ImageCard(
        image = image,
        modifier = modifier,
    )
}

@Composable
fun ArticleCard(
    title: String,
    description: String,
    route: String,
    image: ImageRes,
    modifier: Modifier = Modifier,
) {
    val router = rememberPageContext().router

    var hasMouse by remember { mutableStateOf(false) }
    ImageCard(
        image = image,
        modifier = modifier,
        contentDescription = title,
        onClick = {
            hasMouse = !hasMouse
//            router.navigateTo(route)
        },
    ) {
        SpanText(
            text = hasMouse.toString(),
            modifier = ImageTextHoverStyle.toModifier(),
        )
    }
}

