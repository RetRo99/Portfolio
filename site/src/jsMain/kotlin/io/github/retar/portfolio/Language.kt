package io.github.retar.portfolio

import androidx.compose.runtime.compositionLocalOf

enum class Language(val code: String, val label: String) {
    EN("en", "English"),
    SL("sl", "Slovenščina"),
    LV("lv", "Latviešu");

    companion object {
        val Default = EN
    }
}

val LocalLanguage = compositionLocalOf { Language.Default }
val LocalSetLanguage = compositionLocalOf<(Language) -> Unit> { {} }
