---
routeOverride: "/blog/infinite-carousel"
title: "Building a smooth, infinite carousel in Kobweb"
description: "A gapless, endlessly looping carousel in Kobweb and Silk: one CSS transform, no timers, no scroll code."
date: "2025-11-17"
---
# Building a smooth, infinite carousel in Kobweb

An earlier version of this site had a row of project cards that scrolled forever. My first attempt did it the obvious way: a coroutine that nudged the scroll position every frame and jumped back to the start at the end. It had every problem you'd expect. It stuttered in background tabs, because browsers throttle timers there. It jumped visibly when it reset. And it didn't scroll at all when there were only two or three cards.

The version that worked has no Kotlin running per frame. It's one CSS transform animation, a duplicated list and about forty lines of Silk.

---

## The trick: two copies and a 50% slide

Render the items twice in a single row, `[items][items]`, and animate the whole row from `translateX(0%)` to `translateX(-50%)`.

When the animation reaches the end, the second copy sits exactly where the first one started, so jumping back to `0%` is invisible. The loop is seamless by construction. There are no timers and no scroll APIs, and transforms are cheap for the browser to animate.

In Kobweb this takes three pieces:

- a **viewport** that clips overflow,
- a **track** that lays the items out in one row and runs the animation,
- a generic **`InfiniteCarousel<T>`** composable that takes any list.

---

## Keyframes

The keyframes slide the track from `0%` to `-50%`, half its own width:

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

---

## The track

The track keeps everything in one row and runs the keyframes forever, at a constant speed:

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
}
```

The `hover` block pauses the carousel under the pointer, so people can actually read a card. It changes only `playState` and keeps the same duration, because a different duration would make the track jump to a new position.

`Linear` matters too. With any easing curve, the track slows down and speeds up once per loop, and the seam becomes visible again.

---

## The viewport

The viewport is a full-width flex container that hides whatever is outside it:

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

## The composable

```kotlin
@Composable
fun <T> InfiniteCarousel(
    items: List<T>,
    modifier: Modifier = Modifier,
    itemContent: @Composable (T) -> Unit,
) {
    // With only a few items, one copy is narrower than the screen and the loop shows a gap.
    val baseItems = if (items.size < 4) {
        generateSequence { items }.flatten().take(8).toList()
    } else {
        items
    }

    Box(modifier = InfiniteCarouselViewportStyle.toModifier().then(modifier)) {
        Box(modifier = InfiniteCarouselTrackStyle.toModifier()) {
            baseItems.forEach { itemContent(it) }
            // The second copy only exists to close the loop. Hide it from screen readers
            // and keyboard focus so the list is announced and tabbed through once.
            baseItems.forEach { item ->
                Box(Modifier.attr("aria-hidden", "true").attr("inert", "")) {
                    itemContent(item)
                }
            }
        }
    }
}
```

Two details make this robust.

**Short lists.** If one copy of the items is narrower than the viewport, the end of the second copy comes into view before the loop restarts, and you see empty space. Repeating short lists up to eight items fixes that for card-sized content. If your items are tiny, raise the number.

**The duplicate is decoration.** Without `aria-hidden` and `inert`, a screen reader reads every card twice and the keyboard tabs through every link twice. Only the first copy is real content.

---

## Respect reduced motion

A carousel that never stops moving is exactly what `prefers-reduced-motion` exists for. Silk names the style's class after the property, so `InfiniteCarouselTrackStyle` becomes `infinite-carousel-track`, and one rule in a stylesheet stops it:

```css
@media (prefers-reduced-motion: reduce) {
    .infinite-carousel-track { animation-play-state: paused !important; }
}
```

People who asked their system for less motion get a static row of cards, and nothing else changes.

---

## Using it

Pass a list and say how to draw one item:

```kotlin
InfiniteCarousel(
    items = projects,
    modifier = Modifier.padding(top = 24.px),
) { project ->
    ProjectCard(title = project.title, imagePath = project.imagePath)
}
```

---

## What I'd keep from this

Animate a transform, not the scroll position. Duplicate the content once and slide it by half. Make the loop seamless by construction rather than by timing. Then pause it on hover, hide the copy from assistive tech, and stop it for people who prefer less motion. That's a whole marquee with no per-frame code at all.
