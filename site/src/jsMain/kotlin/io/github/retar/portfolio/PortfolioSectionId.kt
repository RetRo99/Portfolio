package io.github.retar.portfolio

sealed class PortfolioSectionId(val domId: String) {
    object Header : PortfolioSectionId("nav-header")
    object Intro : PortfolioSectionId("intro-section")
    object Projects : PortfolioSectionId("projects-section")
    object Articles : PortfolioSectionId("articles-section")
    object MarkdownPage : PortfolioSectionId("markdown-page")
}