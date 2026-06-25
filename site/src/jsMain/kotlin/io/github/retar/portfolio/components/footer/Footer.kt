package io.github.retar.portfolio.components.footer

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
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
import io.github.retar.portfolio.components.widgets.PrimaryButton
import io.github.retar.portfolio.components.widgets.ResumeDownloadButton
import io.github.retar.portfolio.resources.LinkRes
import io.github.retar.portfolio.resources.StringRes
import io.github.retar.portfolio.styles.BodyStyle
import io.github.retar.portfolio.styles.CaptionStyle
import io.github.retar.portfolio.styles.H2Style
import io.github.retar.portfolio.styles.H2Text
import io.github.retar.portfolio.styles.MonoEyebrowStyle
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

private val FooterTextPrimary = Color.rgb(250, 250, 250)
private val FooterTextSecondary = Color.rgb(161, 161, 170)

val FooterLinkStyle = CssStyle {
    base {
        Modifier.transition(Transition.all(duration = 150.ms))
    }

    hover {
        Modifier.color(FooterTextPrimary)
    }
}

val FooterContainerStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth(95.percent)
            .padding(
                top = clamp(3.cssRem, 6.vw, 5.cssRem),
                bottom = clamp(2.cssRem, 4.vw, 3.cssRem),
            )
    }

    Breakpoint.MD {
        Modifier.fillMaxWidth(85.percent)
    }

    Breakpoint.LG {
        Modifier.fillMaxWidth(70.percent)
    }

    Breakpoint.XL {
        Modifier.fillMaxWidth(60.percent)
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .gap(20.px),
            ) {
                SpanText(
                    text = StringRes.ContactEyebrow.value,
                    modifier = MonoEyebrowStyle.toModifier(),
                )

                H2Text(
                    text = StringRes.ContactHeading.value,
                    modifier = H2Style.toModifier()
                        .fillMaxWidth()
                        .color(FooterTextPrimary),
                )

                SpanText(
                    text = StringRes.ExperienceContactBody.value,
                    modifier = BodyStyle.toModifier()
                        .color(FooterTextSecondary)
                        .maxWidth(52.ch),
                )

                FooterCtaRow()

                FooterLinksRow()

                SpanText(
                    text = StringRes.FooterMeta.value,
                    modifier = CaptionStyle.toModifier()
                        .color(FooterTextSecondary),
                )

                SpanText(
                    text = StringRes.FooterCopyright(Date().getFullYear()).value,
                    modifier = CaptionStyle.toModifier()
                        .color(FooterTextSecondary),
                )
            }
        }
    }
}

@Composable
private fun FooterCtaRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .gap(16.px),
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
        modifier = Modifier
            .fillMaxWidth()
            .gap(16.px),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Link(
            path = LinkRes.External.GitHub,
            modifier = CaptionStyle.toModifier()
                .then(FooterLinkStyle.toModifier())
                .color(FooterTextSecondary)
                .onClick { trackEvent("social-link-click", json("platform" to "github")) },
            ref = ref { it.asDynamic().setAttribute("rel", "noopener noreferrer") },
        ) {
            SpanText(text = StringRes.Github.value)
        }
        SpanText(
            text = "·",
            modifier = CaptionStyle.toModifier()
                .color(FooterTextSecondary),
        )
        Link(
            path = LinkRes.External.LinkedIn,
            modifier = CaptionStyle.toModifier()
                .then(FooterLinkStyle.toModifier())
                .color(FooterTextSecondary)
                .onClick { trackEvent("social-link-click", json("platform" to "linkedin")) },
            ref = ref { it.asDynamic().setAttribute("rel", "noopener noreferrer") },
        ) {
            SpanText(text = StringRes.LinkedIn.value)
        }
        SpanText(
            text = "·",
            modifier = CaptionStyle.toModifier()
                .color(FooterTextSecondary),
        )
        Link(
            path = LinkRes.External.Email,
            modifier = CaptionStyle.toModifier()
                .then(FooterLinkStyle.toModifier())
                .color(FooterTextSecondary)
                .onClick { trackEvent("social-link-click", json("platform" to "email")) },
            ref = ref { it.asDynamic().setAttribute("rel", "noopener noreferrer") },
        ) {
            SpanText(text = StringRes.EmailAddress.value)
        }
    }
}
