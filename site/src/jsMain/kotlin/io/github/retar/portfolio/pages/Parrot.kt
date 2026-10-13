package io.github.retar.portfolio.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.compose.dom.ref
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.LocalSetActiveSection
import io.github.retar.portfolio.components.PortfolioSectionId
import io.github.retar.portfolio.components.widgets.DownloadButton
import io.github.retar.portfolio.components.widgets.EditorialGrid
import io.github.retar.portfolio.components.widgets.PhoneRowItemStyle
import io.github.retar.portfolio.components.widgets.PhoneRowStyle
import io.github.retar.portfolio.components.widgets.PhoneShot
import io.github.retar.portfolio.components.widgets.PrimaryButton
import io.github.retar.portfolio.components.widgets.TextLink
import io.github.retar.portfolio.components.widgets.rise
import io.github.retar.portfolio.resources.ImageRes
import io.github.retar.portfolio.resources.ParrotRelease
import io.github.retar.portfolio.resources.ParrotRes
import io.github.retar.portfolio.resources.ProjectsRes
import io.github.retar.portfolio.resources.StringRes
import io.github.retar.portfolio.seo.Seo
import io.github.retar.portfolio.styles.BodyStyle
import io.github.retar.portfolio.styles.CaptionStyle
import io.github.retar.portfolio.styles.DisplayStyle
import io.github.retar.portfolio.styles.H1Text
import io.github.retar.portfolio.styles.H2Style
import io.github.retar.portfolio.styles.H2Text
import io.github.retar.portfolio.styles.H3Style
import io.github.retar.portfolio.styles.H3Text
import io.github.retar.portfolio.styles.MonoAccentStyle
import io.github.retar.portfolio.styles.MonoEyebrowStyle
import io.github.retar.portfolio.styles.SubtitleStyle
import io.github.retar.portfolio.styles.eyebrowText
import io.github.retar.portfolio.styles.sitePalette
import io.github.retar.portfolio.utils.trackEvent
import org.jetbrains.compose.web.dom.Div

private const val VERSION_HISTORY_ID = "version-history"

