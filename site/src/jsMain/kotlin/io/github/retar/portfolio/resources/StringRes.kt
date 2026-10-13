package io.github.retar.portfolio.resources

import androidx.compose.runtime.Composable
import io.github.retar.portfolio.Language

sealed class StringRes(val en: String, val sl: String, val lv: String) {
    val value: String
        @Composable get() {
            val language = Language.current
            return when (language) {
                Language.EN -> en
                Language.SL -> sl
                Language.LV -> lv
            }
        }

    data object HeroEyebrow : StringRes(
        en = "// mobile engineer in Slovenia",
        sl = "// mobilni inženir iz Slovenije",
        lv = "// mobilo lietotņu inženieris Slovēnijā"
    )

    data object HeroName : StringRes(
        en = "Rok Retar",
        sl = "Rok Retar",
        lv = "Rok Retar"
    )

    data object HeroHeadline : StringRes(
        en = "I build mobile apps in Kotlin, for Android and iOS.",
        sl = "Razvijam mobilne aplikacije v Kotlinu, za Android in iOS.",
        lv = "Es veidoju mobilās lietotnes ar Kotlin, Android un iOS."
    )

    data object HeroSubtitle : StringRes(
        en = "Android is where I started. Today I take Kotlin Multiplatform into production: one codebase for an app's logic and data, while each platform keeps its own native UI. On the side I build Parrot, an ebook and audiobook reader.",
        sl = "Začel sem z Androidom. Danes Kotlin Multiplatform uvajam v produkcijo: ena kodna baza za logiko in podatke aplikacije, vsaka platforma pa obdrži svoj nativni vmesnik. Ob tem razvijam Parrot, bralnik e-knjig in zvočnih knjig.",
        lv = "Sāku ar Android. Šodien es ieviešu Kotlin Multiplatform ražošanā: viena kodbāze lietotnes loģikai un datiem, bet katra platforma saglabā savu nativo saskarni. Brīvajā laikā veidoju Parrot, e-grāmatu un audiogrāmatu lasītāju."
    )

    data object HeroStatusStrip : StringRes(
        en = "Kotlin · Kotlin Multiplatform · Android · Compose",
        sl = "Kotlin · Kotlin Multiplatform · Android · Compose",
        lv = "Kotlin · Kotlin Multiplatform · Android · Compose"
    )

    data object CtaReviewWork : StringRes(
        en = "See my work",
        sl = "Poglejte moje delo",
        lv = "Apskatīt manu darbu"
    )

    data object CtaDownloadResume : StringRes(
        en = "Download resume",
        sl = "Prenesi življenjepis",
        lv = "Lejupielādēt CV"
    )

    data object CtaReadNotes : StringRes(
        en = "Read my engineering notes",
        sl = "Preberite moje inženirske zapise",
        lv = "Lasīt manas inženiertehniskās piezīmes"
    )

    data object Descriptor : StringRes(
        en = "Mobile engineer",
        sl = "Mobilni inženir",
        lv = "Mobilo lietotņu inženieris"
    )

    data object Github : StringRes(
        en = "GitHub",
        sl = "GitHub",
        lv = "GitHub"
    )

    data object LinkedIn : StringRes(
        en = "LinkedIn",
        sl = "LinkedIn",
        lv = "LinkedIn"
    )

    data object PhilosophyEyebrow : StringRes(
        en = "// how I work",
        sl = "// kako delam",
        lv = "// kā es strādāju"
    )

    data object PhilosophyHeading : StringRes(
        en = "Share what should be shared. Keep native what should feel native.",
        sl = "Deli, kar je smiselno deliti. Nativno pusti, kar mora delovati nativno.",
        lv = "Kopīgo to, kas jākopīgo. Atstāj nativu to, kam jājūtas nativam."
    )

    data object PhilosophyBody1 : StringRes(
        en = "I write business logic, data layers and domain models once in Kotlin and share them between Android and iOS. The UI stays native wherever the platform does it better.",
        sl = "Poslovno logiko, podatkovne plasti in domenske modele napišem enkrat v Kotlinu in jih delim med Androidom in iOS. Uporabniški vmesnik ostane nativen povsod, kjer ga platforma naredi bolje.",
        lv = "Biznesa loģiku, datu slāņus un domēna modeļus es uzrakstu vienreiz Kotlin valodā un kopīgoju starp Android un iOS. Lietotāja saskarne paliek nativa visur, kur platforma to dara labāk."
    )

    data object PhilosophyBody2 : StringRes(
        en = "For a company's production iOS app, I designed and led the move to shared Kotlin. It shipped, and real users run it today.",
        sl = "Za produkcijsko iOS aplikacijo podjetja sem zasnoval in vodil prehod na skupni Kotlin. Izšla je in danes jo uporabljajo resnični uporabniki.",
        lv = "Uzņēmuma ražošanas iOS lietotnei es izstrādāju un vadīju pāreju uz kopīgu Kotlin kodu. Tā ir izlaista, un šodien to lieto īsti lietotāji."
    )

    data object PhilosophyBody3 : StringRes(
        en = "Where the line between shared and native code sits is an engineering decision, not a belief. Module boundaries enforce it: the domain depends on nothing, and data and UI depend on the domain.",
        sl = "Kje poteka meja med skupno in nativno kodo, je inženirska odločitev, ne prepričanje. Uveljavljajo jo meje modulov: domena ni odvisna od ničesar, podatki in vmesnik pa so odvisni od domene.",
        lv = "Kur atrodas robeža starp kopīgo un nativo kodu, ir inženiertehnisks lēmums, nevis pārliecība. To nodrošina moduļu robežas: domēns nav atkarīgs ne no kā, bet dati un saskarne ir atkarīgi no domēna."
    )

    data object PhilosophyPrinciple1 : StringRes(
        en = "Share the domain and data layers; keep the UI native where the platform does it better.",
        sl = "Deli domensko in podatkovno plast; vmesnik naj ostane nativen, kjer ga platforma naredi bolje.",
        lv = "Kopīgo domēna un datu slāņus; saskarni atstāj nativu, kur platforma to dara labāk."
    )

