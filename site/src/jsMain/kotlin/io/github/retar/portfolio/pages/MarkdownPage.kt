package io.github.retar.portfolio.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.browser.util.invokeLater
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.Language
import io.github.retar.portfolio.components.widgets.formatLongDate
import io.github.retar.portfolio.components.widgets.rise
import io.github.retar.portfolio.resources.StringRes
import io.github.retar.portfolio.styles.MonoEyebrowStyle
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobwebx.markdown.markdown
import io.github.retar.portfolio.LocalSetActiveSection
import io.github.retar.portfolio.components.PortfolioSectionId
import io.github.retar.portfolio.seo.Seo
import kotlinx.browser.window
import io.github.retar.portfolio.resources.Note
import io.github.retar.portfolio.resources.NotesRes
import io.github.retar.portfolio.utils.trackEvent
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import kotlinx.browser.document
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.Element
import org.w3c.dom.HTMLAnchorElement
import org.w3c.dom.HTMLScriptElement
import kotlin.js.json
import org.jetbrains.compose.web.dom.Div

@Composable
@Layout(".components.layouts.PageLayout")
fun MarkdownPage(content: @Composable () -> Unit) {
    val setActive = LocalSetActiveSection.current
    val ctx = rememberPageContext()
    val fm = ctx.markdown?.frontMatter
    val title = fm?.get("title")?.firstOrNull()
        ?: "Engineering Notes — Rok Retar"
    val description = fm?.get("description")?.firstOrNull()
        ?: "Engineering notes on Kotlin Multiplatform, Kobweb, and Compose by Rok Retar."
    val date = fm?.get("date")?.firstOrNull()
    val cover = fm?.get("cover")?.firstOrNull()?.let { "https://www.retar.app$it" }
        ?: "https://www.retar.app/og-image.png"
    val path = ctx.route.path

    Seo(
        title = "$title · Rok Retar",
        description = description,
        path = path,
        ogType = "article",
        ogImage = cover,
        jsonLd = """
            {
              "@context": "https://schema.org",
              "@graph": [
                {
                  "@type": "BlogPosting",
                  "headline": ${jsonString(title)},
                  "inLanguage": "en",
                  "image": ${jsonString(cover)},
                  "description": ${jsonString(description)},
                  ${if (date != null) "\"datePublished\": ${jsonString(date)}, \"dateModified\": ${jsonString(date)}," else ""}
                  "author": {
                    "@type": "Person",
                    "@id": "https://www.retar.app/#person",
                    "name": "Rok Retar",
                    "url": "https://www.retar.app/"
                  },
                  "publisher": {
                    "@type": "Person",
                    "@id": "https://www.retar.app/#person",
                    "name": "Rok Retar",
                    "url": "https://www.retar.app/"
                  },
                  "mainEntityOfPage": {
                    "@type": "WebPage",
                    "@id": "https://www.retar.app$path"
                  }
                },
                {
                  "@type": "BreadcrumbList",
                  "itemListElement": [
                    {
                      "@type": "ListItem",
                      "position": 1,
                      "name": "Home",
                      "item": "https://www.retar.app/"
                    },
                    {
                      "@type": "ListItem",
                      "position": 2,
                      "name": "Engineering Notes",
                      "item": "https://www.retar.app/#notes"
                    },
                    {
                      "@type": "ListItem",
                      "position": 3,
                      "name": ${jsonString(title)}
                    }
                  ]
                }
              ]
            }
        """.trimIndent(),
    )

    LaunchedEffect(Unit) {
        highlightCode()
        setActive(PortfolioSectionId.Blog)
    }

    var minutes by remember(path) { mutableStateOf<Int?>(null) }
    val language = Language.current
    val sectionLinkLabel = StringRes.ArticleSectionLink.value
    val linkCopiedLabel = StringRes.ArticleLinkCopied.value

    Div(attrs = Modifier.classNames("prose").toAttrs()) {
        Div(
            attrs = Modifier.rise(0).styleModifier {
                property("display", "flex")
                property("flex-wrap", "wrap")
                property("align-items", "baseline")
                property("gap", "0.5rem 1.25rem")
                property("margin-bottom", "2.5rem")
            }.toAttrs(),
        ) {
            Link(
                path = "/${PortfolioSectionId.Blog.path}",
                text = "← ${StringRes.ArticleBack.value}",
                modifier = MonoEyebrowStyle.toModifier().textDecorationLine(TextDecorationLine.None),
            )
            val meta = listOfNotNull(
                date?.let { formatLongDate(it, language) },
                minutes?.let { StringRes.ArticleMinutes(it).value },
            )
            if (meta.isNotEmpty()) {
                SpanText(text = meta.joinToString("  ·  "), modifier = MonoEyebrowStyle.toModifier())
            }
        }
        Div(
            attrs = Modifier.rise(1).classNames("prose-body").toAttrs {
                ref { element ->
                    // Give the Markdown content a frame to render before counting its words.
                    window.invokeLater {
                        val words = (element.textContent ?: "").split(Regex("\\s+")).count { it.isNotBlank() }
                        if (words > 0) minutes = maxOf(1, (words + 115) / 230)
                        addHeadingAnchors(element, sectionLinkLabel, linkCopiedLabel)
                    }
                    onDispose { }
                }
            },
        ) {
            content()
        }
        NotesRes.nextAfter(path)?.let { next -> NextNote(next) }
    }
}

