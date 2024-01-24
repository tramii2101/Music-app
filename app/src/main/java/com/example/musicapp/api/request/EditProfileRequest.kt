package com.example.musicapp.api.request

import com.google.gson.annotations.SerializedName

data class EditProfileRequest(
    @SerializedName("email") val email: String?,
    @SerializedName("fullname") val fullname: String?,
    @SerializedName("dateOfBirth") val dateOfBirth: String?,
    @SerializedName("gender") val gender: String?,
)