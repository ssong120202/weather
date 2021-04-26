package com.shauna.weather.service.model

import java.io.Serializable


data class Description(
        val id: Int = 0,
        val main: String = "",
        val description: String = "",
        val icon: String = ""
) : Serializable