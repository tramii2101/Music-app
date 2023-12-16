package com.example.musicapp.model

import com.google.gson.annotations.SerializedName

data class Singer(
    @SerializedName("fullname"    ) val fullname    : String?,
    @SerializedName("birthday"    ) val birthday    : String?,
    @SerializedName("gender"      ) val gender      : String?,
    @SerializedName("avatar"      ) val avatar      : String?,
    @SerializedName("description" ) val description : String?,
    @SerializedName("id"          ) val id          : String?
)
