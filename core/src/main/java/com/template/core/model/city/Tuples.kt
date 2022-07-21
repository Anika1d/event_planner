package com.template.core.model.city

import androidx.room.ColumnInfo


data class CoordinatesCityTuples(
    @ColumnInfo(name = "lat") val lat: Double,
    @ColumnInfo(name = "lon") val lon: Double,
)