@Page
@Layout(".components.layouts.PageLayout")
@Composable
fun ParrotPage() {
    val setActive = LocalSetActiveSection.current

    LaunchedEffect(Unit) {
        setActive(PortfolioSectionId.Projects)
    }

    Seo(
        title = "Parrot: ebook and audiobook reader for Android · Rok Retar",
        description = "Parrot reads your own ebooks and audiobooks on Android phones, tablets and e-ink readers, with read-along highlighting, on-device voices and Storyteller, Audiobookshelf and Parrot Cloud libraries.",
        path = "/parrot",
        ogType = "article",
        jsonLd = """
            {
              "@context": "https://schema.org",
              "@type": "SoftwareApplication",
              "name": "Parrot",
              "applicationCategory": "BookApplication",
              "operatingSystem": "Android",
              "description": "A reader for your own ebooks and audiobooks on Android, with read-along highlighting, on-device voices and Storyteller, Audiobookshelf and Parrot Cloud libraries.",
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
    Div(attrs = Modifier.styleModifier { property("width", "100%") }.toAttrs()) {
        ParrotHero()
        ScreensRow()
        FeaturesSection()
        LatestReleaseSection()
        if (ParrotRes.releases.size > 1) {
            VersionHistorySection()
        }
    }
}

private fun Modifier.column(gap: String): Modifier = styleModifier {
    property("display", "flex")
    property("flex-direction", "column")
    property("gap", gap)
}

private fun Modifier.measure(ch: Int): Modifier =
    styleModifier { property("max-width", "${ch}ch") }

private fun Modifier.sectionSpacing(): Modifier =
    styleModifier { property("padding-block", "clamp(3rem, 7vw, 6rem)") }

val ParrotHeroStyle = CssStyle {
    base {
        Modifier.styleModifier {
            property("display", "grid")
            property("grid-template-columns", "minmax(0, 1fr)")
            property("gap", "2.5rem")
            property("align-items", "center")
            property("padding-block", "clamp(2.5rem, 6vw, 5rem) clamp(2rem, 4vw, 3rem)")
        }
    }
    Breakpoint.MD {
        Modifier.styleModifier {
            property("grid-template-columns", "minmax(0, 7fr) minmax(0, 5fr)")
            property("gap", "3rem")
        }
    }
}

val ParrotHeroPhoneStyle = CssStyle {
    base {
        Modifier.styleModifier { property("display", "none") }
    }
    Breakpoint.MD {
        Modifier.styleModifier {
            property("display", "block")
            property("width", "min(100%, 300px)")
            property("justify-self", "center")
            property("transform", "rotate(2deg)")
        }
    }
}

@Composable
private fun ParrotHero() {
    val palette = sitePalette()
    Div(attrs = ParrotHeroStyle.toModifier().toAttrs()) {
        Div(attrs = Modifier.column("1.5rem").toAttrs()) {
            Div(
                attrs = Modifier.rise(0).styleModifier {
                    property("display", "flex")
                    property("align-items", "center")
                    property("gap", "0.875rem")
                }.toAttrs(),
            ) {
                Image(
                    src = ImageRes.ParrotIcon.path,
                    description = StringRes.ParrotIconDesc.value,
                    modifier = Modifier.styleModifier {
                        property("width", "56px")
                        property("height", "56px")
                        property("border-radius", "14px")
                        property("outline", "1px solid ${palette.border}")
                    },
                    width = ImageRes.ParrotIcon.intrinsicWidth,
                    height = ImageRes.ParrotIcon.intrinsicHeight,
                )
                SpanText(
                    text = StringRes.ParrotCaseStudyEyebrow.value.eyebrowText(),
                    modifier = MonoEyebrowStyle.toModifier(),
                )
            }
            H1Text(text = StringRes.ParrotPageHeading.value, modifier = DisplayStyle.toModifier().rise(1))
            SpanText(
                text = StringRes.ParrotPageSubhead.value,
                modifier = SubtitleStyle.toModifier().measure(56).rise(2),
            )
            Div(
                attrs = Modifier.rise(3).styleModifier {
                    property("display", "flex")
                    property("flex-wrap", "wrap")
                    property("align-items", "center")
                    property("gap", "0.75rem 1.25rem")
                    property("margin-top", "0.5rem")
                }.toAttrs(),
            ) {
                PrimaryButton(
                    url = ParrotRes.latestRelease.apkUrl,
                    label = StringRes.ParrotDownloadLatest(ParrotRes.latestRelease.version).value,
                    openExternally = true,
                    onClick = { trackEvent("apk-download", kotlin.js.json("version" to ParrotRes.latestRelease.version)) },
                )
                if (ParrotRes.releases.size > 1) {
                    TextLink(
                        url = "#$VERSION_HISTORY_ID",
                        label = StringRes.ParrotViewHistory.value,
                        onClick = { trackEvent("view-version-history") },
                    )
                }
            }
            SpanText(text = StringRes.ParrotMeta.value, modifier = CaptionStyle.toModifier().rise(4))
        }

        val heroShot = ProjectsRes.SelectedProjects.first()
        PhoneShot(
            image = heroShot.image,
            description = heroShot.description.value,
            modifier = ParrotHeroPhoneStyle.toModifier().rise(2),
        )
    }
}

@Composable
private fun ScreensRow() {
    Div(
        attrs = PhoneRowStyle.toModifier()
            .styleModifier { property("padding-bottom", "clamp(2.5rem, 6vw, 5rem)") }
            .toAttrs(),
    ) {
        ProjectsRes.SelectedProjects.drop(1).forEachIndexed { index, shot ->
            val offset = listOf("1.5rem", "0", "2rem", "0.5rem")[index % 4]
            PhoneShot(
                image = shot.image,
                description = shot.description.value,
                modifier = PhoneRowItemStyle.toModifier().styleModifier { property("--offset", offset) },
            )
        }
    }
}

@Composable
private fun FeaturesSection() {
    val features = listOf(
        StringRes.ParrotFeature1PageTitle.value to StringRes.ParrotFeature1PageDesc.value,
        StringRes.ParrotFeature2PageTitle.value to StringRes.ParrotFeature2PageDesc.value,
        StringRes.ParrotFeature3PageTitle.value to StringRes.ParrotFeature3PageDesc.value,
        StringRes.ParrotFeature4PageTitle.value to StringRes.ParrotFeature4PageDesc.value,
    )
    val palette = sitePalette()

    EditorialGrid(eyebrow = StringRes.ParrotWhatItDoes.value, modifier = Modifier.sectionSpacing()) {
        features.forEachIndexed { index, (title, description) ->
            Div(
                attrs = Modifier.column("0.875rem").styleModifier {
                    if (index > 0) {
                        property("border-top", "1px solid ${palette.border}")
                        property("padding-top", "2rem")
                        property("margin-top", "0.5rem")
                    }
                }.toAttrs(),
            ) {
                H2Text(
                    text = title,
                    modifier = H2Style.toModifier().styleModifier {
                        property("font-size", "clamp(1.75rem, 2.5vw + 0.75rem, 2.5rem)")
                    },
                )
                description.split("\n\n").forEach { paragraph ->
                    SpanText(text = paragraph.trim(), modifier = BodyStyle.toModifier().measure(66))
                }
            }
        }
    }
}

@Composable
private fun LatestReleaseSection() {
    val latest = ParrotRes.latestRelease

    EditorialGrid(eyebrow = StringRes.ParrotLatestRelease.value, modifier = Modifier.sectionSpacing()) {
        Div(attrs = Modifier.column("0.5rem").toAttrs()) {
            H2Text(text = StringRes.VersionLabel(latest.version).value, modifier = H2Style.toModifier())
            SpanText(text = latest.date, modifier = CaptionStyle.toModifier())
        }
        ReleaseNotes(latest)
        Div {
            DownloadButton(
                url = latest.apkUrl,
                label = StringRes.ParrotDownloadLatest(latest.version).value,
            )
        }
    }
}

@Composable
private fun VersionHistorySection() {
    Div(
        attrs = Modifier.toAttrs {
            ref { element ->
                element.id = VERSION_HISTORY_ID
                element.style.setProperty("scroll-margin-top", "80px")
                onDispose { }
            }
        },
    ) {
        EditorialGrid(eyebrow = StringRes.VersionHistory.value, modifier = Modifier.sectionSpacing()) {
            ParrotRes.releases.drop(1).forEach { release ->
                ReleaseHistoryRow(release)
            }
        }
    }
}

val ReleaseRowStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier.styleModifier {
            property("display", "grid")
            property("grid-template-columns", "minmax(0, 1fr)")
            property("gap", "0.75rem")
            property("padding", "1.5rem 0")
            property("border-top", "1px solid ${palette.border}")
        }
    }
    Breakpoint.SM {
        Modifier.styleModifier {
            property("grid-template-columns", "9rem minmax(0, 1fr)")
            property("gap", "1.5rem")
        }
    }
}

@Composable
private fun ReleaseHistoryRow(release: ParrotRelease) {
    Div(attrs = ReleaseRowStyle.toModifier().toAttrs()) {
        Div(attrs = Modifier.column("0.25rem").toAttrs()) {
            H3Text(text = StringRes.VersionLabel(release.version).value, modifier = H3Style.toModifier())
            SpanText(text = release.date, modifier = CaptionStyle.toModifier())
            TextLink(
                url = release.apkUrl,
                label = StringRes.DownloadApk.value,
                modifier = Modifier.styleModifier { property("margin-top", "0.5rem") },
                onClick = { trackEvent("apk-download", kotlin.js.json("version" to release.version)) },
            )
        }
        ReleaseNotes(release)
    }
}

@Composable
private fun ReleaseNotes(release: ParrotRelease) {
    Div(attrs = Modifier.column("1rem").measure(70).toAttrs()) {
        ChangeGroup(StringRes.NewFeatures.value, release.features)
        ChangeGroup(StringRes.Improvements.value, release.improvements)
        ChangeGroup(StringRes.BugFixes.value, release.bugFixes)
    }
}

@Composable
private fun ChangeGroup(label: String, items: List<String>) {
    if (items.isEmpty()) return
    Div(attrs = Modifier.column("0.375rem").toAttrs()) {
        SpanText(text = label, modifier = MonoEyebrowStyle.toModifier())
        items.forEach { item ->
            Div(
                attrs = Modifier.styleModifier {
                    property("display", "grid")
                    property("grid-template-columns", "1rem minmax(0, 1fr)")
                    property("gap", "0.5rem")
                }.toAttrs(),
            ) {
                SpanText(text = "–", modifier = MonoAccentStyle.toModifier().styleModifier { property("line-height", "1.65") })
                SpanText(text = item, modifier = BodyStyle.toModifier().styleModifier { property("font-size", "0.9375rem") })
            }
        }
    }
}
