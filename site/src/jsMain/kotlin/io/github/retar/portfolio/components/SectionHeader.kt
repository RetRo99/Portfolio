package io.github.retar.portfolio.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.resources.StringRes
import io.github.retar.portfolio.styles.DescriptorStyle
import io.github.retar.portfolio.styles.SubtitleStyle
import io.github.retar.portfolio.styles.TitleStyle
import org.jetbrains.compose.web.css.CSSSizeValue
import org.jetbrains.compose.web.css.CSSUnit
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun SectionHeader(
    title: String,
    subtitle: String,
    gap: CSSSizeValue<CSSUnit.px> = 0.px,
) {
    Column(
        modifier = Modifier.gap(gap),
    ) {
        SectionTitle(text = title)
        SectionSubtitle(text = subtitle)
    }
}

@Composable
fun SectionTitle(text: String) {
    H1(TitleStyle.toModifier().toAttrs()) {
        Text(text)
    }
}

@Composable
fun SectionSubtitle(text: String) {
    SpanText(
        text = text,
        modifier = SubtitleStyle.toModifier(),
    )
}

@Composable
fun DescriptorText() {
    P(DescriptorStyle.toModifier().toAttrs()) {
        Text(StringRes.Descriptor.value)
    }
}

