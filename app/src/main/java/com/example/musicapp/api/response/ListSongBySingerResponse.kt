package com.example.musicapp.api.response

import com.google.gson.annotations.SerializedName

data class ListSongBySingerResponse(
    @SerializedName("status"  ) val status  : Int?,
    @SerializedName("message" ) val message : String?,
    @SerializedName("data"    ) val data    : ListSongBySingerData?
)

data class MusicData(
    @SerializedName("title"  ) val title  : String?,
    @SerializedName("image"  ) val image  : String?,
    @SerializedName("length" ) val length : Int?,
    @SerializedName("id"     ) val id     : String?
)

data class ListSongBySingerData(
    @SerializedName("singer" ) val singer : String?,
    @SerializedName("musics" ) val musics : ArrayList<MusicData>
)