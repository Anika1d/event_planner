package com.template.data.model.event

data class Event (
    val id: Long,
    val name: String,
    val description: String,
    val dateStart: Long,
    val timeStart: String,
    val dateEnd: Long,
    val timeEnd: String,
    val city: String,
    val address: String,
    val isLiked: Boolean = false,
    val isVisited: Boolean? = null,
    val weather:WeatherByCity
        )