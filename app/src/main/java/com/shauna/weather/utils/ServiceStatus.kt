package com.shauna.weather.utils

sealed class ServiceStatus<out R> {

    data class Success<out T>(val data: T?) : ServiceStatus<T>()
    data class Error(val exception: Exception) : ServiceStatus<Nothing>()
    object Loading : ServiceStatus<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            is Loading -> "Loading"
        }
    }
}