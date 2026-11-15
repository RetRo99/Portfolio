---
routeOverride: "/blog/android-cold-start-baseline-profile"
title: "468 ms to 256: what actually made Parrot's Android cold start faster"
description: "Measuring an Android app's cold start the way Play installs it: why am start's TotalTime says nothing, how a Baseline Profile with dex layout beat compiling everything, and the 32 MB native library that cost no time at all."
cover: "/articles/cold_start_baseline_profile.jpeg"
date: "2026-11-15"
---
# 468 ms to 256: what actually made Parrot's Android cold start faster

Parrot is a reading and listening app, and the first thing it shows is your library. I wanted that shelf on screen sooner after you tap the icon. Before changing anything, I wanted to know where the time went, because my list of suspects was long: Firebase, Koin building its graph, two dozen initializers in `Application.onCreate`, a 32 MB native library for the neural voices ([how those work](/blog/on-device-neural-tts-android)).

The answer was none of them. The change that cut the cold start almost in half on my slowest phone was a Baseline Profile, and the suspect I was surest about turned out to cost nothing measurable.

This note is how I measured it, what the trace showed, and the parts that surprised me.

---

## The number Android gives you is the wrong one

The usual tool is `adb shell am start -W`, which prints a `TotalTime`. On every device and every build I tried, it said 230 to 300 ms. It never moved.

That's because `TotalTime` ends at the first frame. On Android 12 and later that's the splash screen handing over to an empty Compose frame. It says nothing about when the app is usable, which for Parrot means the shelf has finished loading.

So the first change was a measurement hook. The library screen calls `StartupSignals.libraryShown()` when its first load finishes, and the Activity logs the time since the process started:

```kotlin
// MainActivity.onCreate
val self = WeakReference(this)
StartupSignals.armForColdStart {
    self.get()?.reportFullyDrawn()
    Log.i(
        "ParrotStartup",
        "library shown ${SystemClock.elapsedRealtime() - Process.getStartElapsedRealtime()} ms after process start",
    )
}
```

It's armed only for the first Activity of the process, so a rotation or a warm start never reports a "cold start".

`reportFullyDrawn()` is there too, but I couldn't rely on it: when the shelf is ready before the first frame (which happens in the fast cases), the system drops its "Fully drawn" line. The app's own log line is what I tabled.

---

## Measure the build people actually install

A debug build is the wrong thing to time. It's interpreted, unshrunk, and several times slower than what users get. So every number here is a release build: R8, resource shrinking, the works.

But "release" isn't one state either. How much of the app's code is compiled ahead of time depends on how it was installed, and you can force each state with `cmd package compile`:

- **`verify`**: nothing precompiled. That's what a Play install gets when the app ships no profile.
- **`speed-profile`**: compiled from a profile of what actually ran.
- **`speed`**: everything compiled. The upper bound you'd guess is best.

The fourth state matters most: **what Play does with a profile**. Play delivers the profile next to the APK as a `.dm` file and the device compiles it at install. You can do the same locally:

```
adb install-multiple app-release.apk app-release.dm
adb shell dumpsys package <package> | grep status
# [status=speed-profile] [reason=install-dm]
```

One trap: the build writes profiles in two formats. Folder `0` is for API 31 and later, folder `1` for API 28 to 30. Android 16 and 17 refuse the second with "The profile is in the wrong format".

Each mode got one throw-away launch, then ten cold starts (`am force-stop`, a pause, `am start -W`) on three devices: a Galaxy Z Fold 3 on Android 13 (the slow one), a Galaxy S24 on Android 16, and an emulator on Android 17.

A small lesson from the first round, which I threw away: the phones' screen timeout kicked in mid-run, Compose paused, and the shelf never loaded. Wake the screen before every launch.

---

## Where the time goes

Before writing any fix, I recorded Perfetto traces of a fully compiled start on the Fold. The main thread, in milliseconds from process start:

| Step | When | Took |
|---|---|---|
| `bindApplication` (Firebase, androidx.startup, Koin, 24 initializers) | 29-219 | 190 ms |
| First frame | 260 | 68 ms |
| Compose frames composing the shelf | 260-470 | 105 + 43 ms and smaller |
| **Shelf shown** | **about 470** | |

Inside `bindApplication`, Firebase took about 40 ms and androidx.startup about 11. Koin and the initializers took about 70.

So the main thread is ready for the first frame at about 0.2 s. Everything after that is Compose building the library screen, and how fast that goes depends on whether its code is compiled or interpreted.

I also ran a method-sampling profile on a debug build, and it told a very different story: `Application.onCreate` 845 ms, Koin 703 ms of it. That's the trap of profiling debug. The interpreter inflates everything three to five times, and it inflates some code more than others. Useful for ranking, misleading for deciding what to fix. In the release trace, the same work was 60 to 190 ms.

---

## The Baseline Profile

A Baseline Profile is a list of the classes and methods your app uses on a path you care about. It ships inside the APK or bundle, and the device compiles those methods ahead of time at install, instead of interpreting them until ART's own profile catches up after a few launches.

