package io.github.retar.portfolio.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.css.CSSLengthNumericValue
import com.varabyte.kobweb.compose.css.functions.clamp
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flexWrap
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.gridTemplateColumns
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.dom.Div
import io.github.retar.portfolio.components.PortfolioSection
import io.github.retar.portfolio.components.PortfolioSectionId
import io.github.retar.portfolio.components.SectionHeader
import io.github.retar.portfolio.seo.Seo
import io.github.retar.portfolio.components.widgets.ArticleCard
import io.github.retar.portfolio.components.widgets.Callout
import io.github.retar.portfolio.components.widgets.DownloadButton
import io.github.retar.portfolio.components.widgets.FeatureGrid
import io.github.retar.portfolio.components.widgets.FeatureItem
import io.github.retar.portfolio.components.widgets.InfiniteCarousel
import io.github.retar.portfolio.components.widgets.PrimaryButton
import io.github.retar.portfolio.components.widgets.ProjectCard
import io.github.retar.portfolio.components.widgets.ResumeDownloadButton
import io.github.retar.portfolio.components.widgets.TextLink
import io.github.retar.portfolio.resources.ImageRes
import io.github.retar.portfolio.resources.LinkRes
import io.github.retar.portfolio.resources.ParrotRes
import io.github.retar.portfolio.resources.ProjectsRes
import io.github.retar.portfolio.resources.StringRes
import io.github.retar.portfolio.styles.BodyStyle
import io.github.retar.portfolio.styles.CaptionStyle
import io.github.retar.portfolio.styles.DisplayStyle
import io.github.retar.portfolio.styles.H1Style
import io.github.retar.portfolio.styles.H1Text
import io.github.retar.portfolio.styles.H2Style
import io.github.retar.portfolio.styles.H2Text
import io.github.retar.portfolio.styles.MonoAccentStyle
import io.github.retar.portfolio.styles.MonoEyebrowStyle
import io.github.retar.portfolio.styles.SubtitleStyle
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle as WebDisplayStyle
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.ch
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.fr
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vw

@Page
@Layout(".components.layouts.PageLayout")
@Composable
fun IndexPage() {
    Seo(
        title = "Rok Retar — Mobile Engineer (Android & Kotlin Multiplatform)",
        description = "Android and Kotlin Multiplatform engineer specializing in KMP migrations taken to production iOS. One Kotlin codebase, two platforms, native feel intact.",
        path = "/",
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .alignItems(AlignItems.FlexStart),
    ) {
        HeroSection()
        PhilosophySection()
        ParrotShowcaseSection()
        ArticlesSection()
    }
}

private fun Modifier.sectionPadding(
    top: CSSLengthNumericValue = clamp(4.cssRem, 8.vw, 7.cssRem),
    bottom: CSSLengthNumericValue = clamp(4.cssRem, 8.vw, 7.cssRem),
): Modifier = padding(top = top, bottom = bottom)

private fun Modifier.measureMax(charWidth: Int): Modifier = maxWidth(charWidth.ch)

@Composable
private fun HeroSection() {
    PortfolioSection(
        section = PortfolioSectionId.About,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .gap(16.px)
                .sectionPadding(top = clamp(3.cssRem, 6.vw, 5.cssRem)),
        ) {
            SpanText(
                text = StringRes.HeroEyebrow.value,
                modifier = MonoEyebrowStyle.toModifier(),
            )

            SpanText(
                text = StringRes.HeroName.value,
                modifier = DisplayStyle.toModifier(),
            )

            H1Text(
                text = StringRes.HeroHeadline.value,
                modifier = H1Style.toModifier()
                    .measureMax(30),
            )

            SpanText(
                text = StringRes.HeroSubtitle.value,
                modifier = BodyStyle.toModifier()
                    .measureMax(65),
            )

            HeroCtaRow()

            SpanText(
                text = StringRes.HeroStatusStrip.value,
                modifier = CaptionStyle.toModifier()
                    .margin(top = 8.px),
            )
        }
    }
}

@Composable
private fun HeroCtaRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .gap(16.px)
            .flexWrap(FlexWrap.Wrap)
            .alignItems(AlignItems.Center)
            .margin(top = 24.px),
    ) {
        PrimaryButton(
            url = PortfolioSectionId.Projects.path,
            label = StringRes.CtaReviewWork.value,
        )
        ResumeDownloadButton(
            url = LinkRes.External.Resume,
            label = StringRes.CtaDownloadResume.value,
        )
        TextLink(
            url = PortfolioSectionId.Blog.path,
            label = StringRes.CtaReadNotes.value,
        )
    }
}

@Composable
private fun PhilosophySection() {
    PortfolioSection(
        section = PortfolioSectionId.Philosophy,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .sectionPadding(),
        ) {
            Div(
                attrs = PhilosophyGridStyle.toModifier().toAttrs(),
            ) {
                Column(
                    modifier = Modifier.gap(16.px),
                ) {
                    SpanText(
                        text = StringRes.PhilosophyEyebrow.value,
                        modifier = MonoEyebrowStyle.toModifier(),
                    )
                    H2Text(
                        text = StringRes.PhilosophyHeading.value,
                        modifier = H2Style.toModifier()
                            .fillMaxWidth(),
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .gap(24.px),
                ) {
                    SpanText(
                        text = StringRes.PhilosophyBody1.value,
                        modifier = BodyStyle.toModifier()
                            .measureMax(70),
                    )
                    SpanText(
                        text = StringRes.PhilosophyBody2.value,
                        modifier = BodyStyle.toModifier()
                            .measureMax(70),
                    )
                    SpanText(
                        text = StringRes.PhilosophyBody3.value,
                        modifier = BodyStyle.toModifier()
                            .measureMax(70),
                    )

                    PrinciplesList()

                    Callout(
                        label = StringRes.MigrationCalloutLabel.value,
                        body = StringRes.MigrationCalloutBody.value,
                    )
                }
            }
        }
    }
}

