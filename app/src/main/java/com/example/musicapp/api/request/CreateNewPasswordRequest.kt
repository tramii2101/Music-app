package com.example.musicapp.api.request

import com.google.gson.annotations.SerializedName

data class CreateNewPasswordRequest(
    @SerializedName("newPassword"        ) var newPassword        : String? = null,
    @SerializedName("tokenResetPassword" ) var tokenResetPassword : String? = null
)