    data object PhilosophyPrinciple2 : StringRes(
        en = "Let module boundaries enforce dependency direction, so the domain stays plain Kotlin.",
        sl = "Naj meje modulov določajo smer odvisnosti, da domena ostane čisti Kotlin.",
        lv = "Ļauj moduļu robežām noteikt atkarību virzienu, lai domēns paliek tīrs Kotlin."
    )

    data object PhilosophyPrinciple3 : StringRes(
        en = "Ship to production, not to a demo branch.",
        sl = "Izdaj v produkcijo, ne v demo vejo.",
        lv = "Izlaid ražošanā, nevis demonstrācijas zarā."
    )

    data object PhilosophyPrinciple4 : StringRes(
        en = "Use Kotlin wherever it fits, including this website.",
        sl = "Uporabi Kotlin povsod, kjer je smiselno, tudi za to spletno stran.",
        lv = "Izmanto Kotlin visur, kur tas der, arī šai vietnei."
    )

    data object MigrationCalloutLabel : StringRes(
        en = "// shipped",
        sl = "// izdano",
        lv = "// izlaists"
    )

    data object MigrationCalloutBody : StringRes(
        en = "A company's production iOS app now runs on shared Kotlin, packaged as a static framework the iOS team added without changing their build.",
        sl = "Produkcijska iOS aplikacija podjetja zdaj teče na skupnem Kotlinu, zapakiranem v statično ogrodje, ki ga je ekipa iOS dodala brez sprememb v svoji gradnji.",
        lv = "Uzņēmuma ražošanas iOS lietotne tagad darbojas uz kopīga Kotlin koda, iepakota statiskā ietvarā, ko iOS komanda pievienoja, nemainot savu būvēšanas procesu."
    )

    data object ParrotEyebrow : StringRes(
        en = "// my own app",
        sl = "// moja aplikacija",
        lv = "// mana lietotne"
    )

    data object ParrotHeading : StringRes(
        en = "Parrot",
        sl = "Parrot",
        lv = "Parrot"
    )

    data object ParrotSubhead : StringRes(
        en = "A reader for your own ebooks and audiobooks on Android phones, tablets and e-ink readers. It highlights the text as the narrator reads and remembers where you are in every version of a book. Built with Kotlin Multiplatform; an iPhone version is in the works.",
        sl = "Bralnik za lastne e-knjige in zvočne knjige na telefonih, tablicah in bralnikih e-ink z Androidom. Med pripovedovanjem označuje besedilo in si zapomni, kje ste v vsaki različici knjige. Zgrajen s Kotlin Multiplatform; različica za iPhone je v pripravi.",
        lv = "Lasītājs jūsu e-grāmatām un audiogrāmatām Android tālruņos, planšetēs un e-ink lasītājos. Tas izceļ tekstu, kamēr diktors lasa, un atceras, kur esat katrā grāmatas versijā. Veidots ar Kotlin Multiplatform; iPhone versija top."
    )

    data object ParrotFeature1Title : StringRes(
        en = "Read-along",
        sl = "Branje s poslušanjem",
        lv = "Lasīšana kopā ar audio"
    )

    data object ParrotFeature1Desc : StringRes(
        en = "For ebooks with synced narration, such as those made with Storyteller, each sentence lights up as it is read.",
        sl = "Pri e-knjigah s sinhronizirano pripovedjo, na primer tistih iz Storytellerja, se vsak stavek osvetli, ko je prebran.",
        lv = "E-grāmatām ar sinhronizētu stāstījumu, piemēram, no Storyteller, katrs teikums iedegas, kad tas tiek nolasīts."
    )

    data object ParrotFeature2Title : StringRes(
        en = "Voices on your device",
        sl = "Glasovi na napravi",
        lv = "Balsis jūsu ierīcē"
    )

    data object ParrotFeature2Desc : StringRes(
        en = "No audiobook? A downloaded voice reads any ebook aloud. Chapters are prepared ahead so playback has no gaps, and the text never leaves the phone.",
        sl = "Ni zvočne knjige? Prenesen glas prebere katero koli e-knjigo. Poglavja se pripravijo vnaprej, zato predvajanje teče brez premorov, besedilo pa ne zapusti telefona.",
        lv = "Nav audiogrāmatas? Lejupielādēta balss nolasa jebkuru e-grāmatu. Nodaļas tiek sagatavotas iepriekš, lai atskaņošana būtu bez pauzēm, un teksts nepamet tālruni."
    )

    data object ParrotFeature3Title : StringRes(
        en = "One place in every version",
        sl = "Isto mesto v vsaki različici",
        lv = "Viena vieta katrā versijā"
    )

    data object ParrotFeature3Desc : StringRes(
        en = "Link the ebook and the audiobook of a book and Parrot shows where you are in each, then moves the others to the matching place when you ask.",
        sl = "Povežite e-knjigo in zvočno knjigo in Parrot pokaže, kje ste v vsaki, ter na zahtevo premakne ostale na ustrezno mesto.",
        lv = "Sasaistiet grāmatas e-grāmatu un audiogrāmatu, un Parrot parāda, kur esat katrā no tām, un pēc pieprasījuma pārvieto pārējās uz atbilstošo vietu."
    )

    data object ParrotFeature4Title : StringRes(
        en = "Your library, your server",
        sl = "Vaša knjižnica, vaš strežnik",
        lv = "Jūsu bibliotēka, jūsu serveris"
    )

    data object ParrotFeature4Desc : StringRes(
        en = "Connect the Storyteller or Audiobookshelf server you already run, or browse OPDS catalogues such as Project Gutenberg. Books go straight from the server to the device.",
        sl = "Povežite strežnik Storyteller ali Audiobookshelf, ki ga že uporabljate, ali brskajte po katalogih OPDS, kot je Project Gutenberg. Knjige gredo neposredno s strežnika na napravo.",
        lv = "Pievienojiet savu Storyteller vai Audiobookshelf serveri vai pārlūkojiet OPDS katalogus, piemēram, Project Gutenberg. Grāmatas nonāk tieši no servera ierīcē."
    )