Parrot's profile comes from a generator in its own `:baselineprofile` module. It's an instrumented test that starts the app and walks it. There are two tests, and the split matters:

```kotlin
@Test
fun generateStartup() {
    rule.collect(packageName = packageName, includeInStartupProfile = true) {
        pressHome()
        startActivityAndWait()
        device.waitFor("Library", 20_000)
    }
}

@Test
fun generate() {
    rule.collect(packageName = packageName, includeInStartupProfile = false) {
        pressHome()
        startActivityAndWait()
        device.waitFor("Library", 20_000)
        // open the first book, Listen, Play, Pause, Close, back, then the tabs...
    }
}
```

Both feed the Baseline Profile (57,134 rules). Only the first feeds the **startup profile**, which does a second job: with `dexLayoutOptimization` on, R8 puts the startup classes together in the first dex files.

```kotlin
// androidApp/build.gradle.kts
baselineProfile {
    automaticGenerationDuringBuild = false
    saveInSrc = true
    dexLayoutOptimization = true
}

dependencies {
    implementation(libs.androidx.profileinstaller)
    baselineProfile(projects.baselineprofile)
}
```

In the release bundle you can check it worked: `BUNDLE-METADATA/com.android.tools.build.profiles/baseline.prof` is there, and R8's `r8.json` marks `classes.dex` and `classes2.dex` as `startup: true`.

One practical snag. The generator needs a library with books in it, so it ran on a device with real data. Gradle's `generateReleaseBaselineProfile` uninstalls the app when its test run ends, which would have wiped that library. So I installed both APKs by hand and ran the generator with `am instrument`, then pulled the profile file off the device. On a throw-away emulator, the Gradle task is fine.

---

## The results

Process start to library shown, median of ten cold starts, release build, in milliseconds (range in brackets):

| Device | No profile (`verify`) | **With the profile, as Play installs it** | Everything compiled (`speed`) |
|---|---|---|---|
| Fold 3, Android 13 | 468 (456-480) | **256 (236-277)** | 384 |
| Emulator, Android 17 | 886 (655-1491) | **614 (420-770)** | 736 |
| S24, Android 16 | 955 (693-1125) | **816 (726-2618)** | 874 |

On the Fold that's 45 percent off. And on every device, the profile beat compiling everything.

That was the surprise. I'd assumed `speed` was the ceiling and a profile could only approach it. It isn't, because compiling isn't the only cost. The startup code also has to be loaded, and when it's spread across all four of the app's dex files, the start touches pages all over them. With dex layout, the startup classes sit together, so fewer pages are read. Compiling everything doesn't do that.

The S24's gain is smaller for a reason that's specific to that phone: it reopens the last book on start, so it also inflates the reader's WebView, and a background job starts preparing a chapter with a neural voice and keeps a core busy. Its range is also strange: in every mode, some runs reach the shelf in about 0.9 s and others in 2.6 to 3.1 s, nothing in between. I haven't found why yet.

---

## The change that saved nothing

My strongest suspect was onnxruntime. Its Android library registers a `ContentProvider` that runs before `Application.onCreate` on every launch. It loads the 32 MB native library and starts a Microsoft telemetry client, whether or not anyone ever uses a neural voice.

So I removed the provider:

```xml
<provider
    android:name="ai.onnxruntime.TelemetryInitializer"
    tools:node="remove" />
```

This is safe because onnxruntime loads its native library itself when the first session opens (I checked the bytecode with `javap`, then read a book aloud with a neural voice on a release build to be sure).

Measured alone, without the profile: 466 ms against 468. No difference.

I kept the change anyway. A reading app shouldn't start a third-party telemetry client on every launch for a feature you might never turn on, and a 32 MB library is memory the app doesn't need until you press Play. A unit test now checks the manifest so it can't quietly come back. But it was a privacy fix, not a speed fix, and the measurement is what told me which.

---

## What I didn't do

The trace gives an obvious next list: defer Firebase (about 40 ms), WorkManager's setup (about 16 ms), and some of Koin and the initializers (60 to 190 ms). I left them.

Each is small next to what the profile did, and each comes with ordering risks. Crashlytics needs to start early to catch a crash during start-up. Initializers that get deferred start racing whatever needed them. That's worth doing when it's the biggest thing left, and it isn't yet.

---

## Honest status

- **Three devices, one run each.** The Fold is the cleanest. The emulator rows are the least reliable, and the S24 is bimodal, which a median hides.
- **The S24's slow runs are open.** Something sends a cold start to 2.6 s in some runs, in every compile mode. The profile doesn't fix that.
- **The `.dm` I tested is the universal APK's.** I checked that the Play bundle carries the same profile, but I didn't split it with bundletool to look at the `.dm` Play would actually send.
- **The profile needs upkeep.** It records the screens as they were on the day. After a large UI change, it has to be generated again, or the new code runs interpreted.

What stays with me is how close I came to optimising the wrong thing. My list of suspects was all main-thread work in `Application.onCreate`, and a debug profile would have agreed. The release trace said the main thread was done at 0.2 s, and the time I was trying to win back was Compose code that simply hadn't been compiled yet.
