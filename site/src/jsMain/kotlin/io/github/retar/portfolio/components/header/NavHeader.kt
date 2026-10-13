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
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.rotate
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
import com.varabyte.kobweb.compose.ui.styleModifier
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
import io.github.retar.portfolio.styles.MonoEyebrowStyle
import io.github.retar.portfolio.styles.sitePalette
import io.github.retar.portfolio.styles.toSitePalette
import io.github.retar.portfolio.utils.trackEvent
import kotlinx.browser.document
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.CSSLengthValue
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.deg
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgba
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.css.vw
import org.w3c.dom.HTMLElement
import kotlin.js.json

val ThemeToggleSpinKeyframes = Keyframes {
    from {
        Modifier.rotate(0.deg)
    }
    to {
        Modifier.rotate(360.deg)
    }
}

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
        .styleModifier {
            property("border", "1px solid ${colorMode.toSitePalette().border}")
            property("box-shadow", "0 16px 32px -16px rgba(40, 28, 16, 0.25)")
        }
        .zIndex(10)
}

val NavLinkStyle = CssStyle {
    base {
        Modifier
            .userSelect(UserSelect.None)
            .cursor(Cursor.Pointer)
            .styleModifier {
                property("color", "var(--nav-color)")
                property("font-size", "14px")
                property("line-height", "1.4")
                property("text-decoration-line", "underline")
                property("text-decoration-thickness", "1px")
                property("text-underline-offset", "6px")
                property("transition", "color 160ms ease, text-decoration-color 160ms ease")
            }
    }
    hover {
        Modifier.color(colorMode.toSitePalette().textPrimary)
    }
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
            .position(Position.Relative)
            .styleModifier {
                property("max-width", "1200px")
                property("margin-inline", "auto")
                property("padding-inline", "clamp(1rem, 4vw, 2.5rem)")
                property("box-sizing", "border-box")
            },
        contentAlignment = Alignment.Center,
        ref = ref { element -> headerElement = element }
    ) {
        Row(
            modifier = Modifier
                .padding(topBottom = 18.px, leftRight = 0.px)
                .width(100.percent)
                .justifyContent(JustifyContent.SpaceBetween),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left side: Profile image + Name and subtitle
            Row(
                modifier = Modifier
                    .gap(14.px)
                    .alignItems(AlignItems.Baseline)
                .cursor(Cursor.Pointer)
                .onClick {
                    trackEvent("logo-click")
                    router.navigateTo("/")
                },
            ) {
                SpanText(
                    text = StringRes.HeaderTitle.value,
                    modifier = Modifier.styleModifier {
                        property("font-family", "'Instrument Serif', Georgia, serif")
                        property("font-size", "1.625rem")
                        property("line-height", "1")
                        property("letter-spacing", "-0.01em")
                        property("color", palette.textPrimary.toString())
                    },
                )
                SpanText(
                    text = StringRes.Descriptor.value,
                    modifier = MonoEyebrowStyle.toModifier().displayIfAtLeast(Breakpoint.LG),
                )
            }

            // Right side: Nav items + Social icons (desktop)
            Row(
                modifier = Modifier
                    .gap(24.px)
                    .displayIfAtLeast(Breakpoint.MD)
                    .alignItems(AlignItems.Center),
            ) {
                DesktopNavItems()
                Box(
                    modifier = Modifier
                        .width(1.px)
                        .height(16.px)
                        .backgroundColor(palette.border),
                )
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
                                .onClick {
                                    isMenuOpen = true
                                    trackEvent("mobile-menu-open")
                                },
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
            ref = ref {
                it.asDynamic().setAttribute("rel", "noopener noreferrer")
                it.asDynamic().setAttribute("aria-label", "Rok Retar on GitHub")
            },
        ) {
            FaGithub(
                modifier = iconModifier
                    .onClick { trackEvent("social-link-click", json("platform" to "github")) },
                size = IconSize.LG,
            )
        }
        Link(
            path = LinkRes.External.LinkedIn,
            modifier = Modifier.textDecorationLine(TextDecorationLine.None),
            ref = ref {
                it.asDynamic().setAttribute("rel", "noopener noreferrer")
                it.asDynamic().setAttribute("aria-label", "Rok Retar on LinkedIn")
            },
        ) {
            FaLinkedin(
                modifier = iconModifier
                    .onClick { trackEvent("social-link-click", json("platform" to "linkedin")) },
                size = IconSize.LG,
            )
        }
        Link(
            path = "mailto:rok.retar@gmail.com",
            modifier = Modifier.textDecorationLine(TextDecorationLine.None),
            ref = ref {
                it.asDynamic().setAttribute("rel", "noopener noreferrer")
                it.asDynamic().setAttribute("aria-label", "Email Rok Retar")
            },
        ) {
            FaEnvelope(
                modifier = iconModifier
                    .onClick { trackEvent("social-link-click", json("platform" to "email")) },
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
        modifier = NavLinkStyle
            .toModifier()
            .styleModifier {
                property("--nav-color", (if (isActive) palette.textPrimary else palette.textSecondary).toString())
                property("text-decoration-color", if (isActive) palette.accent.toString() else "transparent")
            }
            .onClick {
                trackEvent("nav-click", json("item" to item.name))
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
            .margin(top = 4.px)
            .padding(topBottom = 8.px, leftRight = 20.px)
            .animation(MobileNavSlideDownKeyframes.toAnimation(duration = 240.ms))
            .styleModifier { property("animation-timing-function", "var(--ease-out)") }
            .onClick { it.stopPropagation() },
    ) {
        NavItems(onItemClick = onClose)
        Box(modifier = Modifier.padding(topBottom = 20.px)) {
            HeaderSocialIcons()
        }
    }
}

@Composable
private fun NavItems(onItemClick: () -> Unit) {
    NavItem.entries.forEachIndexed { index, item ->
        HeaderNavItem(
            item = item,
            index = index,
            onClick = onItemClick,
        )
    }
}

@Composable
private fun HeaderNavItem(
    item: NavItem,
    index: Int,
    onClick: () -> Unit,
) {
    val pageContext = rememberPageContext()
    val router = pageContext.router
    val activeSection = LocalActiveSection.current
    val setActiveSection = LocalSetActiveSection.current
    val isActive = item.section == activeSection
    val palette = sitePalette()

    val coroutineScope = rememberCoroutineScope()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .gap(16.px)
            .alignItems(AlignItems.Baseline)
            .padding(topBottom = 14.px)
            .userSelect(UserSelect.None)
            .cursor(Cursor.Pointer)
            .styleModifier { property("border-bottom", "1px solid ${palette.border}") }
            .onClick {
                onClick()
                trackEvent("nav-click", json("item" to item.name))
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
    ) {
        SpanText(
            text = (index + 1).toString().padStart(2, '0'),
            modifier = MonoEyebrowStyle.toModifier(),
        )
        SpanText(
            text = item.label.value,
            modifier = Modifier.styleModifier {
                property("font-family", "'Instrument Serif', Georgia, serif")
                property("font-size", "2rem")
                property("line-height", "1.05")
                property("color", (if (isActive) palette.accent else palette.textPrimary).toString())
            },
        )
    }
}

@Composable
private fun ThemeToggle(modifier: Modifier = Modifier) {
    var colorMode by ColorMode.currentState
    val palette = sitePalette()
    var spinKey by remember { mutableStateOf(0) }

    LaunchedEffect(colorMode) {
        colorMode.saveToLocalStorage(COLOR_MODE_KEY)
    }
    val iconModifier = DescriptorStyle
        .toModifier()
        .color(palette.textSecondary)
        .then(modifier)
        .cursor(Cursor.Pointer)
        .onClick {
            spinKey++
            colorMode = colorMode.opposite
            trackEvent("theme-toggle", json("mode" to colorMode.name.lowercase()))
        }

    val spinModifier = if (spinKey > 0) {
        Modifier.animation(
            ThemeToggleSpinKeyframes.toAnimation(duration = 500.ms),
        )
    } else {
        Modifier
    }

    if (colorMode == ColorMode.LIGHT) {
        SunIcon(modifier = iconModifier.then(spinModifier))
    } else {
        MoonIcon(modifier = iconModifier.then(spinModifier))
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
            modifier = MonoEyebrowStyle
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
                            val previousLanguage = language.code
                            language = item
                            isOpen = false
                            trackEvent(
                                "language-change",
                                json("from" to previousLanguage, "to" to item.code),
                            )
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

