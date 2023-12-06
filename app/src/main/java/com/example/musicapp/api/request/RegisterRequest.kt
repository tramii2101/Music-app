package com.example.musicapp.api.request

data class RegisterRequest(
    val fullname: String,
    val username: String,
    val email: String,
    val password: String,
    val passwordAgain: String
)