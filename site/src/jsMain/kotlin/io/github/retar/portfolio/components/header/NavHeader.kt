package io.github.retar.portfolio.components.header

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.browser.dom.observers.ResizeObserver
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.JustifyContent
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.css.StyleVariable
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.UserSelect
import com.varabyte.kobweb.compose.dom.ref
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.animation
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.draggable
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.left
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.objectFit
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.scale
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.translateX
import com.varabyte.kobweb.compose.ui.modifiers.translateY
import com.varabyte.kobweb.compose.ui.modifiers.userSelect
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.CloseIcon
import com.varabyte.kobweb.silk.components.icons.HamburgerIcon
import com.varabyte.kobweb.silk.components.icons.MoonIcon
import com.varabyte.kobweb.silk.components.icons.SunIcon
import com.varabyte.kobweb.silk.components.icons.fa.FaEnvelope
import com.varabyte.kobweb.silk.components.icons.fa.FaGithub
import com.varabyte.kobweb.silk.components.icons.fa.FaLinkedin
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.animation.toAnimation
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.breakpoint.displayIfAtLeast
import com.varabyte.kobweb.silk.style.breakpoint.displayUntil
import com.varabyte.kobweb.silk.style.extendedBy
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.saveToLocalStorage
import io.github.retar.portfolio.COLOR_MODE_KEY
import io.github.retar.portfolio.Language
import io.github.retar.portfolio.LocalActiveSection
import io.github.retar.portfolio.LocalSetActiveSection
import io.github.retar.portfolio.resources.ImageRes
import io.github.retar.portfolio.resources.LinkRes
import io.github.retar.portfolio.resources.StringRes
import io.github.retar.portfolio.saveToLocalStorage
import io.github.retar.portfolio.styles.BodySmallStyle
import io.github.retar.portfolio.styles.DescriptorStyle
import io.github.retar.portfolio.styles.LabelStyle
import io.github.retar.portfolio.styles.sitePalette
import io.github.retar.portfolio.styles.toSitePalette
import kotlinx.browser.document
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.CSSLengthValue
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

val NavHeaderHeight by StyleVariable<CSSLengthValue>(prefix = "site", defaultFallback = 0.px)

val ProfileImageStyle = CssStyle {
    base {
        Modifier
            .transition(Transition.all(duration = 200.ms))
    }

    hover {
        Modifier.scale(1.1)
    }
}

