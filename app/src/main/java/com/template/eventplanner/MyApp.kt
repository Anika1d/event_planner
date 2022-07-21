package com.template.eventplanner

import android.app.Application
import com.template.core.dagger.EventsModule


class MyApp : Application() {
    companion object {
        lateinit var appComponent: AppComponent
    }
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .eventsModule(EventsModule(this))
            .build()
    }
}