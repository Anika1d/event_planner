package com.template.core.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import com.template.core.model.city.CoordinatesCityTuples
import com.template.core.model.city.CoreCity
import com.template.core.room.entities.CityDbEntity


@Dao
interface CityDao {

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM city WHERE name=:name")
    suspend fun getCoordinates(name: String): CoordinatesCityTuples?

    @Query("SELECT * FROM city WHERE name=:cityName")
    suspend fun getCityByName(cityName: String): CoreCity

    @Insert(entity = CityDbEntity::class)
    suspend fun insertCity(dbEntity: CityDbEntity)
}
