package io.github.retar.portfolio.pages

import androidx.compose.runtime.Composable
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
import io.github.retar.portfolio.components.PortfolioSection
import io.github.retar.portfolio.components.PortfolioSectionId
import io.github.retar.portfolio.components.widgets.ArticleRow
import io.github.retar.portfolio.components.widgets.Callout
import io.github.retar.portfolio.components.widgets.DownloadButton
import io.github.retar.portfolio.components.widgets.EditorialGrid
import io.github.retar.portfolio.components.widgets.FactList
import io.github.retar.portfolio.components.widgets.PhoneRowItemStyle
import io.github.retar.portfolio.components.widgets.PhoneRowStyle
import io.github.retar.portfolio.components.widgets.PhoneShot
import io.github.retar.portfolio.components.widgets.PrimaryButton
import io.github.retar.portfolio.components.widgets.ResumeDownloadButton
import io.github.retar.portfolio.components.widgets.TextLink
import io.github.retar.portfolio.components.widgets.rise
import io.github.retar.portfolio.resources.ImageRes
import io.github.retar.portfolio.resources.LinkRes
import io.github.retar.portfolio.resources.NotesRes
import io.github.retar.portfolio.resources.ParrotRes
import io.github.retar.portfolio.resources.ProjectsRes
import io.github.retar.portfolio.resources.StringRes
import io.github.retar.portfolio.seo.Seo
import io.github.retar.portfolio.styles.BodyStyle
import io.github.retar.portfolio.styles.CaptionStyle
import io.github.retar.portfolio.styles.DisplayStyle
import io.github.retar.portfolio.styles.H1Style
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
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.selectors.hover
import kotlin.js.json
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.Ol

@Page
@Layout(".components.layouts.PageLayout")
@Composable
fun IndexPage() {
    Seo(
        title = "Rok Retar · Android and Kotlin Multiplatform engineer",
        description = "Rok Retar is a mobile engineer in Slovenia. He builds apps in Kotlin, for Android and iOS. Work, engineering notes and contact.",
        path = "/",
        jsonLd = """
            {
              "@context": "https://schema.org",
              "@type": "ProfilePage",
              "@id": "https://www.retar.app/#profile",
              "url": "https://www.retar.app/",
              "name": "Rok Retar",
              "dateModified": "2026-10-13",
              "mainEntity": { "@id": "https://www.retar.app/#person" },
              "isPartOf": { "@id": "https://www.retar.app/#website" }
            }
        """.trimIndent(),
    )
    Div(attrs = Modifier.styleModifier { property("width", "100%") }.toAttrs()) {
        HeroSection()
        ParrotShowcaseSection()
        PhilosophySection()
        ExperienceSection()
        ArticlesSection()
    }
}

private fun Modifier.sectionSpacing(): Modifier =
    styleModifier { property("padding-block", "clamp(3.5rem, 8vw, 7rem)") }

private fun Modifier.measure(ch: Int): Modifier =
    styleModifier { property("max-width", "${ch}ch") }

private fun Modifier.column(gap: String): Modifier = styleModifier {
    property("display", "flex")
    property("flex-direction", "column")
    property("gap", gap)
}

private fun Modifier.actionRow(): Modifier = styleModifier {
    property("display", "flex")
    property("flex-wrap", "wrap")
    property("align-items", "center")
    property("gap", "0.75rem 1.25rem")
}

val HeroGridStyle = CssStyle {
    base {
        Modifier.styleModifier {
            property("display", "grid")
            property("grid-template-columns", "minmax(0, 1fr)")
            property("gap", "2rem")
            property("align-items", "end")
            property("padding-block", "clamp(2.5rem, 7vw, 6rem) clamp(3.5rem, 8vw, 7rem)")
        }
    }
    Breakpoint.MD {
        Modifier.styleModifier {
            property("grid-template-columns", "minmax(0, 8fr) minmax(0, 4fr)")
            property("gap", "3rem")
        }
    }
}

val HeroPortraitStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier.styleModifier {
            property("width", "min(46vw, 180px)")
            property("aspect-ratio", "4 / 5")
            property("border-radius", "10px")
            property("overflow", "hidden")
            property("background", palette.surface.toString())
            property("order", "-1")
        }
    }
    Breakpoint.MD {
        Modifier.styleModifier {
            property("width", "100%")
            property("max-width", "300px")
            property("justify-self", "end")
            property("order", "0")
        }
    }
}

