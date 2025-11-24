package io.github.retar.portfolio.components

sealed class PortfolioSectionId(val domId: String) {
    object About : PortfolioSectionId("about")
    object Projects : PortfolioSectionId("projects")
    object Blog : PortfolioSectionId("blog")
}