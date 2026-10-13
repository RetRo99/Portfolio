package io.github.retar.portfolio.resources

enum class ImageRes(
    val path: String,
    val intrinsicWidth: Int? = null,
    val intrinsicHeight: Int? = null,
) {

    OnDeviceTtsArticle("/articles/on_device_tts.webp", 640, 353),
    AppsFlyerArticle("/articles/appsflyer_kmp.webp", 640, 353),
    InfiniteCarouselArticle("/articles/infinite_carousel.webp", 640, 353),
    SQLCipherArticle("/articles/sqlcipher_kotlin_native.webp", 640, 349),

    ParrotIcon("/parrot/parrot-icon.png", 256, 256),
    ParrotScreenshot1("/parrot/parrot-screenshot-1.webp", 600, 1300),
    ParrotScreenshot2("/parrot/parrot-screenshot-2.webp", 600, 1300),
    ParrotScreenshot3("/parrot/parrot-screenshot-3.webp", 600, 1300),
    ParrotScreenshot4("/parrot/parrot-screenshot-4.webp", 600, 1300),
    ParrotScreenshot5("/parrot/parrot-screenshot-5.webp", 600, 1300),

    ProfileImage("/profile/profile_image.webp", 299, 374),
}
