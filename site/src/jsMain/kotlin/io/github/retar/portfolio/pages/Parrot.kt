package io.github.retar.portfolio.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.compose.dom.ref
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.scrollMargin
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.LocalSetActiveSection
import io.github.retar.portfolio.components.PortfolioSectionId
import io.github.retar.portfolio.components.header.NavHeaderHeight
import io.github.retar.portfolio.components.widgets.DownloadButton
import io.github.retar.portfolio.components.widgets.OutlineButton
import io.github.retar.portfolio.resources.ImageRes
import io.github.retar.portfolio.resources.ParrotRelease
import io.github.retar.portfolio.resources.ParrotRes
import io.github.retar.portfolio.resources.StringRes
import io.github.retar.portfolio.seo.Seo
import io.github.retar.portfolio.styles.BodySmallStyle
import io.github.retar.portfolio.styles.BodyStyle
import io.github.retar.portfolio.styles.H1Style
import io.github.retar.portfolio.styles.H1Text
import io.github.retar.portfolio.styles.H2Style
import io.github.retar.portfolio.styles.H2Text
import io.github.retar.portfolio.styles.H3Style
import io.github.retar.portfolio.styles.H3Text
import io.github.retar.portfolio.styles.LabelStyle
import io.github.retar.portfolio.styles.MonoEyebrowStyle
import io.github.retar.portfolio.styles.SubtitleStyle
import io.github.retar.portfolio.styles.sitePalette
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.ch
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vw

@Page
@Layout(".components.layouts.PageLayout")
@Composable
fun ParrotPage() {
    val setActive = LocalSetActiveSection.current

    LaunchedEffect(Unit) {
        setActive(PortfolioSectionId.Projects)
    }

    Seo(
        title = "Parrot — EPUB Reader & Audiobook Player | Case Study",
        description = "Parrot is a lightweight EPUB e-reader and audiobook player built as the companion app for Storyteller. Typography control, e-ink adaptation, background audio, and read-aloud sync.",
        path = "/parrot",
        ogType = "article",
        jsonLd = """
            {
              "@context": "https://schema.org",
              "@type": "SoftwareApplication",
              "name": "Parrot",
              "applicationCategory": "BookApplication",
              "operatingSystem": "Android",
              "description": "A lightweight EPUB e-reader and audiobook player — the companion app for Storyteller.",
              "author": {
                "@type": "Person",
                "name": "Rok Retar",
                "url": "https://www.retar.app"
              },
              "offers": {
                "@type": "Offer",
                "price": "0",
                "priceCurrency": "EUR"
              }
            }
        """.trimIndent(),
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.px, bottom = 80.px),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .maxWidth(800.px)
                .gap(48.px)
                .alignItems(AlignItems.Center),
        ) {
            ParrotHero()
            LatestReleaseSection()
            FeaturesSection()
            if (ParrotRes.releases.size > 1) {
                VersionHistorySection()
            }
        }
    }
}

@Composable
private fun ParrotHero() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .gap(24.px)
            .alignItems(AlignItems.Center),
    ) {
        Image(
            src = ImageRes.ParrotIcon.path,
            description = StringRes.ParrotIconDesc.value,
            modifier = Modifier
                .size(120.px)
                .borderRadius(24.px),
        )

        H1Text(
            text = StringRes.ParrotPageHeading.value,
            modifier = H1Style.toModifier(),
        )

        SpanText(
            text = StringRes.ParrotPageSubhead.value,
            modifier = SubtitleStyle.toModifier()
                .fillMaxWidth()
                .maxWidth(65.ch),
        )
    }
}

@Composable
private fun LatestReleaseSection() {
    val latestRelease = ParrotRes.latestRelease

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .gap(24.px)
            .alignItems(AlignItems.Center),
    ) {
        Row(
            modifier = Modifier.gap(12.px),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            H2Text(
                text = StringRes.ParrotDownloadSection.value,
                modifier = H2Style.toModifier(),
            )
            Box(
                modifier = Modifier
                    .backgroundColor(sitePalette().accent)
                    .borderRadius(999.px)
                    .padding(leftRight = 10.px, topBottom = 4.px),
            ) {
                SpanText(
                    text = "Latest",
                    modifier = Modifier
                        .fontSize(12.px)
                        .color(sitePalette().buttonText)
                        .fontFamily("JetBrains Mono", "monospace"),
                )
            }
        }

        DownloadButton(
            url = latestRelease.apkUrl,
            label = StringRes.ParrotDownloadLatest(latestRelease.version).value,
        )

        ReleaseChangelog(latestRelease)

        if (ParrotRes.releases.size > 1) {
            OutlineButton(
                url = "#version-history",
                label = StringRes.ParrotViewHistory.value,
                modifier = Modifier.margin(top = 16.px),
            )
        }
    }
}

@Composable
private fun FeaturesSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .gap(32.px)
            .alignItems(AlignItems.FlexStart),
    ) {
        FeatureSection(
            title = StringRes.ParrotFeature1PageTitle.value,
            description = StringRes.ParrotFeature1PageDesc.value,
        )

        FeatureSection(
            title = StringRes.ParrotFeature2PageTitle.value,
            description = StringRes.ParrotFeature2PageDesc.value,
        )

        FeatureSection(
            title = StringRes.ParrotFeature3PageTitle.value,
            description = StringRes.ParrotFeature3PageDesc.value,
        )

        FeatureSection(
            title = StringRes.ParrotFeature4PageTitle.value,
            description = StringRes.ParrotFeature4PageDesc.value,
        )
    }
}

