package com.shauna.weather.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shauna.weather.R
import com.shauna.weather.service.model.Weather
import com.shauna.weather.utils.convertCelsiusToFahrenheit
import com.shauna.weather.utils.convertKelvinToCelsius
import kotlinx.android.synthetic.main.weather_info_details_cell.view.*


class WeatherAdapter(private val weatherInfoList: List<Weather>, private val isCelsius: Boolean) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return WeatherViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is WeatherViewHolder) {
            holder.bind(weatherInfoList[position], isCelsius)
        }
    }

    override fun getItemCount(): Int {
        return weatherInfoList.size
    }

}

class WeatherViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.weather_info_details_cell, parent, false)) {
    fun bind(weatherInfo: Weather, isCelsius: Boolean) {
        val currentTemp = convertKelvinToCelsius(weatherInfo.condition.temp)
        val feelLikeTemp = convertKelvinToCelsius(weatherInfo.condition.feels_like)
        itemView.temp_unit.text = if (isCelsius) itemView.context.getString(R.string.temp_unit_celsius) else itemView.context.getString(R.string.temp_unit_fahrenheit)
        itemView.current_text_view.text = if (isCelsius) currentTemp.toString() else convertCelsiusToFahrenheit(currentTemp).toString()
        itemView.feel_like_info.text = if (isCelsius) feelLikeTemp.toString() else convertCelsiusToFahrenheit(feelLikeTemp).toString()
        itemView.description_text.text = weatherInfo.weatherDescription[0].description
        itemView.current_time.text = weatherInfo.dt_text
    }
}

