package com.example.proyectoshopifyka.network

import android.util.Log
import com.example.proyectoshopifyka.core.RealtimeAPI
import com.example.proyectoshopifyka.core.RetrofitInstance
import com.example.proyectoshopifyka.core.safeCall

import com.example.proyectoshopifyka.core.ResultWrapper
import com.example.proyectoshopifyka.model.Condition
import com.example.proyectoshopifyka.model.WeatherResponse
import retrofit2.HttpException
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val realTimeAPI: RealtimeAPI



) {
    suspend fun getWeatherInfo(apiKey: String, location: String): ResultWrapper<WeatherResponse> = safeCall {
        val response = realTimeAPI.getWeatherInfo(apiKey, location)
        if (response.isSuccessful) {
            response.body() ?: throw Exception("Datos nulos en la respuesta.")
        } else {
            throw HttpException(response)
        }
    }
}
