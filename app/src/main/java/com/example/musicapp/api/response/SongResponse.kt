package com.example.musicapp.api.response

import com.example.musicapp.model.Category
import com.google.gson.annotations.SerializedName

data class SongResponse(
    @SerializedName("status"      ) val status      : Int?    = null,
    @SerializedName("message"     ) val message     : String? = null,
    @SerializedName("data"    )     val data    : SongData
)

data class SongData(
    @SerializedName("singer"      ) val singer      : SingerOfSong,
    @SerializedName("title"       ) val title       : String,
    @SerializedName("description" ) val description : String,
    @SerializedName("country"     ) val country     : String,
    @SerializedName("image"       ) val image       : String,
    @SerializedName("audio"       ) val audio       : String,
    @SerializedName("year"        ) val year        : Int,
    @SerializedName("length"      ) val length      : Int,
    @SerializedName("playCount"   ) var playCount   : Int = 0,
    @SerializedName("category"    ) val category    : Category,
    @SerializedName("id"          ) val id          : String
)
data class SingerOfSong(
    @SerializedName("fullname" ) var fullname : String? = null,
    @SerializedName("avatar"   ) var avatar   : String? = null,
    @SerializedName("id"       ) var id       : String? = null
)
