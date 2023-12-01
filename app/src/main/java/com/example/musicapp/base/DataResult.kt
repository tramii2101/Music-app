package com.example.musicapp.base

open class DataResult<out T> {
    data class Success<T> (val data: T): DataResult<T>()
    data class Error(val exception: Exception) : DataResult<Nothing>()
    data object Loading : DataResult<Nothing>()
}