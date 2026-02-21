package io.github.retar.portfolio.resources

data class ParrotRelease(
    val version: String,
    val date: String,
    val apkUrl: String,
    val features: List<String> = emptyList(),
    val bugFixes: List<String> = emptyList(),
    val improvements: List<String> = emptyList(),
)

object ParrotRes {
    val releases = listOf(
        ParrotRelease(
            version = "0.1.0",
            date = "2026-02-21",
            apkUrl = "/parrot/parrot-0.1.0.apk",
            features = listOf(
                "Multi-User Profiles - Create and switch between multiple user profiles. Each profile has its own reading progress and preferences. Long-press profiles to rename or delete them",
                "Local Book Import - Import EPUB files directly from your device. Automatic detection of readaloud books with audio. Delete imported books from the book details screen",
                "Library Filtering & Sorting - Quick filters: Favorites, Ebook, Readaloud, In Series, Local, Remote. Sort by: Title, Author, Rating, or Date Added. Empty state with \"Reset Filters\" button when no books match",
                "Reading Progress - Progress bars on book list items. \"Downloaded\" badge on cached books. Conflict indicator showing local vs remote progress differences",
                "Multi-Server Support - Connect to multiple Storyteller servers. Manage servers from Settings. Skip login to use local books only"
            ),
            bugFixes = listOf(
                "Fixed readaloud button not appearing for some books",
                "Fixed crash when playing chapters without audio",
                "Fixed iOS file download issues",
                "Fixed app crash when switching user profiles",
                "Fixed reading position sync errors"
            )
        ),
        ParrotRelease(
            version = "0.0.8",
            date = "2026-02-17",
            apkUrl = "/parrot/parrot-0.0.8.apk",
            bugFixes = listOf(
                "Fixed crash when restoring app after process death"
            ),
            improvements = listOf(
                "Improved reader stability and reliability"
            )
        )
    )

    val latestRelease: ParrotRelease
        get() = releases.first()
}

