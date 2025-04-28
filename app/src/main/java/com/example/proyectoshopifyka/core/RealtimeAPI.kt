package com.example.proyectoshopifyka.core

import com.example.proyectoshopifyka.model.Weather
import retrofit2.Response

import retrofit2.http.GET

interface RealtimeAPI {
    @GET ("temp_c")
    suspend fun getTempCelcius(): Response<Weather>

}

