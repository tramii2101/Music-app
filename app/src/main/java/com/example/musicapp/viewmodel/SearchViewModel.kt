package com.example.musicapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.api.ApiHelper
import com.example.musicapp.api.response.SearchResponse
import com.example.musicapp.api.response.SongResult
import com.example.musicapp.model.Category
import com.example.musicapp.model.Singer
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class SearchViewModel : ViewModel() {
    var message = ""
    var listSong = mutableListOf<SongResult>()
    var listSinger = mutableListOf<Singer>()
    var listCategory = mutableListOf<Category>()

    private val _loading = MutableLiveData(/* value = */ true)
    val loading: LiveData<Boolean>
        get() = _loading

    fun search(accessToken: String, keyword: String) {
        viewModelScope.launch {
            try {
                ApiHelper.getInstance().search(accessToken, keyword).enqueue(
                    object : Callback<SearchResponse> {
                        override fun onResponse(
                            call: Call<SearchResponse>,
                            response: Response<SearchResponse>
                        ) {
                            if (response.isSuccessful) {
                                listSong = response.body()?.data?.musics!!
                                listSinger = response.body()?.data?.singers!!
                                listCategory = response.body()?.data?.categories!!
                                _loading.value = false
                            } else {
                                message = response.errorBody()?.string()
                                    ?.let { JSONObject(it).getString("message") }.toString()
                                _loading.value = false
                            }

                        }

                        override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                            message = "OOps"
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
}