package com.shauna.weather.data.entity

import android.os.Parcelable
import androidx.room.*
import com.shauna.weather.data.*
import com.shauna.weather.data.converter.ConditionConverter
import com.shauna.weather.data.converter.ListWeatherConverter
import com.shauna.weather.data.converter.WindConverter
import com.shauna.weather.service.model.Condition
import com.shauna.weather.service.model.Description
import com.shauna.weather.service.model.Weather
import com.shauna.weather.service.model.Wind
import kotlinx.android.parcel.Parcelize
import org.jetbrains.annotations.NotNull

@Parcelize
@Entity(tableName = DB_WEATHER_TABLE_NAME)
class DBWeather() : Parcelable {

    @ColumnInfo(name = DB_WEATHER_UNIQUE_ID)
    @PrimaryKey(autoGenerate = true)
    @NotNull
    var id: Int = 0

    @ColumnInfo(name = DB_WEATHER_CITY_ID)
    @NotNull
    private var cityId: Int = 0

    @ColumnInfo(name = DB_WEATHER_CITY_NAME)
    @NotNull
    private var cityName: String = ""

    @ColumnInfo(name = DB_WEATHER_WIND)
    @TypeConverters(WindConverter::class)
    @NotNull
    private var wind: Wind = Wind()

    @ColumnInfo(name = DB_WEATHER_DETAILS)
    @NotNull
    @TypeConverters(ListWeatherConverter::class)
    private var networkWeatherDescription: List<Description> = listOf()

    @ColumnInfo(name = DB_WEATHER_CONDITION)
    @TypeConverters(ConditionConverter::class)
    @NotNull
    private var networkWeatherCondition: Condition = Condition()

    @Ignore
    constructor(weather: Weather): this() {
        this.id = weather.id
        this.cityId = weather.city.id
        this.cityName = weather.city.name
        this.wind = weather.wind
        this.networkWeatherCondition = weather.condition
        this.networkWeatherDescription = weather.weatherDescription
    }

    fun setCityId(cityId: Int) {
        this.cityId = cityId
    }

    fun getCityId(): Int {
        return cityId
    }

    fun setCityName(name: String) {
        this.cityName = cityName
    }

    fun getCityName(): String {
        return cityName
    }

    fun getWind(): Wind {
        return wind
    }

    fun setWind(wind: Wind) {
        this.wind = wind
    }

    fun getNetworkWeatherDescription(): List<Description> {
        return networkWeatherDescription
    }

    fun setNetworkWeatherDescription(list: List<Description>) {
        this.networkWeatherDescription = list
    }

    fun setNetworkWeatherCondition(condition: Condition) {
        this.networkWeatherCondition = condition
    }

    fun getNetworkWeatherCondition(): Condition {
        return networkWeatherCondition
    }
}
