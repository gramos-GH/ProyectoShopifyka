package com.example.proyectoshopifyka.view.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectoshopifyka.core.LocationProvider
import com.example.proyectoshopifyka.core.ResultWrapper
import com.example.proyectoshopifyka.model.Weather
import com.example.proyectoshopifyka.network.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherFragmentViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationProvider: LocationProvider
): ViewModel() {

    private val _loaderState = MutableLiveData<Boolean>()
    val loaderState: LiveData<Boolean> get() = _loaderState

    private val _weatherInfo = MutableLiveData<Weather>()
    val weatherInfo: LiveData<Weather> get() = _weatherInfo

    fun fetchWeather(apiKey: String) {
        _loaderState.value = true
        viewModelScope.launch {
            val location = locationProvider.getCurrentLocation()?.let {
                "${it.latitude},${it.longitude}"
            } ?: "Mexico City" // Ubicación por defecto en caso de error

            when (val result = repository.getWeatherInfo(apiKey, location)) {
                is ResultWrapper.Success -> _weatherInfo.value = result.data

                is ResultWrapper.Success -> {
                    _loaderState.value = false
                    _weatherInfo.value = result.data
                }
                is ResultWrapper.Error -> {
                    _loaderState.value = false
                    val errorMessage = result.exception.message
                }
            }
        }
    }
}