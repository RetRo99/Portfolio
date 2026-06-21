package io.github.retar.portfolio.resources

enum class ImageRes(
    val path: String,
    val intrinsicWidth: Int? = null,
    val intrinsicHeight: Int? = null,
) {
    Bardy1("/bardy/Bardy1.avif"),
    Bardy2("/bardy/Bardy2.avif"),
    Bardy3("/bardy/Bardy3.avif"),

    InfiniteCarouselArticle("/articles/infinite_carousel.jpeg", 1024, 565),
    SQLCipherArticle("/articles/sqlcipher_kotlin_native.jpeg", 1024, 559),

    ParrotIcon("/parrot/parrot.png"),
    ParrotScreenshot1("/parrot/parrot-screenshot-1.jpeg", 945, 2048),
    ParrotScreenshot2("/parrot/parrot-screenshot-2.jpeg", 945, 2048),
    ParrotScreenshot3("/parrot/parrot-screenshot-3.jpeg", 945, 2048),
    ParrotScreenshot4("/parrot/parrot-screenshot-4.jpeg", 945, 2048),
    ParrotScreenshot5("/parrot/parrot-screenshot-5.jpeg", 945, 2048),

    ProfileImage("/profile/profile_image.png", 512, 374),
}
