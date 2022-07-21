package com.template.core.room.repository.impl

import android.database.sqlite.SQLiteConstraintException
import com.template.core.model.city.CoordinatesCityTuples
import com.template.core.model.city.CoreCity
import com.template.core.model.city.EnterCity
import com.template.core.room.dao.CityDao
import com.template.core.room.entities.CityDbEntity
import com.template.core.room.repository.CityRepository
import javax.inject.Inject


class CityRepositoryImpl @Inject constructor(private  val cityDao: CityDao) : CityRepository {

    override suspend fun insertCity(enterCity: EnterCity) {
        try {
            val city = CityDbEntity.enterCity(enterCity)
            cityDao.insertCity(city)
        } catch (e: SQLiteConstraintException) {

        }
    }

    override suspend fun getCityByName(name: String): CoreCity = cityDao.getCityByName(name)

    override suspend fun getCoordinates(name: String): CoordinatesCityTuples? =
        cityDao.getCoordinates(name = name)
}