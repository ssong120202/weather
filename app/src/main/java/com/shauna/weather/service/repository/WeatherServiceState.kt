package com.shauna.weather.service.repository

sealed class WeatherServiceState {
    object NotStarted : WeatherServiceState()
    object Started : WeatherServiceState()
    object Completed : WeatherServiceState()
    data class Failed(val e: Error) : WeatherServiceState()
}