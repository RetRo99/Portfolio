---
routeOverride: "/blog/sqlcipher-static-kotlin-native-framework"
title: "Shipping SQLCipher inside a static Kotlin/Native framework (so the iOS team changed nothing)"
description: "Baking SQLCipher into a static Kotlin/Native framework so the iOS team adds nothing, not even a linker flag."
date: "2026-06-21"
---
# Shipping SQLCipher inside a static Kotlin/Native framework (so the iOS team changed nothing)

I work on a KMP app. Shared code (networking, domain logic, a Room database) compiles into a single static framework the iOS app links against. One day the requirement landed: the on-device Room cache on iOS needs to be encrypted, same as it already is on Android with SQLCipher.

The interesting part was the constraint. The iOS team shouldn't have to add a dependency, set a build flag, or manage SQLCipher at all. They link one framework today, they should link the same one tomorrow, and encryption comes with it. No SPM package, no `-force_load` flag. They do still hand over the encryption key, but that's a runtime argument, a `ByteArray`, not a build change. I'll get to that.

That constraint is why the standard recipe didn't work, and it's why this took me a while.

## Why the standard recipe doesn't apply

Google "SQLCipher iOS" and you get the same thing everywhere:

1. Add `SQLCipher-SPM` as a Swift Package dependency.
2. Pass `-force_load .../libsqlcipher.a` to Xcode's linker.

That works because the *app's* linker is what resolves symbols. At app-link time it sees your code and the SQLCipher archive together and stitches them in. `-force_load` forces every object in the archive so the encrypted `sqlite3_*` symbols win over the system's plain SQLite.

My situation breaks this in two ways:

- `SharedKit` is a *static* framework (`isStatic = true`). There's no app-level link step I control the way that recipe assumes. By the time the iOS app links it, the `sqlite3_*` symbols inside the framework are already set in stone.
- The iOS team adds nothing. Pushing an SPM package and a linker flag onto them is exactly what I was told to avoid.

But there's a subtler problem. Room's iOS support comes through `androidx.sqlite:sqlite-framework`. That artifact gives me the cinterop bindings my driver uses (`NativeSQLiteConnection`, `sqlite3_open_v2`, and friends), but it binds them against the **system** `libsqlite3` baked into iOS. System SQLite has no encryption codec. SQLCipher is a drop-in replacement: same `sqlite3_*` symbol names, but with encryption. The challenge is getting SQLCipher's `sqlite3_*` symbols to win over the system ones at the point my framework resolves them.

So this is a symbol-resolution fight. That's why `-force_load libsqlcipher.a` as a Kotlin/Native `linkerOpt` was the wrong tool, even though that's where I started.

The symbols had to get *into* `SharedKit` some other way.

## Building SQLCipher as a static library

Nothing exotic here. A script clones a pinned SQLCipher tag and compiles it per arch (`ios-arm64`, `ios-simulator-arm64`) with Apple's CommonCrypto as the crypto backend, so there's no OpenSSL to vendor:

```bash
./configure \
    --host="$host" \
    --with-crypto-lib=commoncrypto \
    --disable-shared --enable-static \
    --disable-tcl \
    CFLAGS="-arch $arch -isysroot $sysroot $min_version_flag $target_flag \
        -DSQLITE_HAS_CODEC \
        -DSQLCIPHER_CRYPTO_CC \
        -DSQLITE_TEMP_STORE=2 \
        ... -Os" \
    LDFLAGS="-arch $arch -isysroot $sysroot ... -framework Security"

make -j"$(sysctl -n hw.ncpu)" libsqlcipher.la
cp .libs/libsqlcipher.a "$output_dir/libsqlcipher.a"
```

The flags that matter: `-DSQLITE_HAS_CODEC` turns on the encryption codec, and `-DSQLCIPHER_CRYPTO_CC` (with `--with-crypto-lib=commoncrypto`) routes crypto through CommonCrypto, which lives in Apple's `Security` framework. Out comes `libsqlcipher.a` per arch. These are gitignored and built on demand, not checked in.

