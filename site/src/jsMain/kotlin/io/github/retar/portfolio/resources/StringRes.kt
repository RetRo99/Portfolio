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

    data object HeroIntro : StringRes(
        en = "Hi, my name is",
        sl = "Živjo, moje ime je",
        lv = "Sveiki, mani sauc"
    )

    data object HeroName : StringRes(
        en = "Rok Retar",
        sl = "Rok Retar",
        lv = "Rok Retar"
    )

    data object HeroTagline : StringRes(
        en = "I build things for the web.",
        sl = "Gradim stvari za splet.",
        lv = "Es būvēju lietas tīmeklim."
    )

    data object HeroTitle : StringRes(
        en = "I'm Rok Retar, a developer specializing in building innovative digital solutions, based in Slovenia.",
        sl = "Sem Rok Retar, razvijalec, ki ustvarja inovativne digitalne rešitve. Delujem iz Slovenije.",
        lv = "Esmu Rok Retar — izstrādātājs, kurš veido inovatīvus digitālos risinājumus. Strādāju no Slovēnijas."
    )

    data object HeroSubtitle : StringRes(
        en = "I'm a mobile developer specializing in building exceptional digital experiences. Currently focused on creating accessible, human-centered products with modern mobile technologies.",
        sl = "Sem mobilni razvijalec, specializiran za gradnjo izjemnih digitalnih izkušenj. Trenutno se osredotočam na ustvarjanje dostopnih, človeku prijaznih izdelkov z modernimi mobilnimi tehnologijami.",
        lv = "Esmu mobilo lietotņu izstrādātājs, kas specializējas izcilu digitālo pieredžu veidošanā. Pašlaik koncentrējos uz pieejamu, cilvēkcentrētu produktu radīšanu ar modernām mobilajām tehnoloģijām."
    )

    data object ViewMyWork : StringRes(
        en = "View My Work",
        sl = "Poglej moje delo",
        lv = "Skatīt manus darbus"
    )

    data object Descriptor : StringRes(
        en = "Mobile Developer",
        sl = "Mobilni razvijalec",
        lv = "Mobilo lietotņu izstrādātājs"
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
        en = "Parrot",
        sl = "Parrot",
        lv = "Parrot"
    )

    data object SelectedProjectsSubtitle : StringRes(
        en = "A modern EPUB reader for Android with a focus on simplicity and reading experience.",
        sl = "Sodoben EPUB bralnik za Android s poudarkom na preprostosti in bralni izkušnji.",
        lv = "Moderns EPUB lasītājs Android ierīcēm ar uzsvaru uz vienkāršību un lasīšanas pieredzi."
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
