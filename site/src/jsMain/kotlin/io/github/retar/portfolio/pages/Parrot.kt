package io.github.retar.portfolio.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.styles.HeadingLStyle
import io.github.retar.portfolio.styles.HeadingXLStyle
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.px

@Page
@Layout(".components.layouts.PageLayout")
@Composable
fun ParrotPage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .alignItems(AlignItems.Center)
                .gap(16.px)
                .padding(top = 80.px, bottom = 80.px),
        ) {
            SpanText(
                text = "🦜",
                modifier = HeadingXLStyle.toModifier(),
            )

            SpanText(
                text = "Parrot",
                modifier = HeadingXLStyle.toModifier(),
            )

            SpanText(
                text = "Welcome to the Parrot page!",
                modifier = HeadingLStyle.toModifier(),
            )
        }
    }
}

