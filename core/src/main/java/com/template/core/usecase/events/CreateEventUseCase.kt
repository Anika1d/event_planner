package com.template.core.usecase.events

import com.template.core.model.events.EnterEvents
import com.template.core.room.repository.impl.EventsRepositoryImpl
import javax.inject.Inject

class CreateEventUseCase @Inject constructor(private val repositoryImpl: EventsRepositoryImpl) {
    suspend operator fun invoke(enterEvents: EnterEvents) = repositoryImpl.createEvent(enterEvents)
}