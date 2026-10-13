package io.github.retar.portfolio.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.dom.ref
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.graphics.ImageLoading
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.resources.ImageRes
import io.github.retar.portfolio.styles.BodyStyle
import io.github.retar.portfolio.styles.H3Style
import io.github.retar.portfolio.styles.H3Text
import io.github.retar.portfolio.styles.MonoEyebrowStyle
import io.github.retar.portfolio.styles.eyebrowText
import io.github.retar.portfolio.styles.sitePalette
import io.github.retar.portfolio.utils.trackEvent
import kotlin.js.json
import org.jetbrains.compose.web.dom.Div

/** Adds the staggered "rise" entrance defined in the site head; [order] sets the delay step. */
fun Modifier.rise(order: Int): Modifier =
    classNames("rise").styleModifier { property("--i", order.toString()) }

/**
 * Editorial section frame: a narrow label column (eyebrow) beside a wide content column on
 * desktop, stacked on mobile. A hairline sits above every section.
 */
val EditorialGridStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier.styleModifier {
            property("display", "grid")
            property("grid-template-columns", "minmax(0, 1fr)")
            property("gap", "1.25rem")
            property("border-top", "1px solid ${palette.border}")
            property("padding-top", "clamp(1.25rem, 2.5vw, 2rem)")
        }
    }
    Breakpoint.MD {
        Modifier.styleModifier {
            property("grid-template-columns", "minmax(0, 3fr) minmax(0, 9fr)")
            property("gap", "2.5rem")
        }
    }
}

@Composable
fun EditorialGrid(
    eyebrow: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Div(attrs = EditorialGridStyle.toModifier().then(modifier).toAttrs()) {
        Div(attrs = Modifier.styleModifier { property("padding-top", "0.35rem") }.toAttrs()) {
            SpanText(text = eyebrow.eyebrowText(), modifier = MonoEyebrowStyle.toModifier())
        }
        Div(
            attrs = Modifier.styleModifier {
                property("display", "flex")
                property("flex-direction", "column")
                property("gap", "1.5rem")
                property("min-width", "0")
            }.toAttrs(),
        ) {
            content()
        }
    }
}

/** A device-shaped frame for app screenshots. Screenshots carry their own status bar, so the frame stays minimal. */
val PhoneFrameStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier.styleModifier {
            property("aspect-ratio", "945 / 2048")
            property("border-radius", "28px")
            property("overflow", "hidden")
            property("background", "#0e0d10")
            property("border", "6px solid #0e0d10")
            property("outline", "1px solid ${palette.border}")
            property(
                "box-shadow",
                "0 1px 2px rgba(40, 28, 16, 0.08), 0 24px 48px -16px rgba(40, 28, 16, 0.28)",
            )
            property("flex", "0 0 auto")
            property("scroll-snap-align", "center")
        }
    }
}

@Composable
fun PhoneShot(image: ImageRes, description: String, modifier: Modifier = Modifier) {
    Div(attrs = PhoneFrameStyle.toModifier().then(modifier).toAttrs()) {
        Image(
            src = image.path,
            description = description,
            modifier = Modifier.styleModifier {
                property("width", "100%")
                property("height", "100%")
                property("object-fit", "cover")
                property("display", "block")
            },
            width = image.intrinsicWidth,
            height = image.intrinsicHeight,
            loading = ImageLoading.Lazy,
        )
    }
}

/**
 * Screenshots as a staggered row: on desktop the middle phones sit lower to break the line,
 * on mobile the row becomes a horizontal scroll-snap strip.
 */
val PhoneRowStyle = CssStyle {
    base {
        Modifier.styleModifier {
            property("display", "flex")
            property("gap", "1rem")
            property("overflow-x", "auto")
            property("scroll-snap-type", "x mandatory")
            property("padding", "0.5rem 0 2rem")
            property("margin-inline", "-1rem")
            property("padding-inline", "1rem")
            property("scrollbar-width", "none")
        }
    }
    Breakpoint.MD {
        Modifier.styleModifier {
            property("overflow-x", "visible")
            property("margin-inline", "0")
            property("padding-inline", "0")
            property("gap", "clamp(0.75rem, 1.5vw, 1.5rem)")
            property("align-items", "flex-start")
        }
    }
}

