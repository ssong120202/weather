package com.shauna.weather.service.repository.local

import com.shauna.weather.data.entity.DBWeather

interface WeatherLocalSourceImp {
    fun insertWeatherList(loungePassList: DBWeather)
    fun getWeatherLists(): List<DBWeather>
    fun clearWeatherList()
}