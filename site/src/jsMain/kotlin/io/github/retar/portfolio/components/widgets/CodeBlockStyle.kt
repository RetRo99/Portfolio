package io.github.retar.portfolio.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.browser.util.invokeLater
import com.varabyte.kobweb.compose.css.MinWidth
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.PointerEvents
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.attrsModifier
import com.varabyte.kobweb.compose.ui.modifiers.attr
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.left
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.pointerEvents
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.compose.ui.thenIfNotNull
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.common.SmoothColorStyle
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.name
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Code
import org.jetbrains.compose.web.dom.Pre
import org.jetbrains.compose.web.dom.Text

val CodeBlockStyle = CssStyle.base(extraModifier = { SmoothColorStyle.toModifier() }) {
    Modifier
        .borderRadius(10.px)
        .overflow { x(Overflow.Auto) }
//        .siteText(SiteTextSize.CODE)
//        .border(1.px, LineStyle.Solid, DividerColor.value())
        .padding(1.em)
}

val CodeLabelStyle = CssStyle.base {
    Modifier
        .position(Position.Absolute)
        .backgroundColor("var(--syntax-gutter-background-color-selected)".unsafeCast<CSSColorValue>())
        .color("var(--mono-1)".unsafeCast<CSSColorValue>())
//        .border(1.px, LineStyle.Solid, DividerColor.value())
        .fontSize(0.75.em)
        .top((-1).em)
        .left(1.em)
        .padding(topBottom = 0.em, leftRight = 0.5.em)
        .borderRadius(5.px)
        .pointerEvents(PointerEvents.None)
}

/**
 * Creates a code block that is colored by PrismJs
 */
// Note: To enable this widget to work, we needed to add PrismJs support to this project. See the kobweb
// block in our build.gradle.kts file to see how this was done.
@Composable
fun CodeBlock(
    code: String,
    modifier: Modifier = Modifier,
    lang: String? = null,
    highlightLines: String? = null,
    label: String? = null
) {
    Pre(
        CodeBlockStyle.toModifier()
            .thenIfNotNull(highlightLines) { Modifier.attr("data-line", it) }
            .thenIfNotNull(label) {
                Modifier
                    .margin { top(2.em) }
                    .attrsModifier {
                        ref { preElement ->
                            window.invokeLater { // Give prismjs plugin a frame to create the parent toolbar
                                preElement.parentElement?.takeIf { it.classList.contains("code-toolbar") }
                                    ?.let { toolbar ->
                                        preElement.insertAdjacentElement(
                                            "afterend",
                                            document.createElement("div").apply {
                                                textContent = label
                                                className = CodeLabelStyle.name
                                            })
                                    }
                            }
                            onDispose { }
                        }
                    }
            }
            .toAttrs()) {
        Code(
            attrs = SmoothColorStyle.toModifier()
                // Set min width so that `diff-highlight` coverts the entire width even when the code is scrollable
                .display(DisplayStyle.Block).minWidth(MinWidth.MaxContent)
                // The above style messes up text sizes inside code blocks that can
                // scroll horizontally (but only on iOS...)
                .styleModifier { property("-webkit-text-size-adjust", 100.percent) }
                .classNames("language-${lang ?: "none"}")
                .thenIf(lang?.startsWith("diff") == true, Modifier.classNames("diff-highlight"))
                .then(modifier)
                .toAttrs()
        ) {
            Text(code)
        }
    }
}
