package com.template.core.retrofit

import com.template.core.model.weather.WeatherByCity
import com.template.core.usecase.coordinate.CityInfoList
import retrofit2.http.GET
import retrofit2.http.Query


const val apiKey = "ba467e176306416de236d8ee33a366d2"

interface RetrofitServices {


    @GET("geo/1.0/direct?")
    suspend fun getCoordinates(
        @Query("q") name: String,
        @Query("limit") limit: String = "1",
        @Query("appid") appid: String = apiKey
    ): CityInfoList?
    @GET("data/2.5/weather?appid=$apiKey")
    suspend fun getWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String
    ): WeatherByCity

}