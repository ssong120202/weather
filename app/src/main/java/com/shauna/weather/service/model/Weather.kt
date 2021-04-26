package com.shauna.weather.service.model

import java.io.Serializable

data class Weather(
        val id: Int = 0,
        val city: City = City(),
        val wind: Wind = Wind(),
        val weatherDescription: List<Description> = listOf(),
        val condition: Condition = Condition(),
        val dt_text : String = ""
) : Serializable
