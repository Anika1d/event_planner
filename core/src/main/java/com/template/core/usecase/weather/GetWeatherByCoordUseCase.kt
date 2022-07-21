package com.template.core.usecase.weather

import com.template.core.retrofit.repository.RepositoryRetrofitImpl
import javax.inject.Inject

class GetWeatherByCoordUseCase @Inject constructor(private val repositoryImpl: RepositoryRetrofitImpl) {
    suspend operator fun invoke(lat: String, lon: String) =
        repositoryImpl.getWeather(lat = lat, lon = lon)
}