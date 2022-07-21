package com.template.core.room.repository

import com.template.core.model.events.CoreEvents
import com.template.core.model.events.EnterEvents
import com.template.core.room.entities.EventsDbEntity

interface EventsRepository {
    fun getEventList(): List<CoreEvents>
    fun getEventById(id: Long): CoreEvents
    fun getEventListByDate(dateStart: String): List<CoreEvents>
    fun getCurrentEventList():List<CoreEvents>
    suspend fun createEvent(enterEvents: EnterEvents)
    suspend fun changeEvent(events: CoreEvents)
    suspend fun deleteEvent(events: EventsDbEntity)
    fun getIsVisitedEventList(isVisited: Boolean): List<CoreEvents>
    fun getIsLikedEventList(isLiked: Boolean): List<CoreEvents>
    fun getIsLikedEventListByDate(isLiked: Boolean, dateStart: String): List<CoreEvents>
    fun getIsVisitedEventListByDate(isVisited: Boolean, dateStart: String): List<CoreEvents>
}