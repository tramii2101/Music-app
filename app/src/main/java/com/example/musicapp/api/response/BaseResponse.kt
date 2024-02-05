package com.example.musicapp.api.response

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("status"      ) val status      : Int?    = null,
    @SerializedName("message"     ) val message     : String? = null,
)