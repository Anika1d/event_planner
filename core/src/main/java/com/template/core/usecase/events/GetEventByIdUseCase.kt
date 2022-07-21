package com.template.core.usecase.events

import com.template.core.room.repository.impl.EventsRepositoryImpl
import javax.inject.Inject

class GetEventByIdUseCase @Inject constructor(private val repositoryImpl: EventsRepositoryImpl) {
   operator fun invoke(id:Long) =
        repositoryImpl.getEventById(id=id)
}