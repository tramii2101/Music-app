package com.example.musicapp.model

import com.google.gson.annotations.SerializedName

data class Song(
    @SerializedName("singer"      ) val singerId    : String?,
    @SerializedName("title"       ) val title       : String?,
    @SerializedName("description" ) val description : String?,
    @SerializedName("country"     ) val country     : String?,
    @SerializedName("image"       ) val image       : String?,
    @SerializedName("audio"       ) val audio       : String?,
    @SerializedName("year"        ) val year        : Int?   ,
    @SerializedName("length"      ) val length      : Int?   ,
    @SerializedName("playCount"   ) var playCount   : Int?   ,
    @SerializedName("category"    ) val category    : String?,
    @SerializedName("id"          ) val id          : String?
)