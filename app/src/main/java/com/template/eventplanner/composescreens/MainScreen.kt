package com.template.eventplanner.composescreens

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.template.eventplanner.R
import com.template.eventplanner.data.event.Event
import com.template.eventplanner.fragments.viewmodels.MainViewModel
import com.template.eventplanner.math.iconIdToIcon
import com.template.eventplanner.ui.theme.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil


@ExperimentalMaterial3Api
@Composable
fun mainScreen(
    modifier: Modifier,
    lifecycle: LifecycleOwner,
    navControllerCompose: NavHostController,
    navController: NavController,
    viewModel: MainViewModel
) {

    val listEvents = remember {
        mutableStateOf(
            emptyList<Event>()
        )
    }

    viewModel.apply {
        changeCurrentEventList()
        changeVisibleEventListByDate()
        listEvents.value = visibleEventList
    }

    Column(
        modifier = modifier
    ) {
        ///  if (viewModel.modeScreen.value == 0)
        calendar(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkPrimaryColor)
                .padding(8.dp),
            viewModel = viewModel
        )
        /*TODO("Убрать высоту 1ф")*/

        listEvents(
            listEvents = listEvents.value,
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(1f)
                .background(LightPrimaryColor)
                .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 0.dp),
            navController = navController,
        )
    }

}


