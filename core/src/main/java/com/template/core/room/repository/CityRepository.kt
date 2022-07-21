package com.template.core.room.repository

import com.template.core.model.city.CoordinatesCityTuples
import com.template.core.model.city.CoreCity
import com.template.core.model.city.EnterCity

interface CityRepository {
    suspend fun getCoordinates(name: String): CoordinatesCityTuples?
    suspend fun getCityByName(name: String): CoreCity
    suspend fun insertCity(enterCity: EnterCity)
}