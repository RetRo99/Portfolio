package io.github.retar.portfolio.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.components.widgets.DownloadButton
import io.github.retar.portfolio.resources.ImageRes
import io.github.retar.portfolio.resources.ParrotRelease
import io.github.retar.portfolio.resources.ParrotRes
import io.github.retar.portfolio.styles.BodyStyle
import io.github.retar.portfolio.styles.BodySmallStyle
import io.github.retar.portfolio.styles.HeadingLStyle
import io.github.retar.portfolio.styles.HeadingMStyle
import io.github.retar.portfolio.styles.HeadingXLStyle
import io.github.retar.portfolio.styles.LabelStyle
import io.github.retar.portfolio.styles.SubtitleStyle
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.px

@Page
@Layout(".components.layouts.PageLayout")
@Composable
fun ParrotPage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.px, bottom = 80.px),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .maxWidth(800.px)
                .gap(48.px)
                .alignItems(AlignItems.Center),
        ) {
            // Hero Section
            Column(
                modifier = Modifier
                    .gap(24.px)
                    .alignItems(AlignItems.Center),
            ) {
                Image(
                    src = ImageRes.ParrotIcon.path,
                    description = "Parrot App Icon",
                    modifier = Modifier
                        .size(120.px)
                        .borderRadius(24.px)
                )

                SpanText(
                    text = "Parrot",
                    modifier = HeadingXLStyle.toModifier(),
                )

                SpanText(
                    text = "A beautifully crafted e-reader app and companion app for Storyteller, designed for book lovers who want more than just reading — they want an experience.",
                    modifier = SubtitleStyle.toModifier()
                        .fillMaxWidth()
                        .maxWidth(650.px),
                )
            }

            // Download Section
            ReleasesSection()

            // Features Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .gap(32.px)
                    .alignItems(AlignItems.FlexStart),
            ) {
                FeatureSection(
                    title = "Read Your Way",
                    description = "Every reader is different, and Parrot embraces that. Customize your reading experience down to the finest detail — choose from multiple fonts, adjust text size with a simple pinch gesture, and set margins and line spacing exactly how you like them. Reading at night? Switch to Dark mode. Prefer a warmer tone? Sepia mode reduces eye strain during long reading sessions. And for those using e-ink devices, we've built a dedicated display mode that looks crisp and natural on your screen.\n\nNavigation is intuitive and stays out of your way. Tap the edges of the screen to flip pages, swipe through chapters, or dive into the table of contents to jump to any section instantly. When you want to lose yourself in a story, fullscreen mode removes all distractions, leaving just you and the words."
                )

                FeatureSection(
                    title = "Listen and Follow Along",
                    description = "Parrot isn't just for reading — it's for listening too. Play audiobooks in the background while you commute, exercise, or relax. Control playback directly from your notification bar without ever opening the app.\n\nBut the real magic happens with Read-Aloud books. As the narrator speaks, the text highlights in real-time, word by word or sentence by sentence. It's perfect for language learners, children developing reading skills, or anyone who loves the combination of seeing and hearing a story unfold. Customize the highlight color and style to match your preference — whether you like a soft yellow highlight, a subtle underline, or bold text. Speed up the narration when you're in a hurry, or slow it down to savor every word."
                )

                FeatureSection(
                    title = "Track Your Reading Journey",
                    description = "Building a reading habit is easier when you can see your progress. Parrot tracks your reading time automatically — see how much you've read today, this week, or this month. Watch your reading streak grow as you read consistently day after day. Discover which books you've spent the most time with, and see how your reading splits between ebooks and audiobooks.\n\nEach chapter shows an estimated reading time based on your personal reading speed, so you always know if you have time for \"just one more chapter\" before bed."
                )

                FeatureSection(
                    title = "Pick Up Where You Left Off",
                    description = "Life is busy, and Parrot understands that. Your reading position is saved automatically, so you can seamlessly switch between your phone, tablet, and desktop without losing your place. Tap a notification and jump straight back into your book — no searching, no scrolling, just reading."
                )
            }
        }
    }
}

@Composable
private fun ReleasesSection() {
    val latestRelease = ParrotRes.latestRelease

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .gap(24.px)
            .alignItems(AlignItems.Center),
    ) {
        SpanText(
            text = "Download",
            modifier = HeadingMStyle.toModifier(),
        )

        DownloadButton(
            url = latestRelease.apkUrl,
            label = "Download v${latestRelease.version} (APK)",
        )

        // Latest Release Changelog
        ReleaseChangelog(latestRelease)

        // Version History
        if (ParrotRes.releases.size > 1) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .gap(24.px)
                    .alignItems(AlignItems.FlexStart)
                    .margin(top = 48.px),
            ) {
                SpanText(
                    text = "Version History",
                    modifier = HeadingMStyle.toModifier(),
                )

                ParrotRes.releases.drop(1).forEach { release ->
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
            text = "Version ${release.version} (${release.date})",
            modifier = LabelStyle.toModifier(),
        )

        if (release.features.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .gap(8.px),
            ) {
                SpanText(
                    text = "New Features",
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
                    text = "Bug Fixes",
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
                    text = "Improvements",
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
                text = "Version ${release.version}",
                modifier = LabelStyle.toModifier(),
            )
            SpanText(
                text = release.date,
                modifier = BodySmallStyle.toModifier(),
            )
            DownloadButton(
                url = release.apkUrl,
                label = "Download APK",
            )
        }

        if (release.features.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .gap(4.px),
            ) {
                SpanText(
                    text = "New Features:",
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
                    text = "Bug Fixes:",
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
                    text = "Improvements:",
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
        SpanText(
            text = title,
            modifier = HeadingMStyle.toModifier(),
        )

        // Split description by newlines to create paragraphs
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

