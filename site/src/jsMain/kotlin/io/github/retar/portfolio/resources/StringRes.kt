package io.github.retar.portfolio.resources

sealed class StringRes {
    abstract val value: String

    data object HeroTitle : StringRes() {
        override val value =
            "I'm Rok Retar, a developer specializing in building innovative digital solutions, " +
                "based in Slovenia."
    }

    data object HeroSubtitle : StringRes() {
        override val value =
            "Kotlin-focused developer who enjoys building polished mobile and web" +
                "experiences. This portfolio is built with Kobweb and Silk, which bring a Compose-like developer experience to the web."
    }

    data object Descriptor : StringRes() {
        override val value = "Kotlin / Android / Web Developer"
    }

    data object Github : StringRes() {
        override val value = "GitHub"
    }

    data object LinkedIn : StringRes() {
        override val value = "LinkedIn"
    }

    data object SelectedProjectsTitle : StringRes() {
        override val value = "Selected Projects"
    }

    data object SelectedProjectsSubtitle : StringRes() {
        override val value =
            "Explore a curated collection of projects that showcase my dedication to cutting-edge technology and intuitive design."
    }

    data object ArticlesTitle : StringRes() {
        override val value = "Articles"
    }

    data object ArticlesSubtitle : StringRes() {
        override val value =
            "A selection of write-ups about how I build things, including Kobweb, Compose, and Kotlin."
    }

    data object HeaderTitle : StringRes() {
        override val value = "Rok Retar"
    }

    data object NavIntro : StringRes() {
        override val value = "Intro"
    }

    data object NavProjects : StringRes() {
        override val value = "Projects"
    }

    data object NavArticles : StringRes() {
        override val value = "Articles"
    }

    data object NavContact : StringRes() {
        override val value = "Contact"
    }

    data class FooterCopyright(val year: Int) : StringRes() {
        override val value = "© $year Rok Retar"
    }

    data object ProfileImageDesc : StringRes() {
        override val value = "Portrait of Rok Retar"
    }

    data object InfiniteCarouselArticleTitle : StringRes() {
        override val value = "Building a Smooth, Infinite Carousel in Kobweb"
    }

    data object InfiniteCarouselArticleDesc : StringRes() {
        override val value = "How to build an infinite, gapless carousel using Silk and Kobweb."
    }
}