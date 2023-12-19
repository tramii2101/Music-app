package com.example.musicapp.api.response

import com.example.musicapp.model.Singer
import com.google.gson.annotations.SerializedName

data class SingerDetailResponse(
    @SerializedName("status") var status: Int?,
    @SerializedName("message") var message: String?,
    @SerializedName("data") var data: Singer?
)
