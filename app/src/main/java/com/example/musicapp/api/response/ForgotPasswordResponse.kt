package com.example.musicapp.api.response

import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponse(
    @SerializedName("status"         ) var status         : Int?    = null,
    @SerializedName("message"        ) var message        : String? = null,
    @SerializedName("tokenVerifyOTP" ) var tokenVerifyOTP : String? = null
)