@Composable
fun NavHeader() {
    var isMenuOpen by remember { mutableStateOf(false) }

    val palette = sitePalette()
    val router = rememberPageContext().router
    var headerElement by remember { mutableStateOf<HTMLElement?>(null) }
    DisposableEffect(headerElement) {
        val element = headerElement
        if (element != null) {
            val observer = ResizeObserver { entries, _ ->
                val height = entries.firstOrNull()?.contentRect?.height ?: 0.0

                // FIX: We check if the name already has dashes, if not we add them.
                val variableName = NavHeaderHeight.name.let {
                    if (it.startsWith("--")) it else "--$it"
                }

                (document.documentElement as? HTMLElement)?.style?.setProperty(
                    variableName,
                    "${height}px"
                )
            }
            observer.observe(element)
            onDispose { observer.disconnect() }
        } else {
            onDispose { }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .position(Position.Relative),
        contentAlignment = Alignment.Center,
        ref = ref { element -> headerElement = element }
    ) {
        Row(
            modifier = Modifier
                .padding(topBottom = 16.px, leftRight = 16.px)
                .width(100.percent)
                .justifyContent(JustifyContent.SpaceBetween),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left side: Profile image + Name and subtitle
            Row(
                modifier = Modifier
                    .gap(12.px)
                    .cursor(Cursor.Pointer)
                    .onClick {
                        router.navigateTo("/")
                    },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    src = ImageRes.ProfileImage.path,
                    description = StringRes.ProfileImageDesc.value,
                    modifier = ProfileImageStyle.toModifier()
                        .size(40.px)
                        .borderRadius(999.px)
                        .objectFit(ObjectFit.Cover)
                        .draggable(false)
                        .userSelect(UserSelect.None),
                )
                Column(
                    modifier = Modifier.gap(0.px),
                ) {
                    SpanText(
                        text = StringRes.HeaderTitle.value,
                        modifier = LabelStyle.toModifier(),
                    )
                    SpanText(
                        text = StringRes.Descriptor.value,
                        modifier = BodySmallStyle.toModifier(),
                    )
                }
            }

            // Right side: Nav items + Social icons (desktop)
            Row(
                modifier = Modifier
                    .gap(24.px)
                    .displayIfAtLeast(Breakpoint.MD)
                    .alignItems(AlignItems.Center),
            ) {
                DesktopNavItems()
                Row(
                    modifier = Modifier.gap(16.px),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    HeaderSocialIcons()
                    Row(modifier = Modifier.gap(12.px)) {
                        ThemeToggle()
                        LanguageSwitcher()
                    }
                }
            }

            // Mobile menu
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
                    LanguageSwitcher(
                        isMenuOpen = isMenuOpen,
                        onMenuClose = { isMenuOpen = false },
                    )
                    if (isMenuOpen) {
                        CloseIcon(
                            modifier = MenuIconStyle
                                .toModifier()
                                .color(palette.textSecondary)
                                .onClick { isMenuOpen = false },
                        )
                    } else {
                        HamburgerIcon(
                            modifier = MenuIconStyle
                                .toModifier()
                                .color(palette.textSecondary)
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
private fun HeaderSocialIcons() {
    val palette = sitePalette()
    val iconModifier = Modifier
        .color(palette.textSecondary)
        .cursor(Cursor.Pointer)

    Row(
        modifier = Modifier.gap(16.px),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Link(
            path = LinkRes.External.GitHub,
            modifier = Modifier.textDecorationLine(TextDecorationLine.None),
            ref = ref { it.asDynamic().setAttribute("rel", "noopener noreferrer") },
        ) {
            FaGithub(
                modifier = iconModifier,
                size = IconSize.LG,
            )
        }
        Link(
            path = LinkRes.External.LinkedIn,
            modifier = Modifier.textDecorationLine(TextDecorationLine.None),
            ref = ref { it.asDynamic().setAttribute("rel", "noopener noreferrer") },
        ) {
            FaLinkedin(
                modifier = iconModifier,
                size = IconSize.LG,
            )
        }
        Link(
            path = "mailto:rok.retar@gmail.com",
            modifier = Modifier.textDecorationLine(TextDecorationLine.None),
            ref = ref { it.asDynamic().setAttribute("rel", "noopener noreferrer") },
        ) {
            FaEnvelope(
                modifier = iconModifier,
                size = IconSize.LG,
            )
        }
    }
}

@Composable
private fun DesktopNavItems() {
    Row(
        modifier = Modifier.gap(20.px),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        NavItem.entries.forEach { item ->
            DesktopNavItem(item)
        }
    }
}

@Composable
private fun DesktopNavItem(item: NavItem) {
    val router = rememberPageContext().router
    val activeSection = LocalActiveSection.current
    val setActiveSection = LocalSetActiveSection.current
    val isActive = item.section == activeSection
    val palette = sitePalette()
    val coroutineScope = rememberCoroutineScope()

    SpanText(
        text = item.label.value,
        modifier = DescriptorStyle
            .toModifier()
            .userSelect(UserSelect.None)
            .cursor(Cursor.Pointer)
            .color(if (isActive) palette.accent else palette.textSecondary)
            .transition(Transition.all(duration = 150.ms))
            .onClick {
                val section = item.section
                if (section != null) {
                    router.tryRoutingTo(item.route + section.path)
                } else {
                    router.navigateTo(item.route)
                }
                coroutineScope.launch {
                    delay(200)
                    setActiveSection(item.section)
                }
            },
    )
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
        HeaderSocialIcons()
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
    val pageContext = rememberPageContext()
    val router = pageContext.router
    val activeSection = LocalActiveSection.current
    val setActiveSection = LocalSetActiveSection.current
    val isActive = item.section == activeSection
    val palette = sitePalette()

    val coroutineScope = rememberCoroutineScope()
    SpanText(
        text = item.label.value,
        modifier = DescriptorStyle
            .toModifier()
            .userSelect(UserSelect.None)
            .cursor(Cursor.Pointer)
            .color(if (isActive) palette.accent else palette.textSecondary)
            .transition(Transition.all(duration = 150.ms))
            .onClick {
                onClick()
                val section = item.section

                if (section != null) {
                    router.tryRoutingTo(
                        item.route + section.path
                    )
                } else {
                    router.navigateTo(item.route)
                }
                coroutineScope.launch {
                    delay(200)
                    setActiveSection(item.section)
                }
            },
    )
}

@Composable
private fun ThemeToggle(modifier: Modifier = Modifier) {
    var colorMode by ColorMode.currentState
    val palette = sitePalette()

    LaunchedEffect(colorMode) {
        colorMode.saveToLocalStorage(COLOR_MODE_KEY)
    }
    val iconModifier = DescriptorStyle
        .toModifier()
        .color(palette.textSecondary)
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
private fun LanguageSwitcher(
    modifier: Modifier = Modifier,
    isMenuOpen: Boolean = false,
    onMenuClose: () -> Unit = {},
) {
    var language by Language.currentState
    var isOpen by remember { mutableStateOf(false) }
    val palette = sitePalette()
    val ctx = rememberPageContext()
    val currentPath = ctx.route.path

    LaunchedEffect(language) {
        language.saveToLocalStorage()
    }

    LaunchedEffect(isMenuOpen) {
        if (isMenuOpen) isOpen = false
    }

    LaunchedEffect(currentPath) {
        isOpen = false
    }
    Box(
        modifier = modifier.position(Position.Relative).userSelect(UserSelect.None),
        contentAlignment = Alignment.Center
    ) {
        SpanText(
            text = language.code.uppercase(),
            modifier = DescriptorStyle
                .toModifier()
                .color(palette.textSecondary)
                .cursor(Cursor.Pointer)
                .onClick {
                    isOpen = !isOpen
                    if (isOpen) onMenuClose()
                }
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
                            language = item
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
            .userSelect(UserSelect.None)
            .onClick { onClick() }
    ) {
        SpanText(
            text = language.label,
            modifier = DescriptorStyle
                .toModifier()
                .userSelect(UserSelect.None)
                .color(if (isSelected) palette.accent else palette.textSecondary)
        )
    }
}

