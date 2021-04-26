package com.shauna.weather.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.shauna.weather.service.model.LocationModel

class SharedPrefManager private constructor(val context: Context) {

    companion object {

        private const val WEATHER_PREF_TIME = "Weather pref time"
        private const val SHARED_PREF_NAME_KEY: String = "com.shauna.weather"
        private const val WEATHER_FORECAST_PREF_TIME = "Forecast pref time"
        private const val CITY_ID = "CityID"
        lateinit var instance: SharedPrefManager
        private const val LOCATION = "LOCATION"

        @JvmStatic
        fun init(context: Context) {
            instance = SharedPrefManager(context)
        }

    }

    private var sharedPrefs: SharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME_KEY, Context.MODE_PRIVATE)
    private var prefEditor: SharedPreferences.Editor = sharedPrefs.edit()


    fun saveTimeOfInitialWeatherFetch(time: Long) {
        prefEditor.putLong(WEATHER_PREF_TIME, time)
        prefEditor.commit()
    }

    fun getTimeOfInitialWeatherFetch() = sharedPrefs.getLong(WEATHER_PREF_TIME, 0L)


    fun saveTimeOfInitialWeatherForecastFetch(time: Long) {
        prefEditor.putLong(WEATHER_FORECAST_PREF_TIME, time)
        prefEditor.commit()
    }

    fun saveLocation(location: LocationModel) {
        val gson = Gson()
        val json = gson.toJson(location)
        prefEditor.putString(LOCATION, json)
        prefEditor.commit()
    }

    fun getLocation(): LocationModel {
        val gson = Gson()
        val json = sharedPrefs.getString(LOCATION, null)
        return gson.fromJson(json, LocationModel::class.java)
    }

    fun getTimeOfInitialWeatherForecastFetch() = sharedPrefs.getLong(WEATHER_FORECAST_PREF_TIME, 0L)


    fun saveCityId(cityId: Int) {
        prefEditor.putInt(CITY_ID, cityId)
        prefEditor.commit()
    }

    fun getCityId() = sharedPrefs.getInt(CITY_ID, 0)


    fun getUserSetCacheDuration() = sharedPrefs.getString("cache_key", "0")


    fun getSelectedThemePref() = sharedPrefs.getString("theme_key", "")


    fun getSelectedTemperatureUnit() = sharedPrefs.getString("unit_key", "")

}

