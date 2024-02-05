package com.example.musicapp.api.request

import com.google.gson.annotations.SerializedName

data class ForgotPasswordRequest(
    @SerializedName("email") val email: String,
)
