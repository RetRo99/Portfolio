package io.github.retar.portfolio.components.header

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.JustifyContent
import com.varabyte.kobweb.compose.css.UserSelect
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.animation
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderBottom
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.left
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.translateX
import com.varabyte.kobweb.compose.ui.modifiers.translateY
import com.varabyte.kobweb.compose.ui.modifiers.userSelect
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.silk.components.icons.CloseIcon
import com.varabyte.kobweb.silk.components.icons.HamburgerIcon
import com.varabyte.kobweb.silk.components.icons.MoonIcon
import com.varabyte.kobweb.silk.components.icons.SunIcon
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.animation.toAnimation
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.breakpoint.displayIfAtLeast
import com.varabyte.kobweb.silk.style.breakpoint.displayUntil
import com.varabyte.kobweb.silk.style.extendedBy
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.saveToLocalStorage
import io.github.retar.portfolio.Language
import io.github.retar.portfolio.LocalActiveSection
import io.github.retar.portfolio.LocalLanguage
import io.github.retar.portfolio.LocalSetActiveSection
import io.github.retar.portfolio.LocalSetLanguage
import io.github.retar.portfolio.PortfolioSectionId
import io.github.retar.portfolio.resources.StringRes
import io.github.retar.portfolio.styles.DescriptorStyle
import io.github.retar.portfolio.styles.sitePalette
import io.github.retar.portfolio.styles.toSitePalette
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgba
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.css.vw
import org.w3c.dom.HTMLElement

val MobileNavSlideDownKeyframes = Keyframes {
    from {
        Modifier
            .opacity(0)
            .translateY((-10).percent)
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

val DropdownStyle = CssStyle.base {
    Modifier
        .backgroundColor(colorMode.toSitePalette().dropdownBackground)
        .borderRadius(8.px)
        .boxShadow(0.px, 4.px, 12.px, 0.px, rgba(0, 0, 0, 0.1))
        .zIndex(10)
}

@Composable
fun NavHeader() {
    var isMenuOpen by remember { mutableStateOf(false) }

    val palette = sitePalette()
    val borderColor = palette.headerBorder

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .position(Position.Relative)
            .borderBottom(1.px, LineStyle.Solid, borderColor),
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
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                NavItems({ isMenuOpen = false })
                Row(modifier = Modifier.gap(12.px)) {
                    ThemeToggle()
                    LanguageSwitcher()
                }
            }

            Box(
                modifier = Modifier.displayUntil(Breakpoint.MD),
                contentAlignment = Alignment.Center,
            ) {
                Row(
                    modifier = Modifier.gap(12.px),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ThemeToggle()
                    LanguageSwitcher()
                    if (isMenuOpen) {
                        CloseIcon(
                            modifier = MenuIconStyle
                                .toModifier()
                                .onClick { isMenuOpen = false },
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
        }

        if (isMenuOpen) {
            MobileNavDropdown(onClose = { isMenuOpen = false })
        }
    }
}

@Composable
private fun MobileNavDropdown(onClose: () -> Unit) {
    sitePalette()

    Box(
        modifier = Modifier
            .position(Position.Fixed)
            .top(0.px)
            .left(0.px)
            .width(100.vw)
            .height(100.vh)
            .zIndex(9)
            .onClick { onClose() }
    )

    Column(
        modifier = DropdownStyle.toModifier()
            .position(Position.Absolute)
            .top(100.percent)
            .left(0.px)
            .width(100.percent)
            .margin(top = 10.px)
            .padding(16.px)
            .gap(16.px)
            .animation(MobileNavSlideDownKeyframes.toAnimation(duration = 300.ms))
            .onClick { it.stopPropagation() },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        NavItems(onItemClick = onClose)
    }
}

@Composable
private fun NavItems(onItemClick: () -> Unit) {
    NavItem.entries.forEach { item ->
        HeaderNavItem(
            item = item,
            onClick = onItemClick,
        )
    }
}

@Composable
private fun HeaderNavItem(
    item: NavItem,
    onClick: () -> Unit,
) {
    val activeSection = LocalActiveSection.current
    val setActiveSection = LocalSetActiveSection.current
    val isActive = item.section == activeSection
    val palette = sitePalette()

    SpanText(
        text = item.label.value,
        modifier = DescriptorStyle
            .toModifier()
            .cursor(Cursor.Pointer)
            .color(if (isActive) palette.primary else palette.textSecondary)
            .onClick {
                onClick()

                val section = item.section
                if (section != null) {
                    setActiveSection(section)

                    val target =
                        document
                            .getElementById(section.domId)
                                as? HTMLElement
                            ?: return@onClick

                    val headerHeight =
                        (document.getElementById(PortfolioSectionId.Header.domId) as? HTMLElement)
                            ?.offsetHeight
                            ?.toDouble()
                            ?: 0.0

                    val targetY =
                        target.getBoundingClientRect().top +
                                window.scrollY -
                                headerHeight

                    window.scrollTo(x = 0.0, y = targetY)
                }
            },
    )
}

@Composable
private fun ThemeToggle(modifier: Modifier = Modifier) {
    var colorMode by ColorMode.currentState

    LaunchedEffect(colorMode) {
        colorMode.saveToLocalStorage()
    }
    val iconModifier = DescriptorStyle
        .toModifier()
        .then(modifier)
        .cursor(Cursor.Pointer)
        .onClick {
            colorMode = colorMode.opposite
        }

    if (colorMode == ColorMode.LIGHT) {
        SunIcon(modifier = iconModifier)
    } else {
        MoonIcon(modifier = iconModifier)
    }
}

@Composable
private fun LanguageSwitcher(modifier: Modifier = Modifier) {
    val language = LocalLanguage.current
    val setLanguage = LocalSetLanguage.current
    var isOpen by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.position(Position.Relative),
        contentAlignment = Alignment.Center
    ) {
        SpanText(
            text = language.code.uppercase(),
            modifier = DescriptorStyle
                .toModifier()
                .cursor(Cursor.Pointer)
                .onClick { isOpen = !isOpen }
        )

        if (isOpen) {
            Box(
                modifier = Modifier
                    .position(Position.Fixed)
                    .top(0.px)
                    .left(0.px)
                    .width(100.vw)
                    .height(100.vh)
                    .zIndex(9)
                    .onClick { isOpen = false }
            )

            Column(
                modifier = DropdownStyle.toModifier()
                    .position(Position.Absolute)
                    .top(100.percent)
                    .left(50.percent)
                    .translateY(10.px)
                    .translateX((-50).percent)
                    .padding(8.px)
                    .minWidth(150.px)
            ) {
                Language.entries.forEach { item ->
                    LanguageItem(
                        language = item,
                        isSelected = item == language,
                        onClick = {
                            setLanguage(item)
                            isOpen = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun LanguageItem(
    language: Language,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val palette = sitePalette()

    Box(
        modifier = Modifier
            .width(100.percent)
            .padding(8.px)
            .cursor(Cursor.Pointer)
            .onClick { onClick() }
    ) {
        SpanText(
            text = language.label,
            modifier = DescriptorStyle
                .toModifier()
                .color(if (isSelected) palette.primary else palette.textSecondary)
        )
    }
}

