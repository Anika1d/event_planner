package com.template.core.retrofit.repository

import com.template.core.model.city.CoordinatesCityTuples
import com.template.core.model.weather.WeatherByCity
import com.template.core.retrofit.RetrofitServices
import javax.inject.Inject

class RepositoryRetrofitImpl @Inject constructor(
    private val retrofitServices: RetrofitServices
) : RepositoryRetrofit {
    override suspend fun getCoordinates(city_name: String): CoordinatesCityTuples? {
        return try {
            val tmp = retrofitServices.getCoordinates(city_name)
            if (tmp != null && tmp.isNotEmpty())
                CoordinatesCityTuples(
                    lat = tmp[0].lat,
                    lon = tmp[0].lon,
                )
            else
                null
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getWeather(lat: String, lon: String): WeatherByCity? {
        return try {
            retrofitServices.getWeather(lat = lat, lon = lon)
        } catch (e: Exception) {
            null
        }
    }
}