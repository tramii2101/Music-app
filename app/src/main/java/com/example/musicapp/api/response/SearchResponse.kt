package com.example.musicapp.api.response

import com.example.musicapp.model.Category
import com.example.musicapp.model.Singer
import com.example.musicapp.model.Song
import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("status"  ) val status  : Int?   ,
    @SerializedName("message" ) val message : String?,
    @SerializedName("data"    ) val data : SearchResult?
)

data class SearchResult(
    @SerializedName("musics"  ) val musics: ArrayList<SongResult>?,
    @SerializedName("singers"  ) val singers: ArrayList<Singer>?,
    @SerializedName("categories"  ) val categories: ArrayList<Category>?,
)

data class SongResult(
    @SerializedName("singer"      ) val singer      : SingerOfSong? ,
    @SerializedName("title"       ) val title       : String?,
    @SerializedName("description" ) val description : String?,
    @SerializedName("country"     ) val country     : String?,
    @SerializedName("image"       ) val image       : String?,
    @SerializedName("audio"       ) val audio       : String?,
    @SerializedName("year"        ) val year        : Int?   ,
    @SerializedName("length"      ) val length      : Int?   ,
    @SerializedName("playCount"   ) var playCount   : Int?   ,
    @SerializedName("category"    ) val category    : String?,
    @SerializedName("createdAt"   ) val createdAt   : String?,
    @SerializedName("id"          ) val id          : String?
)
