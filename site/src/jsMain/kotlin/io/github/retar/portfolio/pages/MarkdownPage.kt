package io.github.retar.portfolio.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.core.layout.Layout
import io.github.retar.portfolio.LocalSetActiveSection
import io.github.retar.portfolio.components.PortfolioSectionId
import kotlinx.browser.window
import org.jetbrains.compose.web.css.AlignItems

@Composable
@Layout(".components.layouts.PageLayout")
fun MarkdownPage(content: @Composable () -> Unit) {
    val setActive = LocalSetActiveSection.current

    LaunchedEffect(Unit) {
        window.asDynamic().Prism?.highlightAll()
        setActive(PortfolioSectionId.Blog)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .alignItems(AlignItems.FlexStart),
    ) {
        content()
    }
}
