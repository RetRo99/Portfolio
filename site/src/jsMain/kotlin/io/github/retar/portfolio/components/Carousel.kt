package io.github.retar.portfolio.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.styles.ProjectsCarouselContainerStyle
import io.github.retar.portfolio.styles.ProjectsCarouselTrackStyle
import org.jetbrains.compose.web.dom.Div

@Composable
fun <T> InfiniteCarousel(
    items: List<T>,
    modifier: Modifier = Modifier,
    itemContent: @Composable (T) -> Unit,
) {
    val baseRepeatCount = if (items.size < 4) 3 else 1
    val baseItems = List(baseRepeatCount) { items }.flatten()

    Box(
        modifier = ProjectsCarouselContainerStyle
            .toModifier()
            .then(modifier)
    ) {
        Box(
            modifier = ProjectsCarouselTrackStyle
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

