package io.github.retar.portfolio.components.footer

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.functions.clamp
import com.varabyte.kobweb.compose.dom.ref
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.components.PortfolioSection
import io.github.retar.portfolio.components.PortfolioSectionId
import io.github.retar.portfolio.components.widgets.EditorialGrid
import io.github.retar.portfolio.components.widgets.PrimaryButton
import io.github.retar.portfolio.components.widgets.ResumeDownloadButton
import io.github.retar.portfolio.resources.LinkRes
import io.github.retar.portfolio.resources.StringRes
import io.github.retar.portfolio.styles.BodyStyle
import io.github.retar.portfolio.styles.CaptionStyle
import io.github.retar.portfolio.styles.DisplayStyle
import io.github.retar.portfolio.styles.H2Text
import io.github.retar.portfolio.styles.MonoEyebrowStyle
import io.github.retar.portfolio.styles.eyebrowText
import io.github.retar.portfolio.styles.sitePalette
import io.github.retar.portfolio.utils.trackEvent
import kotlin.js.json
import org.jetbrains.compose.web.css.ch
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vw
import kotlin.js.Date
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.dom.Div


val FooterLinkStyle = CssStyle {
    base {
        Modifier.transition(Transition.all(duration = 150.ms))
    }

    hover {
        Modifier.color(sitePalette().accent)
    }
}

val FooterContainerStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .maxWidth(1200.px)
            .padding(
                top = clamp(3.cssRem, 6.vw, 5.cssRem),
                bottom = clamp(2.cssRem, 4.vw, 3.cssRem),
                leftRight = clamp(1.cssRem, 4.vw, 2.5.cssRem),
            )
            .styleModifier { property("box-sizing", "border-box") }
    }
}

@Composable
fun Footer() {
    val palette = sitePalette()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .backgroundColor(palette.footerBg),
        contentAlignment = Alignment.Center,
    ) {
        PortfolioSection(
            section = PortfolioSectionId.Contact,
            modifier = FooterContainerStyle.toModifier(),
        ) {
            EditorialGrid(
                eyebrow = StringRes.ContactEyebrow.value,
                // The footer band already separates the section, so it skips the grid's top hairline.
                modifier = Modifier.styleModifier {
                    property("border-top", "none")
                    property("padding-top", "0")
                },
            ) {
                H2Text(
                    text = StringRes.ContactHeading.value,
                    modifier = DisplayStyle.toModifier()
                        .color(palette.textPrimary)
                        .styleModifier { property("font-size", "clamp(3rem, 8vw, 6.5rem)") },
                )
                SpanText(
                    text = StringRes.ExperienceContactBody.value,
                    modifier = BodyStyle.toModifier()
                        .color(palette.textSecondary)
                        .maxWidth(52.ch),
                )
                FooterCtaRow()
            }

            Div(
                attrs = Modifier.styleModifier {
                    property("display", "flex")
                    property("flex-wrap", "wrap")
                    property("justify-content", "space-between")
                    property("align-items", "baseline")
                    property("gap", "1rem 2rem")
                    property("margin-top", "clamp(3rem, 7vw, 5rem)")
                    property("padding-top", "1.25rem")
                    property("border-top", "1px solid ${palette.border}")
                }.toAttrs(),
            ) {
                SpanText(
                    text = StringRes.FooterCopyright(Date().getFullYear()).value + "  ·  " + StringRes.FooterMeta.value,
                    modifier = MonoEyebrowStyle.toModifier(),
                )
                FooterLinksRow()
            }
        }
    }
}

@Composable
private fun FooterCtaRow() {
    Row(
        modifier = Modifier
            .gap(16.px)
            .styleModifier { property("flex-wrap", "wrap") },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PrimaryButton(
            url = LinkRes.External.Email,
            label = StringRes.CtaEmail.value,
            openExternally = true,
            onClick = { trackEvent("contact-email-click") },
        )
        ResumeDownloadButton(
            url = LinkRes.External.Resume,
            label = StringRes.CtaDownloadResume.value,
        )
    }
}

@Composable
private fun FooterLinksRow() {
    Row(
        modifier = Modifier.gap(20.px),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Link(
            path = LinkRes.External.GitHub,
            modifier = MonoEyebrowStyle.toModifier()
                .then(FooterLinkStyle.toModifier())
                .onClick { trackEvent("social-link-click", json("platform" to "github")) },
            ref = ref { it.asDynamic().setAttribute("rel", "noopener noreferrer") },
        ) {
            SpanText(text = StringRes.Github.value)
        }
        Link(
            path = LinkRes.External.LinkedIn,
            modifier = MonoEyebrowStyle.toModifier()
                .then(FooterLinkStyle.toModifier())
                .onClick { trackEvent("social-link-click", json("platform" to "linkedin")) },
            ref = ref { it.asDynamic().setAttribute("rel", "noopener noreferrer") },
        ) {
            SpanText(text = StringRes.LinkedIn.value)
        }
        Link(
            path = LinkRes.External.Email,
            modifier = MonoEyebrowStyle.toModifier()
                .then(FooterLinkStyle.toModifier())
                .onClick { trackEvent("social-link-click", json("platform" to "email")) },
            ref = ref { it.asDynamic().setAttribute("rel", "noopener noreferrer") },
        ) {
            SpanText(text = StringRes.EmailAddress.value)
        }
    }
}
