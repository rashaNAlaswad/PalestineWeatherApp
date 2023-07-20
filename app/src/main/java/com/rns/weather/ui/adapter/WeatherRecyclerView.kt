package com.rns.weather.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rns.weather.R
import com.rns.weather.databinding.ItemWeatherBinding
import com.rns.weather.model.response.WeatherState
import com.rns.weather.util.formatDate
import com.rns.weather.util.formatDecimals

class WeatherRecyclerView(private val list: List<WeatherState>) :
    RecyclerView.Adapter<WeatherRecyclerView.WeatherViewHolder>() {

    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemWeatherBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val currentWeather = list[position]

        val imageUrl = "https://openweathermap.org/img/wn/${currentWeather.weather[0].icon}.png"

        holder.binding.apply {
            textDate.text = formatDate(currentWeather.dt)
            textTemp.text = "${formatDecimals(currentWeather.temp.day)}º"
            textMaxDegree.text = "${formatDecimals(currentWeather.temp.max)}º"
            textMinDegree.text = "${formatDecimals(currentWeather.temp.min)}º"

            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(icWeather)
        }
    }

    override fun getItemCount(): Int = list.size
}