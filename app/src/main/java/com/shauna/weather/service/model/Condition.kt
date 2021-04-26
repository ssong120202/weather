package com.shauna.weather.service.model

import java.io.Serializable

data class Condition(
        var temp: Double = 0.0,
        val feels_like: Double = 0.0,
        val temp_min: Double = 0.0,
        val temp_max: Double = 0.0,
        val pressure: Int = 0,
        val sea_level: Int = 0,
        val grnd_level: Int = 0,
        val humidity: Int = 0,
        val temp_kf: Double = 0.0
) : Serializable