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
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.components.PortfolioButton
import io.github.retar.portfolio.components.PortfolioSection
import io.github.retar.portfolio.resources.StringRes
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.CSSSizeValue
import org.jetbrains.compose.web.css.CSSUnit
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgb
import org.jetbrains.compose.web.css.rgba
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

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
    PortfolioSection {
        TitleWithSubtitle(
            title = StringRes.HeroTitle.value,
            subtitle = StringRes.HeroSubtitle.value,
            gap = 40.px
        )
        Descriptor()
        SocialButtons(
            modifier = Modifier.padding(top = 44.px)
        )
    }
}

@Composable
private fun SocialButtons(
    modifier: Modifier = Modifier
) {
    Row(modifier.gap(8.px)) {
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
private fun TitleWithSubtitle(
    title: String,
    subtitle: String,
    gap: CSSSizeValue<CSSUnit.px> = 0.px
) {
    Column(
        modifier = Modifier.gap(gap)
    ) {
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
    SpanText(
        text = subtitle,
        modifier = SubtitleStyle.toModifier(),
    )
}

@Composable
private fun Descriptor() {
    P(DescriptorStyle.toModifier().toAttrs()) {
        Text(StringRes.Descriptor.value)
    }
}