    data object ParrotFeature5Title : StringRes(
        en = "Highlights, notes and a dictionary",
        sl = "Poudarki, opombe in slovar",
        lv = "Izcēlumi, piezīmes un vārdnīca"
    )

    data object ParrotFeature5Desc : StringRes(
        en = "Four highlight colours, notes in the margin and one list of everything you marked, exportable as Markdown. Tap a word for an offline definition.",
        sl = "Štiri barve poudarkov, opombe ob robu in en seznam vsega, kar ste označili, z izvozom v Markdown. Tapnite besedo za razlago brez povezave.",
        lv = "Četras izcēlumu krāsas, piezīmes malās un viens saraksts ar visu atzīmēto, ko var eksportēt kā Markdown. Pieskarieties vārdam, lai redzētu skaidrojumu bezsaistē."
    )

    data object ParrotFeature6Title : StringRes(
        en = "Parrot Cloud",
        sl = "Parrot Cloud",
        lv = "Parrot Cloud"
    )

    data object ParrotFeature6Desc : StringRes(
        en = "No server? Parrot Cloud keeps your books, reading position, highlights and statistics in sync on every device you sign in to. In early access.",
        sl = "Nimate strežnika? Parrot Cloud sinhronizira vaše knjige, mesto branja, poudarke in statistiko na vseh napravah, v katere se prijavite. V zgodnjem dostopu.",
        lv = "Nav servera? Parrot Cloud sinhronizē jūsu grāmatas, lasīšanas vietu, izcēlumus un statistiku visās ierīcēs, kurās piesakāties. Agrīnajā piekļuvē."
    )

    data object ParrotFeature7Title : StringRes(
        en = "Made for e-ink",
        sl = "Narejeno za e-ink",
        lv = "Radīts e-ink ekrāniem"
    )

    data object ParrotFeature7Desc : StringRes(
        en = "A black-and-white theme with no animation, clear outlines and larger type, for e-readers that run Android.",
        sl = "Črno-bela tema brez animacij, z jasnimi obrobami in večjo pisavo, za bralnike z Androidom.",
        lv = "Melnbalta tēma bez animācijām, ar skaidrām kontūrām un lielāku burtu izmēru e-lasītājiem ar Android."
    )

    data object ParrotFeature8Desc : StringRes(
        en = "Time read and listened by day and by book, streaks and every session, computed on the device and synced through Parrot Cloud.",
        sl = "Čas branja in poslušanja po dnevih in knjigah, nizi in vsaka seja, izračunani na napravi in sinhronizirani prek Parrot Cloud.",
        lv = "Lasīšanas un klausīšanās laiks pa dienām un grāmatām, sērijas un katra sesija, aprēķināti ierīcē un sinhronizēti caur Parrot Cloud."
    )

    data object ParrotFeature8Title : StringRes(
        en = "Reading statistics",
        sl = "Statistika branja",
        lv = "Lasīšanas statistika"
    )

    data object CtaReadNote : StringRes(
        en = "Read the note",
        sl = "Preberi zapis",
        lv = "Lasīt piezīmi"
    )

    data object CtaOpenCaseStudy : StringRes(
        en = "Read the Parrot case study",
        sl = "Preberite študijo primera Parrot",
        lv = "Lasīt Parrot gadījuma izpēti"
    )

    data object NotesEyebrow : StringRes(
        en = "// writing",
        sl = "// pisanje",
        lv = "// raksti"
    )

    data object NotesTitle : StringRes(
        en = "Engineering notes",
        sl = "Inženirske zabeležke",
        lv = "Inženierijas piezīmes"
    )

    data object NotesSubtitle : StringRes(
        en = "Write-ups of problems I solved in Kotlin, Kotlin Multiplatform and Kobweb, with the code.",
        sl = "Zapisi o problemih, ki sem jih rešil v Kotlinu, Kotlin Multiplatform in Kobwebu, skupaj s kodo.",
        lv = "Apraksti par problēmām, ko atrisināju ar Kotlin, Kotlin Multiplatform un Kobweb, kopā ar kodu."
    )

    data object ContactEyebrow : StringRes(
        en = "// contact",
        sl = "// kontakt",
        lv = "// kontakti"
    )

    data object ContactHeading : StringRes(
        en = "Have a Kotlin Multiplatform project in mind?",
        sl = "Načrtujete projekt s Kotlin Multiplatform?",
        lv = "Plānojat Kotlin Multiplatform projektu?"
    )

    data object ContactBody : StringRes(
        en = "I'm open to senior mobile engineering roles, Kotlin Multiplatform migrations and Android architecture work. Email is the quickest way to reach me.",
        sl = "Odprt sem za vodilne vloge v mobilnem razvoju, selitve na Kotlin Multiplatform in arhitekturo Android. Najhitreje me dosežete po e-pošti.",
        lv = "Esmu atvērts vecākā mobilo lietotņu inženiera lomām, Kotlin Multiplatform migrācijām un Android arhitektūras darbam. Ātrāk mani sazināt pa e-pastu."
    )

    data object CtaEmail : StringRes(
        en = "Email me",
        sl = "Pišite mi",
        lv = "Rakstiet man"
    )

    data object FooterLinks : StringRes(
        en = "GitHub · LinkedIn · rok.retar@gmail.com",
        sl = "GitHub · LinkedIn · rok.retar@gmail.com",
        lv = "GitHub · LinkedIn · rok.retar@gmail.com"
    )

    data object FooterMeta : StringRes(
        en = "Built in Kotlin with Kobweb",
        sl = "Zgrajeno v Kotlinu s Kobwebom",
        lv = "Veidots ar Kotlin un Kobweb"
    )

    data object HeaderTitle : StringRes(
        en = "Rok Retar",
        sl = "Rok Retar",
        lv = "Rok Retar"
    )

    data object NavAbout : StringRes(
        en = "About",
        sl = "O meni",
        lv = "Par mani"
    )

    data object NavPhilosophy : StringRes(
        en = "How I work",
        sl = "Kako delam",
        lv = "Kā strādāju"
    )

    data object NavProjects : StringRes(
        en = "Projects",
        sl = "Projekti",
        lv = "Projekti"
    )

