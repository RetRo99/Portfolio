import com.varabyte.kobweb.common.text.isSurrounded
import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import kotlinx.html.link
import kotlinx.html.meta
import kotlinx.html.script
import kotlinx.html.title
import kotlinx.html.unsafe

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kobweb.application)
    alias(libs.plugins.kobwebx.markdown)
}

group = "io.github.retar.portfolio"
version = "1.0-SNAPSHOT"

kobweb {
    app {
        index {
            description.set("Rok Retar — mobile engineer specializing in Android and Kotlin Multiplatform. KMP migrations, native architecture, and the Parrot e-reader case study.")
            faviconPath.set("/favicon.svg")
            head.add {
                link(rel = "icon", href = "/favicon.ico", type = "image/x-icon")
                link(rel = "icon", href = "/favicon-32.png", type = "image/png", sizes = "32x32")
                link(rel = "icon", href = "/favicon-192.png", type = "image/png", sizes = "192x192")
                link(rel = "icon", href = "/favicon-512.png", type = "image/png", sizes = "512x512")
                link(rel = "apple-touch-icon", href = "/apple-touch-icon.png")
            unsafe {
                    +"""<meta http-equiv="Content-Security-Policy" content="frame-ancestors 'self';"/>"""
                    +"""<style>::selection { background-color: rgb(245, 158, 11); color: rgb(24, 24, 27); }</style>"""
                }
                meta(name = "author", content = "Rok Retar")
                meta(content = "Rok Retar") { attributes["property"] = "og:site_name" }
                meta(content = "en_US") { attributes["property"] = "og:locale" }
                meta(content = "1200") { attributes["property"] = "og:image:width" }
                meta(content = "630") { attributes["property"] = "og:image:height" }
                meta(content = "Rok Retar — Mobile Engineer (Android & Kotlin Multiplatform)") { attributes["property"] = "og:image:alt" }
                link(rel = "me", href = "https://github.com/retro99")
                link(rel = "me", href = "https://www.linkedin.com/in/rok-retar/")
                script(type = "application/ld+json") {
                    unsafe {
                        +"""
                        {
                          "@context": "https://schema.org",
                          "@type": "Person",
                          "@id": "https://www.retar.app/#person",
                          "name": "Rok Retar",
                          "givenName": "Rok",
                          "familyName": "Retar",
                          "jobTitle": "Mobile Engineer",
                          "url": "https://www.retar.app",
                          "email": "mailto:rok.retar@gmail.com",
                          "gender": "Male",
                          "nationality": {
                            "@type": "Country",
                            "name": "Slovenia"
                          },
                          "knowsAbout": [
                            "Android",
                            "Kotlin",
                            "Kotlin Multiplatform",
                            "Jetpack Compose",
                            "Kobweb",
                            "Software Architecture"
                          ],
                          "sameAs": [
                            "https://github.com/retro99",
                            "https://www.linkedin.com/in/rok-retar/",
                            "https://www.retar.app"
                          ]
                        }
                        """.trimIndent()
                    }
                }
                script(type = "application/ld+json") {
                    unsafe {
                        +"""
                        {
                          "@context": "https://schema.org",
                          "@type": "WebSite",
                          "@id": "https://www.retar.app/#website",
                          "name": "Rok Retar",
                          "url": "https://www.retar.app",
                          "description": "Android and Kotlin Multiplatform engineer specializing in KMP migrations taken to production iOS.",
                          "author": {
                            "@id": "https://www.retar.app/#person"
                          }
                        }
                        """.trimIndent()
                    }
                }
                script(type = "application/ld+json") {
                    unsafe {
                        +"""
                        {
                          "@context": "https://schema.org",
                          "@type": "ProfilePage",
                          "dateCreated": "2024-01-01T00:00:00Z",
                          "dateModified": "2026-06-24T00:00:00Z",
                          "mainEntity": {
                            "@id": "https://www.retar.app/#person"
                          }
                        }
                        """.trimIndent()
                    }
                }
                link(rel = "manifest", href = "/site.webmanifest")
                link(rel = "preconnect", href = "https://fonts.googleapis.com")
                link(
                    rel = "preconnect",
                    href = "https://fonts.gstatic.com",
                ) { attributes["crossorigin"] = "" }
                link(
                    href = "https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&family=JetBrains+Mono:wght@400;500;700&family=Outfit:wght@400;500;600;700&display=swap",
                    rel = "stylesheet",
                )
                link(
                    rel = "stylesheet",
                    href = "/prism/prism.css",
                )
                link(rel = "sitemap", type = "application/xml", href = "/sitemap.xml")
                script {
                    src = "/prism/prism.js"
                }
                script {
                    defer = true
                    src = "https://cloud.umami.is/script.js"
                    attributes["data-website-id"] = "75b85e4b-8c2d-4491-b957-0d6d27fd6307"
                }
            }
        }
    }

    markdown {
        defaultLayout.set(".pages.MarkdownPage")

        handlers {
            val WIDGET_PATH = "io.github.retar.portfolio.components.widgets"

            code.set { code ->
                var lang: String? = null
                var lines: String? = null
                var label: String? = null
                var editingLabel = false

                code.info
                    .split(" ")
                    .filter { it.isNotBlank() }
                    .forEach { infoPart ->
                        if (editingLabel) {
                            label += " "
                            if (infoPart.endsWith("\"")) {
                                label += infoPart.removeSuffix("\"")
                                editingLabel = false
                            } else {
                                label += infoPart
                            }
                        } else {
                            if (infoPart.isSurrounded("\"")) {
                                label = infoPart.removeSurrounding("\"")
                            } else if (infoPart.startsWith("\"")) {
                                label = infoPart.removePrefix("\"")
                                editingLabel = true
                            } else if (infoPart.first().isDigit()) {
                                lines = infoPart
                            } else {
                                lang = infoPart
                            }
                        }
                    }

                buildString {
                    append("$WIDGET_PATH.CodeBlock(\"\"\"${code.literal.escapeTripleQuotedText()}\"\"\"")
                    if (lang != null) {
                        append(", lang = \"$lang\"")
                    }
                    if (lines != null) {
                        append(", highlightLines = \"$lines\"")
                    }
                    if (label != null) {
                        append(", label = \"$label\"")
                    }
                    append(")")
                }
            }

            inlineCode.set { code ->
                "$WIDGET_PATH.InlineCode(\"\"\"${code.literal.escapeTripleQuotedText()}\"\"\")"
            }
        }
    }
}

kotlin {
    configAsKobwebApplication("portfolio")

    sourceSets {
        jsMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.html.core)
            implementation(libs.kobweb.core)
            implementation(libs.kobweb.silk)
            implementation(libs.silk.icons.fa)
            implementation(libs.kobwebx.markdown)

        }
    }
}

private fun String.escapeTripleQuotedText(): String =
    replace("\"\"\"", "\\\"\\\"\\\"")
