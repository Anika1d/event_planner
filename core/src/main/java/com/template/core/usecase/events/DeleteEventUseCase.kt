package com.template.core.usecase.events

import com.template.core.room.entities.EventsDbEntity
import com.template.core.room.repository.impl.EventsRepositoryImpl
import javax.inject.Inject

class DeleteEventUseCase @Inject constructor(private val repositoryImpl: EventsRepositoryImpl) {
    suspend operator fun invoke(events: EventsDbEntity) =
        repositoryImpl.deleteEvent(events = events)
}