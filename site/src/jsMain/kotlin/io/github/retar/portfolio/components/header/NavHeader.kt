package io.github.retar.portfolio.components.header

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.JustifyContent
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderBottom
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.components.icons.CloseIcon
import com.varabyte.kobweb.silk.components.icons.HamburgerIcon
import com.varabyte.kobweb.silk.components.overlay.Overlay
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.breakpoint.displayIfAtLeast
import com.varabyte.kobweb.silk.style.breakpoint.displayUntil
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.resources.StringRes
import io.github.retar.portfolio.styles.AppColors
import io.github.retar.portfolio.styles.DescriptorStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Composable
fun NavHeader() {
    var isMenuOpen by remember { mutableStateOf(false) }

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
                modifier = Modifier
                    .gap(24.px)
                    .displayIfAtLeast(Breakpoint.MD),
            ) {
                HeaderNavItem(StringRes.NavIntro)
                HeaderNavItem(StringRes.NavProjects)
                HeaderNavItem(StringRes.NavArticles)
                HeaderNavItem(StringRes.NavContact)
            }

            Box(
                modifier = Modifier.displayUntil(Breakpoint.MD),
                contentAlignment = Alignment.Center,
            ) {
                HamburgerIcon(
                    modifier = DescriptorStyle
                        .toModifier()
                        .onClick { isMenuOpen = true },
                )
            }
        }

        if (isMenuOpen) {
            MobileNavOverlay(onClose = { isMenuOpen = false })
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

@Composable
private fun MobileNavOverlay(onClose: () -> Unit) {
    Overlay(
        Modifier.onClick { onClose() },
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .justifyContent(JustifyContent.FlexEnd),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(260.px)
                    .backgroundColor(AppColors.DrawerBackground)
                    .padding(16.px)
                    .onClick { it.stopPropagation() },
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .gap(16.px)
                        .textAlign(TextAlign.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    CloseIcon(
                        modifier = DescriptorStyle
                            .toModifier()
                            .onClick { onClose() },
                    )
                    HeaderNavItem(StringRes.NavIntro)
                    HeaderNavItem(StringRes.NavProjects)
                    HeaderNavItem(StringRes.NavArticles)
                    HeaderNavItem(StringRes.NavContact)
                }
            }
        }
    }
}

