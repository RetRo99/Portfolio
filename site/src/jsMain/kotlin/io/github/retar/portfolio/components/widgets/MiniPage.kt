package io.github.retar.portfolio.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.Language
import io.github.retar.portfolio.resources.ListGroup
import io.github.retar.portfolio.resources.MiniPageStrings
import io.github.retar.portfolio.styles.CaptionStyle
import io.github.retar.portfolio.styles.DisplayStyle
import io.github.retar.portfolio.styles.H1Text
import io.github.retar.portfolio.styles.MonoEyebrowStyle
import io.github.retar.portfolio.styles.SubtitleStyle
import io.github.retar.portfolio.styles.eyebrowText
import org.jetbrains.compose.web.dom.Div

/** A short standalone page (Now, Uses): a display title, an intro, then grouped two-column lists. */
@Composable
fun MiniPage(
    eyebrow: String,
    title: String,
    intro: String,
    groups: List<ListGroup>,
    updatedIso: String? = null,
    footnote: String? = null,
) {
    val language = Language.current
    Div(attrs = Modifier.styleModifier { property("width", "100%") }.toAttrs()) {
        Div(
            attrs = Modifier.styleModifier {
                property("display", "flex")
                property("flex-direction", "column")
                property("gap", "1.5rem")
                property("padding-block", "clamp(2.5rem, 7vw, 6rem) clamp(1.5rem, 4vw, 3rem)")
            }.toAttrs(),
        ) {
            SpanText(text = eyebrow.eyebrowText(), modifier = MonoEyebrowStyle.toModifier().rise(0))
            H1Text(text = title, modifier = DisplayStyle.toModifier().rise(1))
            SpanText(
                text = intro,
                modifier = SubtitleStyle.toModifier().rise(2).styleModifier { property("max-width", "58ch") },
            )
            if (updatedIso != null) {
                SpanText(
                    text = "${MiniPageStrings.updated.value} ${formatLongDate(updatedIso, language)}",
                    modifier = CaptionStyle.toModifier().rise(3),
                )
            }
        }

        groups.forEach { group ->
            EditorialGrid(
                eyebrow = group.eyebrow.value,
                modifier = Modifier.styleModifier { property("padding-block", "clamp(2rem, 5vw, 4rem)") },
            ) {
                FactList(items = group.entries.map { it.title.value to it.body.value })
            }
        }

        if (footnote != null) {
            SpanText(
                text = footnote,
                modifier = CaptionStyle.toModifier().styleModifier {
                    property("display", "block")
                    property("padding-block", "0 clamp(3rem, 7vw, 5rem)")
                },
            )
        }
    }
}

fun formatLongDate(iso: String, language: Language): String {
    val locale = when (language) {
        Language.EN -> "en-GB"
        Language.SL -> "sl-SI"
        Language.LV -> "lv-LV"
    }
    val options = js("({ year: 'numeric', month: 'long', day: 'numeric' })")
    return js("new Date(iso + 'T12:00:00')").toLocaleDateString(locale, options) as String
}
