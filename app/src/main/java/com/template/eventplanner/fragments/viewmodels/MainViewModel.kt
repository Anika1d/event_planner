package com.template.eventplanner.fragments.viewmodels

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.template.core.model.city.CoordinatesCityTuples
import com.template.core.model.city.EnterCity
import com.template.core.model.events.CoreEvents
import com.template.core.model.weather.WeatherByCity
import com.template.core.usecase.city.GetCoordinatesUseCase
import com.template.core.usecase.city.InsertCityUseCase
import com.template.core.usecase.events.*
import com.template.core.usecase.weather.GetCoordByNameCityUseCase
import com.template.core.usecase.weather.GetWeatherByCoordUseCase
import com.template.eventplanner.MyApp
import com.template.eventplanner.data.event.Event
import com.template.eventplanner.math.convertLongToTripleTime
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainViewModel : ViewModel() {


    private var allEventsList: MutableState<List<Event>> = mutableStateOf(emptyList())
    private var eventListByDate: MutableState<List<Event>> = mutableStateOf(emptyList())
    var visibleEventList = mutableStateListOf<Event>()
    var modeScreen = mutableStateOf(0)


    init {
        MyApp.appComponent.inject(mainViewModel = this)
        initEventList()
    }



    fun changeModeScreen(mode: Int) {
        modeScreen.value = mode
    }

    fun changeCurrentEventList() {
        viewModelScope.launch {
            val tmp = useCaseGetCurrentEventList.invoke().map {
                val cityCoordFromDb = initCoordByCity(it)
                val weatherByCity: WeatherByCity? = initWeatherByCity(cityCoordFromDb)
                Event.createEvent(coreEvent = it, cityCoordFromDb, weatherByCity)
            }
            visibleEventList.apply {
                clear()
                addAll(tmp)
            }
        }
    }

    fun changeVisibleEventListByDate(date: String? = null) {
        if (date != null) {
            viewModelScope.launch {
                eventListByDate.value = (
                        useCaseGetEventListByDate.invoke(date).map {
                            val cityCoordFromDb = initCoordByCity(it)
                            val weatherByCity: WeatherByCity? = initWeatherByCity(cityCoordFromDb)
                            Event.createEvent(coreEvent = it, cityCoordFromDb, weatherByCity)
                        }
                        )
                eventListByDate.value.let {
                    visibleEventList.apply {
                        clear()
                        addAll(it)
                    }
                }
            }

        } else {
            allEventsList.value.let {
                visibleEventList.apply {
                    clear()
                    addAll(it)
                }
            }
        }
    }

    fun changeVisibleEventListByVisited(isVisited: Boolean) {

        viewModelScope.launch {
            val tmp = useCaseGetVisitedEventList.invoke(isVisited).map {
                val cityCoordFromDb = initCoordByCity(it)
                val weatherByCity: WeatherByCity? = initWeatherByCity(cityCoordFromDb)
                Event.createEvent(coreEvent = it, cityCoordFromDb, weatherByCity)
            }
            visibleEventList.apply {
                clear()
                addAll(tmp)
            }
        }
    }


    fun changeVisibleEventListByLiked(isLiked: Boolean) {
        viewModelScope.launch {
            val tmp = useCaseGetLikedEventList.invoke(isLiked).map {
                val cityCoordFromDb = initCoordByCity(it)
                val weatherByCity: WeatherByCity? = initWeatherByCity(cityCoordFromDb)
                Event.createEvent(coreEvent = it, cityCoordFromDb, weatherByCity)
            }
            visibleEventList.apply {
                clear()
                addAll(tmp)
            }
        }
    }


    fun initEventList() {
        viewModelScope.launch {
            val mutableList: MutableList<Event> = emptyList<Event>().toMutableList()
            useCaseGetAllEventList.invoke().forEach {
                val cityCoordFromDb = initCoordByCity(it)
                val weatherByCity: WeatherByCity? = initWeatherByCity(cityCoordFromDb)
                mutableList.add(
                    Event.createEvent(it, cityCoordFromDb, weatherByCity)
                )
            }
            val time = convertLongToTripleTime(System.currentTimeMillis())
            val currentEventsList: MutableList<Event> = emptyList<Event>().toMutableList()
            for (i in 0 until mutableList.size) {
                if (mutableList[i].dateStart.third < time.third) {
                    if (mutableList[i].isVisited != true) {
                        mutableList[i].isVisited = false
                        changeEvents(mutableList[i])
                    }
                } else if (mutableList[i].dateStart.second < time.second) {
                    if (mutableList[i].isVisited != true) {
                        mutableList[i].isVisited = false
                        changeEvents(mutableList[i])
                    }
                } else if (mutableList[i].dateStart.first < time.first) {
                    if (mutableList[i].isVisited != true) {
                        mutableList[i].isVisited = false
                        changeEvents(mutableList[i])
                    }
                } else {
                    currentEventsList.add(mutableList[i])
                }
            }
            allEventsList.value = currentEventsList.toList()
        }
    }

    suspend fun initCoordByCity(coreEvent: CoreEvents): CoordinatesCityTuples? {
        var cityCoord = useCaseGetCoord.invoke(nameCity = coreEvent.city)
        if (cityCoord == null) {
            cityCoord = useCaseGetCoordRetrofit.invoke(nameCity = coreEvent.city)
            useCaseInsertCity.invoke(
                EnterCity(
                    name = coreEvent.city,
                    lat = cityCoord?.lat,
                    lon = cityCoord?.lat,
                )
            )
        }
        return cityCoord

    }

    suspend fun initWeatherByCity(cityCoord: CoordinatesCityTuples?): WeatherByCity? {
        return if (cityCoord != null)
            useCaseWeatherByCoord.invoke(
                lat = cityCoord.lat.toString(),
                lon = cityCoord.lon.toString()
            )
        else
            null
    }


    @SuppressLint("StopShip")
    private fun changeEvents(event: Event) {
        viewModelScope.launch {
            useCaseChangeEvent.invoke(
                CoreEvents(
                    id = event.id,
                    name = event.name,
                    description = event.description,
                    timeStart = event.timeStart,
                    timeEnd = event.timeEnd,
                    isVisited = event.isVisited,
                    isLiked = event.isLiked,
                    address = event.address,
                    dateEnd = "${event.dateEnd.first}.${event.dateEnd.second}.${event.dateEnd.third}",
                    dateStart = "${event.dateStart.first}.${event.dateStart.second}.${event.dateStart.third}",
                    city = event.city.name
                )
            )
        }
    }


    @Inject
    lateinit var useCaseGetCurrentEventList: GetCurrentEventListUseCase


    @Inject
    lateinit var useCaseChangeEvent: ChangeEventUseCase

    @Inject
    lateinit var useCaseGetLikedEventList: GetLikedEventListUseCase

    @Inject
    lateinit var useCaseGetVisitedEventList: GetVisitedEventListUseCase

    @Inject
    lateinit var useCaseGetLikedEventListByDate: GetLikedEventListByDateUseCase

    @Inject
    lateinit var useCaseGetVisitedEventListByDate: GetVisitedEventListByDateUseCase

    @Inject
    lateinit var useCaseGetEventListByDate: GetEventListByDateUseCase

    @Inject
    lateinit var useCaseGetCoordRetrofit: GetCoordByNameCityUseCase

    @Inject
    lateinit var useCaseGetAllEventList: GetAllEventListUseCase

    @Inject
    lateinit var useCaseInsertCity: InsertCityUseCase

    @Inject
    lateinit var useCaseGetCoord: GetCoordinatesUseCase

    @Inject
    lateinit var useCaseWeatherByCoord: GetWeatherByCoordUseCase


}