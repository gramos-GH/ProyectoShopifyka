package com.example.proyectoshopifyka.network

import android.util.Log
import com.example.proyectoshopifyka.core.RealtimeAPI
import com.example.proyectoshopifyka.core.RetrofitInstance
import com.example.proyectoshopifyka.model.Weather

class WeatherRepository {
    private val retrofit = RetrofitInstance.getRetrofit().create(RealtimeAPI::class.java)

    suspend fun getTempCelcius(): Weather? {
        val response = retrofit.getTempCelcius()
            Log.i("RESPONSE", response.body().toString())

            return response.body()
    }
}