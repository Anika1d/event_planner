package com.template.eventplanner.fragments.viewmodels

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.template.core.model.events.CoreEvents
import com.template.core.model.events.EnterEvents
import com.template.core.model.weather.WeatherByCity
import com.template.core.usecase.city.GetCoordinatesUseCase
import com.template.core.usecase.events.CreateEventUseCase
import com.template.core.usecase.events.ChangeEventUseCase
import com.template.core.usecase.events.GetEventByIdUseCase
import com.template.core.usecase.weather.GetWeatherByCoordUseCase
import com.template.eventplanner.MyApp
import com.template.eventplanner.data.event.Event
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChangeEventViewModel : ViewModel() {
    @Inject
    lateinit var useCaseCreateEvent: CreateEventUseCase

    @Inject
    lateinit var useCaseChangeEvent: ChangeEventUseCase


    @Inject
    lateinit var useCaseGetEventById: GetEventByIdUseCase

    @Inject
    lateinit var useCaseGetCoord: GetCoordinatesUseCase

    @Inject
    lateinit var useCaseWeatherByCoord: GetWeatherByCoordUseCase
    var event: MutableState<Event?> = mutableStateOf(null)

    init {
        MyApp.appComponent.inject(this)
    }

    fun createEvents(enterEvents: EnterEvents) {
        viewModelScope.launch {
            useCaseCreateEvent.invoke(
                enterEvents
            )
        }
    }

    @SuppressLint("StopShip")
    fun changeEvent(event: EnterEvents, id: String) {
        viewModelScope.launch {
            useCaseChangeEvent.invoke(
                CoreEvents(
                    id = id.toLong(),
                    name = event.name,
                    description = event.description,
                    timeStart = event.timeStart,
                    timeEnd = event.timeEnd,
                    isVisited = event.isVisited,
                    isLiked = event.isLiked,
                    address = event.address,
                    dateEnd = event.dateEnd,
                    dateStart = event.dateStart,
                    city = event.city
                )
            )
        }
    }

    fun initEvent(id: Long) {
        viewModelScope.launch {
            val tmpEvent = useCaseGetEventById.invoke(id = id)
            val cityCoordFromDb = useCaseGetCoord.invoke(nameCity = tmpEvent.city)

            val weatherByCity: WeatherByCity? = if (cityCoordFromDb != null)
                useCaseWeatherByCoord.invoke(
                    lat = cityCoordFromDb.lat.toString(),
                    lon = cityCoordFromDb.lon.toString()
                )
            else
                null
            event.value = Event.createEvent(
                coreEvent = tmpEvent,
                cityCoordFromDb, weatherByCity
            )
        }
    }
}