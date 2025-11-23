package io.github.retar.portfolio.resources

import androidx.compose.runtime.Composable
import io.github.retar.portfolio.Language
import io.github.retar.portfolio.LocalLanguage

sealed class StringRes(val en: String, val sl: String, val lv: String) {
    val value: String
        @Composable get() {
            val language = LocalLanguage.current
            return when (language) {
                Language.EN -> en
                Language.SL -> sl
                Language.LV -> lv
            }
        }

    data object HeroTitle : StringRes(
        en = "I'm Rok Retar, a developer specializing in building innovative digital solutions, based in Slovenia.",
        sl = "Sem Rok Retar, razvijalec specializiran za gradnjo inovativnih digitalnih rešitev, s sedežem v Sloveniji.",
        lv = "Esmu Rok Retar, izstrādātājs, kas specializējas inovatīvu digitālo risinājumu izveidē, bāzēts Slovēnijā."
    )

    data object HeroSubtitle : StringRes(
        en = "Kotlin-focused developer who enjoys building polished mobile and web experiences. This portfolio is built with Kobweb and Silk, which bring a Compose-like developer experience to the web.",
        sl = "Razvijalec osredotočen na Kotlin, ki uživa v gradnji poliranih mobilnih in spletnih izkušenj. Ta portfelj je zgrajen s Kobweb in Silk, ki prinašata Compose-like izkušnjo na splet.",
        lv = "Kotlin orientēts izstrādātājs, kuram patīk veidot noslīpētu mobilo un tīmekļa pieredzi. Šis portfelis ir veidots ar Kobweb un Silk, kas tīmeklī ienes Compose līdzīgu izstrādātāja pieredzi."
    )

    data object Descriptor : StringRes(
        en = "Kotlin / Android / Web Developer",
        sl = "Kotlin / Android / Spletni Razvijalec",
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
        sl = "Izbrani Projekti",
        lv = "Izvēlētie projekti"
    )

    data object SelectedProjectsSubtitle : StringRes(
        en = "Explore a curated collection of projects that showcase my dedication to cutting-edge technology and intuitive design.",
        sl = "Raziščite zbirko projektov, ki prikazujejo mojo predanost najnovejši tehnologiji in intuitivnemu dizajnu.",
        lv = "Izpētiet izvēlētu projektu kolekciju, kas parāda manu aizrautību ar jaunākajām tehnoloģijām un intuitīvu dizainu."
    )

    data object ArticlesTitle : StringRes(
        en = "Articles",
        sl = "Članki",
        lv = "Raksti"
    )

    data object ArticlesSubtitle : StringRes(
        en = "A selection of write-ups about how I build things, including Kobweb, Compose, and Kotlin.",
        sl = "Izbor zapisov o tem, kako gradim stvari, vključno s Kobweb, Compose in Kotlin.",
        lv = "Rakstu izlase par to, kā es būvēju lietas, ieskaitot Kobweb, Compose un Kotlin."
    )

    data object HeaderTitle : StringRes(
        en = "Rok Retar",
        sl = "Rok Retar",
        lv = "Rok Retar"
    )

    data object NavIntro : StringRes(
        en = "Intro",
        sl = "Uvod",
        lv = "Ievads"
    )

    data object NavProjects : StringRes(
        en = "Projects",
        sl = "Projekti",
        lv = "Projekti"
    )

    data object NavArticles : StringRes(
        en = "Articles",
        sl = "Članki",
        lv = "Raksti"
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
        lv = "Rok Retar portrets"
    )

    data object InfiniteCarouselArticleTitle : StringRes(
        en = "Building a Smooth, Infinite Carousel in Kobweb",
        sl = "Gradnja gladkega, neskončnega vrtiljaka v Kobwebu",
        lv = "Gluda, bezgalīga karuseļa izveide Kobweb"
    )

    data object InfiniteCarouselArticleDesc : StringRes(
        en = "How to build an infinite, gapless carousel using Silk and Kobweb.",
        sl = "Kako zgraditi neskončen vrtiljak brez vrzeli z uporabo Silk in Kobweb.",
        lv = "Kā izveidot bezgalīgu karuseli bez atstarpēm, izmantojot Silk un Kobweb."
    )
}