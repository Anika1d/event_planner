package com.template.eventplanner.composescreens

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.template.eventplanner.R
import com.template.eventplanner.data.event.Event
import com.template.eventplanner.fragments.viewmodels.EventViewModel
import com.template.eventplanner.math.iconIdToIcon
import com.template.eventplanner.ui.theme.DarkPrimaryColor
import com.template.eventplanner.ui.theme.LightPrimaryColor
import kotlin.math.ceil

@Composable
fun InfoEvent(lifecycleOwner: LifecycleOwner, navController: NavController, id: Long) {

    val viewModel = viewModel(EventViewModel::class.java)
    var events by remember {
        mutableStateOf(emptyList<Event>())
    }
    viewModel.apply {
        putId(id)
        initEvent()
        observeEvent(lifecycleOwner) {
            events = listOf(it)
        }
    }
    if (events.isNotEmpty())
        Column() {
            toolbar(
                event = events[0],
                viewModel = viewModel,
                navController = navController
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(LightPrimaryColor)
                    .padding(8.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                events[0].apply {
                    Text(
                        text = "${if (dateStart.first < 10) '0' + (dateStart.first.toString()) else dateStart.first}." +
                                "${if (dateStart.second < 10) '0' + (dateStart.second.toString()) else dateStart.second}" +
                                ".${dateStart.third} c " +
                                "${events[0].timeStart[0]}${events[0].timeStart[1]}:${events[0].timeStart[2]}${events[0].timeStart[3]}" +
                                " до ${events[0].timeEnd[0]}${events[0].timeEnd[1]}:${events[0].timeEnd[2]}${events[0].timeEnd[3]}"
                    )
                    Text(text = events[0].description)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {

                            Text("Ожидаемая погода:")

                            AsyncImage(
                                model =
                                iconIdToIcon[events[0].city.weatherByCity?.weather?.get(0)?.icon],
                                contentDescription = "weather",
                                placeholder = painterResource(id = R.drawable.ic_baseline_cached_24),
                                error = painterResource(id = R.drawable.ic_baseline_cached_24),
                                modifier = Modifier.size(30.dp)

                            )
                            val tmp = events[0].city.weatherByCity?.main?.temp
                            if (tmp != null) {
                                Text("${ceil(tmp - 273.15)}  °C")
                            }
                        }
                    }
                    Text(text = "${events[0].city.name}, ${events[0].address}")
                }
            }

        }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun toolbar(event: Event, navController: NavController, viewModel: EventViewModel) {
    val weightScreen = Resources.getSystem().displayMetrics.widthPixels
    var expanded by remember { mutableStateOf(false) }
    var isVisited by remember { mutableStateOf(if (event.isVisited != null) event.isVisited else false) }
    var isLiked by remember { mutableStateOf(event.isLiked) }
    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        topBar = {
            SmallTopAppBar(
                title = { Text(text = event.name) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Default.ArrowBack,
                            contentDescription = "arrowback"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(imageVector = Default.Menu, contentDescription = "menu")
                    }

                },
                colors = TopAppBarDefaults.smallTopAppBarColors(DarkPrimaryColor)
            )
        },
    ) {
        DropdownMenu(
            offset = DpOffset(weightScreen.dp, 0.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = { isLiked = !isLiked },
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(if (isLiked) "В избранном" else "В избранное")
                        TextButton(onClick = {
                            isLiked = !isLiked
                            event.isLiked = isLiked
                            viewModel.changeEvents(event)
                        }) {
                            Text(
                                "♥",
                                color = if (isLiked) Color.Red else LightPrimaryColor,
                                fontSize = 24.sp
                            )
                        }
                    }
                })
            DropdownMenuItem(onClick = {
                isVisited = !isVisited!!
                if (event.isVisited != null) event.isVisited = isVisited
                else event.isVisited = true
                viewModel.changeEvents(event)
            },
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(if (isVisited!!) "\tПосещенно\t" else "Не посещенно")
                        Checkbox(checked = isVisited!!, onCheckedChange = {
                            isVisited = !isVisited!!
                            if (event.isVisited != null) event.isVisited = isVisited
                            else event.isVisited = true
                            viewModel.changeEvents(event)
                        })
                    }
                })
            Divider()
            DropdownMenuItem(onClick = {
                val bundle = Bundle()
                bundle.putStringArray("event", event.toStringArray())
                navController.navigate(
                    R.id.action_infoEventFragment_to_changeEventFragment,
                    bundle
                )
            },
                text = {
                    Text("Изменить")
                })
        }
    }

}