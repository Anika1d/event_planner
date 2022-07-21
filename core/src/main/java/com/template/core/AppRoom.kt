package com.template.core

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.template.core.room.dao.CityDao
import com.template.core.room.dao.EventsDao
import com.template.core.room.entities.CityDbEntity
import com.template.core.room.entities.EventsDbEntity

@Database(
    version = 1,
    entities = [EventsDbEntity::class, CityDbEntity::class]

)
abstract class AppRoom : RoomDatabase() {
    abstract fun getEventDao(): EventsDao
    abstract fun getCityDao(): CityDao


    companion object {
        private var dbInstance: AppRoom? = null
        fun getAppRoomInstance(context: Context): AppRoom {
            if (dbInstance == null) {
                dbInstance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        AppRoom::class.java,
                        "database3.db"
                    )
                        .allowMainThreadQueries()
                        .build()
            }
            return dbInstance as AppRoom
        }
    }
}