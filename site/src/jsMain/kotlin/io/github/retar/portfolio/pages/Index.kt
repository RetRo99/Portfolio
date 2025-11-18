package io.github.retar.portfolio.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.core.Page
import io.github.retar.portfolio.components.ArticleCard
import io.github.retar.portfolio.components.DescriptorText
import io.github.retar.portfolio.components.InfiniteCarousel
import io.github.retar.portfolio.components.PortfolioSection
import io.github.retar.portfolio.components.ProjectCard
import io.github.retar.portfolio.components.SectionHeader
import io.github.retar.portfolio.components.SocialButtons
import io.github.retar.portfolio.resources.ImageRes
import io.github.retar.portfolio.resources.LinkRes
import io.github.retar.portfolio.resources.StringRes
import io.github.retar.portfolio.resources.ProjectsRes

import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.px

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
        SectionHeader(
            title = StringRes.HeroTitle.value,
            subtitle = StringRes.HeroSubtitle.value,
            gap = 40.px,
        )
        DescriptorText()
        SocialButtons(
            modifier = Modifier.padding(top = 44.px),
        )
    }
}

@Composable
private fun SelectedProjectsSection() {
    PortfolioSection {
        SectionHeader(
            title = StringRes.SelectedProjectsTitle.value,
            subtitle = StringRes.SelectedProjectsSubtitle.value,
            gap = 10.px,
        )
        InfiniteCarousel(
            items = ProjectsRes.SelectedProjects,
            modifier = Modifier
                .padding(top = 16.px, bottom = 16.px)
                .fillMaxWidth(),
        ) { projectImage ->
            ProjectCard(
                image = projectImage,
            )
        }
    }
}

@Composable
private fun ArticlesSection() {
    PortfolioSection {
        SectionHeader(
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

