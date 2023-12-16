package com.example.musicapp.api.response

import com.example.musicapp.model.Category
import com.google.gson.annotations.SerializedName

data class ListCategoriesResponse(
    @SerializedName("status") val status: Int? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("data") val data: ListCategoryData? = null
)

data class ListCategoryData(
    @SerializedName("results"      ) var results      : ArrayList<Category> = arrayListOf(),
)