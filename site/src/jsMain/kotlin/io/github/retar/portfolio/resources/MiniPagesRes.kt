package io.github.retar.portfolio.resources

/** One-off copy for the small pages, without a named object per line. */
class Copy(en: String, sl: String, lv: String) : StringRes(en, sl, lv)

class ListEntry(val title: StringRes, val body: StringRes)

class ListGroup(val eyebrow: StringRes, val entries: List<ListEntry>)

private fun same(text: String) = Copy(text, text, text)

object NowRes {
    /** Bump this whenever the page changes; it is shown as "Updated …". */
    const val UPDATED = "2026-10-13"

    val title = Copy(en = "Now", sl = "Zdaj", lv = "Tagad")

    val intro = Copy(
        en = "What I'm focused on at the moment. If you're reading this long after the date below, ask me what changed.",
        sl = "S čim se trenutno ukvarjam. Če to berete dolgo po spodnjem datumu, me vprašajte, kaj se je spremenilo.",
        lv = "Ar ko es šobrīd nodarbojos. Ja to lasāt krietni pēc zemāk norādītā datuma, pajautājiet man, kas ir mainījies.",
    )

    val groups = listOf(
        ListGroup(
            eyebrow = Copy(en = "// work", sl = "// delo", lv = "// darbs"),
            entries = listOf(
                ListEntry(
                    title = Copy(
                        en = "Leading Android at DevRev",
                        sl = "Vodenje Androida pri DevRevu",
                        lv = "Android komandas vadīšana DevRev",
                    ),
                    body = Copy(
                        en = "Six engineers, one Compose codebase, and CI that keeps getting faster. Most of my week is architecture reviews, code review and unblocking people.",
                        sl = "Šest inženirjev, ena kodna baza v Compose in CI, ki je vedno hitrejši. Večino tedna namenim pregledom arhitekture in kode ter odstranjevanju ovir.",
                        lv = "Seši inženieri, viena Compose kodbāze un CI, kas kļūst arvien ātrāks. Lielāko daļu nedēļas veltu arhitektūras un koda pārskatiem un šķēršļu novēršanai.",
                    ),
                ),
                ListEntry(
                    title = Copy(
                        en = "Open to the next role",
                        sl = "Odprt za novo vlogo",
                        lv = "Atvērts nākamajai lomai",
                    ),
                    body = Copy(
                        en = "Senior mobile and Kotlin Multiplatform roles, remote across the EU. Email works best.",
                        sl = "Vodilne vloge v mobilnem razvoju in Kotlin Multiplatform, na daljavo po EU. Najbolje mi je pisati po e-pošti.",
                        lv = "Vecākā līmeņa mobilo lietotņu un Kotlin Multiplatform lomas, attālināti visā ES. Vislabāk rakstīt e-pastu.",
                    ),
                ),
            ),
        ),
        ListGroup(
            eyebrow = Copy(en = "// parrot", sl = "// parrot", lv = "// parrot"),
            entries = listOf(
                ListEntry(
                    title = Copy(
                        en = "Parrot on iPhone",
                        sl = "Parrot za iPhone",
                        lv = "Parrot iPhone tālrunim",
                    ),
                    body = Copy(
                        en = "Bringing the reader to iOS on the same Kotlin codebase: library, sync and read-along are shared, the player and the reading view stay native.",
                        sl = "Bralnik prenašam na iOS na isti kodni bazi v Kotlinu: knjižnica, sinhronizacija in sledenje besedilu so skupni, predvajalnik in pogled za branje ostajata nativna.",
                        lv = "Pārnesu lasītāju uz iOS ar to pašu Kotlin kodbāzi: bibliotēka, sinhronizācija un lasīšana līdzi ir kopīgas, atskaņotājs un lasīšanas skats paliek nativi.",
                    ),
                ),
                ListEntry(
                    title = same("Parrot Cloud"),
                    body = Copy(
                        en = "Books, reading positions, highlights and statistics synced across every device you sign in to. It's in early access while I harden it.",
                        sl = "Knjige, mesta branja, označbe in statistika, usklajeni na vseh napravah, v katere se prijavite. Je v zgodnjem dostopu, dokler ga utrjujem.",
                        lv = "Grāmatas, lasīšanas vietas, iezīmējumi un statistika, sinhronizēti visās ierīcēs, kurās pierakstāties. Tas ir agrīnajā piekļuvē, kamēr to nostiprinu.",
                    ),
                ),
            ),
        ),
        ListGroup(
            eyebrow = Copy(en = "// writing", sl = "// pisanje", lv = "// raksti"),
            entries = listOf(
                ListEntry(
                    title = Copy(
                        en = "More engineering notes",
                        sl = "Več inženirskih zapisov",
                        lv = "Vairāk inženierijas piezīmju",
                    ),
                    body = Copy(
                        en = "Write-ups of what it takes to run Kotlin Multiplatform in production, from the build to the App Store. Follow along with the RSS feed.",
                        sl = "Zapisi o tem, kaj zahteva Kotlin Multiplatform v produkciji, od gradnje do App Storea. Spremljate jih lahko prek vira RSS.",
                        lv = "Raksti par to, ko prasa Kotlin Multiplatform ražošanā, no būvēšanas līdz App Store. Sekojiet līdzi RSS plūsmā.",
                    ),
                ),
            ),
        ),
    )
}

object UsesRes {
    val title = Copy(en = "Uses", sl = "Orodja", lv = "Rīki")

    val intro = Copy(
        en = "The tools, libraries and services I build with every day, and why each one earned its place.",
        sl = "Orodja, knjižnice in storitve, s katerimi gradim vsak dan, in zakaj si je vsako prislužilo svoje mesto.",
        lv = "Rīki, bibliotēkas un pakalpojumi, ar kuriem strādāju katru dienu, un kāpēc katrs no tiem ir šeit.",
    )

    val groups = listOf(
        ListGroup(
            eyebrow = Copy(en = "// editor & tools", sl = "// urejevalnik in orodja", lv = "// redaktors un rīki"),
            entries = listOf(
                ListEntry(
                    title = same("Android Studio"),
                    body = Copy(
                        en = "Where almost all of my Kotlin gets written, Android and shared code alike.",
                        sl = "Tu nastane skoraj vsa moja koda v Kotlinu, za Android in skupna koda.",
                        lv = "Šeit top gandrīz viss mans Kotlin kods, gan Android, gan kopīgais.",
                    ),
                ),
                ListEntry(
                    title = same("Xcode"),
                    body = Copy(
                        en = "For the thin Swift layer around the shared Kotlin framework, and for profiling on a real iPhone.",
                        sl = "Za tanko plast v Swiftu okoli skupnega ogrodja v Kotlinu in za profiliranje na pravem iPhonu.",
                        lv = "Plānajam Swift slānim ap kopīgo Kotlin ietvaru un profilēšanai uz īsta iPhone.",
                    ),
                ),
                ListEntry(
                    title = same("Claude · Cursor"),
                    body = Copy(
                        en = "For reviews, tests and first drafts. Nothing ships that I haven't read.",
                        sl = "Za preglede, teste in prve osnutke. Nič ne gre v produkcijo, česar nisem prebral.",
                        lv = "Pārskatiem, testiem un pirmajiem melnrakstiem. Nekas netiek izlaists, ko neesmu izlasījis.",
                    ),
                ),
                ListEntry(
                    title = same("GitHub Actions · Fastlane"),
                    body = Copy(
                        en = "Builds, tests and store releases for Android and iOS from one pipeline.",
                        sl = "Gradnje, testi in izdaje v trgovinah za Android in iOS iz enega cevovoda.",
                        lv = "Būvējumi, testi un laidieni veikalos Android un iOS no viena konveijera.",
                    ),
                ),
                ListEntry(
                    title = same("Maestro"),
                    body = Copy(
                        en = "End-to-end UI flows that read like a checklist and run on both platforms.",
                        sl = "Celoviti testi vmesnika, ki se berejo kot kontrolni seznam in tečejo na obeh platformah.",
                        lv = "Pilna cikla saskarnes testi, kas lasās kā kontrolsaraksts un darbojas abās platformās.",
                    ),
                ),
            ),
        ),
        ListGroup(
            eyebrow = Copy(en = "// kotlin stack", sl = "// kotlin", lv = "// kotlin"),
            entries = listOf(
                ListEntry(
                    title = same("Kotlin Multiplatform · Compose Multiplatform"),
                    body = Copy(
                        en = "Shared logic everywhere, shared UI where the platform doesn't do it better.",
                        sl = "Skupna logika povsod, skupni vmesnik tam, kjer ga platforma ne naredi bolje.",
                        lv = "Kopīga loģika visur, kopīga saskarne tur, kur platforma to nedara labāk.",
                    ),
                ),
                ListEntry(
                    title = same("Ktor · kotlinx.serialization"),
                    body = Copy(
                        en = "Networking and JSON in common code, with no platform glue.",
                        sl = "Omrežje in JSON v skupni kodi, brez lepila za posamezno platformo.",
                        lv = "Tīkls un JSON kopīgajā kodā bez platformas līmes.",
                    ),
                ),
                ListEntry(
                    title = same("SQLDelight · SQLCipher"),
                    body = Copy(
                        en = "Type-safe SQL that compiles for both platforms, encrypted when the data needs it.",
                        sl = "Tipsko varen SQL, ki se prevede za obe platformi, in šifriran, kadar podatki to zahtevajo.",
                        lv = "Tipdrošs SQL, kas kompilējas abām platformām, un šifrēts, kad dati to prasa.",
                    ),
                ),
                ListEntry(
                    title = same("Koin · kotlin-inject"),
                    body = Copy(
                        en = "Koin when setup speed matters, kotlin-inject when I want the graph checked at compile time.",
                        sl = "Koin, ko šteje hitra postavitev, kotlin-inject, ko želim graf preverjen ob prevajanju.",
                        lv = "Koin, kad svarīga ātra uzstādīšana, kotlin-inject, kad gribu grafu pārbaudītu kompilēšanas laikā.",
                    ),
                ),
                ListEntry(
                    title = same("Decompose · SKIE"),
                    body = Copy(
                        en = "Navigation and lifecycle that behave the same on Android and iOS, and Kotlin flows that feel native in Swift.",
                        sl = "Navigacija in življenjski cikel, ki se na Androidu in iOS obnašata enako, ter tokovi v Kotlinu, ki so v Swiftu videti nativni.",
                        lv = "Navigācija un dzīves cikls, kas Android un iOS uzvedas vienādi, un Kotlin plūsmas, kas Swift valodā šķiet nativas.",
                    ),
                ),
                ListEntry(
                    title = same("Media3 · Readium"),
                    body = Copy(
                        en = "Background audio and EPUB rendering in Parrot.",
                        sl = "Predvajanje zvoka v ozadju in prikaz EPUB v Parrotu.",
                        lv = "Audio atskaņošana fonā un EPUB attēlošana lietotnē Parrot.",
                    ),
                ),
            ),
        ),
        ListGroup(
            eyebrow = Copy(en = "// this site", sl = "// ta stran", lv = "// šī vietne"),
            entries = listOf(
                ListEntry(
                    title = same("Kobweb · Compose for Web"),
                    body = Copy(
                        en = "The whole site is Kotlin, exported to static pages.",
                        sl = "Celotna stran je napisana v Kotlinu in izvožena v statične strani.",
                        lv = "Visa vietne ir Kotlin, eksportēta statiskās lapās.",
                    ),
                ),
                ListEntry(
                    title = same("GitHub Pages · Umami"),
                    body = Copy(
                        en = "Free hosting, and analytics without cookies or personal data.",
                        sl = "Brezplačno gostovanje in analitika brez piškotkov ali osebnih podatkov.",
                        lv = "Bezmaksas mitināšana un analītika bez sīkdatnēm un personas datiem.",
                    ),
                ),
                ListEntry(
                    title = same("Instrument Serif · Geist"),
                    body = Copy(
                        en = "A serif for headlines, a clean sans for reading, and Geist Mono for the small print.",
                        sl = "Serifna pisava za naslove, čista neserifna za branje in Geist Mono za drobni tisk.",
                        lv = "Serifa fonts virsrakstiem, tīrs bezserifa fonts lasīšanai un Geist Mono sīkajai drukai.",
                    ),
                ),
            ),
        ),
    )
}

object MiniPageStrings {
    val updated = Copy(en = "Updated", sl = "Posodobljeno", lv = "Atjaunināts")
    val nowEyebrow = Copy(en = "// what I'm doing", sl = "// s čim se ukvarjam", lv = "// ar ko nodarbojos")
    val usesEyebrow = Copy(en = "// my setup", sl = "// moja oprema", lv = "// mans aprīkojums")
    val nowAbout = Copy(
        en = "This is a now page, an idea from nownownow.com.",
        sl = "To je stran »zdaj«, ideja s spletišča nownownow.com.",
        lv = "Šī ir \"tagad\" lapa, ideja no nownownow.com.",
    )
}