    data object NavBlog : StringRes(
        en = "Notes",
        sl = "Zabeležke",
        lv = "Piezīmes"
    )

    data object NavContact : StringRes(
        en = "Contact",
        sl = "Kontakt",
        lv = "Kontakti"
    )

    data class FooterCopyright(val year: Int) : StringRes(
        en = "© $year Rok Retar",
        sl = "© $year Rok Retar",
        lv = "© $year Rok Retar"
    )

    data object ProfileImageDesc : StringRes(
        en = "Portrait of Rok Retar",
        sl = "Portret Roka Retarja",
        lv = "Roka Retara portrets"
    )

    data object ParrotScreenshot1Desc : StringRes(
        en = "The reader with a highlighted passage and a note in the margin, in the day theme",
        sl = "Bralnik s poudarjenim odlomkom in opombo ob robu v dnevni temi",
        lv = "Lasītājs ar izceltu fragmentu un piezīmi malā dienas tēmā"
    )

    data object ParrotScreenshot2Desc : StringRes(
        en = "Listening with an on-device voice: sentence controls, voice, rate and sleep timer",
        sl = "Poslušanje z glasom na napravi: krmiljenje po stavkih, glas, hitrost in časovnik za spanje",
        lv = "Klausīšanās ar ierīces balsi: teikumu vadība, balss, ātrums un miega taimeris"
    )

    data object ParrotScreenshot3Desc : StringRes(
        en = "Reading positions across the ebook and audiobook versions of one book",
        sl = "Mesta branja v e-knjigi in zvočni knjigi iste knjige",
        lv = "Lasīšanas vietas vienas grāmatas e-grāmatas un audiogrāmatas versijās"
    )

    data object ParrotScreenshot4Desc : StringRes(
        en = "A short recap of where you stopped last time, shown when you return to a book",
        sl = "Kratek povzetek, kje ste nazadnje končali, ko se vrnete h knjigi",
        lv = "Īss kopsavilkums par to, kur pēdējo reizi apstājāties, kad atgriežaties pie grāmatas"
    )

    data object ParrotScreenshot5Desc : StringRes(
        en = "Browsing the Project Gutenberg OPDS catalogue",
        sl = "Brskanje po katalogu OPDS Project Gutenberg",
        lv = "Project Gutenberg OPDS kataloga pārlūkošana"
    )

    data object ReadAlongArticleTitle : StringRes(
        en = "Highlighting the sentence the narrator is reading: EPUB read-along with Media3",
        sl = "Označevanje stavka, ki ga bere pripovedovalec: sočasno branje EPUB z Media3",
        lv = "Teikuma izcelšana, ko lasa diktors: EPUB lasīšana līdzi ar Media3"
    )

    data object ReadAlongArticleDesc : StringRes(
        en = "How Parrot keeps text and narration in step with SMIL media overlays, turns the page mid-sentence at the right moment, and the Media3 update that silently killed playback.",
        sl = "Kako Parrot z medijskimi prekrivanji SMIL usklajuje besedilo in pripoved, obrne stran sredi stavka ob pravem trenutku, in posodobitev Media3, ki je tiho ustavila predvajanje.",
        lv = "Kā Parrot ar SMIL multivides pārklājumiem saskaņo tekstu un ierakstu, pāršķir lappusi teikuma vidū īstajā brīdī, un Media3 atjauninājums, kas klusi apturēja atskaņošanu."
    )

    data object PositionSyncArticleTitle : StringRes(
        en = "Never lose someone's place: syncing reading positions across devices and formats",
        sl = "Nikoli ne izgubi bralčevega mesta: usklajevanje položaja branja med napravami in formati",
        lv = "Nekad nepazaudēt lasītāja vietu: lasīšanas pozīciju sinhronizācija starp ierīcēm un formātiem"
    )

    data object PositionSyncArticleDesc : StringRes(
        en = "Local-first writes, compare-and-set revisions, when to settle a conflict and when to ask, and moving a position between an ebook and its audiobook.",
        sl = "Lokalni zapisi najprej, revizije s primerjavo in zamenjavo, kdaj spor rešiti samodejno in kdaj vprašati, ter prenos položaja med e-knjigo in zvočno knjigo.",
        lv = "Vispirms lokāli ieraksti, salīdzināšanas un aizstāšanas revīzijas, kad konfliktu atrisināt pašam un kad jautāt, un pozīcijas pārnešana starp e-grāmatu un audiogrāmatu."
    )

    data object OpdsArticleTitle : StringRes(
        en = "Every catalogue is hostile: an OPDS client in Kotlin Multiplatform",
        sl = "Vsak katalog je sovražen: odjemalec OPDS v Kotlin Multiplatform",
        lv = "Katrs katalogs ir naidīgs: OPDS klients Kotlin Multiplatform"
    )

    data object OpdsArticleDesc : StringRes(
        en = "Parsing two feed formats in shared Kotlin, keeping passwords on their origin, hard budgets for every response, and the nine bugs a security review found.",
        sl = "Razčlenjevanje dveh formatov virov v skupni kodi Kotlin, gesla ostanejo na svojem izvoru, trde omejitve za vsak odgovor in devet napak, ki jih je našel varnostni pregled.",
        lv = "Divu plūsmu formātu parsēšana koplietotā Kotlin kodā, paroles paliek savā izcelsmē, stingri limiti katrai atbildei un deviņas kļūdas, ko atrada drošības pārbaude."
    )

    data object OnDeviceTtsArticleTitle : StringRes(
        en = "Neural voices on an e-ink reader: on-device TTS with sherpa-onnx",
        sl = "Nevronski glasovi na bralniku e-ink: TTS na napravi s sherpa-onnx",
        lv = "Neironu balsis e-tintes lasītājā: runas sintēze ierīcē ar sherpa-onnx"
    )

