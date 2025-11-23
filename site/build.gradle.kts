import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import kotlinx.html.link
import kotlinx.html.script
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
                link(rel = "preconnect", href = "https://fonts.googleapis.com")
                link(
                    rel = "preconnect",
                    href = "https://fonts.gstatic.com"
                ) { attributes["crossorigin"] = "" }
                link(
                    href = "https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&family=Outfit:wght@400;500;600;700&display=swap",
                    rel = "stylesheet"
                )
                script {
                    async = true
                    src = "https://plausible.io/js/pa-KV_Y-QH0U3Bxdqi_EJupK.js"
                }
                script {
                    unsafe {
                        raw(
                            """
                window.plausible=window.plausible||function(){(plausible.q=plausible.q||[]).push(arguments)},plausible.init=plausible.init||function(i){plausible.o=i||{}};
                plausible.init()
            """.trimIndent()
                        )
                    }
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
