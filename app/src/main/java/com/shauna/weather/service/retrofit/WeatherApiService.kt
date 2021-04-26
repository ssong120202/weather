package com.shauna.weather.service.retrofit

import com.shauna.weather.service.model.Weather
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface WeatherApiService {
    @GET("/data/2.5/weather")
    suspend fun getSpecificWeather(@Query("q") location: String): Response<Weather>

    // This function gets the weather information for the user's location.
    @GET("/data/2.5/weather")
    suspend fun getCurrentWeather(@Query("lat") latitude: Double, @Query("lon") longitude: Double): Response<Weather>

}