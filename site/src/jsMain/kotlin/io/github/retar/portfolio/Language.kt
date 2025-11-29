package io.github.retar.portfolio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import com.varabyte.kobweb.browser.storage.createStorageKey
import com.varabyte.kobweb.browser.storage.getItem
import com.varabyte.kobweb.browser.storage.setItem
import kotlinx.browser.window

enum class Language(val code: String, val label: String) {
    EN("en", "English"),
    SL("sl", "Slovenščina"),
    LV("lv", "Latviešu");

    companion object {
        val Default = EN

        val currentState: MutableState<Language>
            @Composable get() = LocalLanguage.current

        val current: Language
            @Composable
            @ReadOnlyComposable
            get() = LocalLanguage.current.value

        @OptIn(ExperimentalWasmJsInterop::class)
        val systemPreference: Language
            get() {
                val languages = window.navigator.languages
                for (i in 0 until languages.length) {
                    val lang = languages[i]
                    val code = lang.take(2).lowercase()
                    entries.find { it.code == code }?.let { return it }
                }
                val lang = window.navigator.language
                val code = lang.take(2).lowercase()
                return entries.find { it.code == code } ?: Default
            }
    }
}

private const val LANGUAGE_STORAGE_KEY = "portfolio.language"

private fun createLanguageStorageKey(key: String) =
    Language.entries.createStorageKey(key)

fun Language.Companion.loadFromLocalStorage(
    key: String = LANGUAGE_STORAGE_KEY,
): Language? {
    val languageKey = createLanguageStorageKey(key)
    return window.localStorage.getItem(languageKey)
}

// Initialize with stored value immediately to prevent flash of default language
private val rootLanguageState by lazy {
    mutableStateOf(Language.loadFromLocalStorage() ?: Language.systemPreference)
}

private val LocalLanguage = compositionLocalOf { rootLanguageState }

fun Language.saveToLocalStorage(
    key: String = LANGUAGE_STORAGE_KEY,
) {
    val languageKey = createLanguageStorageKey(key)
    window.localStorage.setItem(languageKey, this)
}

fun Language.Companion.initializeFromLocalStorage(
    key: String = LANGUAGE_STORAGE_KEY,
) {
    rootLanguageState.value = loadFromLocalStorage(key) ?: systemPreference
}
