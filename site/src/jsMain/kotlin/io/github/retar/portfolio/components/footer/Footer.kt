package io.github.retar.portfolio.components.footer

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.styles.DescriptorStyle
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Composable
fun Footer() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .padding(topBottom = 24.px, leftRight = 16.px)
                .width(100.percent)
                .maxWidth(1200.px),
            contentAlignment = Alignment.Center,
        ) {
            SpanText(
                text = "© 2025 Rok Retar",
                modifier = DescriptorStyle.toModifier(),
            )
        }
    }
}

