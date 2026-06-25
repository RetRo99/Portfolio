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
        en = "// mobile engineer",
        sl = "// mobilni inženir",
        lv = "// mobilo lietotņu inženieris"
    )

    data object HeroName : StringRes(
        en = "Rok Retar",
        sl = "Rok Retar",
        lv = "Rok Retar"
    )

    data object HeroHeadline : StringRes(
        en = "I engineer Android and Kotlin Multiplatform systems — including migrating a company's iOS app to shared Kotlin.",
        sl = "Razvijam sisteme za Android in Kotlin Multiplatform — vključno s selitvijo aplikacije podjetja za iOS na skupno platformo Kotlin.",
        lv = "Veidoju sistēmas Android un Kotlin Multiplatform platformām — tostarp migrēju uzņēmuma iOS lietotni uz kopīgu Kotlin kodu."
    )

    data object HeroSubtitle : StringRes(
        en = "Mobile development rooted in the Android ecosystem. I take Kotlin Multiplatform to production — one codebase serving two platforms without surrendering native feel. This page is built natively in Kotlin.",
        sl = "Razvoj mobilnih aplikacij, zasnovan na ekosistemu Android. V produkcijo uvajam Kotlin Multiplatform – eno kodno bazo, ki podpira dve platformi, ne da bi pri tem izgubila občutek nativne aplikacije. Ta stran je nativno razvita v jeziku Kotlin.",
        lv = "Mobilā lietotņu izstrāde, kas balstās uz Android ekosistēmu. Es ieviešu Kotlin Multiplatform ražošanas vidē — viena kodbāze, kas apkalpo divas platformas, nezaudējot lietotnes dabisko izjūtu. Šī lapa ir izstrādāta, izmantojot Kotlin valodu."
    )

    data object HeroStatusStrip : StringRes(
        en = "Kotlin · KMP · Android · Kobweb / Compose for Web",
        sl = "Kotlin · KMP · Android · Kobweb / Compose for Web",
        lv = "Kotlin · KMP · Android · Kobweb / Compose for Web"
    )

    data object CtaReviewWork : StringRes(
        en = "Review the Work",
        sl = "Preglejte delo",
        lv = "Apskatīt darbus"
    )

    data object CtaDownloadResume : StringRes(
        en = "Download Resume",
        sl = "Prenesite življenjepis",
        lv = "Lejupielādēt CV"
    )

    data object CtaReadNotes : StringRes(
        en = "Engineering notes",
        sl = "Inženirske zabeležke",
        lv = "Inženierijas piezīmes"
    )

    data object Descriptor : StringRes(
        en = "Mobile Developer",
        sl = "Razvijalec mobilnih aplikacij",
        lv = "Mobilo lietotņu izstrādātājs"
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
        en = "// 01 — engineering",
        sl = "// 01 — inženirstvo",
        lv = "// 01 — inženierija"
    )

    data object PhilosophyHeading : StringRes(
        en = "One codebase. Two platforms. Native discipline intact.",
        sl = "Ena kodna baza. Dve platformi. Naravna disciplina ohranjena.",
        lv = "Viena kodbāze. Divas platformas. Vietējā disciplīna saglabāta."
    )

    data object PhilosophyBody1 : StringRes(
        en = "My specialization is the Android ecosystem and Kotlin Multiplatform. I treat the codebase as the source of truth: shared business logic, shared data layers, shared domain models — with UI remaining native where it earns its keep.",
        sl = "Moja specializacija je ekosistem Android in Kotlin Multiplatform. Kodno bazo obravnavam kot vir resnice: skupna poslovna logika, skupne podatkovne plasti in skupni domenski modeli – pri čemer uporabniški vmesnik ostaja nativni tam, kjer to prinaša največ koristi.",
        lv = "Mana specializācija ir Android ekosistēma un Kotlin Multiplatform. Es uzskatu kodbāzi par patiesības avotu: kopīga biznesa loģika, kopīgi datu slāņi, kopīgi domēna modeļi — turklāt lietotāja saskarne paliek nativā, ja tas ir lietderīgi."
    )

    data object PhilosophyBody2 : StringRes(
        en = "I architected and executed a complete corporate migration, transitioning production systems to Kotlin Multiplatform for iOS. Shared Kotlin now backs an iOS app in the field — not a prototype, not a branch, a shipped product.",
        sl = "Načrtoval in izvedel sem celovito migracijo podjetja, v okviru katere smo produkcijske sisteme prešli na Kotlin Multiplatform za iOS. Skupni Kotlin zdaj podpira aplikacijo za iOS, ki je že v uporabi – ne gre za prototip in ne za razvojno vejo, temveč za izdan produkt.",
        lv = "Es izstrādāju un īstenoju pilnīgu uzņēmuma migrāciju, pārnesot ražošanas sistēmas uz Kotlin Multiplatform iOS vajadzībām. Kopīgais Kotlin tagad nodrošina iOS lietotnes darbību reālajā vidē — tas nav ne prototips, ne izstrādes atzars, bet gan tirgū laists produkts."
    )

    data object PhilosophyBody3 : StringRes(
        en = "The discipline is architectural, not ideological. Shared code goes where it reduces drift and risk; platform code stays where it delivers native performance and feel. The boundary is deliberate, tested, and enforced by module structure — domain never depends on data or UI.",
        sl = "Ta pristop je arhitekturne narave, ne ideološke. Skupna koda se uporablja tam, kjer zmanjšuje odstopanja in tveganje; koda platforme pa ostane tam, kjer zagotavlja izvirno zmogljivost in uporabniško izkušnjo. Ta ločnica je namerna, preizkušena in se uveljavlja prek strukture modulov – domena nikoli ni odvisna od podatkov ali uporabniškega vmesnika.",
        lv = "Šī pieeja ir arhitektoniska, nevis ideoloģiska. Kopīgais kods tiek izmantots tur, kur tas samazina novirzes un riskus; platformas kods paliek tur, kur tas nodrošina sistēmai raksturīgo veiktspēju un lietošanas sajūtu. Šī robežšķirtne ir apzināti izveidota, pārbaudīta un tiek nodrošināta ar moduļu struktūru — domēns nekad nav atkarīgs no datiem vai lietotāja saskarnes."
    )

    data object PhilosophyPrinciple1 : StringRes(
        en = "Shared domain and data layers; native UI where the platform earns it.",
        sl = "Skupni domenski in podatkovni sloji; nativni uporabniški vmesnik tam, kjer si ga platforma zasluži.",
        lv = "Kopīgi domēna un datu slāņi; platformai piemērota lietotāja saskarne tur, kur tas ir nepieciešams."
    )

    data object PhilosophyPrinciple2 : StringRes(
        en = "Module boundaries that enforce dependency direction — domain stays pure Kotlin.",
        sl = "Meje modulov, ki določajo smer odvisnosti — domena ostaja v čistem Kotlinu.",
        lv = "Moduļu robežas, kas nosaka atkarību virzienu — domēns paliek tīrā Kotlin."
    )

    data object PhilosophyPrinciple3 : StringRes(
        en = "KMP taken to production iOS, not demoed on a branch.",
        sl = "KMP je bil prenesen v produkcijsko okolje iOS, ni bil predstavljen na razvojni veji.",
        lv = "KMP ir ieviests iOS ražošanas vidē, nevis demonstrēts atzarā."
    )

    data object PhilosophyPrinciple4 : StringRes(
        en = "The portfolio itself is built natively in Kotlin — the medium is the evidence.",
        sl = "Portfelj je v celoti razvit v jeziku Kotlin — to dokazuje tudi sam medij.",
        lv = "Pats portfelis ir izstrādāts tieši Kotlin valodā — par to liecina šis piemērs."
    )

    data object MigrationCalloutLabel : StringRes(
        en = "// migration",
        sl = "// selitev",
        lv = "// migrācija"
    )

    data object MigrationCalloutBody : StringRes(
        en = "Production iOS now runs on shared Kotlin. The migration is complete and shipped — Android and iOS backed by a single Kotlin codebase.",
        sl = "Proizvodna različica za iOS zdaj deluje na skupni platformi v jeziku Kotlin. Migracija je zaključena in izdana – Android in iOS temeljita na eni sami kodni bazi v jeziku Kotlin.",
        lv = "Ražošanas iOS tagad darbojas uz kopīgu Kotlin kodbāzi. Migrācija ir pabeigta un izlaista — gan Android, gan iOS balstās uz vienu Kotlin kodbāzi."
    )

    data object ParrotEyebrow : StringRes(
        en = "// 02 — signature work",
        sl = "// 02 — prepoznavno delo",
        lv = "// 02 — galvenais darbs"
    )

    data object ParrotHeading : StringRes(
        en = "Parrot",
        sl = "Parrot",
        lv = "Parrot"
    )

    data object ParrotSubhead : StringRes(
        en = "A lightweight EPUB e-reader and audiobook player — the companion app for Storyteller, built for readers who want a calibrated, immersive experience over generic digital layouts.",
        sl = "Lahki bralnik e-knjig v formatu EPUB in predvajalnik zvočnih knjig — spremljevalna aplikacija za Storyteller, zasnovana za bralce, ki si želijo natančno prilagojeno, vživajočo izkušnjo namesto običajnih digitalnih postavitev.",
        lv = "Viegla EPUB e-grāmatu lasīšanas un audio grāmatu atskaņošanas lietotne — Storyteller papildu lietotne, kas izstrādāta lasītājiem, kuri vēlas precīzi pielāgotu, aizraujošu pieredzi, nevis standarta digitālos noformējumus."
    )

    data object ParrotFeature1Title : StringRes(
        en = "Typography & text control",
        sl = "Tipografija in upravljanje besedila",
        lv = "Tipogrāfija un teksta vadība"
    )

    data object ParrotFeature1Desc : StringRes(
        en = "User-defined font configurations and pinch-to-zoom text sizing. Every reader calibrates type, size, margins, and line height to their own ergonomics.",
        sl = "Uporabniško določene nastavitve pisav in spreminjanje velikosti besedila s ščipkanjem. Vsak bralec prilagodi vrsto pisave, velikost, robove in višino vrstice svojim ergonomskim potrebam.",
        lv = "Lietotāja definētas fontu konfigurācijas un teksta izmēra maiņa ar divu pirkstu pieskārienu. Katrs lasītājs pielāgo burtveidu, izmēru, malu atstarpes un rindu augstumu atbilstoši savām ergonomiskajām vajadzībām."
    )

    data object ParrotFeature2Title : StringRes(
        en = "Reading modes",
        sl = "Načini branja",
        lv = "Lasīšanas režīmi"
    )

    data object ParrotFeature2Desc : StringRes(
        en = "Intuitive page-flip navigation and a true fullscreen mode that strips away every chrome element, leaving only the text. Dark, sepia, and a dedicated e-ink display mode.",
        sl = "Intuitivna navigacija z obračanjem strani in pravi način polnega zaslona, ki odstrani vse elemente vmesnika in pusti le besedilo. Na voljo so načini temnega zaslona, sepije in posebni način zaslona e-ink.",
        lv = "Intuitīva navigācija, kas atgādina lappušu pāršķiršanu, un īsts pilnekrāna režīms, kurā tiek noņemti visi interfeisa elementi, atstājot tikai tekstu. Tumšais, sepijas un īpašais e-ink displeja režīms."
    )

    data object ParrotFeature3Title : StringRes(
        en = "E-ink adaptation",
        sl = "Prilagoditev za e-ink",
        lv = "Pielāgojums e-ink"
    )

    data object ParrotFeature3Desc : StringRes(
        en = "A display mode built specifically for e-ink screens — high contrast, no decorative motion, refresh behavior that respects the hardware. Crisp and natural on specialized devices.",
        sl = "Način prikazovanja, zasnovan posebej za zaslone e-ink – visok kontrast, brez okrasnih gibov, način osveževanja, ki upošteva zmogljivosti strojne opreme. Jasna in naravna slika na specializiranih napravah.",
        lv = "Ekrāna režīms, kas izstrādāts īpaši e-ink ekrāniem — augsts kontrasts, bez dekoratīvām kustībām, atjaunošanas darbība, kas ņem vērā aparatūras iespējas. Skaidrs un dabīgs attēls specializētās ierīcēs."
    )

    data object ParrotFeature4Title : StringRes(
        en = "Background audio",
        sl = "Zvok v ozadju",
        lv = "Fona audio"
    )

    data object ParrotFeature4Desc : StringRes(
        en = "Audiobook playback that continues in the background with persistent system notification controls. Skip chapters, scrub, and set speed without ever opening the app.",
        sl = "Predvajanje avdio knjige, ki poteka v ozadju, s stalnimi nadzornimi elementi v sistemskih obvestilih. Preskakujte poglavja, premikajte se po posnetku in nastavljajte hitrost predvajanja, ne da bi sploh odprli aplikacijo.",
        lv = "Audio grāmatas atskaņošana, kas turpinās fonā, izmantojot pastāvīgus sistēmas paziņojumu vadības elementus. Varat pārlēkt uz nākamo nodaļu, pārvietoties pa ierakstu un mainīt atskaņošanas ātrumu, pat neatraujot lietotni."
    )

    data object ParrotFeature5Title : StringRes(
        en = "Read-Aloud sync",
        sl = "Sinhronizacija Read-Aloud",
        lv = "Read-Aloud sinhronizācija"
    )

    data object ParrotFeature5Desc : StringRes(
        en = "Real-time, word-by-word highlighted text synchronization. As the narrator speaks, the text tracks the audio precisely — for language learners, developing readers, and anyone who reads with their ears.",
        sl = "Sinhronizacija besedila v realnem času z označevanjem posameznih besed. Medtem ko pripovedovalec govori, besedilo natančno sledi avdio posnetku — za tiste, ki se učijo jezika, za začetnike v branju in za vse, ki berejo z ušesi.",
        lv = "Teksta sinhronizācija reāllaikā, izceļot katru vārdu. Kamēr stāstītājs runā, teksts precīzi seko līdzi audio ierakstam — valodu apguvējiem, lasīšanas prasmes pilnveidotājiem un ikvienam, kurš lasa, klausoties."
    )

    data object ParrotFeature6Title : StringRes(
        en = "Reading analytics",
        sl = "Analitika branja",
        lv = "Lasīšanas analītika"
    )

    data object ParrotFeature6Desc : StringRes(
        en = "Automated local background analytics that map reading durations and calculate personalized reading-speed estimates per chapter. No servers, no telemetry — the math runs on-device.",
        sl = "Avtomatizirana lokalna analiza ozadja, ki beleži trajanje branja in izračuna prilagojene ocene hitrosti branja za vsako poglavje. Brez strežnikov, brez telemetrije – izračuni potekajo na sami napravi.",
        lv = "Automatizēta vietējā fona analītika, kas fiksē lasīšanas ilgumu un aprēķina personalizētus lasīšanas ātruma novērtējumus katrai nodaļai. Nav ne serveru, ne telemetrijas — aprēķini tiek veikti pašā ierīcē."
    )

    data object ParrotFeature7Title : StringRes(
        en = "Companion architecture",
        sl = "Arhitektura spremljevalca",
        lv = "Pavadlietotnes arhitektūra"
    )

    data object ParrotFeature7Desc : StringRes(
        en = "Built as the companion client for Storyteller. Reading position and progress sync so a reader moves between phone, tablet, and desktop without losing their place.",
        sl = "Narejen je bil kot spremljevalni odjemalec za aplikacijo Storyteller. Sinhronizira se položaj branja in napredek, tako da bralec lahko preklaplja med telefonom, tablico in namiznim računalnikom, ne da bi izgubil mesto, kjer je prenehal brati.",
        lv = "Izstrādāts kā papildklients programmai Storyteller. Lasīšanas pozīcija un progress tiek sinhronizēti, tādējādi lasītājs var pārslēgties starp tālruni, planšetdatoru un datoru, nezaudējot lasīšanas vietu."
    )

    data object CtaOpenCaseStudy : StringRes(
        en = "Open the Parrot case study",
        sl = "Odpri študijski primer Parrot",
        lv = "Atvērt Parrot piemēra izpēti"
    )

    data object NotesEyebrow : StringRes(
        en = "// 03 — engineering notes",
        sl = "// 03 — inženirske zabeležke",
        lv = "// 03 — inženierijas piezīmes"
    )

    data object NotesTitle : StringRes(
        en = "Engineering notes",
        sl = "Inženirske zabeležke",
        lv = "Inženierijas piezīmes"
    )

    data object NotesSubtitle : StringRes(
        en = "Occasional deep-tech write-ups on how I build things — Kobweb, Compose, and Kotlin under the hood.",
        sl = "Občasni prispevki o »deep-tech« tehnologijah, v katerih opisujem, kako razvijam svoje projekte — Kobweb, Compose in Kotlin v ozadju.",
        lv = "Laiku pa laikam publicēju rakstus par tehnoloģiskām tēmām, kuros aprakstu, kā es veidoju savus projektus — Kobweb, Compose un Kotlin no iekšpuses."
    )

    data object ContactEyebrow : StringRes(
        en = "// 03 — contact",
        sl = "// 03 — kontakt",
        lv = "// 03 — kontakti"
    )

    data object ContactHeading : StringRes(
        en = "Let's build something precise.",
        sl = "Naredimo nekaj natančnega.",
        lv = "Uzbūvēsim kaut ko precīzu."
    )

    data object ContactBody : StringRes(
        en = "I'm open to senior mobile engineering roles, KMP migrations, and Android architecture work. The fastest path is email.",
        sl = "Zainteresiran sem za vodilne položaje na področju mobilnega inženirstva, migracije KMP in delo na področju arhitekture sistema Android. Najhitrejši način za stik je prek e-pošte.",
        lv = "Esmu ieinteresēts vadošajos amatos mobilo ierīču inženierijas jomā, KMP migrācijās un darbā ar Android arhitektūru. Ātrākais veids, kā ar mani sazināties, ir e-pasts."
    )

    data object CtaEmail : StringRes(
        en = "Email Rok Retar",
        sl = "Pošljite e-pošto Roku Retarju",
        lv = "Sūtīt e-pastu Roku Retaram"
    )

    data object FooterLinks : StringRes(
        en = "GitHub · LinkedIn · rok.retar@gmail.com",
        sl = "GitHub · LinkedIn · rok.retar@gmail.com",
        lv = "GitHub · LinkedIn · rok.retar@gmail.com"
    )

    data object FooterMeta : StringRes(
        en = "Built natively in Kotlin with Kobweb",
        sl = "Izvorno zgrajeno v Kotlinu s Kobwebom",
        lv = "Veidots vietēji Kotlinā ar Kobweb"
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
        en = "Engineering",
        sl = "Inženirstvo",
        lv = "Inženierija"
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
        en = "Parrot e-reader in light mode showing custom typography and margins",
        sl = "Bralnik Parrot v svetlem načinu s prilagojeno tipografijo in robovi",
        lv = "Lasītājs Parrot gaišajā režīmā ar pielāgotu tipogrāfiju un malām"
    )

    data object ParrotScreenshot2Desc : StringRes(
        en = "Parrot e-reader in dark fullscreen mode with distraction-free reading",
        sl = "Bralnik Parrot v temnem celozaslonskem načinu za nemoteno branje",
        lv = "Lasītājs Parrot tumšajā pilnekrāna režīmā bez traucējumiem"
    )

    data object ParrotScreenshot3Desc : StringRes(
        en = "Parrot audiobook player with background notification and chapter controls",
        sl = "Predvajalnik zvočnih knjig Parrot z obvestili v ozadju in upravljanjem poglavij",
        lv = "Audio grāmatu atskaņotājs Parrot ar fona paziņojumiem un nodaļu vadību"
    )

    data object ParrotScreenshot4Desc : StringRes(
        en = "Parrot e-ink display mode on a specialized reader device",
        sl = "Način e-ink prikaza Parrot na specializirani bralni napravi",
        lv = "E-ink displeja režīms uz specializētas lasīšanas ierīces"
    )

    data object ParrotScreenshot5Desc : StringRes(
        en = "Parrot Read-Aloud sync with word-by-word text highlighting",
        sl = "Sinhronizacija Read-Aloud v aplikaciji Parrot z osvetljevanjem besede za besedo",
        lv = "Read-Aloud sinhronizācija ar vārds pa vārdam izcelšanu"
    )

    data object InfiniteCarouselArticleTitle : StringRes(
        en = "Building a Smooth, Infinite Carousel in Kobweb",
        sl = "Izdelava gladkega, neskončnega vrtiljaka v Kobwebu",
        lv = "Gludas bezgalīgas karuseļa komponentes izveide ar Kobweb"
    )

    data object InfiniteCarouselArticleDesc : StringRes(
        en = "How to build an infinite, gapless carousel using Silk and Kobweb.",
        sl = "Kako izdelati neskončni brezšivni vrtiljak s pomočjo knjižnic Silk in Kobweb.",
        lv = "Kā izveidot bezgalīgu, bezstarpīgu karuseli, izmantojot Silk un Kobweb."
    )

    data object SQLCipherArticleTitle : StringRes(
        en = "Shipping SQLCipher inside a static Kotlin/Native framework (so the iOS team changed nothing)",
        sl = "Shipping SQLCipher inside a static Kotlin/Native framework (so the iOS team changed nothing)",
        lv = "Shipping SQLCipher inside a static Kotlin/Native framework (so the iOS team changed nothing)"
    )

    data object SQLCipherArticleDesc : StringRes(
        en = "Baking SQLCipher into a static Kotlin/Native framework so the iOS team adds nothing — not even a linker flag.",
        sl = "Baking SQLCipher into a static Kotlin/Native framework so the iOS team adds nothing — not even a linker flag.",
        lv = "Baking SQLCipher into a static Kotlin/Native framework so the iOS team adds nothing — not even a linker flag."
    )

    data object ParrotIconDesc : StringRes(
        en = "Parrot App Icon",
        sl = "Ikona aplikacije Parrot",
        lv = "Parrot lietotnes ikona"
    )

    data object ParrotPageHeading : StringRes(
        en = "Parrot",
        sl = "Parrot",
        lv = "Parrot"
    )

    data object ParrotPageSubhead : StringRes(
        en = "A lightweight EPUB e-reader and audiobook player — the companion app for Storyteller, built for readers who want a calibrated, immersive experience over generic digital layouts.",
        sl = "Lahki bralnik e-knjig v formatu EPUB in predvajalnik zvočnih knjig — spremljevalna aplikacija za Storyteller, zasnovana za bralce, ki si želijo natančno prilagojeno, vživajočo izkušnjo namesto običajnih digitalnih postavitev.",
        lv = "Viegla EPUB e-grāmatu lasīšanas un audio grāmatu atskaņošanas lietotne — Storyteller papildu lietotne, kas izstrādāta lasītājiem, kuri vēlas precīzi pielāgotu, aizraujošu pieredzi, nevis standarta digitālos noformējumus."
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
        en = "View Version History",
        sl = "Prikaži zgodovino različic",
        lv = "Skatīt versiju vēsturi"
    )

    data object ParrotFeature1PageTitle : StringRes(
        en = "Typography & Reading Modes",
        sl = "Tipografija in bralni načini",
        lv = "Tipogrāfija un lasīšanas režīmi"
    )

    data object ParrotFeature1PageDesc : StringRes(
        en = "Every reader calibrates differently. Parrot exposes user-defined font configurations and pinch-to-zoom text sizing, so type, size, margins, and line height adapt to the reader's ergonomics — not a generic default.\n\nNavigation stays out of the way: tap the screen edges to flip pages, swipe through chapters, or jump from the table of contents. A true fullscreen mode strips every chrome element away, leaving only the text. Dark, sepia, and a dedicated e-ink display mode handle the rest.",
        sl = "Vsak bralec umerja drugače. Parrot omogoča uporabniško določene nastavitve pisave in povečevanje besedila s stiskanjem, tako da se pisava, velikost, robovi in višina vrstice prilagajajo ergonomiji bralca — in ne kaki splošni privzeti vrednosti.\n\nKrmarjenje ni na poti: z dotikom robov zaslona listate po straneh, z vlečenjem se premikate med poglavji ali pa skočite iz kazala. Pravi celozaslonski način odstrani vse elemente vmesnika in pusti le besedilo. Preostalo poskrbijo temni način, sepia in poseben način za e-ink zaslone.",
        lv = "Katrs lasītājs kalibrē atšķirīgi. Parrot piedāvā lietotāja definētus fontu iestatījumus un teksta izmēra pielāgošanu ar kniebšanas žestu, lai fonts, izmērs, malas un rindas augstums pielāgotos lasītāja ergonomijai — nevis vispārīgam noklusējumam.\n\nNavigācija netraucē: pieskarieties ekrāna malām, lai pāršķirtu lapas, slīdiet cauri nodaļām vai pārlēciet no satura rādītāja. Īstais pilnekrāna režīms noņem visus UI elementus un atstāj tikai tekstu. Pārējo nodrošina tumšais, sēpijas un īpašais e-ink displeja režīms."
    )

    data object ParrotFeature2PageTitle : StringRes(
        en = "Background Audio & Read-Aloud Sync",
        sl = "Zvok v ozadju in sinhronizacija Read-Aloud",
        lv = "Fona audio un Read-Aloud sinhronizācija"
    )

    data object ParrotFeature2PageDesc : StringRes(
        en = "Audiobook playback continues in the background with persistent system notification controls — skip chapters, scrub, and set speed without opening the app.\n\nThe Read-Aloud system is where reading and listening converge. As the narrator speaks, the text highlights in real time, word by word, tracking the audio precisely. It's built for language learners, developing readers, and anyone who reads with their ears. Highlight color and style are configurable; narration speed scales to taste.",
        sl = "Predvajanje zvočnih knjig teče v ozadju s stalnimi sistemskimi obvestili za nadzor — preskakujete lahko poglavja, premikate se po posnetku in nastavljate hitrost, ne da bi odprli aplikacijo.\n\nSistem Read-Aloud je točka, kjer se branje in poslušanje združita. Ko pripovedovalec govori, se besedilo v realnem času osvetljuje beseda za besedo in natančno sledi zvoku. Namenjen je učečim se jezikov, bralcem, ki šele razvijajo bralne spretnosti, in vsem, ki berejo z ušesi. Barva in slog osvetlitve sta nastavljiva; hitrost pripovedi se prilagaja okusu.",
        lv = "Audio grāmatu atskaņošana turpinās fonā ar pastāvīgiem sistēmas paziņojumu vadības elementiem — var pāršķirstīt nodaļas, ritināt un iestatīt ātrumu, ne atverot lietotni.\n\nRead-Aloud ir vieta, kur lasīšana un klausīšanās saplūst. Kad runātājs runā, teksts reāllaikā izceļas vārds pa vārdam un precīzi seko audio. Tas radīts valodu apguvējiem, lasītprasmi apguvējiem un ikvienam, kas lasa ar ausīm. Izcelšanas krāsas un stila iestatījumi ir pielāgojami; runāšanas ātrumu var pielāgot pēc patikas."
    )

    data object ParrotFeature3PageTitle : StringRes(
        en = "Local Reading Analytics",
        sl = "Lokalna analitika branja",
        lv = "Vietējā lasīšanas analītika"
    )

    data object ParrotFeature3PageDesc : StringRes(
        en = "Reading habits are mapped locally, on-device, with no servers and no telemetry. Parrot tracks reading durations across days, weeks, and months, and calculates personalized reading-speed estimates per chapter — so you always know whether there's time for one more chapter before bed.",
        sl = "Bralne navade se zbirajo lokalno, na sami napravi — brez strežnikov in brez telemetrije. Parrot sledi trajanju branja skozi dni, tedne in mesece ter za posamezno poglavje izračuna prilagojeno oceno hitrosti branja — tako vedno veste, ali je pred spanjem čas še za eno poglavje.",
        lv = "Lasīšanas ieradumi tiek apkopoti vietēji — uz ierīces, bez serveriem un bez telemetrijas. Parrot seko lasīšanas ilgumiem dienas, nedēļas un mēneša griezumā un katrai nodaļai aprēķina personalizētu lasīšanas ātruma novērtējumu — lai vienmēr zinātu, vai pirms gulētiešanas pagūsiet izlasīt vēl vienu nodaļu."
    )

    data object ParrotFeature4PageTitle : StringRes(
        en = "Companion Sync",
        sl = "Sinhronizacija spremljevalca",
        lv = "Pavadlietotnes sinhronizācija"
    )

    data object ParrotFeature4PageDesc : StringRes(
        en = "Parrot is the companion client for Storyteller. Reading position and progress sync across phone, tablet, and desktop, so a reader picks up exactly where they left off — no searching, no scrolling, just reading.",
        sl = "Parrot je odjemalec-spremljevalec za Storyteller. Položaj in napredek branja se sinhronizirata med telefonom, tablico in namizjem, tako da bralec nadaljuje točno tam, kjer je končal — brez iskanja, brez pomikanja, samo branje.",
        lv = "Parrot ir Storyteller pavadklients. Lasīšanas vieta un progress sinhronizējas starp telefonu, planšeti un datoru, tā ka lasītājs turpina tieši tur, kur pārtrauca — bez meklēšanas, bez ritināšanas, tikai lasīšana."
    )

    data object VersionHistory : StringRes(
        en = "Version History",
        sl = "Zgodovina različic",
        lv = "Versiju vēsture"
    )

    data class VersionWithDate(val version: String, val date: String) : StringRes(
        en = "Version $version ($date)",
        sl = "Različica $version ($date)",
        lv = "Versija $version ($date)"
    )

    data object NewFeatures : StringRes(
        en = "New Features",
        sl = "Nove funkcije",
        lv = "Jaunas funkcijas"
    )

    data object BugFixes : StringRes(
        en = "Bug Fixes",
        sl = "Popravki napak",
        lv = "Kļūdu labojumi"
    )

    data object Improvements : StringRes(
        en = "Improvements",
        sl = "Izboljšave",
        lv = "Uzlabojumi"
    )

    data object NewFeaturesLabel : StringRes(
        en = "New Features:",
        sl = "Nove funkcije:",
        lv = "Jaunas funkcijas:"
    )

    data object BugFixesLabel : StringRes(
        en = "Bug Fixes:",
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
        en = "Technology stack and specialization",
        sl = "Tehnološki sklop in specializacija",
        lv = "Tehnoloģiju steks un specializācija"
    )

    data object ExperienceStackBody : StringRes(
        en = "I'm a mobile engineer based in Slovenia. Android is where I started, and Kotlin Multiplatform is where I ended up. The stack I reach for daily: Kotlin, Jetpack Compose, Compose Multiplatform, Kobweb, SQLDelight, SQLCipher, Ktor, Koin, and Decompose.\n\nThe architecture I build is multi-module with strict dependency rules. Domain stays pure Kotlin. Data layers handle persistence and networking. UI stays native where the platform earns it. For Swift interop I use SKIE. For databases, SQLDelight with SQLCipher. Ktor handles networking, Koin handles DI, and Decompose handles navigation and lifecycle. This site is built in Kotlin with Kobweb and Compose for Web. If you're reading this, the proof is already loading.",
        sl = "Sem mobilni inženir iz Slovenije. Začel sem z Androidom, končal pa sem pri Kotlin Multiplatform. Tehnološki stack, ki ga uporabljam vsak dan: Kotlin, Jetpack Compose, Compose Multiplatform, Kobweb, SQLDelight, SQLCipher, Ktor, Koin in Decompose.\n\nArhitektura, ki jo gradim, je večmodulna s strogimi pravili odvisnosti. Domena ostaja izključno v Kotlinu. Podatkovni sloji skrbijo za trajnost in omrežne povezave. Uporabniški vmesnik ostaja nativni, kjer si to platforma zasluži. Za medopravilnost s Swiftom uporabljam SKIE. Za baze podatkov uporabljam SQLDelight s SQLCipherjem. Ktor skrbi za omrežje, Koin za DI, Decompose pa za navigacijo in življenjski cikel. Ta spletna stran je zgrajena v Kotlinu s Kobwebom in Compose for Web. Če to bereš, se dokaz že nalaga.",
        lv = "Esmu mobilo lietotņu inženieris, kas dzīvo Slovēnijā. Savu karjeru sāku ar Android, bet nonācu pie Kotlin Multiplatform. Tehnoloģiju kopums, ko ikdienā izmantoju: Kotlin, Jetpack Compose, Compose Multiplatform, Kobweb, SQLDelight, SQLCipher, Ktor, Koin un Decompose.\n\nEs veidoju daudzmoduļu arhitektūru ar stingriem atkarību noteikumiem. Domēns paliek tīrā Kotlin. Datu slāņi nodrošina datu saglabāšanu un tīkla darbību. Lietotāja saskarne paliek nativā, ja platforma to atbalsta. Swift sadarbībai es izmantoju SKIE. Datubāzēm — SQLDelight kopā ar SQLCipher. Ktor nodrošina tīkla savienojumus, Koin — atkarību injekciju (DI), bet Decompose — navigāciju un dzīves ciklu. Šī vietne ir izstrādāta Kotlin valodā, izmantojot Kobweb un Compose for Web. Ja jūs to lasāt, pierādījums jau tiek ielādēts."
    )

    data object ExperienceMigrationTitle : StringRes(
        en = "Production KMP migration",
        sl = "Produkcijska selitev na KMP",
        lv = "Ražošanas KMP migrācija"
    )

    data object ExperienceMigrationBody : StringRes(
        en = "The headline work: migrating production iOS to shared Kotlin. I architected the whole thing. Shared business logic, data layers with SQLCipher encryption, domain models. All written once in Kotlin, compiled to a static Kotlin/Native framework. The iOS team plugged it in without adding a single linker flag. Android and iOS now run on one Kotlin codebase. In production. Shipping to real users.\n\nI've also shipped production apps in three very different domains. An AI platform with real-time chat, timelines, and push notifications. A gaming app with NFC, biometric auth, cashless payments, and whitelabel multi-app deployment. And Parrot, a cross-platform EPUB reader and audiobook player with Read-Aloud sync, e-ink support, and reading analytics. Each one pushed KMP in a different direction. Shared networking and database layers in one. Full shared UI with Compose Multiplatform in another.",
        sl = "Glavni projekt: migracija produkcijskega iOS-a na skupni Kotlin. Celotno arhitekturo sem zasnoval sam. Skupna poslovna logika, podatkovne plasti s šifriranjem SQLCipher, domenski modeli. Vse je bilo enkrat napisano v Kotlinu in kompilirano v statični okvir Kotlin/Native. Ekipa za iOS je to vključila brez dodajanja enega samega zastavka povezovalnika. Android in iOS zdaj delujeta na eni kodni bazi v Kotlinu. V produkciji. Na voljo resničnim uporabnikom.\n\nPrav tako sem izdal produkcijske aplikacije v treh zelo različnih področjih. Platforma umetne inteligence s klepetom v realnem času, časovnicami in push obvestili. Igralna aplikacija z NFC, biometrično avtentifikacijo, brezgotovinskimi plačili in uvajanjem več aplikacij pod lastno blagovno znamko. In Parrot, večplatformski bralnik EPUB in predvajalnik zvočnih knjig s sinhronizacijo glasovnega branja, podporo za e-ink in analitiko branja. Vsaka od njih je KMP popeljala v drugo smer. Skupne omrežne in podatkovne plasti v enem primeru. Popolnoma skupni uporabniški vmesnik s Compose Multiplatform v drugem.",
        lv = "Galvenais uzdevums: iOS ražošanas vides migrēšana uz kopīgo Kotlin vidi. Es izstrādāju visus arhitektūras risinājumus. Kopīga biznesa loģika, datu slāņi ar SQLCipher šifrēšanu, domēna modeļi. Viss vienreiz uzrakstīts Kotlin valodā un kompilēts statiskā Kotlin/Native frameworkā. iOS komanda to integrēja, nepievienojot nevienu linkera karodziņu. Tagad Android un iOS darbojas uz vienas Kotlin kodbāzes. Ražošanas vidē. Piegādāts reāliem lietotājiem.\n\nEsmu arī izlaidis ražošanas lietotnes trīs ļoti atšķirīgās jomās. AI platforma ar reāllaika čatu, laika joslām un push paziņojumiem. Spēļu lietotne ar NFC, biometrisko autentifikāciju, bezskaidras naudas maksājumiem un vairāku lietotņu izvietošanu ar balto zīmolu. Un Parrot — daudzplatformu EPUB lasītājs un audio grāmatu atskaņotājs ar sinhronizāciju ar skaļas lasīšanas funkciju, e-ink atbalstu un lasīšanas analītiku. Katra no tām virzīja KMP citā virzienā. Vienā gadījumā — kopīgi tīkla un datubāzes slāņi. Citā gadījumā — pilnībā kopīga lietotāja saskarne ar Compose Multiplatform."
    )

    data object ExperienceContactBody : StringRes(
        en = "I'm open to senior mobile engineering roles, KMP migration projects, and Android architecture consulting. Based in Slovenia, available for remote work across the EU. Email me at rok.retar@gmail.com. Full-time role, migration assessment, or just a technical conversation about Kotlin Multiplatform. All welcome.",
        sl = "Zainteresiran sem za vodilne inženirske položaje na področju mobilnih tehnologij, projekte migracije KMP ter svetovanje na področju arhitekture sistema Android. Prebivam v Sloveniji in sem na voljo za delo na daljavo po vsej EU. Pišite mi na rok.retar@gmail.com. Bodisi za polno zaposlitev, oceno migracije ali pa zgolj za strokovni pogovor o Kotlin Multiplatform – vse je dobrodošlo.",
        lv = "Esmu ieinteresēts vecāko mobilo inženieru amatos, KMP migrācijas projektos un konsultācijās par Android arhitektūru. Atrodos Slovēnijā, esmu pieejams attālinātajam darbam visā ES. Rakstiet man uz e-pastu rok.retar@gmail.com. Pilna laika darbs, migrācijas novērtējums vai vienkārši tehniska saruna par Kotlin Multiplatform — viss ir laipni gaidīts."
    )
}