    data object OnDeviceTtsArticleDesc : StringRes(
        en = "How Parrot reads any ebook aloud with Kokoro and Supertonic running on the phone, prepares chapters ahead, and survived a lambda that crashed every synthesis.",
        sl = "Kako Parrot z modeloma Kokoro in Supertonic na telefonu bere katerokoli e-knjigo, vnaprej pripravi poglavja in preživel lambdo, ki je zrušila vsako sintezo.",
        lv = "Kā Parrot ar Kokoro un Supertonic tālrunī nolasa jebkuru e-grāmatu, iepriekš sagatavo nodaļas un pārdzīvoja lambda funkciju, kas avarēja katrā sintēzē."
    )

    data object AppsFlyerArticleTitle : StringRes(
        en = "Wrapping the AppsFlyer SDK into a Kotlin Multiplatform library",
        sl = "Wrapping the AppsFlyer SDK into a Kotlin Multiplatform library",
        lv = "Wrapping the AppsFlyer SDK into a Kotlin Multiplatform library"
    )

    data object AppsFlyerArticleDesc : StringRes(
        en = "A coroutine-first, expect/actual wrapper around the AppsFlyer SDKs: one Kotlin API, two native delegates, no callbacks.",
        sl = "Ovoj okoli SDK-jev AppsFlyer z expect/actual, zasnovan na korutinah: en Kotlin API, dva nativna delegata, brez povratnih klicev.",
        lv = "Uz korutīnām balstīts expect/actual apvalks AppsFlyer SDK: viens Kotlin API, divi nativi delegāti, bez atzvaniem."
    )

    data object InfiniteCarouselArticleTitle : StringRes(
        en = "Building a smooth, infinite carousel in Kobweb",
        sl = "Gladek, neskončen vrtiljak v Kobwebu",
        lv = "Gluda, bezgalīga karuseļa izveide ar Kobweb"
    )

    data object InfiniteCarouselArticleDesc : StringRes(
        en = "How to build a gapless, endlessly looping carousel with Kobweb and Silk.",
        sl = "Kako s Kobwebom in Silkom zgraditi vrtiljak brez vrzeli, ki se neskončno ponavlja.",
        lv = "Kā ar Kobweb un Silk izveidot karuseli bez spraugām, kas griežas bezgalīgi."
    )

    data object SQLCipherArticleTitle : StringRes(
        en = "Shipping SQLCipher inside a static Kotlin/Native framework (so the iOS team changed nothing)",
        sl = "Shipping SQLCipher inside a static Kotlin/Native framework (so the iOS team changed nothing)",
        lv = "Shipping SQLCipher inside a static Kotlin/Native framework (so the iOS team changed nothing)"
    )

    data object SQLCipherArticleDesc : StringRes(
        en = "Baking SQLCipher into a static Kotlin/Native framework, so the iOS team adds nothing, not even a linker flag.",
        sl = "Kako SQLCipher vgraditi v statično ogrodje Kotlin/Native, da ekipi iOS ni treba dodati ničesar, niti zastavice povezovalnika.",
        lv = "Kā iebūvēt SQLCipher statiskā Kotlin/Native ietvarā, lai iOS komandai nekas nav jāpievieno, pat ne saistītāja karogs."
    )

    data object ParrotCaseStudyEyebrow : StringRes(
        en = "// case study",
        sl = "// študija primera",
        lv = "// gadījuma izpēte"
    )

    data object ParrotWhatItDoes : StringRes(
        en = "// what it does",
        sl = "// kaj zmore",
        lv = "// ko tā dara"
    )

    data object ParrotLatestRelease : StringRes(
        en = "// latest release",
        sl = "// zadnja različica",
        lv = "// jaunākā versija"
    )

    data object ParrotMeta : StringRes(
        en = "Android · Free · Kotlin Multiplatform",
        sl = "Android · Brezplačno · Kotlin Multiplatform",
        lv = "Android · Bez maksas · Kotlin Multiplatform"
    )

    data object ParrotTourEyebrow : StringRes(
        en = "// the short version",
        sl = "// na kratko",
        lv = "// īsumā"
    )

    data object ParrotTourCaption : StringRes(
        en = "Parrot in 20 seconds: read-along, moving your place between versions, and connecting your library. The video has no sound.",
        sl = "Parrot v 20 sekundah: branje s poudarjanjem, premik mesta med različicami in povezava knjižnice. Video nima zvoka.",
        lv = "Parrot 20 sekundēs: lasīšana ar izcēlumu, vietas pārnešana starp versijām un bibliotēkas pievienošana. Video nav skaņas."
    )

    data object ParrotTourFallback : StringRes(
        en = "Watch the 20-second tour (MP4)",
        sl = "Oglejte si 20-sekundni ogled (MP4)",
        lv = "Skatīt 20 sekunžu apskatu (MP4)"
    )

    data object ArticleBack : StringRes(
        en = "All notes",
        sl = "Vsi zapisi",
        lv = "Visas piezīmes"
    )

    data class ArticleMinutes(val minutes: Int) : StringRes(
        en = "$minutes min read",
        sl = "$minutes min branja",
        lv = "$minutes min lasīšanai"
    )

    data object ArticleNext : StringRes(
        en = "Next note",
        sl = "Naslednji zapis",
        lv = "Nākamā piezīme"
    )

    data object ArticleSectionLink : StringRes(
        en = "Copy link to this section",
        sl = "Kopiraj povezavo do tega razdelka",
        lv = "Kopēt saiti uz šo sadaļu"
    )

    data object ArticleLinkCopied : StringRes(
        en = "Link copied",
        sl = "Povezava kopirana",
        lv = "Saite nokopēta"
    )

    data object ArticleFeed : StringRes(
        en = "RSS feed",
        sl = "Vir RSS",
        lv = "RSS plūsma"
    )

    data object SkipToContent : StringRes(
        en = "Skip to content",
        sl = "Preskoči na vsebino",
        lv = "Pāriet uz saturu"
    )

    data object ParrotIconDesc : StringRes(
        en = "Parrot app icon",
        sl = "Ikona aplikacije Parrot",
        lv = "Parrot lietotnes ikona"
    )

    data object ParrotPageHeading : StringRes(
        en = "Parrot",
        sl = "Parrot",
        lv = "Parrot"
    )

