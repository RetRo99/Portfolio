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
            description.set("Rok Retar is a mobile engineer in Slovenia who builds Android apps and takes Kotlin Multiplatform to production on iOS. Work, engineering notes and contact.")
            faviconPath.set("/favicon.ico")
            head.add {
                unsafe {
                    +"""<link rel="icon" href="/favicon-32.png" type="image/png" sizes="32x32"/>"""
                    +"""<link rel="icon" href="/favicon-192.png" type="image/png" sizes="192x192"/>"""
                    +"""<link rel="icon" href="/favicon-512.png" type="image/png" sizes="512x512"/>"""
                }
                link(rel = "apple-touch-icon", href = "/apple-touch-icon.png")
            unsafe {
                    +"""<meta http-equiv="Content-Security-Policy" content="frame-ancestors 'self';"/>"""
                    +"""<style>::selection { background-color: rgb(178, 69, 34); color: rgb(251, 248, 242); }</style>"""
                    // Motion system: one strong ease-out, a staggered entrance, visible focus, and a
                    // reduced-motion escape hatch that turns every animation off.
                    +"""<style>
                        :root { --ease-out: cubic-bezier(0.23, 1, 0.32, 1); }
                        html { -webkit-font-smoothing: antialiased; text-rendering: optimizeLegibility; }
                        @keyframes rise { from { opacity: 0; transform: translateY(10px); } to { opacity: 1; transform: none; } }
                        .rise { animation: rise 560ms var(--ease-out) both; animation-delay: calc(var(--i, 0) * 70ms + 60ms); }
                        :focus-visible { outline: 2px solid rgb(178, 69, 34); outline-offset: 3px; border-radius: 4px; }
                        .silk-dark :focus-visible { outline-color: rgb(226, 124, 84); }
                        @media (prefers-reduced-motion: reduce) {
                          *, *::before, *::after { animation-duration: 1ms !important; animation-delay: 0ms !important; transition-duration: 1ms !important; scroll-behavior: auto !important; }
                        }
                    </style>"""
                }
                meta(name = "author", content = "Rok Retar")
                meta(content = "Rok Retar") { attributes["property"] = "og:site_name" }
                meta(content = "en_US") { attributes["property"] = "og:locale" }
                meta(content = "1200") { attributes["property"] = "og:image:width" }
                meta(content = "630") { attributes["property"] = "og:image:height" }
                meta(content = "Rok Retar, mobile engineer: Android and Kotlin Multiplatform, shipped to production iOS.") { attributes["property"] = "og:image:alt" }
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
                          "alternateName": "RetRo99",
                          "jobTitle": "Mobile Engineer",
                          "disambiguatingDescription": "Android and Kotlin Multiplatform software engineer from Slovenia, author of the Parrot e-reader app.",
                          "worksFor": {
                            "@type": "Organization",
                            "name": "DevRev",
                            "url": "https://devrev.ai"
                          },
                          "alumniOf": [
                            {
                              "@type": "CollegeOrUniversity",
                              "name": "University of Ljubljana",
                              "url": "https://www.uni-lj.si"
                            },
                            {
                              "@type": "EducationalOrganization",
                              "name": "OpenClassrooms",
                              "url": "https://openclassrooms.com"
                            }
                          ],
                          "description": "Mobile engineer in Slovenia building Android apps and taking Kotlin Multiplatform to production on iOS.",
                          "url": "https://www.retar.app/",
                          "image": "https://www.retar.app/profile/profile_image.png",
                          "mainEntityOfPage": "https://www.retar.app/",
                          "homeLocation": {
                            "@type": "Place",
                            "address": {
                              "@type": "PostalAddress",
                              "addressCountry": "SI"
                            }
                          },
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
                            "https://www.linkedin.com/in/rok-retar/"
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
                          "url": "https://www.retar.app/",
                          "inLanguage": ["en", "sl", "lv"],
                          "description": "Android and Kotlin Multiplatform engineer specializing in KMP migrations taken to production iOS.",
                          "author": {
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
                    href = "https://fonts.googleapis.com/css2?family=Geist:wght@400;500;600&family=Geist+Mono:wght@400;500&family=Instrument+Serif:ital@0;1&display=swap",
                    rel = "stylesheet",
                )
                link(
                    rel = "stylesheet",
                    href = "/prism/prism.css",
                )
                link(rel = "stylesheet", href = "/editorial.css")
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
