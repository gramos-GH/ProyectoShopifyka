package com.example.proyectoshopifyka.core

import okhttp3.Response
import retrofit2.http.GET

interface RealtimeAPI {
    @GET ("temp_c")
    suspend fun getTempCelcius(): Response <>

}

