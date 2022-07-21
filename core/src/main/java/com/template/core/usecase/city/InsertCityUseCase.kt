package com.template.core.usecase.city

import com.template.core.model.city.EnterCity
import com.template.core.room.entities.CityDbEntity
import com.template.core.room.repository.impl.CityRepositoryImpl
import javax.inject.Inject

class InsertCityUseCase @Inject constructor(private val repositoryImpl: CityRepositoryImpl) {
    suspend operator fun invoke(cityEnter: EnterCity) =
        repositoryImpl.insertCity(cityEnter)
}