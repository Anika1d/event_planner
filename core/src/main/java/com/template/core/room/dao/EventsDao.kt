package com.template.core.room.dao

import androidx.room.*
import com.template.core.model.events.EventsChangeInfoTuple
import com.template.core.room.entities.EventsDbEntity

@Dao
interface EventsDao {

    @Query("SELECT *  FROM events WHERE date_start=:dateStart ORDER by date_start DESC")
    fun getEventListByDate(dateStart: String): List<EventsDbEntity>

    @Query("SELECT *  FROM events")
    fun getEventList(): List<EventsDbEntity>


    @Query("SELECT *  FROM events WHERE is_visited=null" )
    fun getCurrentEventList(): List<EventsDbEntity>



    @Query("SELECT * FROM events WHERE id=:ID")
    fun getEventById(ID: Long): EventsDbEntity

    @Query("SELECT *  FROM events WHERE is_liked=:isLiked ORDER by created_at DESC")
    fun getIsLikedEventList(isLiked: Boolean): List<EventsDbEntity>

    @Query("SELECT *  FROM events WHERE is_visited=:isVisited ORDER by created_at DESC")
    fun getIsVisitedEventList(isVisited: Boolean): List<EventsDbEntity>

   /// @Query("SELECT *  FROM events WHERE date_end>:TIME and date_start<:TIME ORDER by date_start DESC")

    @Query("SELECT *  FROM events WHERE is_liked=:isLiked AND date_start=:dateStart ORDER by date_start DESC")
    fun getIsLikedEventListByDate(isLiked: Boolean, dateStart: String): List<EventsDbEntity>

    @Query("SELECT *  FROM events WHERE is_visited=:isVisited AND date_start=:dateStart ORDER by date_start DESC")
    fun getIsVisitedEventListByDate(isVisited: Boolean, dateStart: String): List<EventsDbEntity>


    @Update(entity = EventsDbEntity::class)
    suspend fun changeEvent(changeInfoTuple: EventsChangeInfoTuple)

    @Insert(entity = EventsDbEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun createEvent(eventsDbEntity: EventsDbEntity)

    @Delete(entity = EventsDbEntity::class)
    suspend fun deleteEvent(eventsDbEntity: EventsDbEntity)
}