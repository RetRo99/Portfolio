package io.github.retar.portfolio.components.header

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.JustifyContent
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.borderBottom
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.resources.StringRes
import io.github.retar.portfolio.styles.AppColors
import io.github.retar.portfolio.styles.DescriptorStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Composable
fun NavHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .borderBottom(1.px, LineStyle.Solid, AppColors.ButtonBorder),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier
                .padding(topBottom = 16.px, leftRight = 16.px)
                .width(100.percent)
                .justifyContent(JustifyContent.SpaceBetween),
        ) {
            SpanText(
                text = StringRes.HeaderTitle.value,
                modifier = DescriptorStyle.toModifier(),
            )

            Row(
                modifier = Modifier.gap(24.px),
            ) {
                HeaderNavItem(StringRes.NavIntro)
                HeaderNavItem(StringRes.NavProjects)
                HeaderNavItem(StringRes.NavArticles)
                HeaderNavItem(StringRes.NavContact)
            }
        }
    }
}

@Composable
private fun HeaderNavItem(label: StringRes) {
    SpanText(
        text = label.value,
        modifier = DescriptorStyle.toModifier(),
    )
}