/** A heading that links somewhere: no underline, an accent arrow, and the accent colour on hover. */
val HeadingLinkStyle = CssStyle {
    base {
        Modifier.styleModifier {
            property("color", "inherit")
            property("text-decoration", "none")
            property("align-self", "flex-start")
        }
    }
    hover {
        Modifier.styleModifier { property("color", sitePalette().accent.toString()) }
    }
}

/** A screenshot that opens the case study: it lifts slightly on hover. */
val PhoneLinkStyle = CssStyle {
    base {
        Modifier.styleModifier {
            property("display", "block")
            property("border-radius", "28px")
            property("transition", "transform 220ms var(--ease-out)")
        }
    }
    hover {
        Modifier.styleModifier { property("transform", "translateY(-6px)") }
    }
}

@Composable
private fun HeroSection() {
    PortfolioSection(
        section = PortfolioSectionId.About,
        modifier = Modifier.styleModifier { property("width", "100%") },
    ) {
        Div(attrs = HeroGridStyle.toModifier().toAttrs()) {
            Div(attrs = Modifier.column("1.5rem").toAttrs()) {
                SpanText(
                    text = StringRes.HeroEyebrow.value.eyebrowText(),
                    modifier = MonoEyebrowStyle.toModifier().rise(0),
                )
                // The name rises from behind its own baseline instead of the shared fade-up.
                H1(attrs = DisplayStyle.toModifier().toAttrs()) {
                    Span(attrs = { classes("mask-line") }) {
                        Span { Text(StringRes.HeroName.value) }
                    }
                }
                Div(attrs = Modifier.column("1rem").rise(2).toAttrs()) {
                    SpanText(
                        text = StringRes.HeroHeadline.value,
                        modifier = H1Style.toModifier().measure(34),
                    )
                    SpanText(
                        text = StringRes.HeroSubtitle.value,
                        modifier = BodyStyle.toModifier().measure(60),
                    )
                }
                HeroCtaRow()
                SpanText(
                    text = StringRes.HeroStatusStrip.value,
                    modifier = CaptionStyle.toModifier().rise(4),
                )
            }

            Div(attrs = HeroPortraitStyle.toModifier().rise(2).toAttrs()) {
                Image(
                    src = ImageRes.ProfileImage.path,
                    description = "Portrait of Rok Retar",
                    modifier = Modifier.styleModifier {
                        property("width", "100%")
                        property("height", "100%")
                        property("object-fit", "cover")
                        property("display", "block")
                    },
                    width = ImageRes.ProfileImage.intrinsicWidth,
                    height = ImageRes.ProfileImage.intrinsicHeight,
                )
            }
        }
    }
}

@Composable
private fun HeroCtaRow() {
    Div(attrs = Modifier.actionRow().rise(3).styleModifier { property("margin-top", "0.5rem") }.toAttrs()) {
        PrimaryButton(
            url = PortfolioSectionId.Projects.path,
            label = StringRes.CtaReviewWork.value,
            onClick = { trackEvent("cta-review-work") },
        )
        ResumeDownloadButton(
            url = LinkRes.External.Resume,
            label = StringRes.CtaDownloadResume.value,
        )
        TextLink(
            url = PortfolioSectionId.Blog.path,
            label = StringRes.CtaReadNotes.value,
            onClick = { trackEvent("cta-read-notes") },
        )
    }
}

