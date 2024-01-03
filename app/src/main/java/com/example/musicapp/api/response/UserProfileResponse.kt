package com.example.musicapp.api.response

import com.example.musicapp.model.User
import com.google.gson.annotations.SerializedName

data class UserProfileResponse(
    @SerializedName("status"      ) val status      : Int?    = null,
    @SerializedName("message"     ) val message     : String? = null,
    @SerializedName("data"    )     val data    : User
)