package com.template.eventplanner.fragments.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.*
import com.template.core.model.events.CoreEvents
import com.template.core.model.weather.WeatherByCity
import com.template.core.room.entities.EventsDbEntity
import com.template.core.usecase.city.GetCoordinatesUseCase
import com.template.core.usecase.events.ChangeEventUseCase
import com.template.core.usecase.events.GetEventByIdUseCase
import com.template.core.usecase.weather.GetWeatherByCoordUseCase
import com.template.eventplanner.MyApp
import com.template.eventplanner.data.event.Event
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventViewModel : ViewModel() {


    @Inject
    lateinit var useCaseChangeEvent: ChangeEventUseCase


    @Inject
    lateinit var useCaseGetEventById: GetEventByIdUseCase

    @Inject
    lateinit var useCaseGetCoord: GetCoordinatesUseCase

    @Inject
    lateinit var useCaseWeatherByCoord: GetWeatherByCoordUseCase
    private var event =MutableLiveData<Event>()
  private  var id: Long?=null
    init {
        MyApp.appComponent.inject(this)
    }

   fun initEvent() {
        viewModelScope.launch {
            val tmpEvent = useCaseGetEventById.invoke(id = id!!)
            val cityCoordFromDb = useCaseGetCoord.invoke(nameCity = tmpEvent.city)

            val weatherByCity: WeatherByCity? = if (cityCoordFromDb != null)
                useCaseWeatherByCoord.invoke(
                    lat = cityCoordFromDb.lat.toString(),
                    lon = cityCoordFromDb.lon.toString()
                )
            else
                null
            event.postValue( Event.createEvent(coreEvent = tmpEvent,
                cityCoordFromDb,weatherByCity))
        }
    }

    fun putId(ID: Long) {
       id= ID
    }

    fun observeEvent(owner: LifecycleOwner, observer: Observer<Event>) {
       event.observe(owner, observer)
    }

    @SuppressLint("StopShip")
    fun changeEvents(event:Event) {
        viewModelScope.launch {
            useCaseChangeEvent.invoke(
               CoreEvents(
                   id = event.id,
                   name = event.name,
                   description =event.description,
                   timeStart = event.timeStart,
                   timeEnd = event.timeEnd,
                   isVisited =event.isVisited,
                   isLiked = event.isLiked,
                   address = event.address,
                   dateEnd = "${event.dateEnd.first}.${event.dateEnd.second}.${event.dateEnd.third}",
                   dateStart = "${event.dateStart.first}.${event.dateStart.second}.${event.dateStart.third}",
                   city = event.city.name
                   )
            )
        }
    }
}