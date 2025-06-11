package com.example.proyectoshopifyka.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("location") val location: Location,
    @SerializedName("current") val current: Current
)

data class Location(
    @SerializedName("name") val name: String,        // Para textCiudad
    @SerializedName("localtime") val localtime: String  // Para textSemana
)

data class Current(
    @SerializedName("temp_c") val tempC: Double,    // Para textTemp
    @SerializedName("feelslike_c") val feelsLikeC: Double,
    @SerializedName("condition") val condition: Condition,
    @SerializedName("wind_kph") val windKph: Double,  // Para textNumWind
    @SerializedName("humidity") val humidity: Int
)

data class Condition(
    @SerializedName("text") val text: String,      // Para textSaludo
    @SerializedName("icon") val icon: String       // Para imgClima
)