package io.github.retar.portfolio.seo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import kotlinx.browser.document
import org.w3c.dom.Element
import org.w3c.dom.HTMLLinkElement
import org.w3c.dom.HTMLMetaElement
import org.w3c.dom.HTMLTitleElement

private const val SITE_ORIGIN = "https://www.retar.app"
private const val DEFAULT_OG_IMAGE = "$SITE_ORIGIN/og-image.png"
private const val SEO_MARKER = "data-seo"

@Composable
fun Seo(
    title: String,
    description: String,
    path: String,
    ogType: String = "website",
    ogImage: String = DEFAULT_OG_IMAGE,
    jsonLd: String? = null,
) {
    DisposableEffect(title, description, path, ogType, ogImage, jsonLd) {
        val canonical = "$SITE_ORIGIN$path"
        val created = mutableListOf<Element>()

        fun upsertMeta(
            name: String? = null,
            property: String? = null,
            content: String,
        ) {
            val selector = when {
                property != null -> "meta[property=\"$property\"]"
                name != null -> "meta[name=\"$name\"]"
                else -> return
            }
            val existing = document.querySelector(selector) as? HTMLMetaElement
            val el = existing ?: (document.createElement("meta") as HTMLMetaElement).also { newEl ->
                if (property != null) newEl.setAttribute("property", property)
                if (name != null) newEl.name = name
                newEl.setAttribute(SEO_MARKER, "true")
                document.head!!.appendChild(newEl)
                created += newEl
            }
            el.content = content
        }

        fun upsertLink(rel: String, href: String) {
            val existing = document.querySelector("link[rel=\"$rel\"]") as? HTMLLinkElement
            val el = existing ?: (document.createElement("link") as HTMLLinkElement).also { newEl ->
                newEl.rel = rel
                newEl.setAttribute(SEO_MARKER, "true")
                document.head!!.appendChild(newEl)
                created += newEl
            }
            el.href = href
        }

        val titleEl = document.querySelector("title") as? HTMLTitleElement
            ?: (document.createElement("title") as HTMLTitleElement).also { newEl ->
                newEl.setAttribute(SEO_MARKER, "true")
                document.head!!.appendChild(newEl)
                created += newEl
            }
        titleEl.text = title

        upsertMeta(name = "description", content = description)
        upsertMeta(name = "robots", content = "index, follow")
        upsertLink(rel = "canonical", href = canonical)

        upsertMeta(property = "og:title", content = title)
        upsertMeta(property = "og:description", content = description)
        upsertMeta(property = "og:url", content = canonical)
        upsertMeta(property = "og:type", content = ogType)
        upsertMeta(property = "og:image", content = ogImage)

        upsertMeta(name = "twitter:card", content = "summary_large_image")
        upsertMeta(name = "twitter:title", content = title)
        upsertMeta(name = "twitter:description", content = description)
        upsertMeta(name = "twitter:image", content = ogImage)

        jsonLd?.let { json ->
            val script = document.querySelector("script[type=\"application/ld+json\"][data-seo-page=\"true\"]")
                ?: document.createElement("script").also { newEl ->
                    newEl.setAttribute("type", "application/ld+json")
                    newEl.setAttribute("data-seo-page", "true")
                    newEl.setAttribute(SEO_MARKER, "true")
                    document.head!!.appendChild(newEl)
                    created += newEl
                }
            script.textContent = json
        }

        onDispose {
            created.forEach { element -> element.remove() }
        }
    }
}
