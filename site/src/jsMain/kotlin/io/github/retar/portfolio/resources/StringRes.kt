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
        sl = "Gradim sisteme za Android in Kotlin Multiplatform — vključno s selitvijo iOS aplikacije podjetja v skupno Kotlin kodo.",
        lv = "Es veidoju Android un Kotlin Multiplatform sistēmas — tostarp migrēju uzņēmuma iOS lietotni uz kopīgu Kotlin kodu."
    )

    data object HeroSubtitle : StringRes(
        en = "Mobile development rooted in the Android ecosystem. I take Kotlin Multiplatform to production — one codebase serving two platforms without surrendering native feel. This page is built natively in Kotlin.",
        sl = "Mobilni razvoj, zakoreninjen v ekosistemu Android. Kotlin Multiplatform peljem v produkcijo — ena koda baza, ki služi dvema platformama, ne da bi žrtvovala občutek izvornosti. Ta stran je zgrajena izvorno v Kotlinu.",
        lv = "Mobilā izstrāde, kas sakņojas Android ekosistēmā. Kotlin Multiplatform es vedu ražošanā — viena kodbāze, kas apkalpo divas platformas, nezaudējot vietējo sajūtu. Šī lapa ir būvēta vietēji Kotlinā."
    )

    data object HeroStatusStrip : StringRes(
        en = "Kotlin · KMP · Android · Kobweb / Compose for Web",
        sl = "Kotlin · KMP · Android · Kobweb / Compose for Web",
        lv = "Kotlin · KMP · Android · Kobweb / Compose for Web"
    )

    data object CtaReviewWork : StringRes(
        en = "Review the Work",
        sl = "Preglej delo",
        lv = "Apskatīt darbus"
    )

    data object CtaDownloadResume : StringRes(
        en = "Download Resume",
        sl = "Prenesi življenjepis",
        lv = "Lejupielādēt CV"
    )

    data object CtaReadNotes : StringRes(
        en = "Engineering notes",
        sl = "Inženirske zabeležke",
        lv = "Inženierijas piezīmes"
    )

    data object Descriptor : StringRes(
        en = "Mobile Developer",
        sl = "Mobilni razvijalec",
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
        sl = "Ena koda baza. Dve platformi. Izvorna disciplina nespremenjena.",
        lv = "Viena kodbāze. Divas platformas. Vietējā disciplīna neskarta."
    )

    data object PhilosophyBody1 : StringRes(
        en = "My specialization is the Android ecosystem and Kotlin Multiplatform. I treat the codebase as the source of truth: shared business logic, shared data layers, shared domain models — with UI remaining native where it earns its keep.",
        sl = "Moja specializacija je ekosistem Android in Kotlin Multiplatform. Kodo dojemam kot vir resnice: skupna poslovna logika, skupne podatkovne plasti, skupni domenski modeli — UI ostane izvoren tam, kjer to opraviči svojo ceno.",
        lv = "Mana specializācija ir Android ekosistēma un Kotlin Multiplatform. Es uzskatu kodbāzi par patiesības avotu: kopīga biznesa loģika, kopīgi datu slāņi, kopīgi domēna modeļi — UI paliek vietējs tur, kur tas attaisno sevi."
    )

    data object PhilosophyBody2 : StringRes(
        en = "I architected and executed a complete corporate migration, transitioning production systems to Kotlin Multiplatform for iOS. Shared Kotlin now backs an iOS app in the field — not a prototype, not a branch, a shipped product.",
        sl = "Arhiviral in izvedel sem popolno selitev podjetja, prehod produkcijskih sistemov na Kotlin Multiplatform za iOS. Skupni Kotlin zdaj pogan iOS aplikacijo v praksi — ne prototip, ne veja, odposlani produkt.",
        lv = "Es arhitektēju un izpildīju pilnīgu uzņēmuma migrāciju, pārejot ražošanas sistēmas uz Kotlin Multiplatform iOS vajadzībām. Kopīgais Kotlin tagad atbalsta iOS lietotni prakse — ne prototips, ne zars, nosūtīts produkts."
    )

    data object PhilosophyBody3 : StringRes(
        en = "The discipline is architectural, not ideological. Shared code goes where it reduces drift and risk; platform code stays where it delivers native performance and feel. The boundary is deliberate, tested, and enforced by module structure — domain never depends on data or UI.",
        sl = "Disciplina je arhitekturna, ne ideološka. Skupna koda gre tam, kjer zmanjša odmik in tveganje; platformna koda ostane tam, kjer prinaša izvorno zmogljivost in občutek. Meja je premišljena, preizkušena in vsiljena s strukturo modulov — domena nikoli ni odvisna od podatkov ali UI.",
        lv = "Disciplīna ir arhitektoniska, ne ideoloģiska. Kopīgais kods iet tur, kur tas mazina novirzi un risku; platformas kods paliek tur, kur tas sniedz vietējo veiktspēju un sajūtu. Robeža ir apzināta, pārbaudīta un uzspiesta ar moduļu struktūru — domēns nekad nav atkarīgs no datiem vai UI."
    )

    data object PhilosophyPrinciple1 : StringRes(
        en = "Shared domain and data layers; native UI where the platform earns it.",
        sl = "Skupna domenska in podatkovna plast; izvoren UI tam, kjer platforma to zasluži.",
        lv = "Kopīgs domēna un datu slānis; vietējais UI tur, kur platforma to pelna."
    )

    data object PhilosophyPrinciple2 : StringRes(
        en = "Module boundaries that enforce dependency direction — domain stays pure Kotlin.",
        sl = "Meje modulov, ki vsiljujejo smer odvisnosti — domena ostane čisti Kotlin.",
        lv = "Moduļu robežas, kas uzspiež atkarību virzienu — domēns paliek tīrs Kotlin."
    )

    data object PhilosophyPrinciple3 : StringRes(
        en = "KMP taken to production iOS, not demoed on a branch.",
        sl = "KMP pripeljan v produkcijski iOS, ne predstavljen na veji.",
        lv = "KMP aizvests līdz ražošanas iOS, ne demonstrēts zarā."
    )

    data object PhilosophyPrinciple4 : StringRes(
        en = "The portfolio itself is built natively in Kotlin — the medium is the evidence.",
        sl = "Samo portfelj je zgrajen izvorno v Kotlinu — medij je dokaz.",
        lv = "Pats portfelis ir būvēts vietēji Kotlinā — vide ir pierādījums."
    )

    data object MigrationCalloutLabel : StringRes(
        en = "// migration",
        sl = "// selitev",
        lv = "// migrācija"
    )

    data object MigrationCalloutBody : StringRes(
        en = "Production iOS now runs on shared Kotlin. The migration is complete and shipped — Android and iOS backed by a single Kotlin codebase.",
        sl = "Produkcijski iOS zdaj teče na skupnem Kotlinu. Selitev je popolna in odposlana — Android in iOS na eni sami Kotlin koda bazi.",
        lv = "Ražošanas iOS tagad darbojas uz kopīgā Kotlin. Migrācija ir pabeigta un nosūtīta — Android un iOS uz vienas Kotlin kodbāzes."
    )

    data object ParrotEyebrow : StringRes(
        en = "// 02 — signature work",
        sl = "// 02 — zaščitno delo",
        lv = "// 02 — paraksta darbs"
    )

    data object ParrotHeading : StringRes(
        en = "Parrot",
        sl = "Parrot",
        lv = "Parrot"
    )

    data object ParrotSubhead : StringRes(
        en = "A lightweight EPUB e-reader and audiobook player — the companion app for Storyteller, built for readers who want a calibrated, immersive experience over generic digital layouts.",
        sl = "Lahek bralnik EPUB in predvajalnik zvočnih knjig — spremljevalna aplikacija za Storyteller, zgrajena za bralce, ki želijo umerjeno, poglobljeno izkušnjo namesto generičnih digitalnih postavitev.",
        lv = "Viegls EPUB lasītājs un audio grāmatu atskaņotājs — Storyteller pavadājuma lietotne, būvēta lasītājiem, kas vēlas kalibrētu, iegrimušu pieredzi nevis vispārīgu digitālu izkārtojumu."
    )

    data object ParrotFeature1Title : StringRes(
        en = "Typography & text control",
        sl = "Tipografija in nadzor besedila",
        lv = "Tipogrāfija un teksta kontrole"
    )

    data object ParrotFeature1Desc : StringRes(
        en = "User-defined font configurations and pinch-to-zoom text sizing. Every reader calibrates type, size, margins, and line height to their own ergonomics.",
        sl = "Uporabniško določene nastavitve pisave in povečevanje besedila s približevanjem. Vsak bralec umerja pisavo, velikost, robove in višino vrstice svoji ergonomiji.",
        lv = "Lietotāja definēti fontu iestatījumi un teksta izmēra pielāgošana ar kniebšanu. Katrs lasītājs kalibrē fontu, izmēru, malas un rindas augstumu savai ergonomikai."
    )

    data object ParrotFeature2Title : StringRes(
        en = "Reading modes",
        sl = "Bralni načini",
        lv = "Lasīšanas režīmi"
    )

    data object ParrotFeature2Desc : StringRes(
        en = "Intuitive page-flip navigation and a true fullscreen mode that strips away every chrome element, leaving only the text. Dark, sepia, and a dedicated e-ink display mode.",
        sl = "Intuitivno listanje strani in pravi celozaslonski način, ki odstrani vsak element vmesnika, tako da ostane samo besedilo. Temni, sepia in namenski način za e-ink zaslon.",
        lv = "Intuitīva lapu pāršķiršana un īsts pilnekrāna režīms, kas noņem katru hromēta elementu, atstājot tikai tekstu. Tumšais, sēpija un īpašs e-ink displeja režīms."
    )

    data object ParrotFeature3Title : StringRes(
        en = "E-ink adaptation",
        sl = "E-ink prilagoditev",
        lv = "E-ink adaptācija"
    )

    data object ParrotFeature3Desc : StringRes(
        en = "A display mode built specifically for e-ink screens — high contrast, no decorative motion, refresh behavior that respects the hardware. Crisp and natural on specialized devices.",
        sl = "Prikazovalni način, zgrajen posebej za e-ink zaslone — visok kontrast, brez okrasnega gibanja, obnašanje osveževanja, ki spoštuje strojno opremo. Ostrina in naravnost na specializiranih napravah.",
        lv = "Displeja režīms, būvēts īpaši e-ink ekrāniem — augsts kontrasts, bez dekoratīvas kustības, atsvaidzināšanas uzvedība, kas ciena aparatūru. Ass un dabisks uz specializētām ierīcēm."
    )

    data object ParrotFeature4Title : StringRes(
        en = "Background audio",
        sl = "Zvočno v ozadju",
        lv = "Fona audio"
    )

    data object ParrotFeature4Desc : StringRes(
        en = "Audiobook playback that continues in the background with persistent system notification controls. Skip chapters, scrub, and set speed without ever opening the app.",
        sl = "Predvajanje zvočnih knjig, ki teče v ozadju z vztrajnimi sistemskimi obvestili. Preskakuj poglavja, premikaj se in nastavi hitrost, ne da bi odprl aplikacijo.",
        lv = "Audio grāmatu atskaņošana, kas turpinās fonā ar noturīgiem sistēmas paziņojumu vadības elementiem. Pārlekšanās nodaļas, ritināšana un ātruma iestatīšana bez lietotnes atvēršanas."
    )

    data object ParrotFeature5Title : StringRes(
        en = "Read-Aloud sync",
        sl = "Read-Aloud usklajevanje",
        lv = "Read-Aloud sinhronizācija"
    )

    data object ParrotFeature5Desc : StringRes(
        en = "Real-time, word-by-word highlighted text synchronization. As the narrator speaks, the text tracks the audio precisely — for language learners, developing readers, and anyone who reads with their ears.",
        sl = "Sinhronizacija besedila v realnem času, beseda za besedo z osvetlitvijo. Ko pripovedovalec govori, besedilo natančno sledi zvoku — za učence jezikov, bralce v razvoju in vsakogar, ki bere z ušesi.",
        lv = "Reāllaika, vārds pa vārdam izceltas teksta sinhronizācija. Kad runātājs runā, teksts precīzi seko audio — valodu apguvējiem, attīstošiem lasītājiem un ikvienam, kas lasa ar ausīm."
    )

    data object ParrotFeature6Title : StringRes(
        en = "Reading analytics",
        sl = "Bralna analitika",
        lv = "Lasīšanas analītika"
    )

    data object ParrotFeature6Desc : StringRes(
        en = "Automated local background analytics that map reading durations and calculate personalized reading-speed estimates per chapter. No servers, no telemetry — the math runs on-device.",
        sl = "Avtomatizirana lokalna analitika v ozadju, ki preslika trajanja branja in izračuna prilagojene ocene hitrosti branja na poglavje. Brez strežnikov, brez telemetrije — matematika teče na napravi.",
        lv = "Automatizēta vietējā fona analītika, kas kartē lasīšanas ilgumus un aprēķina personalizētus lasīšanas ātruma novērtējumus uz nodaļu. Bez serveriem, bez telemetrijas — matemātika darbojas ierīcē."
    )

    data object ParrotFeature7Title : StringRes(
        en = "Companion architecture",
        sl = "Spremljevalna arhitektura",
        lv = "Pavadājuma arhitektūra"
    )

    data object ParrotFeature7Desc : StringRes(
        en = "Built as the companion client for Storyteller. Reading position and progress sync so a reader moves between phone, tablet, and desktop without losing their place.",
        sl = "Zgrajen kot spremljevalni odjemalec za Storyteller. Položaj in napredek branja se sinhronizirata, tako da se bralec premika med telefonom, tablico in namizjem, ne da bi izgubil mesto.",
        lv = "Būvēts kā Storyteller pavadājuma klients. Lasīšanas pozīcija un progress sinhronizējas, lai lasītājs pārvietojas starp telefonu, planšeti un datoru, nezaudējot vietu."
    )

    data object CtaOpenCaseStudy : StringRes(
        en = "Open the Parrot case study",
        sl = "Odpri študijo primera Parrot",
        lv = "Atvērt Parrot gadījuma izpēti"
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
        sl = "Občasne poglobljene tehniške zabeležke o tem, kako gradim — Kobweb, Compose in Kotlin pod pokrovom.",
        lv = "Reizēm padziļināti tehniski raksti par to, kā es būvēju — Kobweb, Compose un Kotlin zem vāka."
    )

    data object ContactEyebrow : StringRes(
        en = "// 03 — contact",
        sl = "// 03 — kontakt",
        lv = "// 03 — kontakti"
    )

    data object ContactHeading : StringRes(
        en = "Let's build something precise.",
        sl = "Zgradiva nekaj natančnega.",
        lv = "Uzbūvēsim kaut ko precīzu."
    )

    data object ContactBody : StringRes(
        en = "I'm open to senior mobile engineering roles, KMP migrations, and Android architecture work. The fastest path is email.",
        sl = "Odprt sem za višje mobilno-inženirske vloge, selitve KMP in arhitekturno delo na Androidu. Najhitrejša pot je e-pošta.",
        lv = "Esmu atvērts seniora mobilo lietotņu inženiera lomām, KMP migrācijām un Android arhitektūras darbam. Ātrākais ceļš ir e-pasts."
    )

    data object CtaEmail : StringRes(
        en = "Email Rok Retar",
        sl = "Pošlji e-pošto Rok Retar",
        lv = "Sūtīt e-pastu Rok Retar"
    )

    data object FooterLinks : StringRes(
        en = "GitHub · LinkedIn · rok.retar@gmail.com",
        sl = "GitHub · LinkedIn · rok.retar@gmail.com",
        lv = "GitHub · LinkedIn · rok.retar@gmail.com"
    )

    data object FooterMeta : StringRes(
        en = "Built natively in Kotlin with Kobweb",
        sl = "Zgrajeno izvorno v Kotlinu s Kobweb",
        lv = "Būvēts vietēji Kotlinā ar Kobweb"
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
        sl = "Bralnik Parrot v svetlem načinu s poljubno tipografijo in robovi",
        lv = "Lasītājs Parrot gaišajā režīmā ar pielāgotu tipogrāfiju un malām"
    )

    data object ParrotScreenshot2Desc : StringRes(
        en = "Parrot e-reader in dark fullscreen mode with distraction-free reading",
        sl = "Bralnik Parrot v temnem celozaslonskem načinu za nemoteno branje",
        lv = "Lasītājs Parrot tumšajā pilnekrāna režīmā bez traucējumiem"
    )

    data object ParrotScreenshot3Desc : StringRes(
        en = "Parrot audiobook player with background notification and chapter controls",
        sl = "Predvajalnik zvočnih knjig Parrot z obvestili v ozadju in kontrolami poglavij",
        lv = "Audio grāmatu atskaņotājs Parrot ar fona paziņojumiem un nodaļu vadību"
    )

    data object ParrotScreenshot4Desc : StringRes(
        en = "Parrot e-ink display mode on a specialized reader device",
        sl = "Način za e-ink zaslon na specializirani bralni napravi",
        lv = "E-ink displeja režīms uz specializētas lasīšanas ierīces"
    )

    data object ParrotScreenshot5Desc : StringRes(
        en = "Parrot Read-Aloud sync with word-by-word text highlighting",
        sl = "Sinhronizacija Read-Aloud z osvetljevanjem besede za besedo",
        lv = "Read-Aloud sinhronizācija ar vārds pa vārdam izcelšanu"
    )

    data object InfiniteCarouselArticleTitle : StringRes(
        en = "Building a Smooth, Infinite Carousel in Kobweb",
        sl = "Gradnja gladkega, neskončnega vrtiljaka v Kobwebu",
        lv = "Gludas, bezgalīgas karuseļa komponentes izveide Kobweb"
    )

    data object InfiniteCarouselArticleDesc : StringRes(
        en = "How to build an infinite, gapless carousel using Silk and Kobweb.",
        sl = "Kako zgraditi neskončen, brezšivni vrtiljak z uporabo Silk in Kobweb.",
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
        sl = "Lahek bralnik EPUB in predvajalnik zvočnih knjig — spremljevalna aplikacija za Storyteller, zgrajena za bralce, ki želijo umerjeno, poglobljeno izkušnjo namesto generičnih digitalnih postavitev.",
        lv = "Viegls EPUB lasītājs un audio grāmatu atskaņotājs — Storyteller pavadājuma lietotne, būvēta lasītājiem, kas vēlas kalibrētu, iegrimušu pieredzi nevis vispārīgu digitālu izkārtojumu."
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
        sl = "Vsak bralec umerja drugače. Parrot izpostavlja uporabniško določene nastavitve pisave in povečevanje besedila s približevanjem, tako da se pisava, velikost, robovi in višina vrstice prilagajajo ergonomiji bralca — ne generični privzeti vrednosti.\n\nNavigacija ostaja ob strani: tapni robove zaslona za listanje strani, podrsni skozi poglavja ali skoči iz kazala. Pravi celozaslonski način odstrani vsak element vmesnika in pusti samo besedilo. Temni, sepia in namenski način za e-ink zaslon poskrbijo za preostanek.",
        lv = "Katrs lasītājs kalibrē atšķirīgi. Parrot atklāj lietotāja definētus fontu iestatījumus un teksta izmēra pielāgošanu ar kniebšanu, lai fonts, izmērs, malas un rindas augstums pielāgotos lasītāja ergonomikai — ne vispārīgam noklusējumam.\n\nNavigācija netraucē: pieskaries ekrāna malām, lai šķirtu lapas, slaidi cauri nodaļām vai lec no satura rādītāja. Īsts pilnekrāna režīms noņem katru hromēto elementu, atstājot tikai tekstu. Tumšais, sēpija un īpašais e-ink displeja režīms parūpējas par pārējo."
    )

    data object ParrotFeature2PageTitle : StringRes(
        en = "Background Audio & Read-Aloud Sync",
        sl = "Zvok v ozadju in sinhronizacija Read-Aloud",
        lv = "Fona audio un Read-Aloud sinhronizācija"
    )

    data object ParrotFeature2PageDesc : StringRes(
        en = "Audiobook playback continues in the background with persistent system notification controls — skip chapters, scrub, and set speed without opening the app.\n\nThe Read-Aloud system is where reading and listening converge. As the narrator speaks, the text highlights in real time, word by word, tracking the audio precisely. It's built for language learners, developing readers, and anyone who reads with their ears. Highlight color and style are configurable; narration speed scales to taste.",
        sl = "Predvajanje zvočnih knjig teče v ozadju z vztrajnimi sistemskimi obvestili — preskakuj poglavja, premikaj se in nastavljaj hitrost, ne da bi odprl aplikacijo.\n\nSistem Read-Aloud je mesto, kjer se branje in poslušanje združita. Ko pripovedovalec govori, se besedilo v realnem času osvetljuje beseda za besedo in natančno sledi zvoku. Zgrajen je za učence jezikov, bralce v razvoju in vsakogar, ki bere z ušesi. Barva in slog osvetlitve sta nastavljiva; hitrost pripovedi se prilagaja okusu.",
        lv = "Audio grāmatu atskaņošana turpinās fonā ar noturīgiem sistēmas paziņojumu Vadības elementiem — pārlec nodaļas, ritini un iestati ātrumu, ne atverot lietotni.\n\nRead-Aloud sistēma ir vieta, kur lasīšana un klausīšanās saplūst. Kad runātājs runā, teksts reāllaikā izceļas vārds pa vārdam, precīzi sekojot audio. Tas ir būvēts valodu apguvējiem, attīstošiem lasītājiem un ikvienam, kas lasa ar ausīm. Izcelšanas krāsa un stils ir konfigurējami; runāšanas ātrums pielāgojas gaumei."
    )

    data object ParrotFeature3PageTitle : StringRes(
        en = "Local Reading Analytics",
        sl = "Lokalna bralna analitika",
        lv = "Vietējā lasīšanas analītika"
    )

    data object ParrotFeature3PageDesc : StringRes(
        en = "Reading habits are mapped locally, on-device, with no servers and no telemetry. Parrot tracks reading durations across days, weeks, and months, and calculates personalized reading-speed estimates per chapter — so you always know whether there's time for one more chapter before bed.",
        sl = "Bralne navade se preslikajo lokalno, na napravi, brez strežnikov in brez telemetrije. Parrot sledi trajanjem branja skozi dni, tedne in mesece ter izračuna prilagojene ocene hitrosti branja na poglavje — tako vedoš, ali je čas še za eno poglavje pred spanjem.",
        lv = "Lasīšanas ieradumi tiek kartēti vietēji, uz ierīces, bez serveriem un bez telemetrijas. Parrot seko lasīšanas ilgumiem cauri dienām, nedēļām un mēnešiem, un aprēķina personalizētus lasīšanas ātruma novērtējumus uz nodaļu — lai vienmēr zinātu, vai ir laiks vēl vienai nodaļai pirms gulētiešanas."
    )

    data object ParrotFeature4PageTitle : StringRes(
        en = "Companion Sync",
        sl = "Sinhronizacija spremljevalca",
        lv = "Pavadājuma sinhronizācija"
    )

    data object ParrotFeature4PageDesc : StringRes(
        en = "Parrot is the companion client for Storyteller. Reading position and progress sync across phone, tablet, and desktop, so a reader picks up exactly where they left off — no searching, no scrolling, just reading.",
        sl = "Parrot je spremljevalni odjemalec za Storyteller. Položaj in napredek branja se sinhronizirata med telefonom, tablico in namizjem, tako da bralec nadaljuje točno tam, kjer je končal — brez iskanja, brez pomikanja, samo branje.",
        lv = "Parrot ir Storyteller pavadājuma klients. Lasīšanas pozīcija un progress sinhronizējas starp telefonu, planšeti un datoru, lai lasītājs turpina tieši tur, kur pārtrauca — bez meklēšanas, bez ritināšanas, tikai lasīšana."
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
}
