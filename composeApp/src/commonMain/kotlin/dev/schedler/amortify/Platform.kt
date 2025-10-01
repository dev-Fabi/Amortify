package dev.schedler.amortify

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform