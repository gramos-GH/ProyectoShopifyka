package com.example.proyectoshopifyka.network

import android.util.Log
import com.example.proyectoshopifyka.core.RealtimeAPI
import com.example.proyectoshopifyka.core.RetrofitInstance
import com.example.proyectoshopifyka.model.Weather

class WeatherRepository {
    private val retrofit = RetrofitInstance.getRetrofit().create(RealtimeAPI::class.java)

    suspend fun getWeatherInfo(apiKey: String, location: String, aqi: String): Weather? {
        val response = retrofit.getWeatherInfo(apiKey, location, aqi)
            Log.i("RESPONSE", response.body().toString())

            return response.body()
    }
}