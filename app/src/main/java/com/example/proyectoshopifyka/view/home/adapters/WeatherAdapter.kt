package com.example.proyectoshopifyka.view.home.adapters

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoshopifyka.model.Weather
import android.view.View
import android.view.ViewGroup
import com.example.proyectoshopifyka.R
import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import com.example.proyectoshopifyka.databinding.FragmentWeatherItemBinding

import java.text.SimpleDateFormat
import java.util.*


class WeatherAdapter (
    private val weathers: MutableList<Weather>,
    private val onItemClick: (String) -> Unit
    ): RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {
        private lateinit var context: Context

        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
            val binding = FragmentWeatherItemBinding.bind(view)

            fun setUpUI(weather: Weather) {
                binding.weatherTitleDayTextView.text = getDayOfWeek(weather.lastupdated)
                binding.weatherCentiTextView.text = "${weather.tempc}°C"
                binding.weatherNumTextView.text = "${weather.feelslikec}°C"

                binding.itemContainerView.setOnClickListener {
                    onItemClick(weather.condition.text)
                }

                Glide.with(itemView.context)
                    .load("https:${weather.condition.icon}")
                    .placeholder(R.drawable.outline_downloading_24)
                    .error(R.drawable.outline_cloud_off_24)
                    .into(binding.weatherImageView)
            }
        }

        fun add(weatherItems: List<Weather>) {
            weathers.addAll(weatherItems)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            context = parent.context
            val view = LayoutInflater.from(context).inflate(R.layout.fragment_weather_item, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return weathers.count()
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.setUpUI(weathers[position])
        }

        fun getDayOfWeek(dateString: String): String {
            return try {
                val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()) // Formato de la API
                val date = format.parse(dateString) // Convertir String a Date
                val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault()) // Día completo en inglés (Ej: Monday)
                dayFormat.format(date ?: Date()) // Si hay error, usa la fecha actual
            } catch (e: Exception) {
                Log.e("WeatherAdapter", "Error parsing date: $dateString", e)
                "Unknown Day" // Retorna un valor genérico en caso de error
            }
        }

}