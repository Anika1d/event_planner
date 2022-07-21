package com.template.core.usecase.weather

import com.template.core.retrofit.repository.RepositoryRetrofitImpl
import javax.inject.Inject

class GetCoordByNameCityUseCase @Inject constructor(private val repositoryImpl: RepositoryRetrofitImpl) {
    suspend operator fun invoke(nameCity: String) =
        repositoryImpl.getCoordinates(nameCity)
}