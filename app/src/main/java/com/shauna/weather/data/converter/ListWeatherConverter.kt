package com.shauna.weather.data.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shauna.weather.service.model.Condition
import com.shauna.weather.service.model.Description
import com.shauna.weather.service.model.Wind
import java.lang.reflect.Type

object ListWeatherConverter {
    private val gson = Gson()

    private val type: Type = object : TypeToken<List<Description?>?>() {}.type

    @TypeConverter
    @JvmStatic
    fun fromWeatherDtoList(list: List<Description?>?): String {
        return gson.toJson(list, type)
    }

    @TypeConverter
    @JvmStatic
    fun toWeatherDtoList(json: String?): List<Description> {
        return gson.fromJson(json, type)
    }
}

object WindConverter {
    private val gson = Gson()

    @TypeConverter
    @JvmStatic
    fun stringToObject(data: String): Wind {
        return gson.fromJson(data, Wind::class.java)
    }

    @TypeConverter
    @JvmStatic
    fun objectToString(someObjects: Wind): String {
        return gson.toJson(someObjects)
    }
}

object ConditionConverter {
    private val gson = Gson()

    @TypeConverter
    @JvmStatic
    fun stringToObject(data: String): Condition {
        return gson.fromJson(data, Condition::class.java)
    }

    @TypeConverter
    @JvmStatic
    fun objectToString(someObjects: Condition): String {
        return gson.toJson(someObjects)
    }
}
