package io.github.retar.portfolio.components

sealed class PortfolioSectionId(val domId: String) {
    object About : PortfolioSectionId("about")
    object Philosophy : PortfolioSectionId("philosophy")
    object Projects : PortfolioSectionId("projects")
    object Blog : PortfolioSectionId("notes")
    object Contact : PortfolioSectionId("contact")

    val path get() = "#$domId"
}
