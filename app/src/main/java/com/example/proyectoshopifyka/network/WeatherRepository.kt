package com.example.proyectoshopifyka.network

import android.util.Log
import com.example.proyectoshopifyka.core.RealtimeAPI
import com.example.proyectoshopifyka.core.RetrofitInstance
import com.example.proyectoshopifyka.core.safeCall
import com.example.proyectoshopifyka.model.Weather
import com.example.proyectoshopifyka.core.ResultWrapper
import retrofit2.HttpException
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val realTimeAPI: RealtimeAPI
){
    suspend fun getWeatherInfo(apiKey: String, location: String): ResultWrapper<Weather> = safeCall{
        val response = realTimeAPI.getWeatherInfo(apiKey, location)
        if (response.isSuccessful) {
            response.body()?.let { apiData ->
                Weather(
                    lastupdated = apiData.lastupdated, // Coincide con el modelo
                    tempc = apiData.tempc, // Coincide con el modelo
                    feelslikec = apiData.feelslikec, // Coincide con el modelo
                    condition = apiData.condition,
                    windkph = apiData.windkph, // Coincide con el modelo
                    humidity = apiData.humidity // Coincide con el modelo
                )
            } ?: throw Exception("Datos nulos")
        } else {
            throw HttpException(response)
        }
    }
}
