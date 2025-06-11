package com.example.proyectoshopifyka.view.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectoshopifyka.core.LocationProvider
import com.example.proyectoshopifyka.core.ResultWrapper
import com.example.proyectoshopifyka.model.WeatherResponse
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

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _weatherInfo = MutableLiveData<WeatherResponse>()
    val weatherInfo: LiveData<WeatherResponse> get() = _weatherInfo


    fun fetchWeather(apiKey: String) {
        _loaderState.value = true
        viewModelScope.launch {

            val hardcodedCoordinates = "19.303710263763854,-99.05872923057291" // Latitud,Longitud fijas

            when (val result = repository.getWeatherInfo(apiKey, hardcodedCoordinates)) {
                is ResultWrapper.Success -> {
                    _loaderState.value = false
                    _weatherInfo.value = result.data
                }
                is ResultWrapper.Error -> {
                    _loaderState.value = false
                    _errorMessage.value = "Error: ${result.exception.message}"
                }
            }
        }
    }
}