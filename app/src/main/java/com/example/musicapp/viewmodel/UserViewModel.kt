package com.example.musicapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.api.ApiHelper
import com.example.musicapp.api.request.EditProfileRequest
import com.example.musicapp.api.response.EditProfileResponse
import com.example.musicapp.api.response.UserProfileResponse
import com.example.musicapp.model.User
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class UserViewModel : ViewModel() {
    var message = ""
    private val _loading = MutableLiveData(/* value = */ true)
    val loading: LiveData<Boolean>
        get() = _loading

    var user: User? = null

    fun getUserProfile(accessToken: String) {
        viewModelScope.launch {
            try {
                ApiHelper.getInstance().getUserProfile(accessToken).enqueue(
                    object : Callback<UserProfileResponse> {
                        override fun onResponse(
                            call: Call<UserProfileResponse>,
                            response: Response<UserProfileResponse>
                        ) {
                            if (response.isSuccessful) {
                                user = response.body()?.data
                                _loading.value = false
                            } else {
                                message =
                                    JSONObject(response.errorBody()!!.string()).getString("message")
                                _loading.value = false
                            }
                        }

                        override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                            message = "Oops! Something went wrong..."
                            _loading.value = false
                        }

                    }
                )
            } catch (ex: HttpException) {
                message = ex.response()?.errorBody()?.string()
                    ?.let { JSONObject(it).getString("message") }.toString()
                _loading.value = false
            } catch (ex2: Exception) {
                message = ex2.message.toString()
                _loading.value = false
            }
        }
    }

    fun updateUserProfile(
        accessToken: String,
        fullname: String?,
        email: String?,
        gender: String?,
        dob: String?,
    ) {
        viewModelScope.launch {
            val userInfor = EditProfileRequest(fullname, email, gender, dob)
            try {
                ApiHelper.getInstance().updateUserProfile(
                    accessToken,
                    userInfor
                ).enqueue(
                    object : Callback<EditProfileResponse> {
                        override fun onResponse(
                            call: Call<EditProfileResponse>,
                            response: Response<EditProfileResponse>
                        ) {

                            if (response.isSuccessful) {
                                user = response.body()?.data
                                _loading.value = false
                                message = response.message()
                            } else {
                                message =
                                    JSONObject(response.errorBody()!!.string()).getString("message")
                                _loading.value = false
                            }
                        }

                        override fun onFailure(call: Call<EditProfileResponse>, t: Throwable) {

                        }

                    }
                )
            } catch (ex: HttpException) {
                null
            }
        }

    }

}