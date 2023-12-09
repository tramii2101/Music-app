package com.example.musicapp.api

import com.example.musicapp.api.request.LoginRequest
import com.example.musicapp.api.request.RegisterRequest
import com.example.musicapp.api.response.LoginResponse
import com.example.musicapp.api.response.RegisterResponse
import com.example.musicapp.api.response.SongResponse
import com.example.musicapp.model.User
import com.example.musicapp.utils.common.ApiConstants
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST(ApiConstants.LOGIN)
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @POST(ApiConstants.REGISTER)
    fun register(
        @Body registerRequest: RegisterRequest
    ): Call<RegisterResponse<User>>

    @GET(ApiConstants.GET_SONG_BY_ID)
    fun getSongById(
        @Header("Authorization") accessToken: String,
        @Path("ID") id: String
    ): Call<SongResponse>

}