@Composable
private fun VersionHistorySection() {
    val palette = sitePalette()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .gap(24.px)
            .alignItems(AlignItems.FlexStart)
            .margin(top = 48.px)
            .scrollMargin(top = NavHeaderHeight.value()),
        ref = ref { element ->
            element.id = "version-history"
        },
    ) {
        H2Text(
            text = StringRes.VersionHistory.value,
            modifier = H2Style.toModifier(),
        )

        ParrotRes.releases.drop(1).forEachIndexed { index, release ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .gap(16.px),
                verticalAlignment = Alignment.Top,
            ) {
                Column(
                    modifier = Modifier.gap(0.px),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Box(
                        modifier = Modifier
                            .size(12.px)
                            .borderRadius(999.px)
                            .backgroundColor(palette.accent),
                    )
                    if (index < ParrotRes.releases.size - 2) {
                        Box(
                            modifier = Modifier
                                .width(2.px)
                                .height(100.percent)
                                .backgroundColor(palette.border),
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .gap(12.px)
                        .alignItems(AlignItems.FlexStart)
                        .margin(bottom = 32.px),
                ) {
                    ReleaseHistoryItem(release)
                }
            }
        }
    }
}

@Composable
private fun ReleaseChangelog(release: ParrotRelease) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .gap(16.px)
            .alignItems(AlignItems.FlexStart),
    ) {
        SpanText(
            text = StringRes.VersionWithDate(release.version, release.date).value,
            modifier = LabelStyle.toModifier(),
        )

        if (release.features.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .gap(8.px),
            ) {
                SpanText(
                    text = StringRes.NewFeatures.value,
                    modifier = BodyStyle.toModifier(),
                )
                release.features.forEach { feature ->
                    SpanText(
                        text = "• $feature",
                        modifier = BodySmallStyle.toModifier()
                            .margin(left = 16.px),
                    )
                }
            }
        }

        if (release.bugFixes.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .gap(8.px),
            ) {
                SpanText(
                    text = StringRes.BugFixes.value,
                    modifier = BodyStyle.toModifier(),
                )
                release.bugFixes.forEach { fix ->
                    SpanText(
                        text = "• $fix",
                        modifier = BodySmallStyle.toModifier()
                            .margin(left = 16.px),
                    )
                }
            }
        }

        if (release.improvements.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .gap(8.px),
            ) {
                SpanText(
                    text = StringRes.Improvements.value,
                    modifier = BodyStyle.toModifier(),
                )
                release.improvements.forEach { improvement ->
                    SpanText(
                        text = "• $improvement",
                        modifier = BodySmallStyle.toModifier()
                            .margin(left = 16.px),
                    )
                }
            }
        }
    }
}

@Composable
private fun ReleaseHistoryItem(release: ParrotRelease) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .gap(12.px)
            .alignItems(AlignItems.FlexStart),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .gap(16.px),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SpanText(
                text = StringRes.VersionLabel(release.version).value,
                modifier = LabelStyle.toModifier(),
            )
            SpanText(
                text = release.date,
                modifier = BodySmallStyle.toModifier(),
            )
            DownloadButton(
                url = release.apkUrl,
                label = StringRes.DownloadApk.value,
            )
        }

        if (release.features.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .gap(4.px),
            ) {
                SpanText(
                    text = StringRes.NewFeaturesLabel.value,
                    modifier = BodyStyle.toModifier(),
                )
                release.features.forEach { feature ->
                    SpanText(
                        text = "• $feature",
                        modifier = BodySmallStyle.toModifier()
                            .margin(left = 16.px),
                    )
                }
            }
        }

        if (release.bugFixes.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .gap(4.px),
            ) {
                SpanText(
                    text = StringRes.BugFixesLabel.value,
                    modifier = BodyStyle.toModifier(),
                )
                release.bugFixes.forEach { fix ->
                    SpanText(
                        text = "• $fix",
                        modifier = BodySmallStyle.toModifier()
                            .margin(left = 16.px),
                    )
                }
            }
        }

        if (release.improvements.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .gap(4.px),
            ) {
                SpanText(
                    text = StringRes.ImprovementsLabel.value,
                    modifier = BodyStyle.toModifier(),
                )
                release.improvements.forEach { improvement ->
                    SpanText(
                        text = "• $improvement",
                        modifier = BodySmallStyle.toModifier()
                            .margin(left = 16.px),
                    )
                }
            }
        }
    }
}

@Composable
private fun FeatureSection(title: String, description: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .gap(12.px),
    ) {
        H3Text(
            text = title,
            modifier = H3Style.toModifier(),
        )

        description.split("\n\n").forEach { paragraph ->
            SpanText(
                text = paragraph,
                modifier = BodyStyle.toModifier()
                    .fillMaxWidth()
                    .margin(bottom = 16.px),
            )
        }
    }
}
