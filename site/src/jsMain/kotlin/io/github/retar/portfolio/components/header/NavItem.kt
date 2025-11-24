package io.github.retar.portfolio.components.header

import io.github.retar.portfolio.components.PortfolioSectionId
import io.github.retar.portfolio.resources.StringRes

enum class NavItem(
    val section: PortfolioSectionId?,
    val route: String,
    val label: StringRes,
) {
    About(
        section = PortfolioSectionId.About,
        route = "/",
        label = StringRes.NavAbout,
    ),
    Projects(
        section = PortfolioSectionId.Projects,
        route = "/",
        label = StringRes.NavProjects,
    ),
    Blog(
        section = PortfolioSectionId.Blog,
        route = "/",
        label = StringRes.NavBlog,
    ),
    Contact(
        section = null,
        route = "/",
        label = StringRes.NavContact,
    ),
}
