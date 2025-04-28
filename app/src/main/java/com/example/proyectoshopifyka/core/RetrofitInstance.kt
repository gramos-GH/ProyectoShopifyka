package com.example.proyectoshopifyka.core

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    fun Retrofit(): Retrofit {
        val httpClient = OkHttpClient.Builder()
            .connectTimeout( 30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/current.json?key=b01d6b51a0bf40c282f15334252104&q=Mexico&aqi=yes")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}