@Composable
private fun NextNote(note: Note) {
    Div(attrs = Modifier.classNames("next-note").toAttrs()) {
        SpanText(text = StringRes.ArticleNext.value, modifier = MonoEyebrowStyle.toModifier())
        Link(
            path = note.route,
            modifier = Modifier.classNames("next-note-link")
                .onClick { trackEvent("next-note-open", json("route" to note.route)) },
        ) {
            SpanText(text = note.title.value, modifier = Modifier.classNames("next-note-title"))
            SpanText(text = note.description.value, modifier = Modifier.classNames("next-note-desc"))
        }
        Div(attrs = Modifier.classNames("next-note-meta").toAttrs()) {
            Link(
                path = "/${PortfolioSectionId.Blog.path}",
                text = "← ${StringRes.ArticleBack.value}",
                modifier = MonoEyebrowStyle.toModifier().textDecorationLine(TextDecorationLine.None),
            )
            A(href = "/feed.xml", attrs = MonoEyebrowStyle.toModifier().toAttrs()) {
                Text(StringRes.ArticleFeed.value)
            }
        }
    }
}

/**
 * Prism is only needed on notes, so it loads here instead of in every page's head.
 * Once it has loaded, later notes reuse it straight away.
 */
private fun highlightCode() {
    val prism = window.asDynamic().Prism
    if (prism != null) {
        prism.highlightAll()
        return
    }
    if (document.getElementById(PRISM_SCRIPT_ID) != null) return // Still loading; its onload highlights.
    val script = document.createElement("script") as HTMLScriptElement
    script.id = PRISM_SCRIPT_ID
    script.src = "/prism/prism.js"
    script.async = true
    script.onload = { window.asDynamic().Prism?.highlightAll() }
    document.head?.appendChild(script)
}

private const val PRISM_SCRIPT_ID = "prism-js"

/** Adds a "#" link to every section heading that copies a deep link to it. */
private fun addHeadingAnchors(root: Element, label: String, copiedLabel: String) {
    val headings = root.querySelectorAll("h2[id], h3[id]")
    for (i in 0 until headings.length) {
        val heading = headings.item(i) as? Element ?: continue
        if (heading.querySelector(".heading-anchor") != null) continue
        val id = heading.id
        val anchor = document.createElement("a") as HTMLAnchorElement
        anchor.className = "heading-anchor"
        anchor.href = "#$id"
        anchor.textContent = "#"
        anchor.setAttribute("aria-label", label)
        anchor.title = label
        anchor.addEventListener("click", { event ->
            event.preventDefault()
            window.history.replaceState(null, "", "#$id")
            heading.scrollIntoView(js("({ behavior: 'smooth', block: 'start' })"))
            window.navigator.asDynamic().clipboard?.writeText(window.location.href)
            anchor.classList.add("copied")
            anchor.setAttribute("aria-label", copiedLabel)
            anchor.title = copiedLabel
            window.setTimeout({
                anchor.classList.remove("copied")
                anchor.setAttribute("aria-label", label)
                anchor.title = label
            }, 1600)
        })
        heading.appendChild(anchor)
    }
}

private fun jsonString(value: String): String {
    val escaped = value
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\t", "\\t")
    return "\"$escaped\""
}

