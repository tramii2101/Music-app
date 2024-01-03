package com.example.musicapp.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("username"    ) val username    : String,
    @SerializedName("fullname"    ) val fullname    : String,
    @SerializedName("email"       ) val email       : String,
    @SerializedName("avatar"      ) val avatar      : String,
    @SerializedName("dateOfBirth" ) val dateOfBirth : String,
    @SerializedName("gender"      ) val gender      : String,
    @SerializedName("role"        ) val role        : String,
    @SerializedName("activeVip"   ) val activeVip   : Boolean,
    @SerializedName("id"          ) val id          : String)

//    @SerializedName("phoneNumber" ) val phoneNumber : String,