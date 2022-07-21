package com.template.eventplanner

import com.template.core.dagger.CityModule
import com.template.core.dagger.EventsModule
import com.template.core.retrofit.NewsModule
import com.template.core.retrofit.RetrofitClient
import com.template.core.retrofit.RetrofitModule
import com.template.eventplanner.fragments.viewmodels.ChangeEventViewModel
import com.template.eventplanner.fragments.viewmodels.EventViewModel
import com.template.eventplanner.fragments.viewmodels.MainViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [EventsModule::class, CityModule::class,
        RetrofitModule::class, RetrofitClient::class, NewsModule::class]
)
interface AppComponent {
    fun inject(mainViewModel: MainViewModel)
    fun inject(changeEventViewModel: ChangeEventViewModel)
    fun inject(eventViewModel: EventViewModel)

}