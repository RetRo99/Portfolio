package io.github.retar.portfolio.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.flexGrow
import com.varabyte.kobweb.compose.ui.modifiers.flexWrap
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.browser.window
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgb
import org.jetbrains.compose.web.css.rgba
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

// Styles
val SectionContainerStyle = CssStyle {
    base {
        Modifier
            .padding(topBottom = 32.px, leftRight = 16.px)
            .width(100.percent)
            .maxWidth(1200.px)
    }
}

val HeroTitleStyle = CssStyle {
    base {
        Modifier
            .fontSize(55.px)
            .fontWeight(FontWeight.Normal)
    }
}

val SubtitleStyle = CssStyle {
    base {
        Modifier
            .fontSize(18.px)
    }
}

val DescriptorStyle = CssStyle {
    base {
        Modifier
            .color(Color.lightgray)
            .fontSize(18.px)
    }
}
val CardStyle = CssStyle {
    base {
        Modifier
            .padding(20.px)
            .borderRadius(12.px)
            .border(1.px, LineStyle.Solid, rgba(0, 0, 0, 0.1))
            .backgroundColor(rgb(250, 250, 250))
    }
    hover { Modifier.boxShadow(0.px, 8.px, 24.px, 0.px, rgba(0, 0, 0, 0.12)) }
}

val LinkButtonStyle = CssStyle {
    base {
        Modifier
            .border { width(1.px); style(LineStyle.Solid); color(Color.lightgray) }
            .borderRadius(32.px)
            .backgroundColor(Color.transparent)
            .color(rgb(74, 74, 69))
    }
    hover {
        Modifier.backgroundColor(rgb(122, 136, 254)).color(Color.white)
    }
}

@Page
@Composable
fun IndexPage() {
    // Page wrapper: center content and set nice gaps between sections
    Column(
        modifier = Modifier
            .fillMaxSize()
            .gap(40.px)
            .alignItems(AlignItems.Center)
            .padding(top = 48.px, bottom = 64.px)
    ) {
        // Hero Section
        Column(SectionContainerStyle.toModifier()) {
            Title()
            Subtitle()
            Descriptor()
            Row(Modifier.gap(8.px)) {
                SocialLink(
                    href = "https://www.linkedin.com/in/rok-retar/",
                    label = "LinkedIn"
                )
                SocialLink(
                    href = "https://github.com/retro99",
                    label = "GitHub"
                )
            }
        }

        // About Section
        Column(SectionContainerStyle.toModifier().gap(8.px)) {
            H2 { Text("About") }
            P(CardStyle.toModifier().toAttrs()) {
                Text(
                    "I’m a Kotlin-focused developer who enjoys building polished mobile and web experiences. " +
                            "This portfolio is built with Kobweb and Silk, which bring a Compose-like developer experience to the web."
                )
            }
        }

        // Projects Section
        Column(SectionContainerStyle.toModifier().gap(16.px)) {
            H2 { Text("Projects") }
            // Simple responsive grid using flex wrap
            Div(Modifier.display(DisplayStyle.Flex).flexWrap(FlexWrap.Wrap).gap(16.px).toAttrs()) {
                ProjectCard(
                    title = "Portfolio (this site)",
                    description = "A small responsive site built with Kobweb + Silk.",
                    link = null,
                )
                ProjectCard(
                    title = "Sample Android App",
                    description = "A clean-architecture Android app written in Kotlin.",
                    link = "https://github.com/RetRo99/ai-bardly"
                )
                ProjectCard(
                    title = "Kotlin Playground",
                    description = "Experiments with Kotlin Multiplatform and Compose.",
                    link = null
                )
            }
        }

        // Contact Section
        Column(SectionContainerStyle.toModifier().gap(8.px)) {
            H2 { Text("Contact") }
            P { Text("Reach out on LinkedIn or GitHub, or open an issue on one of my repos.") }
        }
    }
}

@Composable
private fun Title() {
    H1(HeroTitleStyle.toModifier().toAttrs()) {
        Text("I'm Rok Retar, a developer specializing in building innovative digital solutions, based in Slovenia.")
    }
}

@Composable
private fun Subtitle() {
    P(
        SubtitleStyle.toModifier().toAttrs()
    ) {
        Text("Kotlin-focused developer who enjoys building polished mobile and web experiences. This portfolio is built with Kobweb and Silk, which bring a Compose-like developer experience to the web.")
    }
}

@Composable
private fun Descriptor() {
    P(DescriptorStyle.toModifier().toAttrs()) {
        Text("Kotlin / Android / Web Developer")
    }
}

@Composable
private fun SocialLink(href: String, label: String) {
    P {
        Button(
            onClick = {
                window.open(href, "_blank")
            },
            modifier = LinkButtonStyle.toModifier()
        ) {
            Text(label)
        }
    }
}

@Composable
private fun ProjectCard(title: String, description: String, link: String?) {
    val cardModifier = CardStyle.toModifier()
        .flexGrow(1)
        .minWidth(260.px)
        .maxWidth(360.px)
        .display(DisplayStyle.InlineBlock)

    A(link, attrs = cardModifier.toAttrs()) {
        Column(Modifier.gap(8.px)) {
            P { Text(title) }
            P { Text(description) }
            P(Modifier.margin(top = 6.px).toAttrs()) { Text("View →") }
        }
    }
}
