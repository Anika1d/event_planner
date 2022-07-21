package com.template.core.usecase.events

import com.template.core.room.repository.impl.EventsRepositoryImpl
import javax.inject.Inject

class GetEventListByDateUseCase @Inject constructor(private val repositoryImpl: EventsRepositoryImpl) {
  operator fun invoke(startDate: String) = repositoryImpl.getEventListByDate(startDate)
}