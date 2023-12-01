package com.example.musicapp.api

import com.example.musicapp.utils.common.ApiConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiHelper {
    fun getInstance(): ApiService {
        val api by lazy {
            Retrofit.Builder().baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(ApiService::class.java)
        }
        return api
    }
}