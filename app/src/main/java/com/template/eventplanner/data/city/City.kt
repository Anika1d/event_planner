package com.template.eventplanner.data.city

import com.template.core.model.weather.WeatherByCity

data class City(
    val name: String,
    val lat: Double?,
    val lon: Double?,
    val weatherByCity: WeatherByCity?
)