## Merging SQLCipher into the framework binary

This is the move nobody documents, and I got it wrong before I got it right.

My first instinct was to port the standard recipe into Gradle: `linkerOpts("-force_load", ".../libsqlcipher.a")` per iOS target. Felt right. It's wrong. `-force_load` is a flag for `ld`, the linker that produces a final executable or dynamic image. A **static** framework never goes through that step inside our build. `isStatic = true` means the framework binary is itself a static archive, assembled with `ar` and `libtool`, not linked with `ld`. There's no `ld` invocation for `-force_load` to attach to. The flag gets ignored, or only matters at the iOS app's final link, which is exactly the iOS-side step I'm trying to eliminate.

So the right tool isn't a linker flag at all. If the framework binary is a static archive, then embedding SQLCipher means **merging two static archives into one**, a job for `ar` and `libtool`, the same tools that built the framework in the first place. It's the same kind of operation the build system already does, just one stage later.

After Gradle assembles the XCFramework, a `finalizedBy` task runs for each slice of the `.xcframework`, takes the assembled framework binary, and re-combines it with SQLCipher's objects:

```kotlin
// 1. Explode the framework binary back into its object files.
exec("ar", "x", binary.absolutePath, workingDir = extractFramework)

// 2. Explode the SQLCipher static lib into its object files.
exec("ar", "x", sqlcipherLib.absolutePath, workingDir = extractSqlcipher)

// 3. Relocatable-link everything into one combined object.
val objectFiles = (extractFramework.listFiles() + extractSqlcipher.listFiles())
    .filter { file -> file.extension == "o" }
val merged = tmpDir.resolve("merged.o")
exec(listOf("ld", "-r", "-arch", "arm64", "-o", merged.absolutePath) +
    objectFiles.map { it.absolutePath })

// 4. Re-wrap as a static archive, overwriting the framework binary in place.
exec("libtool", "-static", "-o", binary.absolutePath, merged.absolutePath)
```

`ar x` unpacks an archive into its `.o` members. `ld -r` does a partial (relocatable) link, combining many objects into one without producing a final executable. `libtool -static` wraps that back into the archive that *is* the framework binary. The net effect: SQLCipher's `sqlite3_open_v2`, the codec, the `PRAGMA key` handler, all physically inside the framework.

Wired into the build so it runs automatically after assembly:

```kotlin
tasks.matching { it.name == assembleTask }.configureEach {
    finalizedBy("mergeSqlCipher${variant}XCFramework")
}
```

The only thing the framework itself has to declare is the system framework CommonCrypto lives in:

```kotlin
linkerOpts("-S", "-framework", "Security")
```

That's the build-side iOS story. The iOS team links the `.xcframework` exactly as before, no SPM package, no linker flags. The encrypted SQLite engine is already in it.

## Using it, and proving it's on

A custom `SQLiteDriver` opens the connection via cinterop and immediately sets the key, then checks that the SQLite it's talking to is really SQLCipher:

```kotlin
val connection = NativeSQLiteConnection(dbPointer.value!!)
connection.execSQL("PRAGMA key = \"x'${encryptionKey.toHexString()}'\"")
verifyCipherAvailable(connection)
```

```kotlin
private fun verifyCipherAvailable(connection: SQLiteConnection) {
    connection.prepare("PRAGMA cipher_version").use { stmt ->
        if (stmt.step()) {
            Logger.i { "SQLCipher active, version: ${stmt.getText(0)}" }
        } else {
            Logger.w { "SQLCipher is NOT available. Database is NOT encrypted." }
        }
    }
}
```

`PRAGMA cipher_version` returns a row only on SQLCipher. On plain SQLite it returns nothing. If the merge ever silently stops working, this fires.

## The one thing iOS still owns: the key

