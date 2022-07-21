package com.template.core.room.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.template.core.model.city.CoreCity
import com.template.core.model.city.EnterCity

@Entity(
    tableName = "city",
    indices = [
        Index("name", unique = true)
    ]
)
data class CityDbEntity(
    @PrimaryKey(autoGenerate = true)     val id: Long,
    val name: String,
    val lat: Double?,
    val lon: Double?,
) {
    fun toCity(): CoreCity = CoreCity(
        id = id,
        name = name,
        lat = lat,
        lon = lon
    )

    companion object {
        fun enterCity(enterCity: EnterCity): CityDbEntity = CityDbEntity(
            id=0,
            name = enterCity.name,
            lat = enterCity.lat,
            lon = enterCity.lon,
        )
    }

}