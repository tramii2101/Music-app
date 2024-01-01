package com.example.musicapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.api.ApiHelper
import com.example.musicapp.api.response.ListCategoriesResponse
import com.example.musicapp.model.Category
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class CategoryViewModel : ViewModel() {
    var message = ""
    var categories: MutableList<Category> = mutableListOf()

    private val _loading = MutableLiveData(/* value = */ true)
    val loading: LiveData<Boolean>
        get() = _loading

    fun getListCategories(accessToken: String) {
        viewModelScope.launch {
            try {
                ApiHelper.getInstance().getListCategories(accessToken).enqueue(
                    object : Callback<ListCategoriesResponse> {
                        override fun onResponse(
                            call: Call<ListCategoriesResponse>,
                            response: Response<ListCategoriesResponse>
                        ) {
                            if (response.isSuccessful && response.body() != null) {
                                categories = response.body()!!.data!!.results
                                _loading.value = false
                            } else {
                                message =
                                    JSONObject(response.errorBody()!!.string()).getString("message")
                                _loading.value = false
                            }
                        }

                        override fun onFailure(call: Call<ListCategoriesResponse>, t: Throwable) {
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