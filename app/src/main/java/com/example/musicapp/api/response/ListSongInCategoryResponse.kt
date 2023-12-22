package com.example.musicapp.api.response

import com.google.gson.annotations.SerializedName

data class ListSongInCategoryResponse(
    @SerializedName("status"  ) val status  : Int?,
    @SerializedName("message" ) val message : String?,
    @SerializedName("data"    ) val data    : ListSongInCategory?
)
data class ListSongInCategory(
    @SerializedName("musics"       ) val musics       : ArrayList<MusicData>,
)