package com.example.proyectoshopifyka.core

import com.example.proyectoshopifyka.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RealtimeAPI {

    @GET("current.json")
    suspend fun getWeatherInfo(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no"
    ): Response<WeatherResponse>
}