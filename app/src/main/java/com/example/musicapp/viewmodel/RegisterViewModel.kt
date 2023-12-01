package com.example.musicapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.api.ApiHelper
import com.example.musicapp.api.request.RegisterRequest
import com.example.musicapp.api.response.RegisterResponse
import com.example.musicapp.model.User
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int>
        get() = _status

    var message = ""


    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    fun register(
        fullname: String,
        username: String,
        email: String,
        password: String,
        passwordAgain: String
    ) {
        val registerRequest = RegisterRequest(fullname, username, email, password, passwordAgain)
        viewModelScope.launch {
            try {
                ApiHelper.getInstance().register(registerRequest).enqueue(
                    object : Callback<RegisterResponse<User>> {
                        override fun onResponse(
                            call: Call<RegisterResponse<User>>,
                            response: Response<RegisterResponse<User>>
                        ) {
                            if (response.isSuccessful && response.body() != null) {
                                _loading.value = false
                                message = response.body()!!.message
                                _status.value = response.body()!!.status
                            } else {
                                _loading.value = false
                                _status.value = response.code()
                            }
                        }

                        override fun onFailure(call: Call<RegisterResponse<User>>, t: Throwable) {
                            _loading.value = false
                        }
                    }
                )
            } catch (ex: HttpException) {
                message = JSONObject(ex.response()!!.errorBody()!!.string()).getString("message")
                _status.value = ex.code()
                _loading.value = false


            }
        }
    }
}