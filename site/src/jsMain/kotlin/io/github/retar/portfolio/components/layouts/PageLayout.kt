package io.github.retar.portfolio.components.layouts

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.core.layout.Layout
import io.github.retar.portfolio.components.footer.Footer
import io.github.retar.portfolio.components.header.NavHeader

@Layout
@Composable
fun PageLayout(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        NavHeader()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            content()
        }
        Footer()
    }
}

