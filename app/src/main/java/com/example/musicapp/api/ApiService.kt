package com.example.musicapp.api

import com.example.musicapp.api.request.LoginRequest
import com.example.musicapp.api.request.RegisterRequest
import com.example.musicapp.api.response.LoginResponse
import com.example.musicapp.api.response.RegisterResponse
import com.example.musicapp.model.User
import com.example.musicapp.utils.common.ApiConstants
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST(ApiConstants.LOGIN)
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @POST(ApiConstants.REGISTER)
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Call<RegisterResponse<User>>

}