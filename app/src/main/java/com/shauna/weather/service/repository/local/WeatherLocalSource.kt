package com.shauna.weather.service.repository.local

import com.shauna.weather.data.dao.WeatherDao
import com.shauna.weather.data.entity.DBWeather

class WeatherLocalSource internal constructor(private val weatherDao: WeatherDao) : WeatherLocalSourceImp {
    override fun insertWeatherList(dbWeather: DBWeather) {
        weatherDao.insertWeather(dbWeather)
    }

    override fun getWeatherLists(): List<DBWeather> = weatherDao.getAllWeather()

    override fun clearWeatherList() {
        weatherDao.deleteAllWeather()
    }
}