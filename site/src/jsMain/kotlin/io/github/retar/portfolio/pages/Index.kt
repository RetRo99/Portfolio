package io.github.retar.portfolio.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.components.PortfolioButton
import io.github.retar.portfolio.components.PortfolioSection
import io.github.retar.portfolio.resources.StringRes
import io.github.retar.portfolio.styles.AppColors
import io.github.retar.portfolio.styles.DescriptorStyle
import io.github.retar.portfolio.styles.ProjectsCarouselContainerStyle
import io.github.retar.portfolio.styles.ProjectsCarouselTrackStyle
import io.github.retar.portfolio.styles.SubtitleStyle
import io.github.retar.portfolio.styles.TitleStyle
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.CSSSizeValue
import org.jetbrains.compose.web.css.CSSUnit
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.minWidth
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Page
@Composable
fun IndexPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .alignItems(AlignItems.Center)
    ) {
        HeroSection()
        SelectedProjectsSection()
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
private fun SelectedProjectsSection() {
    PortfolioSection {
        TitleWithSubtitle(
            title = StringRes.SelectedProjectsTitle.value,
            subtitle = StringRes.SelectedProjectsSubtitle.value,
            gap = 10.px,
        )
        ProjectsCarousel(
            modifier = Modifier
                .padding(top = 24.px)
                .fillMaxWidth(),
        )
    }
}

private val SelectedProjects = listOf(
    "Portfolio site - this website built with Kotlin & Kobweb",
    "Android client - Kotlin, Compose, and clean architecture",
    "Backend services - Kotlin & Spring / Ktor work",
)

@Composable
private fun ProjectsCarousel(
    modifier: Modifier = Modifier,
) {
    val baseRepeatCount = if (SelectedProjects.size < 4) 3 else 1
    val baseItems = List(baseRepeatCount) { SelectedProjects }.flatten()

    Div(
        attrs = ProjectsCarouselContainerStyle
            .toModifier()
            .then(modifier)
            .toAttrs(),
    ) {
        Div(
            attrs = ProjectsCarouselTrackStyle
                .toModifier()
                .toAttrs(),
        ) {
            repeat(2) {
                baseItems.forEach { project ->
                    ProjectCard(
                        title = project,
                    )
                }
            }
        }
    }
}

@Composable
private fun ProjectCard(
    title: String,
    modifier: Modifier = Modifier,
) {
    Div(
        attrs = modifier
            .padding(16.px)
            .border(1.px, LineStyle.Solid, AppColors.ButtonBorder)
            .borderRadius(16.px)
            .toAttrs {
                style {
                    minWidth(260.px)
                }
            },
    ) {
        SpanText(
            text = title,
            modifier = SubtitleStyle.toModifier(),
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
            url = StringRes.LinkedInLink.value,
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