@Composable
private fun PrinciplesList() {
    val principles = listOf(
        StringRes.PhilosophyPrinciple1.value,
        StringRes.PhilosophyPrinciple2.value,
        StringRes.PhilosophyPrinciple3.value,
        StringRes.PhilosophyPrinciple4.value,
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .gap(16.px)
            .margin(top = 8.px),
    ) {
        principles.forEachIndexed { index, principle ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .gap(16.px),
                verticalAlignment = Alignment.Top,
            ) {
                SpanText(
                    text = (index + 1).toString().padStart(2, '0'),
                    modifier = MonoAccentStyle.toModifier(),
                )
                SpanText(
                    text = principle,
                    modifier = BodyStyle.toModifier()
                        .weight(1f),
                )
            }
        }
    }
}

@Composable
private fun ParrotShowcaseSection() {
    PortfolioSection(
        section = PortfolioSectionId.Projects,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .gap(24.px)
                .sectionPadding(),
        ) {
            SpanText(
                text = StringRes.ParrotEyebrow.value,
                modifier = MonoEyebrowStyle.toModifier(),
            )
            H2Text(
                text = StringRes.ParrotHeading.value,
                modifier = H2Style.toModifier(),
            )
            SpanText(
                text = StringRes.ParrotSubhead.value,
                modifier = SubtitleStyle.toModifier()
                    .measureMax(70),
            )

            InfiniteCarousel(
                items = ProjectsRes.SelectedProjects,
                modifier = Modifier
                    .fillMaxWidth()
                    .margin(top = 16.px, bottom = 16.px),
            ) { projectImage ->
                ProjectCard(
                    image = projectImage.image,
                    route = LinkRes.Internal.Parrot,
                    contentDescription = projectImage.description.value,
                )
            }

            FeatureGrid(
                items = parrotFeatures(),
                modifier = Modifier.margin(top = 16.px),
            )

            ParrotCtaRow()
        }
    }
}

@Composable
private fun parrotFeatures(): List<FeatureItem> = listOf(
    FeatureItem("01", StringRes.ParrotFeature1Title.value, StringRes.ParrotFeature1Desc.value),
    FeatureItem("02", StringRes.ParrotFeature2Title.value, StringRes.ParrotFeature2Desc.value),
    FeatureItem("03", StringRes.ParrotFeature3Title.value, StringRes.ParrotFeature3Desc.value),
    FeatureItem("04", StringRes.ParrotFeature4Title.value, StringRes.ParrotFeature4Desc.value),
    FeatureItem("05", StringRes.ParrotFeature5Title.value, StringRes.ParrotFeature5Desc.value),
    FeatureItem("06", StringRes.ParrotFeature6Title.value, StringRes.ParrotFeature6Desc.value),
    FeatureItem("07", StringRes.ParrotFeature7Title.value, StringRes.ParrotFeature7Desc.value),
)

@Composable
private fun ParrotCtaRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .gap(16.px)
            .flexWrap(FlexWrap.Wrap)
            .alignItems(AlignItems.Center)
            .margin(top = 16.px),
    ) {
        PrimaryButton(
            url = LinkRes.Internal.Parrot,
            label = StringRes.CtaOpenCaseStudy.value,
        )
        DownloadButton(
            url = ParrotRes.latestRelease.apkUrl,
            label = StringRes.DownloadVersionApk(ParrotRes.latestRelease.version).value,
        )
    }
}

@Composable
private fun ArticlesSection() {
    PortfolioSection(
        section = PortfolioSectionId.Blog,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .gap(16.px)
                .sectionPadding(),
        ) {
            SectionHeader(
                eyebrow = StringRes.NotesEyebrow.value,
                title = StringRes.NotesTitle.value,
                subtitle = StringRes.NotesSubtitle.value,
                gap = 10.px,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .gap(12.px)
                    .margin(top = 16.px),
            ) {
                ArticleCard(
                    title = StringRes.InfiniteCarouselArticleTitle.value,
                    description = StringRes.InfiniteCarouselArticleDesc.value,
                    route = LinkRes.Internal.InfiniteCarouselArticle,
                    image = ImageRes.InfiniteCarouselArticle,
                )
                ArticleCard(
                    title = StringRes.SQLCipherArticleTitle.value,
                    description = StringRes.SQLCipherArticleDesc.value,
                    route = LinkRes.Internal.SQLCipherArticle,
                    image = ImageRes.SQLCipherArticle,
                )
            }
        }
    }
}

val PhilosophyGridStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .display(WebDisplayStyle.Grid)
            .gridTemplateColumns {
                size(1.fr)
            }
            .gap(32.px)
    }

    Breakpoint.MD {
        Modifier.gridTemplateColumns {
            minmax(0.px, 5.fr)
            minmax(0.px, 7.fr)
        }
    }
}
