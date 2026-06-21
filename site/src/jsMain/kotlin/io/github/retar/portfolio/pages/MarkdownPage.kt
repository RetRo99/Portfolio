package io.github.retar.portfolio.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobwebx.markdown.markdown
import io.github.retar.portfolio.LocalSetActiveSection
import io.github.retar.portfolio.components.PortfolioSectionId
import io.github.retar.portfolio.seo.Seo
import kotlinx.browser.window
import org.jetbrains.compose.web.css.AlignItems

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
            }
        """.trimIndent(),
    )

    LaunchedEffect(Unit) {
        window.asDynamic().Prism?.highlightAll()
        setActive(PortfolioSectionId.Blog)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .alignItems(AlignItems.FlexStart),
    ) {
        content()
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

