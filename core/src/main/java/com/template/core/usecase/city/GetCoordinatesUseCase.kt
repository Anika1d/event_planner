package com.template.core.usecase.city

import com.template.core.room.repository.impl.CityRepositoryImpl
import javax.inject.Inject

class GetCoordinatesUseCase @Inject constructor(private val repositoryImpl: CityRepositoryImpl) {
    suspend operator fun invoke(nameCity: String) = repositoryImpl.getCoordinates(name = nameCity)
}