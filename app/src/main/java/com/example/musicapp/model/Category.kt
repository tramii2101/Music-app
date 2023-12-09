package com.example.musicapp.model

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("name"  ) val name  : String,
    @SerializedName("image" ) val image : String,
    @SerializedName("id"    ) val id    : String
)
