package com.example.musicapp.api

import com.example.musicapp.api.request.LoginRequest
import com.example.musicapp.api.request.RegisterRequest
import com.example.musicapp.api.response.ListCategoriesResponse
import com.example.musicapp.api.response.ListSingerResponse
import com.example.musicapp.api.response.ListSongBySingerResponse
import com.example.musicapp.api.response.ListSongHomeResponse
import com.example.musicapp.api.response.ListSongInCategoryResponse
import com.example.musicapp.api.response.LoginResponse
import com.example.musicapp.api.response.RegisterResponse
import com.example.musicapp.api.response.SearchResponse
import com.example.musicapp.api.response.SingerDetailResponse
import com.example.musicapp.api.response.SongResponse
import com.example.musicapp.api.response.UserProfileResponse
import com.example.musicapp.model.User
import com.example.musicapp.utils.common.ApiConstants
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

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
        @Header("Authorization") accessToken: String, @Path("ID") id: String
    ): Call<SongResponse>

    @GET(ApiConstants.GET_LIST_CATEGORIES)
    fun getListCategories(@Header("Authorization") accessToken: String): Call<ListCategoriesResponse>

    @GET(ApiConstants.GET_LIST_SINGER)
    fun getListSingers(@Header("Authorization") accessToken: String): Call<ListSingerResponse>

    @GET(ApiConstants.GET_LIST_SONG)
    fun getListSong(@Header("Authorization") accessToken: String): Call<ListSongHomeResponse>

    @GET(ApiConstants.GET_SINGER_BY_ID)
    fun getSingerById(
        @Header("Authorization") accessToken: String,
        @Path("ID") singerId: String
    ): Call<SingerDetailResponse>

    @GET(ApiConstants.GET_SONG_BY_SINGER_ID)
    fun getSongBySinger(
        @Header("Authorization") accessToken: String,
        @Path("singerId") singerId: String
    ): Call<ListSongBySingerResponse>

    @GET(ApiConstants.GET_SONG_IN_CATEGORY)
    fun getSongByCategory(
        @Header("Authorization") accessToken: String,
        @Path("categoryId") categoryId: String
    ): Call<ListSongInCategoryResponse>

    @GET(ApiConstants.SEARCH)
    fun search(
        @Header("Authorization") accessToken: String,
        @Query("keyword") keyword: String
    ): Call<SearchResponse>

    @GET(ApiConstants.USER_PROFILE)
    fun getUserProfile(@Header("Authorization") accessToken: String): Call<UserProfileResponse>

}