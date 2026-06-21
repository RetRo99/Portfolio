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
            description.set("Personal portfolio of Rok Retar, showcasing projects, experience, and skills.")
            faviconPath.set("/favicon.svg")
            head.add {
                title("Rok Retar — Mobile Engineer (Android & Kotlin Multiplatform)")
                meta(name = "description", content = "Rok Retar — mobile engineer specializing in Android and Kotlin Multiplatform. KMP migrations, native architecture, and the Parrot e-reader case study.")
                meta(name = "author", content = "Rok Retar")
                meta(name = "robots", content = "index, follow")
                link(rel = "canonical", href = "https://www.retar.app/")
                meta(property = "og:site_name", content = "Rok Retar")
                meta(property = "og:locale", content = "en_US")
                meta(property = "og:type", content = "website")
                meta(property = "og:title", content = "Rok Retar — Mobile Engineer (Android & Kotlin Multiplatform)")
                meta(property = "og:description", content = "Android and Kotlin Multiplatform engineer. KMP migrations taken to production. Built natively in Kotlin.")
                meta(property = "og:url", content = "https://www.retar.app/")
                meta(property = "og:image", content = "https://www.retar.app/og-image.png")
                meta(name = "twitter:card", content = "summary_large_image")
                meta(name = "twitter:title", content = "Rok Retar — Mobile Engineer (Android & Kotlin Multiplatform)")
                meta(name = "twitter:description", content = "Android and Kotlin Multiplatform engineer. KMP migrations taken to production. Built natively in Kotlin.")
                meta(name = "twitter:image", content = "https://www.retar.app/og-image.png")
                script(type = "application/ld+json") {
                    unsafe {
                        +"""
                        {
                          "@context": "https://schema.org",
                          "@type": "Person",
                          "name": "Rok Retar",
                          "jobTitle": "Mobile Engineer",
                          "url": "https://www.retar.app",
                          "email": "mailto:rok.retar@gmail.com",
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
                            "https://www.linkedin.com/in/rok-retar/"
                          ]
                        }
                        """.trimIndent()
                    }
                }
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
