package com.template.core.model.events

import androidx.room.ColumnInfo

data class EventsDataStartTuple(
    @ColumnInfo(name = "date_start") val dateStart: String,
)

class EventsChangeInfoTuple (
    val id: Long,
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
)


class EventsChangeShort (
    val id: Long,
    val name: String,
    val description: String,
    @ColumnInfo(name = "date_start") val dateStart: String,
    @ColumnInfo(name = "time_start") val timeStart: String,
    @ColumnInfo(name = "date_end") val dateEnd: String,
    @ColumnInfo(name = "time_end") val timeEnd: String,
    val city: String,
    val address: String,
)
