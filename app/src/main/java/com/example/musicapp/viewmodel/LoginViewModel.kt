package com.example.musicapp.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.api.ApiHelper
import com.example.musicapp.api.request.LoginRequest
import com.example.musicapp.api.response.LoginResponse
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class LoginViewModel : ViewModel() {
    lateinit var username: String
    lateinit var password: String

    var accessToken: String = ""

    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int>
        get() = _status

    var message = ""

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    fun getAccessToken() {
            viewModelScope.launch {
                val loginRequest = LoginRequest(username, password)
                try {
                    ApiHelper.getInstance().login(loginRequest).enqueue(
                        object : Callback<LoginResponse> {
                            override fun onResponse(
                                call: Call<LoginResponse>,
                                response: Response<LoginResponse>
                            ) {
                                if (response.isSuccessful && response.body()?.accessToken != null) {
                                    accessToken = response.body()?.accessToken.toString()
                                    _loading.value = false
                                } else {
                                    val jsonObject = JSONObject(response.errorBody()!!.string())
                                    message = jsonObject.getString("message")
                                    _status.value = jsonObject.getInt("status")
                                    _loading.value = false

                                }
                            }

                            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                                Log.e(TAG, "onFailure: $t")
                            }

                        }
                    )
                } catch (ex: HttpException) {
                    message = JSONObject(ex.response()!!.errorBody()!!.string()).getString("message")
                    _loading.value = false

                }
            }



    }

}