package com.template.core.dagger

import com.template.core.AppRoom
import com.template.core.room.dao.CityDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CityModule {
    @Singleton
    @Provides
    fun getCityDao(appDb: AppRoom): CityDao {
        return appDb.getCityDao()
    }
}