@Composable
private fun ParrotShowcaseSection() {
    PortfolioSection(
        section = PortfolioSectionId.Projects,
        modifier = Modifier.styleModifier { property("width", "100%") },
    ) {
        EditorialGrid(eyebrow = StringRes.ParrotEyebrow.value, modifier = Modifier.sectionSpacing()) {
            Div(attrs = Modifier.column("1rem").toAttrs()) {
                Link(
                    path = LinkRes.Internal.Parrot,
                    modifier = HeadingLinkStyle.toModifier()
                        .onClick { trackEvent("case-study-open", json("from" to "heading")) },
                ) {
                    H2(attrs = H2Style.toModifier().toAttrs()) {
                        Text(StringRes.ParrotHeading.value)
                        Span(attrs = Modifier.classNames("heading-link-arrow").toAttrs { attr("aria-hidden", "true") }) {
                            Text("→")
                        }
                    }
                }
                SpanText(
                    text = StringRes.ParrotSubhead.value,
                    modifier = SubtitleStyle.toModifier().measure(58),
                )
            }

            Div(attrs = PhoneRowStyle.toModifier().toAttrs()) {
                ProjectsRes.SelectedProjects.forEachIndexed { index, shot ->
                    // Alternate vertical offsets give the row a loose, hand-placed rhythm on desktop.
                    val offset = listOf("0", "2.5rem", "1rem", "3rem", "0.5rem")[index % 5]
                    Link(
                        path = LinkRes.Internal.Parrot,
                        modifier = PhoneRowItemStyle.toModifier()
                            .then(PhoneLinkStyle.toModifier())
                            .styleModifier { property("--offset", offset) }
                            .onClick { trackEvent("case-study-open", json("from" to "screenshot")) },
                    ) {
                        PhoneShot(image = shot.image, description = shot.description.value)
                    }
                }
            }

            FactList(items = parrotFeatures())

            Div(attrs = Modifier.actionRow().toAttrs()) {
                PrimaryButton(
                    url = LinkRes.Internal.Parrot,
                    label = StringRes.CtaOpenCaseStudy.value,
                    onClick = { trackEvent("case-study-open") },
                )
                DownloadButton(
                    url = ParrotRes.latestRelease.apkUrl,
                    label = StringRes.DownloadVersionApk(ParrotRes.latestRelease.version).value,
                )
            }
        }
    }
}

@Composable
private fun parrotFeatures(): List<Pair<String, String>> = listOf(
    StringRes.ParrotFeature1Title.value to StringRes.ParrotFeature1Desc.value,
    StringRes.ParrotFeature2Title.value to StringRes.ParrotFeature2Desc.value,
    StringRes.ParrotFeature3Title.value to StringRes.ParrotFeature3Desc.value,
    StringRes.ParrotFeature4Title.value to StringRes.ParrotFeature4Desc.value,
    StringRes.ParrotFeature5Title.value to StringRes.ParrotFeature5Desc.value,
    StringRes.ParrotFeature6Title.value to StringRes.ParrotFeature6Desc.value,
    StringRes.ParrotFeature7Title.value to StringRes.ParrotFeature7Desc.value,
    StringRes.ParrotFeature8Title.value to StringRes.ParrotFeature8Desc.value,
)

@Composable
private fun PhilosophySection() {
    PortfolioSection(
        section = PortfolioSectionId.Philosophy,
        modifier = Modifier.styleModifier { property("width", "100%") },
    ) {
        EditorialGrid(eyebrow = StringRes.PhilosophyEyebrow.value, modifier = Modifier.sectionSpacing()) {
            H2Text(
                text = StringRes.PhilosophyHeading.value,
                modifier = H2Style.toModifier().measure(22),
            )
            Div(attrs = Modifier.column("1.25rem").measure(66).toAttrs()) {
                SpanText(text = StringRes.PhilosophyBody1.value, modifier = BodyStyle.toModifier())
                SpanText(text = StringRes.PhilosophyBody2.value, modifier = BodyStyle.toModifier())
                SpanText(text = StringRes.PhilosophyBody3.value, modifier = BodyStyle.toModifier())
            }
            PrinciplesList()
            Callout(
                label = StringRes.MigrationCalloutLabel.value,
                body = StringRes.MigrationCalloutBody.value,
                modifier = Modifier.measure(66),
            )
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
    val palette = sitePalette()

    Div(attrs = Modifier.measure(66).toAttrs()) {
        principles.forEach { principle ->
            Div(
                attrs = Modifier.styleModifier {
                    property("display", "grid")
                    property("grid-template-columns", "1.25rem minmax(0, 1fr)")
                    property("gap", "0.75rem")
                    property("padding", "0.875rem 0")
                    property("border-top", "1px solid ${palette.border}")
                }.toAttrs(),
            ) {
                SpanText(
                    text = "—",
                    modifier = MonoAccentStyle.toModifier().styleModifier { property("line-height", "1.65") },
                )
                SpanText(text = principle, modifier = BodyStyle.toModifier())
            }
        }
    }
}

@Composable
private fun ExperienceSection() {
    // Shares the Philosophy anchor for nav highlighting, but needs its own DOM id to stay valid HTML.
    Div(attrs = Modifier.styleModifier { property("width", "100%") }.toAttrs()) {
        EditorialGrid(eyebrow = StringRes.ExperienceEyebrow.value, modifier = Modifier.sectionSpacing()) {
            H2Text(text = StringRes.ExperienceStackTitle.value, modifier = H2Style.toModifier().measure(22))
            Paragraphs(StringRes.ExperienceStackBody.value)
            H3Text(
                text = StringRes.ExperienceMigrationTitle.value,
                modifier = H3Style.toModifier().styleModifier { property("margin-top", "0.5rem") },
            )
            Paragraphs(StringRes.ExperienceMigrationBody.value)
            H3Text(
                text = StringRes.TimelineTitle.value,
                modifier = H3Style.toModifier().styleModifier { property("margin-top", "0.5rem") },
            )
            Timeline(
                listOf(
                    TimelineEntry(
                        years = StringRes.TimelineDevRevYears.value,
                        role = StringRes.TimelineDevRevRole.value,
                        place = "DevRev · Ljubljana",
                        body = StringRes.TimelineDevRevBody.value,
                    ),
                    TimelineEntry(
                        years = "2019 – 2023",
                        role = StringRes.TimelineNetceteraRole.value,
                        place = "Netcetera · Ljubljana",
                        body = StringRes.TimelineNetceteraBody.value,
                    ),
                    TimelineEntry(
                        years = "2020",
                        role = StringRes.TimelineEducationRole.value,
                        place = "OpenClassrooms",
                        body = StringRes.TimelineEducationBody.value,
                    ),
                ),
            )
        }
    }
}

private class TimelineEntry(val years: String, val role: String, val place: String, val body: String)

val TimelineRowStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier.styleModifier {
            property("display", "grid")
            property("grid-template-columns", "minmax(0, 1fr)")
            property("gap", "0.375rem")
            property("padding", "1.125rem 0")
            property("border-top", "1px solid ${palette.border}")
        }
    }
    Breakpoint.SM {
        Modifier.styleModifier {
            property("grid-template-columns", "9rem minmax(0, 1fr)")
            property("column-gap", "1.5rem")
        }
    }
}

