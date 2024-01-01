package com.example.musicapp.api.response

import com.example.musicapp.model.Singer
import com.google.gson.annotations.SerializedName

data class ListSingerResponse(
    @SerializedName("status") val status: Int? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("data") val data: ListSingerData? = null
)

data class ListSingerData(
    @SerializedName("results") var results: ArrayList<Singer> = arrayListOf(),
)
