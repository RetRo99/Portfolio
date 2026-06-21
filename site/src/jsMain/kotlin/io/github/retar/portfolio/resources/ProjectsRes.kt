package io.github.retar.portfolio.resources

data class ProjectImage(
    val image: ImageRes,
    val description: StringRes,
)

object ProjectsRes {
    val SelectedProjects = listOf(
        ProjectImage(ImageRes.ParrotScreenshot1, StringRes.ParrotScreenshot1Desc),
        ProjectImage(ImageRes.ParrotScreenshot2, StringRes.ParrotScreenshot2Desc),
        ProjectImage(ImageRes.ParrotScreenshot3, StringRes.ParrotScreenshot3Desc),
        ProjectImage(ImageRes.ParrotScreenshot4, StringRes.ParrotScreenshot4Desc),
        ProjectImage(ImageRes.ParrotScreenshot5, StringRes.ParrotScreenshot5Desc),
    )
}
