package io.github.retar.portfolio.resources

data class ParrotRelease(
    val version: String,
    val date: String,
    val apkUrl: String,
    val bugFixes: List<String>,
    val improvements: List<String>,
)

object ParrotRes {
    val releases = listOf(
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

