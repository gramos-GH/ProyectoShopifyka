package com.example.proyectoshopifyka.model

import com.google.gson.annotations.SerializedName

data class Weather(

    @SerializedName("last_updated") val lastupdated: String, // Última actualización
    //@SerializedName("last_updated_epoch") val lastupdatedepoch: String,
    @SerializedName("temp_c") val tempc: String, // Temperatura en °C
    //@SerializedName("temp_f") val tempf: String,
    @SerializedName("feelslike_c") val feelslikec: String, // Sensación térmica

    /*@SerializedName("feelslike_f") val feelslikef: String,
    @SerializedName("windchill_c") val windchillc: String,
    @SerializedName("windchill_f") val windchillf: String,
    @SerializedName("heatindex_c") val heatindexc: String,
    @SerializedName("heatindex_f") val heatindexf: String,
    @SerializedName("dewpoint_c") val dewpointc: String,
    @SerializedName("dewpoint_f") val dewpointf: String,
    */
    @SerializedName("condition") val condition: Condition, // Estado del clima

    //@SerializedName("wind_mph") val windmph: String,
    @SerializedName("wind_kph") val windkph: String, // Velocidad del viento en km/h
    /*@SerializedName("wind_degree") val winddegree: String,
    @SerializedName("wind_dir") val winddir: String,
    @SerializedName("pressure_mb") val pressuremb: String,
    @SerializedName("pressure_in") val pressure_in: String,
    @SerializedName("precip_mm") val precip_mm: String,
    @SerializedName("precip_in") val precip_in: String,*/
    @SerializedName("humidity") val humidity: String, // Humedad %
    //@SerializedName("cloud") val cloud: String,
    //@SerializedName("is_day") val isday: String, // Si es de día o de noche
    /*@SerializedName("uv") val uv: String,
    @SerializedName("gust_mph") val gustmph: String,
    @SerializedName("gust_kph") val gustkph: String*/

)

data class Condition(
    @SerializedName("text") val text: String, // Descripción del clima
    @SerializedName("icon") val icon: String, // URL del ícono del clima
    //@SerializedName("code") val code: String
)
