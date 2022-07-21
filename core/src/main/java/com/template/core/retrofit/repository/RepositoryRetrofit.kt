package com.template.core.retrofit.repository

import com.template.core.model.city.CoordinatesCityTuples
import com.template.core.model.weather.WeatherByCity

interface RepositoryRetrofit {
    suspend fun getCoordinates(city_name: String): CoordinatesCityTuples?
    suspend fun getWeather(lat: String, lon: String): WeatherByCity?
}