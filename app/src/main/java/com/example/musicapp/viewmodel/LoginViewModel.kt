package com.example.musicapp.viewmodel

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
    var accessToken: String = ""

    var message = ""

    private val _loading = MutableLiveData(/* value = */ true)
    val loading: LiveData<Boolean>
        get() = _loading

    fun getAccessToken(username: String, password: String) {
        val loginRequest = LoginRequest(username, password)
        viewModelScope.launch {
            try {
                ApiHelper.getInstance().login(loginRequest).enqueue(
                    object : Callback<LoginResponse> {
                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            if (response.isSuccessful && response.body() != null) {
                                accessToken = response.body()!!.accessToken.toString()
                                _loading.value = false
                            } else {
                                message =
                                    JSONObject(response.errorBody()!!.string()).getString("message")
                                _loading.value = false
                            }
                        }

                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
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