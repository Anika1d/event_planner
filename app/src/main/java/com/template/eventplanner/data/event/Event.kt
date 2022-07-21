package com.template.eventplanner.data.event

import com.template.core.model.city.CoordinatesCityTuples
import com.template.core.model.events.CoreEvents
import com.template.core.model.weather.WeatherByCity
import com.template.eventplanner.data.city.City
import com.template.eventplanner.math.convertStringToTripleDateFormat

data class Event(
    val id: Long,
    val name: String,
    val description: String,
    val dateStart: Triple<Int, Int, Int>,
    val timeStart: String,
    val dateEnd: Triple<Int, Int, Int>,
    val timeEnd: String,
    val city: City,
    val address: String,
    var isLiked: Boolean = false,
    var isVisited: Boolean? = null,
) {
    fun datesAreDifferent(): Boolean {
        return dateStart.first == dateEnd.first && dateStart.second == dateEnd.second && dateStart.third == dateEnd.third
    }

    fun toStringArray(): Array<String> {
        return listOf<String>(
            id.toString(),  //0
            name,//1
            description,//2
            dateStart.toString(),//3
            timeStart,//4
            dateEnd.toString(),//5
            timeEnd,//6
            city.name,//7
            address,//8
        ).toTypedArray()
    }

    companion object {
        fun createEvent(
            coreEvent: CoreEvents,
            cityCoordFromDb: CoordinatesCityTuples?,
            weatherByCity: WeatherByCity?
        ): Event {
            return Event(
                id = coreEvent.id,
                name = coreEvent.name,
                description = coreEvent.description,
                timeStart = coreEvent.timeStart,
                timeEnd = coreEvent.timeEnd,
                isVisited = coreEvent.isVisited,
                isLiked = coreEvent.isLiked,
                address = coreEvent.address,
                city = City(
                    name = coreEvent.city,
                    lat = cityCoordFromDb?.lat,
                    lon = cityCoordFromDb?.lon,
                    weatherByCity = weatherByCity
                ),
                dateStart = convertStringToTripleDateFormat(coreEvent.dateStart),
                dateEnd = convertStringToTripleDateFormat(coreEvent.dateEnd)
            )
        }
    }
}

//Triple(1,2,2002))///