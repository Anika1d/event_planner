package com.template.core.room.repository.impl

import android.database.sqlite.SQLiteConstraintException
import com.template.core.model.events.CoreEvents
import com.template.core.model.events.EnterEvents
import com.template.core.model.events.EventsChangeInfoTuple
import com.template.core.room.dao.EventsDao
import com.template.core.room.entities.EventsDbEntity
import com.template.core.room.repository.EventsRepository
import javax.inject.Inject

class EventsRepositoryImpl @Inject constructor(
    val eventsDao: EventsDao
) : EventsRepository {

    override fun getEventListByDate(dateStart: String): List<CoreEvents> =
        eventsDao.getEventListByDate(dateStart = dateStart).map { it.toEvents() }

    override fun getCurrentEventList(): List<CoreEvents> =
        eventsDao.getCurrentEventList().map { it.toEvents() }

    override fun getEventList(): List<CoreEvents> = eventsDao.getEventList().map { it.toEvents() }

    override fun getEventById(id: Long): CoreEvents = eventsDao.getEventById(id).toEvents()


    override suspend fun createEvent(enterEvents: EnterEvents) {
        try {
            val events = EventsDbEntity.enterEvent(
                enterEvents
            )
            eventsDao.createEvent(events)
        } catch (e: SQLiteConstraintException) {

        }
    }


    override suspend fun changeEvent(events: CoreEvents) {
        eventsDao.changeEvent(
            changeInfoTuple = EventsChangeInfoTuple(
                id = events.id,
                name = events.name,
                description = events.description,
                dateStart = events.dateStart,
                dateEnd = events.dateEnd,
                timeStart = events.timeStart,
                timeEnd = events.timeEnd,
                city = events.city,
                address = events.address,
                isLiked = events.isLiked,
                isVisited = events.isVisited
            )
        )
    }


    override suspend fun deleteEvent(events: EventsDbEntity) {
        eventsDao.deleteEvent(events)
    }

    override fun getIsVisitedEventList(isVisited: Boolean): List<CoreEvents> =
        eventsDao.getIsVisitedEventList(isVisited).map { it.toEvents() }

    override fun getIsLikedEventList(isLiked: Boolean): List<CoreEvents> =
        eventsDao.getIsLikedEventList(isLiked).map { it.toEvents() }

    override fun getIsLikedEventListByDate(isLiked: Boolean, dateStart: String): List<CoreEvents> =
        eventsDao.getIsLikedEventListByDate(isLiked = isLiked, dateStart = dateStart)
            .map { it.toEvents() }

    override fun getIsVisitedEventListByDate(
        isVisited: Boolean,
        dateStart: String
    ): List<CoreEvents> =
        eventsDao.getIsVisitedEventListByDate(isVisited = isVisited, dateStart = dateStart)
            .map { it.toEvents() }

}