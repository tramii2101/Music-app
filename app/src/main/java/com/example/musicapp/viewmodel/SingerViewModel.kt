package com.example.musicapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.api.ApiHelper
import com.example.musicapp.api.response.ListSingerResponse
import com.example.musicapp.api.response.SingerDetailResponse
import com.example.musicapp.model.Singer
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class SingerViewModel : ViewModel() {
    var message = ""

    var listSinger = mutableListOf<Singer>()
    lateinit var singer: Singer

    private val _loading = MutableLiveData(/* value = */ true)
    val loading: LiveData<Boolean>
        get() = _loading

    fun getListSinger(accessToken: String) {
        viewModelScope.launch {
            try {
                ApiHelper.getInstance().getListSingers(accessToken).enqueue(
                    object : Callback<ListSingerResponse> {
                        override fun onResponse(
                            call: Call<ListSingerResponse>,
                            response: Response<ListSingerResponse>
                        ) {
                            if (response.isSuccessful && response.body() != null) {
                                listSinger = response.body()!!.data!!.results
                                _loading.value = false
                            } else {
                                message =
                                    JSONObject(response.errorBody()!!.string()).getString("message")
                                _loading.value = false
                            }
                        }

                        override fun onFailure(call: Call<ListSingerResponse>, t: Throwable) {
                            message = "Oops!..."
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

    fun getSingerDetail(accessToken: String, id: String) {
        viewModelScope.launch {
            try {
                ApiHelper.getInstance().getSingerById(accessToken, id).enqueue(
                    object : Callback<SingerDetailResponse> {
                        override fun onResponse(
                            call: Call<SingerDetailResponse>,
                            response: Response<SingerDetailResponse>
                        ) {
                            if (response.isSuccessful && response.body() != null) {
                                singer = response.body()!!.data!!
                                _loading.value = false
                            } else {
                                message =
                                    JSONObject(response.errorBody()!!.string()).getString("message")
                                _loading.value = false
                            }
                        }

                        override fun onFailure(call: Call<SingerDetailResponse>, t: Throwable) {
                            message = "Oops!..."
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