"iOS changes nothing" is true for the SQLCipher integration: the engine and its linkage are fully inside the framework. But the `encryptionKey` in the snippets above has to come from somewhere. The framework does the encryption. It does not invent or store the secret. That was deliberate:

```kotlin
fun getDatabaseBuilder(
    name: String,
    encryptionKey: ByteArray,
    directoryPath: String? = null,
): RoomDatabase.Builder<AppDatabase>
```

The key arrives as a `ByteArray` when the iOS app constructs the shared SDK entry point (something like `SharedSDK(httpTransport, encryptionKey, ...)`), and flows down to the driver. Generating a random key on first launch and persisting it in the iOS Keychain is a platform concern, so it stays on the iOS side. The framework has no business owning a Keychain item. The split:

- Framework owns the engine: SQLCipher, the driver, `PRAGMA key`, the encrypted reads/writes.
- iOS owns the secret: generate it, store it in the Keychain, hand it over as a `ByteArray`.

This is a one-argument contract, not a build-system change, so it doesn't violate the "no SPM, no linker flags" goal. It does have a consequence worth designing for: if the key ever stops matching the database (Keychain reset on reinstall, a key rotation), the existing file is undecryptable. SQLCipher surfaces that as "file is not a database" the first time it reads an encrypted page, deep inside Room's connection setup, where the error doesn't say "your key is wrong."

So before Room ever opens the file, I pre-flight it with the supplied key:

```kotlin
internal fun recreateDatabaseIfKeyInvalid(fileName: String, encryptionKey: ByteArray) {
    if (!fileManager.fileExistsAtPath(fileName)) return        // fresh start, nothing to decrypt
    if (!canDecrypt(fileName, encryptionKey)) {                // wrong key → unrecoverable
        deleteDatabaseFiles(fileName, fileManager)             // drop it; Room recreates encrypted
    }
}
```

`canDecrypt` opens the file, sets the key, and forces a read of page 1 (`SELECT count(*) FROM sqlite_master`) so a bad key trips *here* rather than inside Room. Because this database is a cache, deleting and recreating is safe. You lose cached data, not user data.

## Sharp edges

The worst one: **if you misconfigure it, you get plaintext instead of a build failure.** The merge task just skips a slice if the `.a` is missing:

```kotlin
if (!binary.exists() || !sqlcipherLib.exists()) return@forEach
```

So if someone forgets to build the SQLCipher libs, the framework builds *successfully*, just unencrypted. The only signal is the runtime log line from above, not a red build. An unencrypted framework that ships as if encrypted is the worst outcome I can think of here. The merge task should fail loudly when a `.a` is missing instead of skipping. I haven't done that yet, and until I do, the runtime check is the only thing catching it.

Two more:

- The system `libsqlite3` and SQLCipher both define `sqlite3_*`. Merging SQLCipher's objects into the framework binary is what tips resolution toward SQLCipher, but a Room or Kotlin/Native version bump is exactly the kind of thing that can disturb that. The `PRAGMA cipher_version` check is what guards it.
- `-arch arm64` is hardcoded in the `ld -r` step, matching the two arm64 slices shipped (device + Apple-silicon simulator). Add an x86_64 simulator slice and this needs to learn about arch.

The runtime check is the only one actually in place today. Failing the build on a missing `.a` and pinning the Room and Kotlin/Native versions are still on the to-do list.

When you need a third-party static library baked into a static Kotlin/Native framework, the same `ar`/`libtool` archive operations that built the framework do the job, applied one stage later (`ar x` → `ld -r` → `libtool -static`). `-force_load` is for the final `ld` link, and a static framework doesn't go through one inside its own build.

The constraint "iOS adds no dependency or build config" drove the design. The easy answer, `addSpm` plus `-force_load`, shifted the integration cost onto the iOS team. The build here absorbed it. iOS constructs the SDK with a `ByteArray` encryption key and otherwise links the framework exactly as before.
