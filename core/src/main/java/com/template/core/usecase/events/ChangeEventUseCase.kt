package com.template.core.usecase.events

import com.template.core.model.events.CoreEvents
import com.template.core.model.events.EnterEvents
import com.template.core.room.entities.EventsDbEntity
import com.template.core.room.repository.impl.EventsRepositoryImpl
import javax.inject.Inject

class ChangeEventUseCase @Inject constructor(private val repositoryImpl: EventsRepositoryImpl) {
    suspend operator fun invoke(events: CoreEvents) =
        repositoryImpl.changeEvent(events)
}