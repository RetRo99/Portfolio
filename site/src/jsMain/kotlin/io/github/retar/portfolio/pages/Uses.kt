package io.github.retar.portfolio.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.layout.Layout
import io.github.retar.portfolio.components.widgets.MiniPage
import io.github.retar.portfolio.resources.MiniPageStrings
import io.github.retar.portfolio.resources.UsesRes
import io.github.retar.portfolio.seo.Seo

@Page
@Layout(".components.layouts.PageLayout")
@Composable
fun UsesPage() {
    Seo(
        title = "Uses · Rok Retar",
        description = "The editor, tools, Kotlin Multiplatform libraries and services Rok Retar uses to build Android and iOS apps, and this site.",
        path = "/uses",
    )
    MiniPage(
        eyebrow = MiniPageStrings.usesEyebrow.value,
        title = UsesRes.title.value,
        intro = UsesRes.intro.value,
        groups = UsesRes.groups,
    )
}
