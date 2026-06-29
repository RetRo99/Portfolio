---
routeOverride: "/blog/appsflyer-kmp-wrapper"
title: "Wrapping the AppsFlyer SDK into a Kotlin Multiplatform library (when no official KMP SDK exists)"
description: "A coroutine-first, expect/actual wrapper around the AppsFlyer Android and iOS SDKs — one Kotlin API, two native delegates, zero callbacks."
date: "2026-06-28"
---
# Wrapping the AppsFlyer SDK into a Kotlin Multiplatform library (when no official KMP SDK exists)

Parrot is a cross-platform EPUB reader and audiobook player. Android and iOS run on the same Kotlin codebase, compiled to a static Kotlin/Native framework. Networking, domain, database, analytics: all shared. When I needed attribution and deep linking, I reached for AppsFlyer. They ship native SDKs for Android and iOS, and the SDKs work. What they don't ship is a KMP SDK.

So I had two options. Write the same integration twice, once in `androidMain` and once in Swift, with callback-based APIs that don't compose into coroutines. Or wrap the native SDKs behind a single Kotlin API that looks like what I wanted in the first place.

I wrote [`appsflyer-kmp`](https://github.com/retro99/appsflyer-kmp). It's an unofficial, experimental wrapper that delegates to the native SDKs via `expect`/`actual`. Same surface area on both platforms, suspending functions instead of callbacks, deep links as `Flow`.

> **Disclaimer:** This is a personal, unofficial project, not affiliated with or endorsed by AppsFlyer. "AppsFlyer" and the AppsFlyer SDK name are trademarks of their respective owners.

---

## The problem with two integrations

The native AppsFlyer SDKs are callback-based:

```kotlin
AppsFlyerLib.getInstance().init(devKey, conversionDataListener, context)
AppsFlyerLib.getInstance().start(application)
```

```swift
AppsFlyerLib.shared().init(devKey, appId: appId, delegate: self)
AppsFlyerLib.shared().start()
```

In a KMP codebase that means duplicate integration logic in `androidMain` and `iosMain`. Callback hell for conversion data, deep link results, in-app event delivery, and purchase validation. No coroutine support: each async operation wraps a listener manually. The Android and iOS SDKs don't even expose identical method signatures, so parameter names, delegate patterns, and feature availability all differ.

Parrot's analytics layer is shared. I didn't want `#ifdef` blocks or platform-specific implementations in a codebase I'd already unified.

---

## The approach: `expect`/`actual` with a coroutine-first API

The library is structured as a standard KMP module:

```
appsflyer/
  src/
    commonMain/    # shared API + implementation
    androidMain/   # delegates to af-android-sdk
    iosMain/       # delegates to AppsFlyerLib via Swift bridge
    swift/         # Swift bridge wrapping iOS delegates
```

The common module defines the full API surface using `expect fun`/`expect val` for platform-bridged operations. Each `actual` implementation delegates to the native SDK, mapping results back into shared domain models.

### API design

Everything async is a `suspend fun`. Deep links arrive as a `Flow`:

```kotlin
suspend fun getStartResult(): StartResult
suspend fun getConversionData(): CampaignData
suspend fun logEventForResult(name: String, params: Map<String, Any?>): LogEventResult
suspend fun validateAndLogInAppPurchase(...): PurchaseValidationResult
```

```kotlin
val deepLink: Flow<DeepLinkResult>
```

No callbacks in user code. No listeners, no manual `Continuation` plumbing.

---

## Bridging the iOS SDK

The iOS SDK uses a delegate pattern (`AppsFlyerLibDelegate`). That doesn't map to Kotlin coroutines on its own, so the `swift/` directory contains a thin Swift bridge that wraps the delegate callbacks and forwards them to a Kotlin interface.

The bridge handles four cases. Conversion data: the delegate's `onConversionDataSuccess` / `onConversionDataFail` methods become a completed `Deferred`. Deep links: `onAppOpenAttribution` and `onDeepLinking` are forwarded into a Kotlin `Flow`. In-app event delivery: the delegate callback becomes the result of `logEventForResult`. Purchase validation: the VAL V2 callback becomes the result of `validateAndLogInAppPurchase`.

The Swift bridge is the only non-Kotlin code in the library. It doesn't make decisions. It translates delegate callbacks into interface calls.

---

## Deep links as `Flow`

Deep links were the hardest part to get right. The native SDKs expose them through delegate callbacks (`onAppOpenAttribution` on both platforms). Both fire for direct deep links (app already installed) and deferred deep links (first launch after install).

In the wrapper, deep links are a `Flow<DeepLinkResult>`:

```kotlin
sealed interface DeepLinkResult {
    data class Found(val deepLinkValue: String, val parameters: Map<String, Any?>) : DeepLinkResult
    data object NotFound : DeepLinkResult
    data class Error(val message: String) : DeepLinkResult
}
```

On the platform side, each `actual` implements a `Channel`-backed flow that the delegate callback pushes to. Backpressure is `BufferOverflow.DROP_OLDEST` with a capacity of 1, because deep link events are time-sensitive. A stale deep link is worse than a dropped one.

This was the API I wanted from an attribution SDK. One subscription, all link events, backpressure handled. The native SDKs give you none of that.

---

## Feature parity

The wrapper maintains 1:1 API parity with the native SDKs:

- Install attribution / conversion data
- OneLink deep linking (deferred and direct)
- In-app event logging with delivery confirmation
- Ad revenue measurement
- In-app purchase validation (VAL V2)
- Uninstall measurement
- GDPR/DMA consent

APIs that exist on only one platform are no-ops on the other (e.g. `setDisableSKAdNetwork` is iOS-only, `setCollectIMEI` is Android-only). This keeps the common API clean without losing platform-specific functionality where it matters.

Android-only extensions that require `Context`/`Activity`/`Intent` are exposed as extension functions:

```kotlin
fun AppsFlyerClient.performOnDeepLinking(intent: Intent, context: Context)
fun AppsFlyerClient.sendPushNotificationData(activity: Activity)
```

iOS doesn't need these. The deep link forwarding is handled through the Swift bridge.

---

## iOS consumption via SPM

The `AppsFlyerLib` SPM dependency is declared via `spmForKmp` in `build.gradle.kts` and resolved automatically. Consumers don't need a manual SPM step.

To expose the API in Swift, use `export` in your framework:

```kotlin
kotlin {
    iosArm64().binaries.framework {
        export("org.retar.appsflyer:appsflyer:0.1.0")
    }
}
```

Then from Swift:

```swift
AppsFlyer.shared.initialize(
    config: AppsFlyerConfig(
        devKey: "YOUR_AF_DEV_KEY",
        isDebug: true,
        iosAppId: "YOUR_APPLE_APP_ID"
    ),
    launchOptions: launchOptions
)
```

```swift
.onOpenURL { url in AppsFlyer.shared.linkHandler.handleOpenUrl(url: url) }
.onContinueUserActivity(NSUserActivityTypeBrowsingWeb) { activity in
    AppsFlyer.shared.linkHandler.handleUserActivity(userActivity: activity)
}
```

---

## What I learned

`expect`/`actual` is well suited for SDK wrapping. The common API stays clean, and each `actual` delegates however makes sense: direct method calls on Android, a Swift bridge on iOS.

Delegates don't map to coroutines for you. The conversion from "SDK calls my delegate method" to "my `suspend fun` returns a value" is manual plumbing. A `CompletableDeferred` per outstanding call, a `Channel` for streams, and you're done. But you have to write it.

Null params should be dropped silently. The native SDKs interpret null values differently, so the wrapper normalizes: null values in `logEvent`, `logAdRevenue`, `setAdditionalData`, `setPartnerData`, and purchase validation params are dropped on both platforms before they reach the native SDK.

The `samples/demo-app` is a Compose Multiplatform app that exercises every API. It's the fastest way to validate that both platforms behave identically. When I wired AppsFlyer into Parrot, the integration was a few lines of Kotlin on each side. That was the goal.

---

## Try it

The library is on GitHub: [retro99/appsflyer-kmp](https://github.com/retro99/appsflyer-kmp).

It's experimental, unofficial, and not affiliated with AppsFlyer. Use in production at your own risk. There's a demo app in `samples/demo-app` that shows the full API on both Android and iOS.
