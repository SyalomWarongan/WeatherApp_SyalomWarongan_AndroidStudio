package com.example.weatherapplication.service

import retrofit2.Call
import com.example.weatherapplication.City
import com.example.weatherapplication.Forecast
import com.example.weatherapplication.Utils
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET("forecast?")
    fun getCurrentWeather(
        @Query("lat")
        lat: String,
        @Query("lon")
        lon: String,
        @Query("appid")
        appid: String = Utils.API_KEY

    ): Call<Forecast>

    @GET("forecast?")
    fun getWeatherByCity(
        @Query("q")
        city: String,
        @Query("appid")
        appid: String = Utils.API_KEY

    ): Call<Forecast>
}