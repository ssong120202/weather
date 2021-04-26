package com.shauna.weather.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shauna.weather.service.repository.WeatherRepository
import com.shauna.weather.service.repository.WeatherRepositoryImp

class OverviewViewModel constructor(private val repository: WeatherRepository) : ViewModel() {

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val weatherRepository = WeatherRepositoryImp.getInstance(application)
            return OverviewViewModel(weatherRepository) as T
        }
    }

    fun fetchService(context: Context, cityName: String = "Toronto") {
        repository.requestWeather(context, cityName)
    }

}