package io.github.retar.portfolio.resources

class Note(
    val route: String,
    val title: StringRes,
    val description: StringRes,
    val image: ImageRes,
)

object NotesRes {
    /** Newest first. The order drives the home page list and each note's "next note" link. */
    val all = listOf(
        Note(
            route = LinkRes.Internal.WebWasmArticle,
            title = StringRes.WebWasmArticleTitle,
            description = StringRes.WebWasmArticleDesc,
            image = ImageRes.WebWasmArticle,
        ),
        Note(
            route = LinkRes.Internal.ReadAlongArticle,
            title = StringRes.ReadAlongArticleTitle,
            description = StringRes.ReadAlongArticleDesc,
            image = ImageRes.ReadAlongArticle,
        ),
        Note(
            route = LinkRes.Internal.PositionSyncArticle,
            title = StringRes.PositionSyncArticleTitle,
            description = StringRes.PositionSyncArticleDesc,
            image = ImageRes.PositionSyncArticle,
        ),
        Note(
            route = LinkRes.Internal.OpdsArticle,
            title = StringRes.OpdsArticleTitle,
            description = StringRes.OpdsArticleDesc,
            image = ImageRes.OpdsArticle,
        ),
        Note(
            route = LinkRes.Internal.OnDeviceTtsArticle,
            title = StringRes.OnDeviceTtsArticleTitle,
            description = StringRes.OnDeviceTtsArticleDesc,
            image = ImageRes.OnDeviceTtsArticle,
        ),
        Note(
            route = LinkRes.Internal.AppsFlyerArticle,
            title = StringRes.AppsFlyerArticleTitle,
            description = StringRes.AppsFlyerArticleDesc,
            image = ImageRes.AppsFlyerArticle,
        ),
        Note(
            route = LinkRes.Internal.SQLCipherArticle,
            title = StringRes.SQLCipherArticleTitle,
            description = StringRes.SQLCipherArticleDesc,
            image = ImageRes.SQLCipherArticle,
        ),
        Note(
            route = LinkRes.Internal.InfiniteCarouselArticle,
            title = StringRes.InfiniteCarouselArticleTitle,
            description = StringRes.InfiniteCarouselArticleDesc,
            image = ImageRes.InfiniteCarouselArticle,
        ),
    )

    /** The note to suggest after [route], wrapping around to the newest one. */
    fun nextAfter(route: String): Note? {
        val index = all.indexOfFirst { it.route == route.trimEnd('/') }
        if (index == -1 || all.size < 2) return null
        return all[(index + 1) % all.size]
    }
}
