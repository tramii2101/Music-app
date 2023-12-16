package com.example.musicapp.utils.common

object ApiConstants {
    const val BASE_URL = "https://hitmusic.tech"

    // Auth API
    const val REGISTER = "/api/auth/register"
    const val LOGIN = "/api/auth/login"
    const val GET_PROFILE = "/api/auth/profile"
    const val UPDATE_PROFILE = "/api/auth/profile"
    const val CHANGE_PASSWORD = "/api/auth/change-password"
    const val FORGOT_PASSWORD = "/api/auth/forgot-password"
    const val VERIFY_OTP = "/api/auth/verify-otp"
    const val RESET_PASSWORD = "/api/auth/reset-password"

    // Music API
    const val GET_SONG_BY_ID = "/api/musics/{ID}"
    const val GET_LIST_SONG = "/api/musics"
    const val GET_LIST_CATEGORIES = "/api/categories"
    const val GET_LIST_SINGER = "/api/singers"

}