package com.shauna.weather.service.repository

import android.content.Context
import android.util.Log
import com.shauna.weather.data.entity.DBWeather
import com.shauna.weather.service.repository.local.WeatherLocalSourceImp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface WeatherRepository {
    val weatherList: MutableList<OnRetrieveWeatherListener>
    val weatherLocalSource: WeatherLocalSourceImp
    var serviceState: WeatherServiceState

    companion object {
        val TAG: String = WeatherRepository::class.java.simpleName
    }

    interface OnRetrieveWeatherListener {
        fun onRetrieve(weatherList: DBWeather?)
        fun onStateChange(subscriptionState: WeatherServiceState)
    }

    fun requestWeather(context: Context, cityName: String = "Toronto")

    fun subscribeTo(listenerOnRetrieve: OnRetrieveWeatherListener) {
        weatherList.add(listenerOnRetrieve)
        CoroutineScope(Dispatchers.IO).launch {
            val weatherLists = weatherLocalSource.getWeatherLists()
            if (weatherLists.isNotEmpty()) {
                listenerOnRetrieve.onRetrieve(weatherLocalSource.getWeatherLists()[0])
            }
            listenerOnRetrieve.onStateChange(serviceState)
        }
    }

    fun unsubscribe(listenerOnRetrieve: OnRetrieveWeatherListener) {
        weatherList.remove(listenerOnRetrieve)
    }

    fun broadcastWeatherList(weatherList: DBWeather) {
        this.weatherList.forEach { listener ->
            try {
                listener.onRetrieve(weatherList)
            } catch (e: IndexOutOfBoundsException) {
                Log.e(TAG, "Exception: ${e.stackTrace}")
            }
        }
    }

    fun broadcastWeatherServiceState(subscriptionState: WeatherServiceState) {
        this.serviceState = subscriptionState
        weatherList.forEach { listener ->
            try {
                listener.onStateChange(subscriptionState)
            } catch (e: IndexOutOfBoundsException) {
                Log.e(TAG, "Exception: ${e.stackTrace}")
            }
        }
    }

    suspend fun replace(weatherList: DBWeather) {
        withContext(Dispatchers.IO) {
            weatherLocalSource.clearWeatherList()
            weatherLocalSource.insertWeatherList(weatherList)
        }
    }

    suspend fun clearWeatherList() {
        withContext(Dispatchers.IO) {
            weatherLocalSource.clearWeatherList()
            weatherList.forEach { listener ->
                try {
                    listener.onRetrieve(null)
                } catch (e: IndexOutOfBoundsException) {
                    Log.e(TAG, "Exception: ${e.stackTrace}")
                }
            }
        }
    }

}