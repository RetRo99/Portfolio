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
    val path = ctx.route.path

    Seo(
        title = "$title — Rok Retar",
        description = description,
        path = path,
        ogType = "article",
        jsonLd = """
            {
              "@context": "https://schema.org",
              "@graph": [
                {
                  "@type": "BlogPosting",
                  "headline": ${jsonString(title)},
                  "description": ${jsonString(description)},
                  ${if (date != null) "\"datePublished\": ${jsonString(date)}," else ""}
                  "author": {
                    "@type": "Person",
                    "name": "Rok Retar",
                    "url": "https://www.retar.app"
                  },
                  "publisher": {
                    "@type": "Person",
                    "name": "Rok Retar",
                    "url": "https://www.retar.app"
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
        window.asDynamic().Prism?.highlightAll()
        setActive(PortfolioSectionId.Blog)
    }

    var minutes by remember(path) { mutableStateOf<Int?>(null) }
    val language = Language.current

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
                date?.let { formatDate(it, language) },
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
                    }
                    onDispose { }
                }
            },
        ) {
            content()
        }
    }
}

private fun formatDate(iso: String, language: Language): String {
    val locale = when (language) {
        Language.EN -> "en-GB"
        Language.SL -> "sl-SI"
        Language.LV -> "lv-LV"
    }
    val options = js("({ year: 'numeric', month: 'long', day: 'numeric' })")
    return js("new Date(iso + 'T12:00:00')").toLocaleDateString(locale, options) as String
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

