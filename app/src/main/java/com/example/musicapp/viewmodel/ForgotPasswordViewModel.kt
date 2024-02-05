package com.example.musicapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.api.ApiHelper
import com.example.musicapp.api.request.CreateNewPasswordRequest
import com.example.musicapp.api.request.ForgotPasswordRequest
import com.example.musicapp.api.request.VerifyOtpRequest
import com.example.musicapp.api.response.BaseResponse
import com.example.musicapp.api.response.ForgotPasswordResponse
import com.example.musicapp.api.response.VerifyOtpResponse
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class ForgotPasswordViewModel : ViewModel() {
    private val _success = MutableLiveData(/* value = */ false)
    val success: LiveData<Boolean>
        get() = _success

    private val _hasError = MutableLiveData(/* value = */ false)
    val hasError: LiveData<Boolean>
        get() = _hasError

    var tokenVerifyOtp: String? = null
    var tokenResetPassword: String? = null


    var message = ""

    fun enterEmail(email: String) {
        viewModelScope.launch {
            try {
                val request = ForgotPasswordRequest(email)
                ApiHelper.getInstance().forgotPassword(request).enqueue(
                    object : Callback<ForgotPasswordResponse> {
                        override fun onResponse(
                            call: Call<ForgotPasswordResponse>,
                            response: Response<ForgotPasswordResponse>
                        ) {
                            if (response.isSuccessful && response.body() != null) {
                                tokenVerifyOtp = response.body()!!.tokenVerifyOTP
                                _success.value = true
                            } else {
                                message = JSONObject(
                                    response.errorBody()?.string()!!
                                ).getString("message")
                                _hasError.value = true
                            }
                        }

                        override fun onFailure(call: Call<ForgotPasswordResponse>, t: Throwable) {
                            message = "Oops! Something went wrong..."
                            _hasError.value = true
                        }

                    }
                )
            } catch (httpEx: HttpException) {
                getMessage(httpEx)
                _hasError.value = true
            } catch (ex: Exception) {
                _hasError.value = true
                null
            }
        }
    }

    fun enterOTP(otp: String) {
        _success.value = false
        _hasError.value = false
        viewModelScope.launch {
            try {
                if (tokenVerifyOtp != null) {
                    val request = VerifyOtpRequest(otp, tokenVerifyOtp)
                    ApiHelper.getInstance().enterOTP(request).enqueue(
                        object : Callback<VerifyOtpResponse> {
                            override fun onResponse(
                                call: Call<VerifyOtpResponse>,
                                response: Response<VerifyOtpResponse>
                            ) {
                                if (response.isSuccessful && response.body() != null) {
                                    tokenResetPassword = response.body()!!.tokenResetPassword
                                    _success.value = true

                                }
                            }

                            override fun onFailure(call: Call<VerifyOtpResponse>, t: Throwable) {

                            }

                        })
                }

            } catch (ex: HttpException) {
                getMessage(ex)
            }
        }
    }

    fun createNewPassword(newPassword: String) {
        _success.value = false
        _hasError.value = false
        viewModelScope.launch {
            try {
                if (tokenResetPassword != null) {
                    val request = CreateNewPasswordRequest(newPassword, tokenResetPassword)
                    ApiHelper.getInstance().resetPassword(request).enqueue(
                        object : Callback<BaseResponse> {
                            override fun onResponse(
                                call: Call<BaseResponse>,
                                response: Response<BaseResponse>
                            ) {
                                if (response.isSuccessful && response.body() != null) {
                                    message = response.body()!!.message.toString()
                                    _success.value = true
                                } else {
                                    message = JSONObject(
                                        response.errorBody()!!.string()
                                    ).getString("message")
                                    _hasError.value = true
                                }
                            }

                            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                                message = "Oops! Something went wrong..."
                                _hasError.value = true
                            }
                        }

                    )
                }
            } catch (httpEx: HttpException) {
                getMessage(httpEx)
                _hasError.value = true
            } catch (ex: Exception) {
                null
            }
        }
    }

    private fun getMessage(ex: HttpException) {
        message = ex.response()?.errorBody()?.string()?.let { JSONObject(it).getString("message") }
            .toString()
        _success.value = false
    }
}