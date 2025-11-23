package io.github.retar.portfolio.components.layouts

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import io.github.retar.portfolio.PortfolioSectionId
import io.github.retar.portfolio.components.footer.Footer
import io.github.retar.portfolio.components.header.NavHeader
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh

val PageContentStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth(95.percent)
            .padding(top = 24.px)

    }
    Breakpoint.MD {
        Modifier.fillMaxWidth(85.percent)
    }
    Breakpoint.LG {
        Modifier.fillMaxWidth(70.percent)
    }
}

val NavHeaderStyle = CssStyle {
    base {
        Modifier.padding(leftRight = 16.px)
    }
    Breakpoint.MD {
        Modifier.padding(leftRight = 32.px)
    }
    Breakpoint.LG {
        Modifier.padding(leftRight = 64.px)
    }
}

@Layout
@Composable
fun PageLayout(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .minHeight(100.vh),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = NavHeaderStyle
                .toModifier()
                .id(PortfolioSectionId.Header.domId)
                .fillMaxWidth()
                .position(Position.Sticky)
                .top(0.px)
                .zIndex(1)
        ) {
            NavHeader()
        }

        Box(
            modifier = PageContentStyle.toModifier().weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
        Footer()
    }
}

