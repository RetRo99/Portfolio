package io.github.retar.portfolio.components.header

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.JustifyContent
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.UserSelect
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.animation
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderBottom
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.left
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.translateY
import com.varabyte.kobweb.compose.ui.modifiers.userSelect
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.components.icons.CloseIcon
import com.varabyte.kobweb.silk.components.icons.HamburgerIcon
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.animation.toAnimation
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.breakpoint.displayIfAtLeast
import com.varabyte.kobweb.silk.style.breakpoint.displayUntil
import com.varabyte.kobweb.silk.style.extendedBy
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.resources.StringRes
import io.github.retar.portfolio.styles.AppColors
import io.github.retar.portfolio.styles.DescriptorStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

val MobileNavSlideDownKeyframes = Keyframes {
    from {
        Modifier
            .opacity(0)
            .translateY((-10).percent) // Starts slightly tucked up
    }
    to {
        Modifier
            .opacity(1)
            .translateY(0.percent)
    }
}

val MenuIconStyle = DescriptorStyle.extendedBy {
    base {
        Modifier
            .userSelect(UserSelect.None)
            .cursor(Cursor.Pointer)
    }
}

@Composable
fun NavHeader() {
    var isMenuOpen by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .position(Position.Relative)
            .borderBottom(1.px, LineStyle.Solid, AppColors.ButtonBorder),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier
                .padding(topBottom = 16.px, leftRight = 16.px)
                .width(100.percent)
                .justifyContent(JustifyContent.SpaceBetween),
            verticalAlignment = Alignment.CenterVertically
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
                if (isMenuOpen) {
                    CloseIcon(
                        modifier = MenuIconStyle
                            .toModifier()
                            .onClick { isMenuOpen = false }
                    )
                } else {
                    HamburgerIcon(
                        modifier = MenuIconStyle
                            .toModifier()
                            .onClick { isMenuOpen = true },
                    )
                }
            }
        }

        if (isMenuOpen) {
            MobileNavDropdown(onClose = { isMenuOpen = false })
        }
    }
}

@Composable
private fun MobileNavDropdown(onClose: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .position(Position.Absolute)
            .top(100.percent)
            .left(0.px)
            .backgroundColor(AppColors.DrawerBackground)
            .padding(top = 24.px)
            .animation(MobileNavSlideDownKeyframes.toAnimation(duration = 300.ms))
            .onClick { onClose() },
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .gap(16.px)
                .textAlign(TextAlign.Center)
                .padding(bottom = 16.px)
                .onClick { it.stopPropagation() },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HeaderNavItem(StringRes.NavIntro)
            HeaderNavItem(StringRes.NavProjects)
            HeaderNavItem(StringRes.NavArticles)
            HeaderNavItem(StringRes.NavContact)
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