@ExperimentalMaterial3Api
@SuppressLint("SimpleDateFormat")
@Composable
internal fun calendar(
    modifier: Modifier = Modifier,
    itemColor: Color = DefaultPrimaryColor,
    itemTextColor: Color = White,
    viewModel: MainViewModel,
) {
    val rcoroutineScope = rememberCoroutineScope()
    val currentDate = Calendar.getInstance().time
    val sdfDay = SimpleDateFormat("dd")
    val sdfMonth = SimpleDateFormat("M")
    val tmpMonth = sdfMonth.format(currentDate).toInt()
    var startMonth by remember { mutableStateOf(tmpMonth) }
    val dayOfMonth = DaysInMonth[startMonth]!!
    val lazyState = rememberLazyListState()
    val tmpDay = sdfDay.format(currentDate).toInt()
    var startDay by remember { mutableStateOf(tmpDay) }
    val listt =
        MutableList(if (tmpMonth == startMonth) dayOfMonth - startDay + 1 else dayOfMonth + 2) {
            remember {
                mutableStateOf(false)
            }
        }
    LazyRow(
        modifier = modifier,
        state = lazyState,
    ) {
        items(count = if (tmpMonth == startMonth) dayOfMonth - startDay + 1 else dayOfMonth + 2) { it ->
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(0.2f)
                    .background(if (!listt[it].value) itemColor else Red)
                    .shadow(5.dp, shape = RectangleShape)
            ) {
                if (tmpMonth == startMonth) {
                    if (it != dayOfMonth - startDay)
                        Button(
                            modifier = Modifier
                                .background(itemColor)
                                .padding(4.dp),
                            colors = ButtonDefaults.buttonColors(itemColor),
                            shape = RectangleShape,
                            onClick = {
                                if (!listt[it].value) {
                                    listt.map {
                                        it.value = false
                                    }
                                    listt[it].value = true
                                    viewModel.changeVisibleEventListByDate("${it + startDay}.${startMonth}.2022")
                                } else {
                                    listt[it].value = false
                                    viewModel.changeVisibleEventListByDate()
                                }
                            }
                        ) {
                            Column(
                                //    modifier = Modifier
                                //        .background(if (!listt[it].value)itemColor else Red),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(text = "${it + startDay}", color = itemTextColor)
                                Text(
                                    text = MonthRussianName[startMonth]!!,
                                    color = itemTextColor
                                )
                            }
                        }
                    else
                        Button(
                            colors = ButtonDefaults.buttonColors(itemColor),
                            onClick = {
                                rcoroutineScope.launch { lazyState.scrollToItem(1) }

                                startDay = 1
                                startMonth += 1
                                listt.map {
                                    it.value = false
                                }
                                if (listt.size < dayOfMonth) {
                                    while (listt.size != dayOfMonth) {
                                        listt.add(
                                            mutableStateOf(false)
                                        )
                                    }
                                } else {
                                    while (listt.size != dayOfMonth) {
                                        listt.remove(
                                            mutableStateOf(false)
                                        )
                                    }
                                }
                            }) {
                            Column(
                                modifier = Modifier
                                    .background(itemColor)
                                    .padding(4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(text = "След")
                                Text(text = "Месяц")
                            }
                        }
                } else {
                    when (it) {
                        0 -> Button(
                            colors = ButtonDefaults.buttonColors(itemColor),
                            onClick = {
                                rcoroutineScope.launch { lazyState.scrollToItem(DaysInMonth[startMonth - 1]!!) }
                                startMonth -= 1
                                startDay = if (tmpMonth == startMonth) tmpDay else 1

                                listt.map {
                                    it.value = false
                                }
                                val tmp_size = listt.size
                                if (tmp_size < dayOfMonth) {
                                    listt.addAll(
                                        Collections.nCopies(
                                            dayOfMonth - tmp_size,
                                            mutableStateOf(false)
                                        )
                                    )

                                } else {
                                    listt.removeAll(
                                        Collections.nCopies(
                                            tmp_size - dayOfMonth,
                                            mutableStateOf(false)
                                        )
                                    )
                                }

                            }) {
                            Column(
                                modifier = Modifier
                                    .background(itemColor)
                                    .padding(4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(text = "Пред")
                                Text(text = "Месяц")
                            }
                        }
                        dayOfMonth + 1 -> {
                            Button(
                                colors = ButtonDefaults.buttonColors(itemColor),
                                onClick = {
                                    rcoroutineScope.launch { lazyState.scrollToItem(1) }
                                    startDay = 1
                                    startMonth += 1
                                    listt.map {
                                        it.value = false
                                    }
                                    val tmp_size = listt.size
                                    if (tmp_size < dayOfMonth) {
                                        listt.addAll(
                                            Collections.nCopies(
                                                dayOfMonth - tmp_size,
                                                mutableStateOf(false)
                                            )
                                        )

                                    } else {
                                        listt.removeAll(
                                            Collections.nCopies(
                                                tmp_size - dayOfMonth,
                                                mutableStateOf(false)
                                            )
                                        )
                                    }

                                }) {
                                Column(
                                    modifier = Modifier
                                        .background(itemColor)
                                        .padding(4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Text(text = "След")

                                    Text(text = "Месяц")
                                }
                            }
                        }
                        else -> {
                            Button(
                                modifier = Modifier
                                    .background(itemColor)
                                    .padding(4.dp),
                                colors = ButtonDefaults.buttonColors(itemColor),
                                shape = RectangleShape,
                                onClick = {
                                    if (!listt[it].value) {
                                        listt.map {
                                            it.value = false
                                        }
                                        listt[it].value = true
                                        viewModel.changeVisibleEventListByDate("${it + startDay}.${startMonth}.2022")
                                    } else {
                                        listt[it].value = false
                                        viewModel.changeVisibleEventListByDate()
                                    }
                                }) {
                                Column(
                                    modifier = Modifier
                                        .background(itemColor),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Text(text = "$it", color = itemTextColor)
                                    Text(
                                        text = MonthRussianName[startMonth]!!,
                                        color = itemTextColor
                                    )
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}


@Composable
internal fun listEvents(
    listEvents: List<Event>,
    modifier: Modifier = Modifier,
    navController: NavController,
) {

    val textFlag by remember {
        mutableStateOf(0)
    }
    Box(modifier = modifier, contentAlignment = Alignment.TopEnd) {
        Column(Modifier.fillMaxSize()) {
            LazyColumn(verticalArrangement = Arrangement.SpaceBetween) {
                listEvents.let {
                    items(it.size) { index ->
//
//                       TODO(оформить печатание текста даты)
//
//                        if (textFlag==0)
//                            Text(
//                                modifier = Modifier.fillMaxWidth(),
//                                text = "Ближайшие мероприятия",
//                                color = SecondaryTextColor,
//                                fontSize = 18.sp
//                            )
//
//                        else{
//                            if (listEvents.value!![index].)
//                        }
                        itemEvent(
                            navController = navController,
                            events = listEvents[index],
                        )
                    }
                }
            }
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            FloatingActionButton(
                modifier = Modifier.padding(bottom = 8.dp), onClick = {
                    val bundle=Bundle()
                    bundle.putStringArray("event", arrayOf())
                    navController.navigate(R.id.action_mainFragment_to_changeEventFragment,bundle)
                }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "addEvent")
            }
        }
    }

}

@Composable
internal fun itemEvent(
    navController: NavController,
    events: Event,
) {
    Spacer(modifier = Modifier.height(6.dp))
    Button(
        modifier = Modifier.shadow(3.dp, shape = CircleShape),
        onClick = {
            val bundle = Bundle()

            bundle.putLong("id", events.id)
            navController.navigate(R.id.action_mainFragment_to_infoEventFragment, bundle)
        },
        colors = ButtonDefaults.buttonColors(Pink80)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(1f),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
                AsyncImage(
                    model =
                    iconIdToIcon[events.city.weatherByCity?.weather?.get(0)?.icon],
                    contentDescription = "weather",
                    placeholder = painterResource(id = R.drawable.ic_baseline_cached_24),
                    error =painterResource(id = R.drawable.ic_baseline_cached_24) ,
                    modifier = Modifier.size(30.dp)

                )
            val tmp = events.city.weatherByCity?.main?.temp
            if (tmp != null) {
                Text("${ceil(tmp - 273.15)}  °C")
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(events.name)
                Text(" ${events.dateStart.first}  ${MonthRussianName[events.dateStart.second - 1]}")
            }
        }
    }
}

internal val DaysInMonth: Map<Int, Int> = mapOf(
    Pair(1, 31), Pair(2, 28), Pair(3, 31), Pair(4, 30),
    Pair(5, 31), Pair(6, 30), Pair(7, 31), Pair(8, 31),
    Pair(9, 30), Pair(10, 31), Pair(11, 30), Pair(12, 31)
)
internal val MonthRussianName: Map<Int, String> = mapOf(
    Pair(1, "Янв"), Pair(2, "Фев"), Pair(3, "Мар"), Pair(4, "Апр"),
    Pair(5, "Мая"), Pair(6, "Июн"), Pair(7, "Июл"), Pair(8, "Авг"),
    Pair(9, "Сен"), Pair(10, "Окт"), Pair(11, "Ноя"), Pair(12, "Дек")
)

