package io.github.retar.portfolio.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.components.PortfolioSection
import io.github.retar.portfolio.components.PortfolioSectionId
import io.github.retar.portfolio.components.SectionHeader
import io.github.retar.portfolio.components.widgets.ArticleCard
import io.github.retar.portfolio.components.widgets.InfiniteCarousel
import io.github.retar.portfolio.components.widgets.OutlineButton
import io.github.retar.portfolio.components.widgets.ProjectCard
import io.github.retar.portfolio.resources.ImageRes
import io.github.retar.portfolio.resources.LinkRes
import io.github.retar.portfolio.resources.ProjectsRes
import io.github.retar.portfolio.resources.StringRes
import io.github.retar.portfolio.styles.BodyStyle
import io.github.retar.portfolio.styles.HeadingLStyle
import io.github.retar.portfolio.styles.HeadingXLStyle
import io.github.retar.portfolio.styles.MonoAccentStyle
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.px

@Page
@Layout(".components.layouts.PageLayout")
@Composable
fun IndexPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .alignItems(AlignItems.FlexStart)
    ) {
        HeroSection()
        SelectedProjectsSection()
        ArticlesSection()
    }
}

@Composable
private fun HeroSection() {
    PortfolioSection(
        section = PortfolioSectionId.About,
    ) {
        Column(
            modifier = Modifier
                .gap(16.px)
                .padding(top = 80.px, bottom = 80.px),
        ) {
            SpanText(
                text = StringRes.HeroIntro.value,
                modifier = MonoAccentStyle.toModifier(),
            )

            SpanText(
                text = StringRes.HeroName.value,
                modifier = HeadingXLStyle.toModifier(),
            )

            SpanText(
                text = StringRes.HeroTagline.value,
                modifier = HeadingLStyle.toModifier(),
            )

            SpanText(
                text = StringRes.HeroSubtitle.value,
                modifier = BodyStyle.toModifier(),
            )

            // CTA Button
            Box(
                modifier = Modifier.padding(top = 32.px),
            ) {
                OutlineButton(
                    url = PortfolioSectionId.Projects.path,
                    label = StringRes.ViewMyWork.value,
                )
            }
        }
    }
}

@Composable
private fun SelectedProjectsSection() {
    PortfolioSection(
        section = PortfolioSectionId.Projects,
    ) {
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
                route = LinkRes.Internal.Parrot,
            )
        }
    }
}

@Composable
private fun ArticlesSection() {
    PortfolioSection(
        section = PortfolioSectionId.Blog,
    ) {
        SectionHeader(
            title = StringRes.BlogTitle.value,
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
                title = StringRes.InfiniteCarouselArticleTitle.value,
                description = StringRes.InfiniteCarouselArticleDesc.value,
                route = LinkRes.Internal.InfiniteCarouselArticle,
                image = ImageRes.InfiniteCarouselArticle,
            )
        }
    }
}

