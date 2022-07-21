package com.template.core.usecase.events

import com.template.core.room.repository.impl.EventsRepositoryImpl
import javax.inject.Inject

class GetVisitedEventListByDateUseCase @Inject constructor(private val repositoryImpl: EventsRepositoryImpl) {
  operator fun invoke(isVisited: Boolean,dateStart:String) =
        repositoryImpl.getIsVisitedEventListByDate(isVisited=isVisited,dateStart=dateStart)
}