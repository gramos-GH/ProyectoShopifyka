package com.example.proyectoshopifyka.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectoshopifyka.model.Weather
import com.example.proyectoshopifyka.network.WeatherRepository
import kotlinx.coroutines.launch
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData

class WeatherFragmentViewModel : ViewModel() {

    private val repository = WeatherRepository()

    private val _weatherData = MutableLiveData<Weather>()
    val weatherData: LiveData<Weather> = _weatherData

    fun fetchWeather(apiKey: String, location: String) {
        viewModelScope.launch {
            val result = repository.getWeatherInfo(apiKey, location, "no")
            result?.let {
                _weatherData.value = it
            }
        }
    }
}