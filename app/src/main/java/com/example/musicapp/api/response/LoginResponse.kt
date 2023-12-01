package com.example.musicapp.api.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("status"      ) val status      : Int?    = null,
    @SerializedName("message"     ) val message     : String? = null,
    @SerializedName("accessToken" ) val accessToken : String? = null
)