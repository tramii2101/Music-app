package com.example.musicapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.api.ApiHelper
import com.example.musicapp.api.response.ListSongBySingerResponse
import com.example.musicapp.api.response.ListSongHomeResponse
import com.example.musicapp.api.response.MusicBySingerData
import com.example.musicapp.api.response.ResultSongAtHome
import com.example.musicapp.api.response.SongData
import com.example.musicapp.api.response.SongResponse
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class SongViewModel : ViewModel() {
    var message = ""
    var song: SongData? = null
    var listSongAtHome = mutableListOf<ResultSongAtHome>()
    var listSongBySinger = arrayListOf<MusicBySingerData>()

    private val _loading = MutableLiveData(/* value = */ true)
    val loading: LiveData<Boolean>
        get() = _loading

    fun getSong(accessToken: String, id: String) {
        viewModelScope.launch {
            try {
                ApiHelper.getInstance().getSongById(accessToken, id).enqueue(
                    object : Callback<SongResponse> {
                        override fun onResponse(
                            call: Call<SongResponse>,
                            response: Response<SongResponse>
                        ) {
                            if (response.isSuccessful && response.body() != null) {
                                song = response.body()!!.data
                                _loading.value = false
                            } else {
                                message =
                                    JSONObject(response.errorBody()!!.string()).getString("message")
                                _loading.value = false
                            }
                        }

                        override fun onFailure(call: Call<SongResponse>, t: Throwable) {
                            message = "Oops! Something went wrong..."
                            _loading.value = false
                        }

                    }
                )
            } catch (ex: HttpException) {
                message = ex.response()?.errorBody()?.string()
                    ?.let { JSONObject(it).getString("message") }.toString()
                _loading.value = false
            }
        }
    }

    fun getListSong(accessToken: String) {
        viewModelScope.launch {
            try {
                ApiHelper.getInstance().getListSong(accessToken).enqueue(
                    object : Callback<ListSongHomeResponse> {
                        override fun onResponse(
                            call: Call<ListSongHomeResponse>,
                            response: Response<ListSongHomeResponse>
                        ) {
                            if (response.isSuccessful && response.body() != null) {
                                listSongAtHome = response.body()!!.data!!.results
                                _loading.value = false
                            } else {
                                message =
                                    JSONObject(response.errorBody()!!.string()).getString("message")
                                _loading.value = false
                            }
                        }

                        override fun onFailure(call: Call<ListSongHomeResponse>, t: Throwable) {
                            message = "Oops! Something went wrong..."
                            _loading.value = false
                        }
                    }
                )

            } catch (ex: HttpException) {
                message = ex.response()?.errorBody()?.string()
                    ?.let { JSONObject(it).getString("message") }.toString()
                _loading.value = false
            }
        }
    }

    fun getListSongBySinger(accessToken: String, singerId: String) {
        viewModelScope.launch {
            try {
                ApiHelper.getInstance().getSongBySinger(accessToken, "singerId").enqueue(
                    object : Callback<ListSongBySingerResponse> {
                        override fun onResponse(
                            call: Call<ListSongBySingerResponse>,
                            response: Response<ListSongBySingerResponse>
                        ) {
                            if (response.isSuccessful && response.body() != null) {
                                listSongBySinger = response.body()!!.data!!.musics
                                _loading.value = false
                            } else {
                                message =
                                    JSONObject(response.errorBody()!!.string()).getString("message")
                                _loading.value = false
                            }
                         }

                        override fun onFailure(call: Call<ListSongBySingerResponse>, t: Throwable) {
                            message = "Oops! Something went wrong..."
                            _loading.value = false
                        }

                    }
                )

            }catch (ex: HttpException) {
                message = ex.response()?.errorBody()?.string()
                    ?.let { JSONObject(it).getString("message") }.toString()
                _loading.value = false
            }
        }
    }
}