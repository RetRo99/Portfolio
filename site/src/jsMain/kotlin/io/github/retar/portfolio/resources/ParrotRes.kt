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
            version = "0.4.4",
            date = "2026-06-02",
            apkUrl = "/parrot/parrot-0.4.4.apk",
            features = listOf(
                "Added reader font weight controls for EPUB reading settings",
                "Added a reader text normalization setting",
                "Added a sleep timer postpone prompt"
            ),
            bugFixes = listOf(
                "Removed redundant Android reader fragment state saving during activity shutdown"
            ),
            improvements = listOf(
                "Reorganized and refined reader settings controls",
                "Improved reader gestures, input dialogs, and screen-awake behavior while reading",
                "Kept reader controls visible while the sleep timer menu is open",
                "Moved e-ink display detection into shared base UI platform code"
            )
        ),
        ParrotRelease(
            version = "0.4.3",
            date = "2026-06-01",
            apkUrl = "/parrot/parrot-0.4.3.apk",
            bugFixes = listOf(
                "Fixed Storyteller browser/OIDC login by completing authentication from the callback app token",
                "Removed the extra user lookup after OAuth login that could fail with a 401"
            ),
            improvements = listOf(
                "Added sanitized analytics diagnostics for OAuth login failures"
            )
        ),
        ParrotRelease(
            version = "0.4.2",
            date = "2026-05-31",
            apkUrl = "/parrot/parrot-0.4.2.apk",
            bugFixes = listOf(
                "Fixed Readium navigator restoration crashes after Android process death",
                "Fixed playback foreground service startup crashes",
                "Fixed duplicate reader scope creation when reopening a book",
                "Fixed large embedded cover artwork causing playback metadata out-of-memory crashes",
                "Fixed browser sign-in state when returning without completing OAuth"
            ),
            improvements = listOf(
                "Updated Compose Multiplatform and Material3 dependencies",
                "Improved release signing so APK updates use the same certificate as previous portfolio builds"
            )
        ),
        ParrotRelease(
            version = "0.4.1",
            date = "2026-03-12",
            apkUrl = "/parrot/parrot-0.4.1.apk",
            bugFixes = listOf(
                "Fixed fragment restoration crash when multiple EPUB navigator fragments are restored after process death"
            )
        ),
        ParrotRelease(
            version = "0.4.0",
            date = "2026-03-11",
            apkUrl = "/parrot/parrot-0.4.0.apk",
            features = listOf(
                "Configurable Tap Navigation - Enable/disable tap navigation and customize left/right tap actions (Next Page or Previous Page)",
                "Configurable Double-Tap Timeout - Adjust double-tap detection timing (200-800ms) in Settings under Read Aloud section",
                "Audio Progress Bar Visibility Setting - Choose when to show the audio progress bar: On Tap (with controls) or Never"
            ),
            improvements = listOf(
                "Moved double-tap detection from JavaScript to native code for more consistent behavior across platforms",
                "Renamed VolumeButtonAction enum to NavigationAction for semantic clarity"
            )
        ),
        ParrotRelease(
            version = "0.3.0",
            date = "2026-03-10",
            apkUrl = "/parrot/parrot-0.3.0.apk",
            features = listOf(
                "Android Auto Integration - Full Android Auto support with headless playback for listening to audiobooks while driving",
                "Background Audio Playback - Audio continues playing when the app is in the background with proper notification controls",
                "Mini Player - New mini player component that shows current playback state across the app",
                "Chapter Navigation in Media Controls - Skip to next/previous chapter directly from notification or Android Auto",
                "Volume Button Navigation - Use hardware volume buttons to navigate pages while reading"
            ),
            bugFixes = listOf(
                "Fixed playback speed not being persisted between sessions",
                "Fixed seek bar not updating when navigating pages",
                "Fixed incorrect ReadAloud starting position",
                "Fixed VisibleSentenceDetector returning not_found on chapter change",
                "Fixed position preservation when changing font size",
                "Fixed multi-audio file playback and chapter completion",
                "Fixed slow audio loading by pre-scanning all SMIL files"
            ),
            improvements = listOf(
                "Improved synchronization between media playback and reader UI",
                "Better notification metadata with chapter titles",
                "Refactored Android audio playback to a service-centric architecture for better reliability",
                "Improved audio sync on navigation"
            )
        ),
        ParrotRelease(
            version = "0.2.0",
            date = "2026-02-21",
            apkUrl = "/parrot/parrot-0.2.0.apk",
            features = listOf(
                "CACHED quick filter - Quickly filter to show only cached/downloaded books",
                "DATE_ADDED sort option - Sort your book library by when books were added",
                "Publication date support - Local/imported books now display publication date",
                "Separate underline color for ReadAloud - Customize the underline color independently when using ReadAloud feature",
                "Custom highlight colors - Full support for custom highlight colors with proper alpha/transparency",
                "Auto-scroll to expanded sections in settings for better navigation",
                "Expandable font selection in Reader settings"
            ),
            bugFixes = listOf(
                "Fixed series position not showing in UI",
                "Fixed race condition in LocalServerInitializer causing imported books not to appear",
                "Fixed underline color in dark theme and conditionally show color pickers",
                "Fixed highlight color selection and persistence issues",
                "Fixed highlight color alpha being ignored by Readium",
                "Fixed highlight/underline decoration styles",
                "Highlight decoration now refreshes immediately when color or style changes"
            ),
            improvements = listOf(
                "Fetch reading progress when initializing imported books",
                "Renamed BooksViewModel to BooksListViewModel for clarity",
                "Renamed DATE_ADDED to DATE_PUBLISHED sort option for accuracy",
                "Code cleanup (removed debug logs)"
            )
        ),
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

