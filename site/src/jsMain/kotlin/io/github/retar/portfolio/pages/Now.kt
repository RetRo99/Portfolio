package io.github.retar.portfolio.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.layout.Layout
import io.github.retar.portfolio.components.widgets.MiniPage
import io.github.retar.portfolio.resources.MiniPageStrings
import io.github.retar.portfolio.resources.NowRes
import io.github.retar.portfolio.seo.Seo

@Page
@Layout(".components.layouts.PageLayout")
@Composable
fun NowPage() {
    Seo(
        title = "Now · Rok Retar",
        description = "What Rok Retar is working on right now: leading Android at DevRev, bringing Parrot to iPhone, Parrot Cloud and new engineering notes.",
        path = "/now",
    )
    MiniPage(
        eyebrow = MiniPageStrings.nowEyebrow.value,
        title = NowRes.title.value,
        intro = NowRes.intro.value,
        groups = NowRes.groups,
        updatedIso = NowRes.UPDATED,
        footnote = MiniPageStrings.nowAbout.value,
    )
}
