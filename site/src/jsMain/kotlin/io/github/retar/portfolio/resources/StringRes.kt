package io.github.retar.portfolio.resources

import androidx.compose.runtime.Composable
import io.github.retar.portfolio.Language

sealed class StringRes(val en: String, val sl: String, val lv: String) {
    val value: String
        @Composable get() {
            val language = Language.current
            return when (language) {
                Language.EN -> en
                Language.SL -> sl
                Language.LV -> lv
            }
        }

    data object HeroTitle : StringRes(
        en = "I'm Rok Retar, a developer specializing in building innovative digital solutions, based in Slovenia.",
        sl = "Sem Rok Retar, razvijalec, ki ustvarja inovativne digitalne rešitve. Delujem iz Slovenije.",
        lv = "Esmu Rok Retar — izstrādātājs, kurš veido inovatīvus digitālos risinājumus. Strādāju no Slovēnijas."
    )

    data object HeroSubtitle : StringRes(
        en = "Kotlin-focused developer who enjoys building polished mobile and web experiences. This portfolio is built with Kobweb and Silk, which bring a Compose-like developer experience to the web.",
        sl = "Sem razvijalec, osredotočen na Kotlin, z veseljem pa ustvarjam dovršene mobilne in spletne izkušnje. Ta portfelj je narejen s Kobwebom in Silkom, ki na splet prinašata izkušnjo, podobno Compose.",
        lv = "Esmu Kotlin izstrādātājs, kuram patīk veidot noslīpētas mobilās un tīmekļa pieredzes. Šis portfelis ir veidots ar Kobweb un Silk, kas tīmeklī sniedz izstrādes pieredzi, līdzīgu Compose."
    )

    data object Descriptor : StringRes(
        en = "Kotlin / Android / Web Developer",
        sl = "Kotlin / Android / Spletni razvijalec",
        lv = "Kotlin / Android / Tīmekļa izstrādātājs"
    )

    data object Github : StringRes(
        en = "GitHub",
        sl = "GitHub",
        lv = "GitHub"
    )

    data object LinkedIn : StringRes(
        en = "LinkedIn",
        sl = "LinkedIn",
        lv = "LinkedIn"
    )

    data object SelectedProjectsTitle : StringRes(
        en = "Selected Projects",
        sl = "Izbrani projekti",
        lv = "Izvēlētie projekti"
    )

    data object SelectedProjectsSubtitle : StringRes(
        en = "Explore a curated collection of projects that showcase my dedication to cutting-edge technology and intuitive design.",
        sl = "Raziščite izbor projektov, ki prikazujejo mojo predanost sodobnim tehnologijam in intuitivnemu dizajnu.",
        lv = "Izpētiet atlasītu projektu kolekciju, kas parāda manu aizrautību ar modernām tehnoloģijām un intuitīvu dizainu."
    )

    data object BlogTitle : StringRes(
        en = "Blog",
        sl = "Blog",
        lv = "Blogs"
    )

    data object ArticlesSubtitle : StringRes(
        en = "A selection of write-ups about how I build things, including Kobweb, Compose, and Kotlin.",
        sl = "Izbor zapisov o tem, kako gradim stvari, vključno s Kobwebom, Compose in Kotlinom.",
        lv = "Rakstu izlase par to, kā es būvēju lietas — tostarp ar Kobweb, Compose un Kotlin."
    )

    data object HeaderTitle : StringRes(
        en = "Rok Retar",
        sl = "Rok Retar",
        lv = "Rok Retar"
    )

    data object NavAbout : StringRes(
        en = "About",
        sl = "O meni",
        lv = "Par mani"
    )
    data object NavProjects : StringRes(
        en = "Projects",
        sl = "Projekti",
        lv = "Projekti"
    )

    data object NavBlog : StringRes(
        en = "Blog",
        sl = "Blog",
        lv = "Blogs"
    )

    data object NavContact : StringRes(
        en = "Contact",
        sl = "Kontakt",
        lv = "Kontakti"
    )

    data class FooterCopyright(val year: Int) : StringRes(
        en = "© $year Rok Retar",
        sl = "© $year Rok Retar",
        lv = "© $year Rok Retar"
    )

    data object ProfileImageDesc : StringRes(
        en = "Portrait of Rok Retar",
        sl = "Portret Roka Retarja",
        lv = "Roka Retara portrets"
    )

    data object InfiniteCarouselArticleTitle : StringRes(
        en = "Building a Smooth, Infinite Carousel in Kobweb",
        sl = "Gradnja gladkega, neskončnega vrtiljaka v Kobwebu",
        lv = "Gludas, bezgalīgas karuseļa komponentes izveide Kobweb"
    )

    data object InfiniteCarouselArticleDesc : StringRes(
        en = "How to build an infinite, gapless carousel using Silk and Kobweb.",
        sl = "Kako zgraditi neskončen, brezšivni vrtiljak z uporabo Silk in Kobweb.",
        lv = "Kā izveidot bezgalīgu, bezstarpīgu karuseli, izmantojot Silk un Kobweb."
    )
}
