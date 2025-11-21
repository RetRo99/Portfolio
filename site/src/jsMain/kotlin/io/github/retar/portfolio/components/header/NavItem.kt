package io.github.retar.portfolio.components.header

import io.github.retar.portfolio.PortfolioSectionId
import io.github.retar.portfolio.resources.StringRes

enum class NavItem(
    val section: PortfolioSectionId?,
    val route: String,
    val label: StringRes,
) {
    Intro(
        section = PortfolioSectionId.Intro,
        route = "/",
        label = StringRes.NavIntro,
    ),
    Projects(
        section = PortfolioSectionId.Projects,
        route = "/",
        label = StringRes.NavProjects,
    ),
    Articles(
        section = PortfolioSectionId.Articles,
        route = "/articles",
        label = StringRes.NavArticles,
    ),
    Contact(
        section = null,
        route = "/",
        label = StringRes.NavContact,
    ),
}
