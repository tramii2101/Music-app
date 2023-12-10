package com.example.musicapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.api.ApiHelper
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
}