/** Roles as a compact list: years on the left, role, place and one line of what I did on the right. */
@Composable
private fun Timeline(entries: List<TimelineEntry>) {
    val palette = sitePalette()
    Ol(attrs = Modifier.measure(66).styleModifier {
        property("list-style", "none")
        property("margin", "0")
        property("padding", "0")
    }.toAttrs()) {
        entries.forEach { entry ->
            Li(attrs = TimelineRowStyle.toModifier().toAttrs()) {
                SpanText(
                    text = entry.years,
                    modifier = MonoEyebrowStyle.toModifier().styleModifier { property("padding-top", "0.3rem") },
                )
                Div(attrs = Modifier.column("0.25rem").toAttrs()) {
                    SpanText(
                        text = entry.role,
                        modifier = BodyStyle.toModifier().styleModifier {
                            property("color", palette.textPrimary.toString())
                            property("font-weight", "600")
                        },
                    )
                    SpanText(text = entry.place, modifier = CaptionStyle.toModifier())
                    SpanText(
                        text = entry.body,
                        modifier = BodyStyle.toModifier().styleModifier { property("margin-top", "0.25rem") },
                    )
                }
            }
        }
    }
}

/** Copy blocks separate paragraphs with a blank line; render each as its own paragraph. */
@Composable
private fun Paragraphs(text: String) {
    Div(attrs = Modifier.column("1.25rem").measure(66).toAttrs()) {
        text.split("\n\n").forEach { paragraph ->
            SpanText(text = paragraph.trim(), modifier = BodyStyle.toModifier())
        }
    }
}

@Composable
private fun ArticlesSection() {
    PortfolioSection(
        section = PortfolioSectionId.Blog,
        modifier = Modifier.styleModifier { property("width", "100%") },
    ) {
        EditorialGrid(eyebrow = StringRes.NotesEyebrow.value, modifier = Modifier.sectionSpacing()) {
            Div(attrs = Modifier.column("1rem").toAttrs()) {
                H2Text(text = StringRes.NotesTitle.value, modifier = H2Style.toModifier())
                SpanText(
                    text = StringRes.NotesSubtitle.value,
                    modifier = SubtitleStyle.toModifier().measure(58),
                )
            }
            Div {
                NotesRes.all.forEach { note ->
                    ArticleRow(
                        title = note.title.value,
                        description = note.description.value,
                        route = note.route,
                        image = note.image,
                        readLabel = StringRes.CtaReadNote.value,
                    )
                }
            }
        }
    }
}
