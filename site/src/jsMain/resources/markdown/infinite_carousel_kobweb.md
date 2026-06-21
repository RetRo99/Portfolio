---
routeOverride: "/blog/infinite-carousel"
title: "Building a Smooth, Infinite Carousel in Kobweb"
description: "How to build a smooth, infinite carousel in Kobweb with Silk."
date: "2025-11-17"
---
# Building a Smooth, Infinite Carousel in Kobweb

If you’ve ever tried to build a carousel in the browser, you’ve probably run into some of these problems:

- It only scrolls if there are “enough” items.
- There’s an awkward jump when the carousel resets.
- You end up running a forever loop in JavaScript / coroutines.
- Background tabs get throttled and everything stutters.

In Kobweb, you don’t need any of that. You can get a **smooth, infinite, GPU‑accelerated carousel** with:

- Pure CSS transform animation (no manual scrolling)
- A small, reusable `InfiniteCarousel` composable
- Silk `CssStyle` and keyframes

This article walks through that pattern step‑by‑step.

---

## 1. The Core Idea: Modern “Marquee” with Transforms

The modern carousel pattern is:

1. **Duplicate your content** exactly once (so you have `[items][items]`).
2. Animate the whole track from `translateX(0%)` to `translateX(-50%)`.
3. Because the second half is identical to the first, when the animation loops, it’s visually seamless.

No timers, no scroll APIs, just a transform animation that the browser can optimize on the GPU.

In Kobweb + Silk, we model this as:

- A **viewport**: `Box` that hides overflow.
- A **track**: `Box` that lays out items in one row and runs the animation.
- A **generic composable**: `InfiniteCarousel<T>` that you can feed any item type.

---

## 2. Define the Keyframes

First, we define the scroll keyframes: from `0%` to `-50%`.

```kotlin
val InfiniteCarouselScrollKeyframes = Keyframes {
    0.percent {
        Modifier.transform { translateX(0.percent) }
    }
    100.percent {
        Modifier.transform { translateX((-50).percent) }
    }
}
```

This tells the browser:

> Take the whole track and move it left until half of it has slid out of view.

---

## 3. Style the Track with Silk

Next, we build a track style that:

- Uses inline‑flex so items sit in a single row.
- Has a gap between items.
- Runs our keyframe animation forever.

```kotlin
val InfiniteCarouselTrackStyle = CssStyle {
    base {
        Modifier
            .display(DisplayStyle.LegacyInlineFlex)
            .gap(24.px)
            .whiteSpace(WhiteSpace.NoWrap)
            .animation(
                InfiniteCarouselScrollKeyframes.toAnimation(
                    duration = 40.s,
                    iterationCount = AnimationIterationCount.Infinite,
                    timingFunction = AnimationTimingFunction.Linear,
                ),
            )
    }
}
```

This is the heart of the marquee behavior.

### Optional: Pause on Hover

If you want the carousel to pause when the user hovers it, add a `hover` block:

```kotlin
hover {
    Modifier.animation(
        InfiniteCarouselScrollKeyframes.toAnimation(
            duration = 40.s,
            iterationCount = AnimationIterationCount.Infinite,
            timingFunction = AnimationTimingFunction.Linear,
            playState = AnimationPlayState.Paused,
        ),
    )
}
```

Note we only change `playState`, not duration. That avoids animation “jumps.”

---

## 4. Style the Viewport

The viewport is just a flex container that **clips overflow** so we only see one “window” onto the infinite track:

```kotlin
val InfiniteCarouselViewportStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .display(DisplayStyle.Flex)
            .overflowX(Overflow.Hidden)
    }
}
```

---

## 5. Implement the Generic `InfiniteCarousel<T>`

Now we can write a reusable composable that:

- Accepts any item list (`List<T>`)
- Handles the “too few items” case
- Duplicates the content
- Emits your custom `itemContent`

```kotlin
@Composable
fun <T> InfiniteCarousel(
    items: List<T>,
    modifier: Modifier = Modifier,
    itemContent: @Composable (T) -> Unit,
) {
    val baseItems = if (items.size < 4) {
        generateSequence { items }.flatten().take(8).toList()
    } else {
        items
    }

    Box(
        modifier = InfiniteCarouselViewportStyle
            .toModifier()
            .then(modifier),
    ) {
        Box(
            modifier = InfiniteCarouselTrackStyle.toModifier(),
        ) {
            (baseItems + baseItems).forEach { itemContent(it) }
        }
    }
}
```

A couple of important details:

- **Small lists**: When there are fewer than 4 items, we expand the list by repeating until it feels “long enough” to scroll.
- **Duplication**: We render `baseItems + baseItems` so the keyframes can move from `0%` to `-50%` and loop perfectly.

---

## 6. Using the Carousel with Your Own Cards

Because `InfiniteCarousel` is generic, you just pass in your data and a composable that knows how to render a single item.

For example, with your `Project` model and `ProjectCard`:

```kotlin
@Composable
private fun SelectedProjectsSection() {
    PortfolioSection {
        TitleWithSubtitle(
            title = StringRes.SelectedProjectsTitle.value,
            subtitle = StringRes.SelectedProjectsSubtitle.value,
            gap = 10.px,
        )
        InfiniteCarousel(
            items = SelectedProjects,
            modifier = Modifier
                .padding(top = 24.px)
                .fillMaxWidth(),
        ) { project ->
            ProjectCard(
                title = project.title,
                imagePath = project.imagePath,
            )
        }
    }
}
```

---

## 7. Recap

We’ve built a carousel that:

- **Scrolls infinitely**, regardless of how many items you have.
- Is powered by **CSS transforms** and **Silk keyframes**, not manual JS logic.
- Is exposed as a simple, reusable `InfiniteCarousel<T>` composable.
- Plays nicely with Kobweb/Silk patterns for styling and hover effects.

