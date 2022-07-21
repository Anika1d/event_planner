package com.template.core.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.template.core.model.events.CoreEvents
import com.template.core.model.events.EnterEvents

@Entity(
    tableName = "events",
    indices = [
        Index("time_start", orders = [Index.Order.DESC])
    ]
)

class EventsDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val description: String,
    @ColumnInfo(name = "date_start") val dateStart: String,
    @ColumnInfo(name = "time_start") val timeStart: String,
    @ColumnInfo(name = "date_end") val dateEnd: String,
    @ColumnInfo(name = "time_end") val timeEnd: String,
    val city: String,
    val address: String,
    @ColumnInfo(name = "is_liked") val isLiked: Boolean = false,
    @ColumnInfo(name = "is_visited") val isVisited: Boolean? = null,
    @ColumnInfo(name = "created_at") val createdAt: Long
) {
    fun toEvents(): CoreEvents = CoreEvents(
        id = id,
        name = name,
        description = description,
        dateStart = dateStart,
        timeStart = timeStart,
        dateEnd = dateEnd,
        timeEnd = timeEnd,
        city = city,
        address = address,
        isLiked=isLiked,
        isVisited=isVisited
    )

    companion object {
        fun enterEvent(enterEvents: EnterEvents): EventsDbEntity = EventsDbEntity(
            id = 0,
            name = enterEvents.name,
            description = enterEvents.description,
            dateStart = enterEvents.dateStart,
            timeStart = enterEvents.timeStart,
            dateEnd = enterEvents.dateEnd,
            timeEnd = enterEvents.timeEnd,
            city = enterEvents.city,
            address = enterEvents.address,
            createdAt = System.currentTimeMillis()
        )
    }
}