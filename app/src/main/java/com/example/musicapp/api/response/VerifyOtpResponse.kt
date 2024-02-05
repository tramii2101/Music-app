package com.example.musicapp.api.response

import com.google.gson.annotations.SerializedName

data class VerifyOtpResponse(
    @SerializedName("status"             ) var status             : Int?    = null,
    @SerializedName("message"            ) var message            : String? = null,
    @SerializedName("tokenResetPassword" ) var tokenResetPassword : String? = null
)
