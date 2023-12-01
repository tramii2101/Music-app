package com.example.musicapp.utils.common

object RegexCommon {
    val USERNAME = "^[a-zA-Z0-9]{6,20}\$".toRegex()
    val EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$".toRegex()
    val PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}\$".toRegex()
}