    data object ParrotPageSubhead : StringRes(
        en = "A reader for your own ebooks and audiobooks on Android phones, tablets and e-ink readers. It highlights the text as the narrator reads, can read any ebook aloud with a voice on your device, and remembers where you are in every version of a book. Built with Kotlin Multiplatform; an iPhone version is in the works.",
        sl = "Bralnik za lastne e-knjige in zvočne knjige na telefonih, tablicah in bralnikih e-ink z Androidom. Med pripovedovanjem označuje besedilo, katero koli e-knjigo lahko prebere z glasom na napravi in si zapomni, kje ste v vsaki različici knjige. Zgrajen s Kotlin Multiplatform; različica za iPhone je v pripravi.",
        lv = "Lasītājs jūsu e-grāmatām un audiogrāmatām Android tālruņos, planšetēs un e-ink lasītājos. Tas izceļ tekstu, kamēr diktors lasa, var nolasīt jebkuru e-grāmatu ar balsi jūsu ierīcē un atceras, kur esat katrā grāmatas versijā. Veidots ar Kotlin Multiplatform; iPhone versija top."
    )

    data object ParrotDownloadSection : StringRes(
        en = "Download",
        sl = "Prenesi",
        lv = "Lejupielādēt"
    )

    data class ParrotDownloadLatest(val version: String) : StringRes(
        en = "Download v$version (APK)",
        sl = "Prenesi v$version (APK)",
        lv = "Lejupielādēt v$version (APK)"
    )

    data object ParrotViewHistory : StringRes(
        en = "View version history",
        sl = "Prikaži zgodovino različic",
        lv = "Skatīt versiju vēsturi"
    )

    data object ParrotFeature1PageTitle : StringRes(
        en = "Reading your way",
        sl = "Branje po vaše",
        lv = "Lasīšana jūsu veidā"
    )

    data object ParrotFeature1PageDesc : StringRes(
        en = "Choose the font, weight, size, margins and line height, and Parrot keeps them for every book. Day, night and e-ink themes cover the rest; the e-ink theme drops animation and uses clear outlines and larger type.\n\nTap the screen edges or use the volume buttons to turn pages, search inside the book, jump from the contents, and tap any word for an offline dictionary definition. Highlights in four colours and notes in the margin collect in one list you can export as Markdown.",
        sl = "Izberite pisavo, debelino, velikost, robove in razmik med vrsticami, Parrot pa jih ohrani za vsako knjigo. Za ostalo poskrbijo dnevna, nočna in e-ink tema; tema e-ink opusti animacije ter uporablja jasne obrobe in večjo pisavo.\n\nStrani obračate s tapom ob robu zaslona ali z gumbi za glasnost, iščete po knjigi, skočite iz kazala in tapnete katero koli besedo za razlago v slovarju brez povezave. Poudarki v štirih barvah in opombe ob robu se zberejo v enem seznamu, ki ga lahko izvozite v Markdown.",
        lv = "Izvēlieties fontu, biezumu, izmēru, malas un rindstarpu, un Parrot tos saglabā katrai grāmatai. Pārējo nodrošina dienas, nakts un e-ink tēmas; e-ink tēma atsakās no animācijām un izmanto skaidras kontūras un lielāku burtu izmēru.\n\nPāršķiriet lapas, pieskaroties ekrāna malām vai ar skaļuma pogām, meklējiet grāmatā, pārejiet no satura rādītāja un pieskarieties jebkuram vārdam, lai bezsaistē redzētu vārdnīcas skaidrojumu. Izcēlumi četrās krāsās un piezīmes malās tiek apkopoti vienā sarakstā, ko var eksportēt kā Markdown."
    )

    data object ParrotFeature2PageTitle : StringRes(
        en = "Listen, or have it read to you",
        sl = "Poslušajte ali naj vam prebere",
        lv = "Klausieties vai ļaujiet nolasīt"
    )

    data object ParrotFeature2PageDesc : StringRes(
        en = "For ebooks with synced narration, such as those made with Storyteller, each sentence lights up as it is read, and you can switch between reading and listening without losing your place. Audio keeps playing in the background, with chapter controls in the notification and on Android Auto, plus a sleep timer.\n\nNo audiobook? Download a voice and Parrot reads any ebook aloud. Chapters are prepared ahead so playback has no gaps, and the text is processed on the device.",
        sl = "Pri e-knjigah s sinhronizirano pripovedjo, na primer tistih iz Storytellerja, se vsak stavek osvetli, ko je prebran, med branjem in poslušanjem pa preklapljate, ne da bi izgubili mesto. Zvok se predvaja v ozadju, poglavja upravljate iz obvestila in prek Android Auto, na voljo je tudi časovnik za spanje.\n\nNimate zvočne knjige? Prenesite glas in Parrot bo prebral katero koli e-knjigo. Poglavja se pripravijo vnaprej, zato predvajanje teče brez premorov, besedilo pa se obdela na napravi.",
        lv = "E-grāmatām ar sinhronizētu stāstījumu, piemēram, no Storyteller, katrs teikums iedegas, kad tas tiek nolasīts, un jūs varat pārslēgties starp lasīšanu un klausīšanos, nezaudējot vietu. Audio turpina skanēt fonā, nodaļas var pārslēgt paziņojumā un Android Auto, un ir arī miega taimeris.\n\nNav audiogrāmatas? Lejupielādējiet balsi, un Parrot nolasīs jebkuru e-grāmatu. Nodaļas tiek sagatavotas iepriekš, lai atskaņošana būtu bez pauzēm, un teksts tiek apstrādāts ierīcē."
    )

    data object ParrotFeature3PageTitle : StringRes(
        en = "Reading statistics",
        sl = "Statistika branja",
        lv = "Lasīšanas statistika"
    )

    data object ParrotFeature3PageDesc : StringRes(
        en = "See how long you read and listened, by day and by book, with streaks and a record of every session. When you come back to a book, an optional recap reminds you where you stopped.",
        sl = "Oglejte si, koliko časa ste brali in poslušali, po dnevih in po knjigah, z nizi in zapisom vsake seje. Ko se vrnete h knjigi, vas neobvezni povzetek spomni, kje ste končali.",
        lv = "Redziet, cik ilgi lasījāt un klausījāties, pa dienām un pa grāmatām, ar sērijām un katras sesijas ierakstu. Kad atgriežaties pie grāmatas, neobligāts kopsavilkums atgādina, kur apstājāties."
    )

