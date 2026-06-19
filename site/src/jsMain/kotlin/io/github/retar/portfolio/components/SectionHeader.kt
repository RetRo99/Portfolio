package io.github.retar.portfolio.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.styles.H2Style
import io.github.retar.portfolio.styles.MonoEyebrowStyle
import io.github.retar.portfolio.styles.SubtitleStyle
import org.jetbrains.compose.web.css.CSSSizeValue
import org.jetbrains.compose.web.css.CSSUnit
import org.jetbrains.compose.web.css.px

@Composable
fun SectionHeader(
    title: String,
    subtitle: String,
    eyebrow: String? = null,
    modifier: Modifier = Modifier,
    gap: CSSSizeValue<CSSUnit.px> = 0.px,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .gap(gap),
    ) {
        if (eyebrow != null) {
            SectionEyebrow(text = eyebrow)
        }
        SectionTitle(text = title)
        SectionSubtitle(text = subtitle)
    }
}

@Composable
fun SectionEyebrow(text: String) {
    SpanText(
        text = text,
        modifier = MonoEyebrowStyle.toModifier(),
    )
}

@Composable
fun SectionTitle(text: String) {
    SpanText(
        text = text,
        modifier = H2Style.toModifier()
            .fillMaxWidth(),
    )
}

@Composable
fun SectionSubtitle(text: String) {
    SpanText(
        text = text,
        modifier = SubtitleStyle.toModifier()
            .fillMaxWidth(),
    )
}
