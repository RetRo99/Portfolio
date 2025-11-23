package io.github.retar.portfolio.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.core.layout.Layout
import io.github.retar.portfolio.PortfolioSectionId
import io.github.retar.portfolio.components.PortfolioSection
import org.jetbrains.compose.web.css.AlignItems

@Composable
@Layout(".components.layouts.PageLayout")
fun MarkdownPage(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .alignItems(AlignItems.FlexStart)
    ) {
        PortfolioSection(
            section = PortfolioSectionId.MarkdownPage,
            modifier = Modifier.fillMaxWidth(),
        ) {
            content()
        }
    }
}
