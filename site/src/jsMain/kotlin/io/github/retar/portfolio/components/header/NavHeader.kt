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
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.compose.ui.modifiers.attr
import com.varabyte.kobweb.compose.ui.modifiers.onKeyDown
import com.varabyte.kobweb.compose.ui.thenIf
import org.jetbrains.compose.web.dom.Span
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

/**
 * Makes a clickable icon or label behave like a button: reachable with Tab, activated with Enter or
 * Space, announced with [label], and given a 40px square hit area around the glyph.
 */
private fun Modifier.headerButton(label: String, onActivate: () -> Unit): Modifier =
    this
        .attr("role", "button")
        .attr("tabindex", "0")
        .attr("aria-label", label)
        .styleModifier {
            property("display", "inline-flex")
            property("align-items", "center")
            property("justify-content", "center")
            property("min-width", "40px")
            property("min-height", "40px")
            property("box-sizing", "border-box")
        }
        .onClick { onActivate() }
        .onKeyDown { event ->
            if (event.key == "Enter" || event.key == " ") {
                event.preventDefault()
                onActivate()
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
            Link(
                path = "/",
                modifier = Modifier
                    .gap(14.px)
                    .alignItems(AlignItems.Baseline)
                    .textDecorationLine(TextDecorationLine.None)
                    .styleModifier { property("display", "flex") }
                    .attr("aria-label", StringRes.HomeLink.value)
                    .onClick { trackEvent("logo-click") },
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
                    modifier = Modifier.gap(2.px),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ThemeToggle()
                    LanguageSwitcher(
                        isMenuOpen = isMenuOpen,
                        onMenuClose = { isMenuOpen = false },
                    )
                    val menuLabel = if (isMenuOpen) StringRes.CloseMenu.value else StringRes.OpenMenu.value
                    Span(
                        attrs = MenuIconStyle
                            .toModifier()
                            .color(palette.textSecondary)
                            .attr("aria-expanded", isMenuOpen.toString())
                            .headerButton(menuLabel) {
                                isMenuOpen = !isMenuOpen
                                if (isMenuOpen) trackEvent("mobile-menu-open")
                            }
                            .toAttrs(),
                    ) {
                        if (isMenuOpen) CloseIcon() else HamburgerIcon()
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
            SocialIcon(platform = "github", viewBoxWidth = 496, path = "M165.9 397.4c0 2-2.3 3.6-5.2 3.6-3.3.3-5.6-1.3-5.6-3.6 0-2 2.3-3.6 5.2-3.6 3-.3 5.6 1.3 5.6 3.6zm-31.1-4.5c-.7 2 1.3 4.3 4.3 4.9 2.6 1 5.6 0 6.2-2s-1.3-4.3-4.3-5.2c-2.6-.7-5.5.3-6.2 2.3zm44.2-1.7c-2.9.7-4.9 2.6-4.6 4.9.3 2 2.9 3.3 5.9 2.6 2.9-.7 4.9-2.6 4.6-4.6-.3-1.9-3-3.2-5.9-2.9zM244.8 8C106.1 8 0 113.3 0 252c0 110.9 69.8 205.8 169.5 239.2 12.8 2.3 17.3-5.6 17.3-12.1 0-6.2-.3-40.4-.3-61.4 0 0-70 15-84.7-29.8 0 0-11.4-29.1-27.8-36.6 0 0-22.9-15.7 1.6-15.4 0 0 24.9 2 38.6 25.8 21.9 38.6 58.6 27.5 72.9 20.9 2.3-16 8.8-27.1 16-33.7-55.9-6.2-112.3-14.3-112.3-110.5 0-27.5 7.6-41.3 23.6-58.9-2.6-6.5-11.1-33.3 2.6-67.9 20.9-6.5 69 27 69 27 20-5.6 41.5-8.5 62.8-8.5s42.8 2.9 62.8 8.5c0 0 48.1-33.6 69-27 13.7 34.7 5.2 61.4 2.6 67.9 16 17.7 25.8 31.5 25.8 58.9 0 96.5-58.9 104.2-114.8 110.5 9.2 7.9 17 22.9 17 46.4 0 33.7-.3 75.4-.3 83.6 0 6.5 4.6 14.4 17.3 12.1C428.2 457.8 496 362.9 496 252 496 113.3 383.5 8 244.8 8zM97.2 352.9c-1.3 1-1 3.3.7 5.2 1.6 1.6 3.9 2.3 5.2 1 1.3-1 1-3.3-.7-5.2-1.6-1.6-3.9-2.3-5.2-1zm-10.8-8.1c-.7 1.3.3 2.9 2.3 3.9 1.6 1 3.6.7 4.3-.7.7-1.3-.3-2.9-2.3-3.9-2-.6-3.6-.3-4.3.7zm32.4 35.6c-1.6 1.3-1 4.3 1.3 6.2 2.3 2.3 5.2 2.6 6.5 1 1.3-1.3.7-4.3-1.3-6.2-2.2-2.3-5.2-2.6-6.5-1zm-11.4-14.7c-1.6 1-1.6 3.6 0 5.9 1.6 2.3 4.3 3.3 5.6 2.3 1.6-1.3 1.6-3.9 0-6.2-1.4-2.3-4-3.3-5.6-2z", modifier = iconModifier)
        }
        Link(
            path = LinkRes.External.LinkedIn,
            modifier = Modifier.textDecorationLine(TextDecorationLine.None),
            ref = ref {
                it.asDynamic().setAttribute("rel", "noopener noreferrer")
                it.asDynamic().setAttribute("aria-label", "Rok Retar on LinkedIn")
            },
        ) {
            SocialIcon(platform = "linkedin", viewBoxWidth = 448, path = "M416 32H31.9C14.3 32 0 46.5 0 64.3v383.4C0 465.5 14.3 480 31.9 480H416c17.6 0 32-14.5 32-32.3V64.3c0-17.8-14.4-32.3-32-32.3zM135.4 416H69V202.2h66.5V416zm-33.2-243c-21.3 0-38.5-17.3-38.5-38.5S80.9 96 102.2 96c21.2 0 38.5 17.3 38.5 38.5 0 21.3-17.2 38.5-38.5 38.5zm282.1 243h-66.4V312c0-24.8-.5-56.7-34.5-56.7-34.6 0-39.9 27-39.9 54.9V416h-66.4V202.2h63.7v29.2h.9c8.9-16.8 30.6-34.5 62.9-34.5 67.2 0 79.7 44.3 79.7 101.9V416z", modifier = iconModifier)
        }
        Link(
            path = "mailto:rok.retar@gmail.com",
            modifier = Modifier.textDecorationLine(TextDecorationLine.None),
            ref = ref {
                it.asDynamic().setAttribute("rel", "noopener noreferrer")
                it.asDynamic().setAttribute("aria-label", "Email Rok Retar")
            },
        ) {
            SocialIcon(platform = "email", viewBoxWidth = 512, path = "M48 64C21.5 64 0 85.5 0 112c0 15.1 7.1 29.3 19.2 38.4L236.8 313.6c11.4 8.5 27 8.5 38.4 0L492.8 150.4c12.1-9.1 19.2-23.3 19.2-38.4c0-26.5-21.5-48-48-48H48zM0 176V384c0 35.3 28.7 64 64 64H448c35.3 0 64-28.7 64-64V176L294.4 339.2c-22.8 17.1-54 17.1-76.8 0L0 176z", modifier = iconModifier)
        }
    }
}

// Font Awesome Free 6.5.2 glyphs (CC BY 4.0) inlined as SVG, so three icons don't cost a
// render-blocking icon-font stylesheet from a third-party origin.
@Composable
private fun SocialIcon(platform: String, viewBoxWidth: Int, path: String, modifier: Modifier) {
    Span(
        attrs = modifier
            .onClick { trackEvent("social-link-click", json("platform" to platform)) }
            .toAttrs {
                style { property("display", "inline-flex") }
                ref {
                    it.innerHTML =
                        """<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 $viewBoxWidth 512" height="20" fill="currentColor" aria-hidden="true"><path d="$path"/></svg>"""
                    onDispose {}
                }
            },
    )
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

    Link(
        path = item.href,
        text = item.label.value,
        modifier = NavLinkStyle
            .toModifier()
            .styleModifier {
                property("--nav-color", (if (isActive) palette.textPrimary else palette.textSecondary).toString())
                property("text-decoration-color", if (isActive) palette.accent.toString() else "transparent")
                property("padding-block", "10px")
            }
            .thenIf(isActive, Modifier.attr("aria-current", "true"))
            .onClick {
                trackEvent("nav-click", json("item" to item.name))
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
    Link(
        path = item.href,
        modifier = Modifier
            .fillMaxWidth()
            .gap(16.px)
            .alignItems(AlignItems.Baseline)
            .padding(topBottom = 14.px)
            .userSelect(UserSelect.None)
            .textDecorationLine(TextDecorationLine.None)
            .styleModifier {
                property("display", "flex")
                property("border-bottom", "1px solid ${palette.border}")
            }
            .thenIf(isActive, Modifier.attr("aria-current", "true"))
            .onClick {
                onClick()
                trackEvent("nav-click", json("item" to item.name))
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

    val spinModifier = if (spinKey > 0) {
        Modifier.animation(
            ThemeToggleSpinKeyframes.toAnimation(duration = 500.ms),
        )
    } else {
        Modifier
    }

    Span(
        attrs = Modifier
            .attr("aria-pressed", (colorMode == ColorMode.DARK).toString())
            .headerButton(StringRes.ToggleTheme.value) {
                spinKey++
                colorMode = colorMode.opposite
                trackEvent("theme-toggle", json("mode" to colorMode.name.lowercase()))
            }
            .toAttrs(),
    ) {
        if (colorMode == ColorMode.LIGHT) {
            SunIcon(modifier = iconModifier.then(spinModifier))
        } else {
            MoonIcon(modifier = iconModifier.then(spinModifier))
        }
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
                .attr("aria-haspopup", "true")
                .attr("aria-expanded", isOpen.toString())
                .headerButton(StringRes.ChooseLanguage.value) {
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
            .attr("role", "button")
            .attr("tabindex", "0")
            .attr("aria-current", if (isSelected) "true" else "false")
            .attr("lang", language.code)
            .onClick { onClick() }
            .onKeyDown { event ->
                if (event.key == "Enter" || event.key == " ") {
                    event.preventDefault()
                    onClick()
                }
            }
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