    data object ParrotFeature4PageTitle : StringRes(
        en = "Your library, on every device",
        sl = "Vaša knjižnica na vseh napravah",
        lv = "Jūsu bibliotēka visās ierīcēs"
    )

    data object ParrotFeature4PageDesc : StringRes(
        en = "Connect the Storyteller or Audiobookshelf server you already run, or browse OPDS catalogues such as Project Gutenberg. No server? Parrot Cloud keeps your books, place, highlights and notes on every device you sign in to.\n\nLink the ebook and audiobook of the same book and Parrot shows where you are in each, then moves the others to the matching place when you ask. Downloaded books work offline, and your place syncs the next time you are online.",
        sl = "Povežite strežnik Storyteller ali Audiobookshelf, ki ga že uporabljate, ali brskajte po katalogih OPDS, kot je Project Gutenberg. Nimate strežnika? Parrot Cloud ohrani vaše knjige, mesto branja, poudarke in opombe na vseh napravah, v katere se prijavite.\n\nPovežite e-knjigo in zvočno knjigo iste knjige in Parrot pokaže, kje ste v vsaki, ter na zahtevo premakne ostale na ustrezno mesto. Prenesene knjige delujejo brez povezave, mesto branja pa se sinhronizira, ko ste spet na spletu.",
        lv = "Pievienojiet savu Storyteller vai Audiobookshelf serveri vai pārlūkojiet OPDS katalogus, piemēram, Project Gutenberg. Nav servera? Parrot Cloud saglabā jūsu grāmatas, vietu, izcēlumus un piezīmes visās ierīcēs, kurās piesakāties.\n\nSasaistiet vienas grāmatas e-grāmatu un audiogrāmatu, un Parrot parāda, kur esat katrā, un pēc pieprasījuma pārvieto pārējās uz atbilstošo vietu. Lejupielādētās grāmatas darbojas bezsaistē, un vieta tiek sinhronizēta, kad atkal esat tiešsaistē."
    )

    data object VersionHistory : StringRes(
        en = "Version history",
        sl = "Zgodovina različic",
        lv = "Versiju vēsture"
    )

    data class VersionWithDate(val version: String, val date: String) : StringRes(
        en = "Version $version ($date)",
        sl = "Različica $version ($date)",
        lv = "Versija $version ($date)"
    )

    data object NewFeatures : StringRes(
        en = "New features",
        sl = "Nove funkcije",
        lv = "Jaunas funkcijas"
    )

    data object BugFixes : StringRes(
        en = "Bug fixes",
        sl = "Popravki napak",
        lv = "Kļūdu labojumi"
    )

    data object Improvements : StringRes(
        en = "Improvements",
        sl = "Izboljšave",
        lv = "Uzlabojumi"
    )

    data object NewFeaturesLabel : StringRes(
        en = "New features:",
        sl = "Nove funkcije:",
        lv = "Jaunas funkcijas:"
    )

    data object BugFixesLabel : StringRes(
        en = "Bug fixes:",
        sl = "Popravki napak:",
        lv = "Kļūdu labojumi:"
    )

    data object ImprovementsLabel : StringRes(
        en = "Improvements:",
        sl = "Izboljšave:",
        lv = "Uzlabojumi:"
    )

    data class VersionLabel(val version: String) : StringRes(
        en = "Version $version",
        sl = "Različica $version",
        lv = "Versija $version"
    )

    data object DownloadApk : StringRes(
        en = "Download APK",
        sl = "Prenesi APK",
        lv = "Lejupielādēt APK"
    )

    data class DownloadVersionApk(val version: String) : StringRes(
        en = "Download v$version APK",
        sl = "Prenesi v$version APK",
        lv = "Lejupielādēt v$version APK"
    )

    data object EmailAddress : StringRes(
        en = "rok.retar@gmail.com",
        sl = "rok.retar@gmail.com",
        lv = "rok.retar@gmail.com"
    )

    data object ExperienceEyebrow : StringRes(
        en = "// experience",
        sl = "// izkušnje",
        lv = "// pieredze"
    )

    data object ExperienceStackTitle : StringRes(
        en = "The tools I use every day",
        sl = "Orodja, ki jih uporabljam vsak dan",
        lv = "Rīki, ko lietoju katru dienu"
    )

    data object ExperienceStackBody : StringRes(
        en = "My daily stack is Kotlin, Jetpack Compose, Compose Multiplatform, Ktor, Koin, Decompose, SQLDelight with SQLCipher, and SKIE for Swift interop.\n\nI build multi-module apps with strict dependency rules: the domain is plain Kotlin, data layers handle storage and networking, and the UI is native where that matters. This site is written in Kotlin too, with Kobweb and Compose for Web.",
        sl = "Moj vsakdanji nabor je Kotlin, Jetpack Compose, Compose Multiplatform, Ktor, Koin, Decompose, SQLDelight s SQLCipherjem in SKIE za povezavo s Swiftom.\n\nGradim večmodulne aplikacije s strogimi pravili odvisnosti: domena je čisti Kotlin, podatkovne plasti skrbijo za shranjevanje in omrežje, vmesnik pa je nativen tam, kjer je to pomembno. Tudi ta stran je napisana v Kotlinu, s Kobwebom in Compose for Web.",
        lv = "Mans ikdienas komplekts ir Kotlin, Jetpack Compose, Compose Multiplatform, Ktor, Koin, Decompose, SQLDelight ar SQLCipher un SKIE sadarbībai ar Swift.\n\nEs veidoju daudzmoduļu lietotnes ar stingriem atkarību noteikumiem: domēns ir tīrs Kotlin, datu slāņi rūpējas par glabāšanu un tīklu, bet saskarne ir nativa tur, kur tas ir svarīgi. Arī šī vietne ir rakstīta Kotlin valodā ar Kobweb un Compose for Web."
    )

