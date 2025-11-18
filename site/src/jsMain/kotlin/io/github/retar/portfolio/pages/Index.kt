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
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.onMouseEnter
import com.varabyte.kobweb.compose.ui.modifiers.onMouseLeave
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.scale
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.components.InfiniteCarousel
import io.github.retar.portfolio.components.PortfolioButton
import io.github.retar.portfolio.components.PortfolioSection
import io.github.retar.portfolio.resources.ImageRes
import io.github.retar.portfolio.resources.LinkRes
import io.github.retar.portfolio.resources.StringRes
import io.github.retar.portfolio.styles.DescriptorStyle
import io.github.retar.portfolio.styles.ImageTextHoverStyle
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
        ArticlesSection()
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
                image = project.image,
            )
        }
    }
}

private data class Project(
    val image: ImageRes,
)

private val SelectedProjects = listOf(
    Project(
        image = ImageRes.Bardy1,
    ),
    Project(
        image = ImageRes.Bardy2,
    ),
    Project(
        image = ImageRes.Bardy3,
    ),
)

@Composable
private fun ArticlesSection() {
    PortfolioSection {
        TitleWithSubtitle(
            title = StringRes.ArticlesTitle.value,
            subtitle = StringRes.ArticlesSubtitle.value,
            gap = 10.px,
        )
        Column(
            modifier = Modifier
                .padding(top = 16.px, bottom = 16.px)
                .fillMaxWidth()
                .gap(12.px),
        ) {
            ArticleCard(
                title = "Building a Smooth, Infinite Carousel in Kobweb",
                description = "How to build an infinite, gapless carousel using Silk and Kobweb.",
                route = LinkRes.Internal.InfiniteCarouselArticle,
                image = ImageRes.InfiniteCarouselArticle,
            )
        }
    }
}

@Composable
private fun ArticleCard(
    title: String,
    description: String,
    route: String,
    image: ImageRes,
) {
    var hasMouse by remember { mutableStateOf(false) }

    Box(
        modifier = ProjectImageStyle.toModifier()
            .onMouseEnter { hasMouse = true }
            .onMouseLeave { hasMouse = false }
            .overflow(Overflow.Hidden),
        contentAlignment = Alignment.Center,
    ) {
        val router = rememberPageContext().router
        Image(
            src = image.path,
            description = title,
            modifier = Modifier.fillMaxSize().onClick {
                router.tryRoutingTo(route)
            },
        )
        if (hasMouse) {
            SpanText(text = description, modifier = ImageTextHoverStyle.toModifier())
        }
    }
}

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
    image: ImageRes,
) {
    Box(
        modifier = ProjectImageStyle.toModifier(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            src = image.path,
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
            url = LinkRes.External.GitHub,
            label = StringRes.Github.value
        )
        PortfolioButton(
            url = LinkRes.External.LinkedIn,
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
