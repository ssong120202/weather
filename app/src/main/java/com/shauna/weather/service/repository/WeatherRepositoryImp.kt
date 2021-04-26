package com.shauna.weather.service.repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.shauna.weather.data.WeatherDatabase
import com.shauna.weather.service.model.*
import com.shauna.weather.service.repository.local.WeatherLocalSource
import com.shauna.weather.service.repository.local.WeatherLocalSourceImp
import org.json.JSONObject


class WeatherRepositoryImp(override val weatherLocalSource: WeatherLocalSourceImp) : WeatherRepository {
    override val weatherList = mutableListOf<WeatherRepository.OnRetrieveWeatherListener>()
    var weatherInfoList = MutableLiveData<List<Weather>>()
    override var serviceState: WeatherServiceState = WeatherServiceState.NotStarted
    private val apikey = "a94ace9be7994ce0beb8b5e27cefcd7b"
    private var url = ""

    override fun requestWeather(context: Context, cityName: String) {
        this.url = "https://api.openweathermap.org/data/2.5/forecast?q=$cityName&appid=$apikey"
        broadcastWeatherServiceState(WeatherServiceState.Started)
        val stringRequest = StringRequest(Request.Method.GET, url, { response ->
            if (response != null) {
                val weatherInfo = buildWeatherInfo(response)
                weatherInfoList.postValue(weatherInfo)
                broadcastWeatherServiceState(WeatherServiceState.Completed)
            }
        }, { })
        val queue: RequestQueue = Volley.newRequestQueue(context)
        queue.add(stringRequest)
    }

    companion object {
        private var INSTANCE: WeatherRepositoryImp? = null
        fun getInstance(application: Application): WeatherRepositoryImp {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: WeatherRepositoryImp(
                        WeatherLocalSource(WeatherDatabase.getInstance(application).weatherDao()),
                ).also { INSTANCE = it }
            }
        }
    }

    private fun buildWeatherInfo(response: String): List<Weather> {
        val weatherList: MutableList<Weather> = mutableListOf()
        val fullResult = JSONObject(response)
        val resultArray = fullResult.getJSONArray("list")
        for (i in 0 until resultArray.length()) {
            val currentObject = resultArray.getJSONObject(i)
            val result = Weather(city = City(id = fullResult.getJSONObject("city").getInt("id"), name = fullResult.getJSONObject("city").getString("name"), country = fullResult.getJSONObject("city").getString("country")), condition = Condition(temp = currentObject.getJSONObject("main").getDouble("temp"), feels_like = currentObject.getJSONObject("main").getDouble("feels_like"), temp_min = currentObject.getJSONObject("main").getDouble("temp_min"), temp_max = currentObject.getJSONObject("main").getDouble("temp_max"), grnd_level = currentObject.getJSONObject("main").getInt("grnd_level"), humidity = currentObject.getJSONObject("main").getInt("humidity"), temp_kf = currentObject.getJSONObject("main").getDouble("temp_kf"), pressure = currentObject.getJSONObject("main").getInt("pressure"), sea_level = currentObject.getJSONObject("main").getInt("sea_level")), weatherDescription = listOf(Description(
                    main = currentObject.getJSONArray("weather").getJSONObject(0).getString("main"),
                    icon = currentObject.getJSONArray("weather").getJSONObject(0).getString("icon"),
                    description = currentObject.getJSONArray("weather").getJSONObject(0).getString("description"),
            )), wind = Wind(speed = currentObject.getJSONObject("wind").getDouble("speed")), dt_text = currentObject.getString("dt_txt"))
            weatherList.add(result)
        }
        return weatherList
    }
}