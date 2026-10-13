---
routeOverride: "/blog/appsflyer-kmp-wrapper"
title: "Wrapping the AppsFlyer SDK into a Kotlin Multiplatform library (when no official KMP SDK exists)"
description: "A coroutine-first wrapper around the AppsFlyer Android and iOS SDKs: all the logic in common code, two thin native adapters, deep links as a Flow, and 160 tests that never touch a real SDK."
date: "2026-06-28"
---
# Wrapping the AppsFlyer SDK into a Kotlin Multiplatform library (when no official KMP SDK exists)

AppsFlyer does attribution and deep linking for mobile apps. It ships good native SDKs for Android and iOS. What it doesn't ship is a Kotlin Multiplatform SDK. In a KMP app, where networking, domain logic and analytics already live in shared code, that means writing the integration twice: once in Kotlin against the Android SDK, once against the iOS SDK, each with its own callbacks.

So I wrote [`appsflyer-kmp`](https://github.com/retro99/appsflyer-kmp), an unofficial, experimental wrapper with one Kotlin API on both platforms. Asynchronous calls are suspending functions, and deep links arrive as a `Flow`.

> **Disclaimer:** This is a personal, unofficial project, not affiliated with or endorsed by AppsFlyer. "AppsFlyer" and the AppsFlyer SDK name are trademarks of their respective owners.

---

## The problem with two integrations

The native SDKs are callback-based, and they don't even agree on shape. Android takes a listener at init:

```kotlin
AppsFlyerLib.getInstance().init(devKey, conversionDataListener, context)
AppsFlyerLib.getInstance().start(application)
```

iOS uses delegate properties on a shared instance, and its deep links come through a second delegate:

```swift
AppsFlyerLib.shared().delegate = self
AppsFlyerLib.shared().deepLinkDelegate = self
AppsFlyerLib.shared().start()
```

Conversion data, deep link results, in-app event delivery and purchase validation all come back through callbacks, and none of them compose with coroutines. Parameter names, delegate patterns and which features exist differ between the two platforms. Every app that wants this in shared code ends up writing the same adapter.

---

## The shape: all the logic in common code

The obvious KMP structure is an `expect` declaration for every SDK call, with an `actual` on each platform. I went a different way. There is exactly one `expect` in the library, a factory. Everything else in common code talks to an internal interface that describes the native SDK:

```kotlin
internal interface AppsFlyerSdk {
    fun configure(
        config: AppsFlyerConfig,
        onConversion: (Map<String, Any?>) -> Unit,
        onConversionError: (String) -> Unit,
        onDeepLink: (DeepLinkResult) -> Unit,
        ...
```

Android and iOS each implement that interface as a thin adapter: translate arguments, call the native SDK, hand results back as plain Kotlin values. All the behaviour (turning callbacks into suspending functions, idempotent start, dropping null parameters, mapping attribution payloads into typed results) lives once, in `commonMain`, in a single client class.

The payoff is testing. Because the platform SDK is just an interface, the tests use a `FakeAppsFlyerSdk` and run in common code. There are about 160 of them, and none needs a device, an emulator or an AppsFlyer account. A small JVM target exists only so the code that converts Android's `org.json` payloads into Kotlin maps can be unit tested too.

---

## Callbacks become suspending functions

One-shot callbacks, like "was this event delivered?", turn into a suspending call with `suspendCancellableCoroutine`:

```kotlin
override suspend fun logEventForResult(
    name: String,
    params: Map<String, Any?>,
): LogEventResult = suspendCancellableCoroutine { continuation ->
    sdk.logEventForResult(
        name = name,
        params = params.filterValues { value -> value != null },
    ) { result ->
        continuation.resume(result)
    }
}
```

Conversion data and the start result are different: they arrive once, at some point after `start()`, and the app may ask for them before or after that happens. Those go into a `MutableSharedFlow` with `replay = 1`, and the public function is just `first()`:

```kotlin
override suspend fun getConversionData(): CampaignData = conversionRelay.first()
```

Called early, it suspends until the SDK answers. Called late, it returns the answer immediately. Callers don't have to know which.

`start()` is safe to call more than once. An atomic compare-and-exchange makes sure the native SDK is configured exactly once, however many places in an app call it.

---

## Deep links as a `Flow`

Deep links are a stream: one on first launch after install (deferred), and more every time the app is opened from a link (direct). Both SDKs have a newer "unified" deep link API that reports both kinds through one callback, so the wrapper uses that: `subscribeForDeepLink` on Android, `AppsFlyerDeepLinkDelegate` on iOS. The older `onAppOpenAttribution` callback is deliberately a no-op.

In common code, the result is one sealed type:

```kotlin
sealed interface DeepLinkResult {
    data class Found(
        val deepLinkValue: String?,
        val isDeferred: Boolean,
        val mediaSource: String?,
        val campaign: String?,
        val raw: Map<String, Any?>,
    ) : DeepLinkResult

    data object NotFound : DeepLinkResult

    data class Error(val message: String?) : DeepLinkResult
}
```

The stream is a `MutableSharedFlow` **without** replay. That was a deliberate choice. A deep link is an instruction to navigate somewhere now. Replaying an old one to a screen that subscribes later would send people somewhere they didn't just ask to go. A test pins the behaviour down (`deepLinkFlowDoesNotReplayPastEmissions`).

The consequence is documented in the README: subscribe to `deepLink` before calling `start()`, or the deferred deep link from the first launch can arrive before anyone is listening.

---

## The iOS side: a Swift bridge

The iOS adapter doesn't implement the Objective-C delegates in Kotlin. It talks to a small Swift class compiled into the library. It implements both delegates and turns each callback into a closure that Kotlin passed in:

```swift
public func configure(
    ...
    onConversion: @escaping (NSDictionary) -> Void,
    onConversionError: @escaping (String?) -> Void,
    onDeepLinkFound: @escaping (String?, Bool, String?, String?, NSDictionary?) -> Void,
    onDeepLinkNotFound: @escaping () -> Void,
    onDeepLinkError: @escaping (String?) -> Void,
    onStart: @escaping (Bool, Int, String?) -> Void
)
```

The bridge makes no decisions; it only translates. The iOS SDK itself comes in through Swift Package Manager, declared with the `spmForKmp` Gradle plugin, so an app that uses the library doesn't add the package by hand. To call the API from Swift, the app exports the library from its own framework:

```kotlin
kotlin {
    iosArm64().binaries.framework {
        export("org.retar.appsflyer:appsflyer:0.1.0")
    }
}
```

---

## Feature parity, and where the platforms differ

The wrapper covers what the native SDKs do: install attribution and conversion data, OneLink deep links (deferred and direct), in-app events with delivery confirmation, ad revenue, purchase validation (VAL V2), uninstall measurement, and GDPR/DMA consent.

Some APIs exist on only one platform, and those are no-ops on the other: 12 iOS-only methods, such as `setDisableSKAdNetwork`, and 14 Android-only ones, such as `setCollectIMEI`. Each says so in its KDoc. A handful behave differently on each platform, for example log levels: Android has six, iOS only has debug on or off. Those are documented too, so nobody finds out in production. Android-only calls that need a `Context`, `Activity` or `Intent` are extension functions in `androidMain`:

```kotlin
fun AppsFlyerClient.performOnDeepLinking(intent: Intent, context: Context)
fun AppsFlyerClient.sendPushNotificationData(activity: Activity)
```

One normalisation applies everywhere: null values in event, ad revenue, partner data and purchase parameters are dropped before they reach the native SDK, because the two SDKs treat nulls differently.

---

## What I'd keep from this

Put the logic in common code and keep the platform code dumb. An `expect`/`actual` pair per SDK call spreads behaviour across three source sets. One interface over the native SDK keeps it in one place, where it can be tested with a fake.

Pick the flow type by what the event means. Data that arrives once and stays true can be replayed. A navigation instruction can't.

---

## Try it

The library is on GitHub: [retro99/appsflyer-kmp](https://github.com/retro99/appsflyer-kmp). There's a Compose Multiplatform demo app in `samples/demo-app` that exercises the API on Android and iOS.

It's experimental and unofficial, and it isn't affiliated with AppsFlyer. Use it in production at your own risk.
