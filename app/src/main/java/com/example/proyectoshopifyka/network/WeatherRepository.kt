package com.example.proyectoshopifyka.network

import android.util.Log
import com.example.proyectoshopifyka.core.RealtimeAPI
import com.example.proyectoshopifyka.core.RetrofitInstance
import com.example.proyectoshopifyka.core.safeCall
import com.example.proyectoshopifyka.model.Weather
import com.example.proyectoshopifyka.core.ResultWrapper
import retrofit2.HttpException
import javax.inject.Inject
import com. example. proyectoshopifyka. model. ForecastResponse
import com.google.gson.Gson

class WeatherRepository @Inject constructor(
    private val realTimeAPI: RealtimeAPI
) {
    suspend fun getForecast(apiKey: String, location: String): ResultWrapper<ForecastResponse> = safeCall {
        val response = realTimeAPI.getForecastInfo(apiKey, location)
        //Log.d("API Response", "Días recibidos en repositorio: ${response.body()?.forecast?.forecastday?.size}")
        //Log.d("API Response", "Codigo: ${response.code()}, JSON: ${Gson().toJson(response.body())}")
        if (response.isSuccessful) {
            response.body() ?: throw Exception("Datos nulos")
        } else {
            throw HttpException(response)
        }

    }
}
