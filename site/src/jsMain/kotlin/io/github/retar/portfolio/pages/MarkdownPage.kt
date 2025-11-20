package io.github.retar.portfolio.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.core.layout.Layout
import io.github.retar.portfolio.components.PortfolioSection
import io.github.retar.portfolio.components.layouts.PageLayout

import org.jetbrains.compose.web.css.AlignItems

@Composable
@Layout(".components.layouts.PageLayout")
fun MarkdownPage(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .alignItems(AlignItems.Center)
    ) {
        PortfolioSection {
            content()
        }
    }
}
