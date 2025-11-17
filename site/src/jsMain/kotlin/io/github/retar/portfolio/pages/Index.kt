package io.github.retar.portfolio.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.functions.brightness
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.filter
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.onMouseEnter
import com.varabyte.kobweb.compose.ui.modifiers.onMouseLeave
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.scale
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.components.InfiniteCarousel
import io.github.retar.portfolio.components.PortfolioButton
import io.github.retar.portfolio.components.PortfolioSection
import io.github.retar.portfolio.resources.StringRes
import io.github.retar.portfolio.styles.DescriptorStyle
import io.github.retar.portfolio.styles.SubtitleStyle
import io.github.retar.portfolio.styles.TitleStyle
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.CSSSizeValue
import org.jetbrains.compose.web.css.CSSUnit
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
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
        InfiniteCarousel(
            items = SelectedProjects,
            modifier = Modifier
                .padding(top = 16.px, bottom = 16.px)
                .fillMaxWidth(),
        ) { project ->
            ProjectCard(
                title = project.title,
                imagePath = project.imagePath,
            )
        }
    }
}

private data class Project(
    val title: String,
    val imagePath: String,
)

private val SelectedProjects = listOf(
    Project(
        title = "Android client - Kotlin, Compose, and clean architecture",
        imagePath = "bardy/Bardy1.avif",
    ),
    Project(
        title = "Portfolio site - this website built with Kotlin & Kobweb",
        imagePath = "bardy/Bardy2.avif",
    ),
    Project(
        title = "Backend services - Kotlin & Spring / Ktor work",
        imagePath = "bardy/Bardy3.avif",
    ),
)

val ProjectImageStyle = CssStyle {
    base {
        Modifier
            .width(220.px)
            .borderRadius(16.px)
            .overflow(Overflow.Hidden)
            .transition(Transition.all(duration = 500.ms))
    }
    hover {
        Modifier
            .filter(brightness(0.6))
            .scale(1.1)
    }
}

@Composable
private fun ProjectCard(
    title: String,
    imagePath: String,
) {
    var hasMouse by remember { mutableStateOf(false) }

    Box(
        modifier = ProjectImageStyle.toModifier()
            .onMouseEnter { hasMouse = true }
            .onMouseLeave { hasMouse = false },
        contentAlignment = Alignment.Center,
    ) {
        Image(
            src = imagePath,
            description = title,
            modifier = Modifier.fillMaxSize(),
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
