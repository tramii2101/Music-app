package com.example.musicapp.api.response

import com.google.gson.annotations.SerializedName

data class ListSongHomeResponse(
    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : DataListSongAtHome?
)

data class ResultSongAtHome(
    @SerializedName("singer"      ) var singerId      : String?,
    @SerializedName("title"       ) var title       : String?,
    @SerializedName("description" ) var description : String?,
    @SerializedName("country"     ) var country     : String?,
    @SerializedName("image"       ) var image       : String?,
    @SerializedName("audio"       ) var audio       : String?,
    @SerializedName("year"        ) var year        : Int?   ,
    @SerializedName("length"      ) var length      : Int?   ,
    @SerializedName("playCount"   ) var playCount   : Int?   ,
    @SerializedName("category"    ) var category    : String?,
    @SerializedName("id"          ) var id          : String?
)
data class DataListSongAtHome(
    @SerializedName("results"      ) var results      : ArrayList<ResultSongAtHome> = arrayListOf(),
)