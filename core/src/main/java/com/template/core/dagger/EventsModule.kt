package com.template.core.dagger

import android.app.Application
import android.content.Context
import com.template.core.AppRoom
import com.template.core.room.dao.EventsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class EventsModule(private val application: Application) {

    @Singleton
    @Provides
    fun getEventDao(appDb: AppRoom): EventsDao {
        return appDb.getEventDao()
    }

    @Singleton
    @Provides
    fun getRoomDbInstance(): AppRoom {
        return AppRoom.getAppRoomInstance(provideAppContext())
    }

    @Singleton
    @Provides
    fun provideAppContext(): Context = application.applicationContext
}