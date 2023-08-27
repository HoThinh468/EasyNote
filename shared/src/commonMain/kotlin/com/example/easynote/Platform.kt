package com.example.easynote

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform