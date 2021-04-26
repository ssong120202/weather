package com.shauna.weather.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.shauna.weather.data.DB_WEATHER_TABLE_NAME
import com.shauna.weather.data.DB_WEATHER_UNIQUE_ID
import com.shauna.weather.data.entity.DBWeather

@Dao
interface WeatherDao {

    @Insert(onConflict = REPLACE)
    fun insertWeather(vararg dbWeather: DBWeather)

    @Query("SELECT * FROM $DB_WEATHER_TABLE_NAME ORDER BY $DB_WEATHER_UNIQUE_ID DESC LIMIT 1")
    fun getWeather(): DBWeather

    @Query("SELECT * FROM $DB_WEATHER_TABLE_NAME ORDER BY $DB_WEATHER_UNIQUE_ID DESC")
    fun getAllWeather(): List<DBWeather>

    @Query("DELETE FROM $DB_WEATHER_TABLE_NAME")
    fun deleteAllWeather()
}