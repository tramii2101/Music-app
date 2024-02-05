package com.example.musicapp.api.request

import com.google.gson.annotations.SerializedName

data class VerifyOtpRequest(
    @SerializedName("otp"            ) var otp            : String?    = null,
    @SerializedName("tokenVerifyOTP" ) var tokenVerifyOTP : String? = null
)
