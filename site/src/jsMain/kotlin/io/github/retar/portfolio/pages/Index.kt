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
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.components.PortfolioButton
import io.github.retar.portfolio.resources.StringRes
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgb
import org.jetbrains.compose.web.css.rgba
import org.jetbrains.compose.web.dom.H1
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

val TitleStyle = CssStyle {
    base {
        Modifier
            .fontSize(55.px)
            .fontWeight(FontWeight.Normal)
    }

    Breakpoint.ZERO {
        Modifier.fontSize(32.px)
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

@Page
@Composable
fun IndexPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .alignItems(AlignItems.Center)
    ) {
        HeroSection()
    }
}

@Composable
private fun HeroSection() {
    Column(SectionContainerStyle.toModifier()) {
        TitleWithSubtitle(
            title = StringRes.HeroTitle.value,
            subtitle = StringRes.HeroSubtitle.value,
        )
        Descriptor()
        SocialButtons()
    }
}

@Composable
private fun SocialButtons() {
    Row(Modifier.gap(8.px)) {
        PortfolioButton(
            url = StringRes.GitHubLink.value,
            label = StringRes.Github.value
        )
        PortfolioButton(
            url = StringRes.LinkedIn.value,
            label = StringRes.LinkedIn.value
        )
    }
}

@Composable
private fun TitleWithSubtitle(title: String, subtitle: String) {
    Column() {
        Title(title)
        Subtitle(subtitle)
    }
}

@Composable
private fun Title(title: String) {
    H1(TitleStyle.toModifier().toAttrs()) {
        Text(title)
    }
}

@Composable
private fun Subtitle(subtitle: String) {
    P(
        SubtitleStyle.toModifier().toAttrs()
    ) {
        Text(subtitle)
    }
}

@Composable
private fun Descriptor() {
    P(DescriptorStyle.toModifier().toAttrs()) {
        Text(StringRes.Descriptor.value)
    }
}