    data object ExperienceMigrationTitle : StringRes(
        en = "Production apps I've shipped",
        sl = "Produkcijske aplikacije, ki sem jih izdal",
        lv = "Ražošanas lietotnes, ko esmu izlaidis"
    )

    data object ExperienceMigrationBody : StringRes(
        en = "The biggest was moving a company's production iOS app to shared Kotlin. I designed the architecture and built the shared code: business logic, domain models and SQLCipher-encrypted data layers, compiled into a static Kotlin/Native framework. The iOS team added it without a single new linker flag.\n\nI've also shipped an AI platform with real-time chat, timelines and push notifications; a gaming app with NFC, biometric sign-in, cashless payments and white-label builds; and Parrot. Each used Kotlin Multiplatform differently, from shared networking and database layers to fully shared Compose UI.",
        sl = "Največji projekt je bil prehod produkcijske iOS aplikacije podjetja na skupni Kotlin. Zasnoval sem arhitekturo in napisal skupno kodo: poslovno logiko, domenske modele in podatkovne plasti, šifrirane s SQLCipherjem, prevedene v statično ogrodje Kotlin/Native. Ekipa iOS ga je dodala brez ene same nove zastavice povezovalnika.\n\nIzdal sem tudi platformo z umetno inteligenco s klepetom v realnem času, časovnicami in potisnimi obvestili; igralniško aplikacijo z NFC, biometrično prijavo, brezgotovinskim plačevanjem in različicami pod tujo blagovno znamko; ter Parrot. Vsaka je Kotlin Multiplatform uporabila drugače, od skupnih omrežnih in podatkovnih plasti do povsem skupnega vmesnika v Compose.",
        lv = "Lielākais bija uzņēmuma ražošanas iOS lietotnes pārcelšana uz kopīgu Kotlin kodu. Es izstrādāju arhitektūru un uzrakstīju kopīgo kodu: biznesa loģiku, domēna modeļus un ar SQLCipher šifrētus datu slāņus, kompilētus statiskā Kotlin/Native ietvarā. iOS komanda to pievienoja bez neviena jauna saistītāja karoga.\n\nEsmu izlaidis arī mākslīgā intelekta platformu ar reāllaika tērzēšanu, laika joslām un push paziņojumiem; spēļu lietotni ar NFC, biometrisko pieteikšanos, bezskaidras naudas maksājumiem un white-label versijām; un Parrot. Katrā Kotlin Multiplatform tika izmantots citādi, no kopīgiem tīkla un datubāzes slāņiem līdz pilnībā kopīgai Compose saskarnei."
    )

    data object TimelineTitle : StringRes(
        en = "Where I've worked",
        sl = "Kje sem delal",
        lv = "Kur esmu strādājis"
    )

    data object TimelineDevRevYears : StringRes(
        en = "2023 – now",
        sl = "2023 – danes",
        lv = "2023 – šodien"
    )

    data object TimelineDevRevRole : StringRes(
        en = "Senior Android Engineer & Tech Lead",
        sl = "Višji inženir za Android in tehnični vodja",
        lv = "Vecākais Android inženieris un tehniskais vadītājs"
    )

    data object TimelineDevRevBody : StringRes(
        en = "I lead six Android engineers and set the architecture standards. I moved the app from XML views to Jetpack Compose and rebuilt the CircleCI pipelines, which cut build times across the company.",
        sl = "Vodim šest inženirjev za Android in postavljam arhitekturne standarde. Aplikacijo sem prenesel s pogledov XML na Jetpack Compose in prenovil cevovode CircleCI, kar je skrajšalo čase gradnje v celotnem podjetju.",
        lv = "Vadu sešus Android inženierus un nosaku arhitektūras standartus. Pārcēlu lietotni no XML skatiem uz Jetpack Compose un pārbūvēju CircleCI konveijerus, kas saīsināja būvēšanas laiku visā uzņēmumā."
    )

    data object TimelineNetceteraRole : StringRes(
        en = "Android Developer",
        sl = "Razvijalec za Android",
        lv = "Android izstrādātājs"
    )

    data object TimelineNetceteraBody : StringRes(
        en = "I built financial modules for large banking apps under strict security and compliance rules, and split monolithic features into independent modules to shorten the release cycle.",
        sl = "Razvijal sem finančne module za velike bančne aplikacije s strogimi varnostnimi in regulativnimi zahtevami ter monolitne funkcionalnosti razdelil v neodvisne module, kar je skrajšalo cikel izdaj.",
        lv = "Izstrādāju finanšu moduļus lielām banku lietotnēm atbilstoši stingrām drošības un atbilstības prasībām un sadalīju monolītas funkcijas neatkarīgos moduļos, lai saīsinātu laidienu ciklu."
    )

    data object TimelineEducationRole : StringRes(
        en = "Diploma in Software Development",
        sl = "Diploma iz razvoja programske opreme",
        lv = "Programmatūras izstrādes diploms"
    )

    data object TimelineEducationBody : StringRes(
        en = "A bachelor-level diploma in software development.",
        sl = "Diploma na ravni prve bolonjske stopnje iz razvoja programske opreme.",
        lv = "Bakalaura līmeņa diploms programmatūras izstrādē."
    )

    data object ExperienceContactBody : StringRes(
        en = "I'm open to senior mobile engineering roles, Kotlin Multiplatform migrations and Android architecture work. I'm based in Slovenia and work remotely across the EU. A job offer, a migration review or just a question about KMP: email me at rok.retar@gmail.com.",
        sl = "Odprt sem za vodilne vloge v mobilnem razvoju, selitve na Kotlin Multiplatform in arhitekturo Android. Živim v Sloveniji in delam na daljavo po vsej EU. Ponudba za delo, pregled selitve ali le vprašanje o KMP: pišite mi na rok.retar@gmail.com.",
        lv = "Esmu atvērts vecākā mobilo lietotņu inženiera lomām, Kotlin Multiplatform migrācijām un Android arhitektūras darbam. Dzīvoju Slovēnijā un strādāju attālināti visā ES. Darba piedāvājums, migrācijas pārskats vai vienkārši jautājums par KMP: rakstiet man uz rok.retar@gmail.com."
    )
}
