package io.github.retar.portfolio.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.gap
import io.github.retar.portfolio.resources.LinkRes
import io.github.retar.portfolio.resources.StringRes
import org.jetbrains.compose.web.css.px

@Composable
fun SocialButtons(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.gap(8.px),
    ) {
        PortfolioButton(
            url = LinkRes.External.GitHub,
            label = StringRes.Github.value,
        )
        PortfolioButton(
            url = LinkRes.External.LinkedIn,
            label = StringRes.LinkedIn.value,
        )
    }
}

