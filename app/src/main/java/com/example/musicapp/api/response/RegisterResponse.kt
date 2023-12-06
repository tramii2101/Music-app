package com.example.musicapp.api.response

data class RegisterResponse<out T>(val status: Int, val message: String, val data: T)