val PhoneRowItemStyle = CssStyle {
    base {
        Modifier.styleModifier {
            property("width", "min(62vw, 240px)")
        }
    }
    Breakpoint.MD {
        Modifier.styleModifier {
            property("width", "auto")
            property("flex", "1 1 0")
            property("min-width", "0")
            property("margin-top", "var(--offset, 0)")
        }
    }
}

/** Two-column list of short facts separated by hairlines. Replaces the numbered feature grid. */
val FactListStyle = CssStyle {
    base {
        Modifier.styleModifier {
            property("display", "grid")
            property("grid-template-columns", "minmax(0, 1fr)")
            property("column-gap", "2.5rem")
            property("margin", "0")
            property("padding", "0")
        }
    }
    Breakpoint.MD {
        Modifier.styleModifier { property("grid-template-columns", "repeat(2, minmax(0, 1fr))") }
    }
}

val FactStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier.styleModifier {
            property("display", "flex")
            property("flex-direction", "column")
            property("gap", "0.375rem")
            property("padding", "1.125rem 0 1.25rem")
            property("border-top", "1px solid ${palette.border}")
        }
    }
}

@Composable
fun FactList(items: List<Pair<String, String>>, modifier: Modifier = Modifier) {
    Div(attrs = FactListStyle.toModifier().then(modifier).toAttrs()) {
        items.forEach { (title, body) ->
            Div(attrs = FactStyle.toModifier().toAttrs()) {
                H3Text(text = title, modifier = H3Style.toModifier())
                SpanText(text = body, modifier = BodyStyle.toModifier())
            }
        }
    }
}

/** An article as a row: thumbnail, title and summary. The whole row is the link. */
val ArticleRowStyle = CssStyle {
    base {
        val palette = sitePalette()
        Modifier.styleModifier {
            property("display", "grid")
            property("grid-template-columns", "minmax(0, 1fr)")
            property("gap", "1rem")
            property("padding", "1.5rem 0")
            property("border-top", "1px solid ${palette.border}")
            property("color", "inherit")
        }
    }
    Breakpoint.SM {
        Modifier.styleModifier {
            property("grid-template-columns", "180px minmax(0, 1fr)")
            property("gap", "1.75rem")
            property("align-items", "start")
        }
    }
    hover {
        val palette = sitePalette()
        Modifier.styleModifier { property("--row-title", palette.accent.toString()) }
    }
}

@Composable
fun ArticleRow(
    title: String,
    description: String,
    route: String,
    image: ImageRes,
    readLabel: String,
) {
    Link(
        path = route,
        modifier = ArticleRowStyle.toModifier()
            .textDecorationLine(TextDecorationLine.None)
            .onClick { trackEvent("article-open", json("route" to route)) },
        ref = ref { it.asDynamic().setAttribute("aria-label", title) },
    ) {
        Div(
            attrs = Modifier.styleModifier {
                property("aspect-ratio", "16 / 9")
                property("border-radius", "6px")
                property("overflow", "hidden")
            }.toAttrs(),
        ) {
            Image(
                src = image.path,
                description = "",
                modifier = Modifier.styleModifier {
                    property("width", "100%")
                    property("height", "100%")
                    property("object-fit", "cover")
                    property("display", "block")
                },
                width = image.intrinsicWidth,
                height = image.intrinsicHeight,
                loading = ImageLoading.Lazy,
            )
        }
        Div(
            attrs = Modifier.styleModifier {
                property("display", "flex")
                property("flex-direction", "column")
                property("gap", "0.5rem")
            }.toAttrs(),
        ) {
            H3Text(
                text = title,
                modifier = H3Style.toModifier().styleModifier {
                    property("color", "var(--row-title, currentColor)")
                    property("transition", "color 160ms ease")
                },
            )
            SpanText(text = description, modifier = BodyStyle.toModifier())
            SpanText(
                text = "$readLabel →",
                modifier = MonoEyebrowStyle.toModifier().styleModifier { property("margin-top", "0.25rem") },
            )
        }
    }
}
