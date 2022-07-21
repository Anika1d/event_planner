@file:OptIn(ExperimentalMaterial3Api::class)

package com.template.eventplanner.composescreens

import android.annotation.SuppressLint
import android.widget.CalendarView
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.template.core.model.events.EnterEvents

import com.template.eventplanner.fragments.viewmodels.ChangeEventViewModel
import com.template.eventplanner.ui.theme.DarkPrimaryColor
import com.template.eventplanner.ui.theme.LightPrimaryColor
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
@Composable
fun CreateEvent(event: Array<String>? = null, navController: NavController) {
    val viewModel = viewModel(ChangeEventViewModel::class.java)

    val context = LocalContext.current
    Column(Modifier.height(4.dp)) {
        SmallTopAppBar(
            title = {
                Text(
                    text = if (event == null) "Создание мероприятия" else
                        "Редактирование мероприятия"
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "arrowback"
                    )
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(DarkPrimaryColor)
        )
        val currentDate = Calendar.getInstance().time

        val sdfDay = SimpleDateFormat("dd")
        val startDay = sdfDay.format(currentDate).toInt()

        val sdfMonth = SimpleDateFormat("M")
        val startMonth = sdfMonth.format(currentDate).toInt()
        ///TODO(не робит 2 июля и 1 июля при не тыкание первого календаря)
        val sdfYear = SimpleDateFormat("yyyy")
        val startYear = sdfYear.format(currentDate).toInt()
        var startDate by remember {
            mutableStateOf(
                Triple(startDay, startMonth, startYear)
            )
        }
        var endDate by remember {
            mutableStateOf(
                Triple(startDay, startMonth, startYear)
            )
        }
        var datesAreDifferent by remember {
            mutableStateOf(
//                event?.datesAreDifferent() ?:
                false
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(LightPrimaryColor)
                .padding(8.dp)
        ) {
            items(1) {
                Text("Выберите дату начала мероприятия")
                AndroidView(
                    { CalendarView(it) },
                    modifier = Modifier.wrapContentWidth(),
                    update = { viewCalendar ->
                        viewCalendar.setOnDateChangeListener { it, year, month, day ->
                            startDate =
                                    //    event?.dateStart ?:
                                Triple(day, month + 1, year)
                        }
                    })
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Дата окончания мероприятия другая?")
                    Checkbox(
                        checked = datesAreDifferent,
                        onCheckedChange = { datesAreDifferent = !datesAreDifferent })
                }
                if (datesAreDifferent)
                    AndroidView(
                        { CalendarView(it) },
                        modifier = Modifier.wrapContentWidth(),
                        update = { viewCalendar ->
                            viewCalendar.setOnDateChangeListener { it, year, month, day ->
                                endDate =
                                        ///event?.dateEnd ?:
                                    Triple(day, month + 1, year)
                            }
                        })
                else endDate = startDate
                var textStateStartTime by remember {
                    mutableStateOf(event?.get(4) ?: "")
                }
                OutlinedTextField(
                    value = textStateStartTime,
                    onValueChange = {
                        textStateStartTime = it.take(4)
                    },
                    maxLines = 1,
                    singleLine = true,
                    label = { Text("Начало") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    visualTransformation = {
                        timeFilter(it)
                    },
                )
                var textStateEndTime by remember {
                    mutableStateOf(event?.get(6) ?: "")
                }
                OutlinedTextField(
                    value = textStateEndTime,
                    onValueChange = {
                        textStateEndTime = it.take(4)
                    },
                    maxLines = 1,
                    singleLine = true,
                    label = { Text("Конец") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    visualTransformation = {
                        timeFilter(it)
                    },
                )
                var textStateNameEvents by remember {
                    mutableStateOf(event?.get(1) ?: "")
                }
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .wrapContentHeight(),
                    value = textStateNameEvents,
                    onValueChange = {
                        textStateNameEvents = it.take(55)
                    },
                    label = { Text("Название мероприятие") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                )
                var textStateDescEvents by remember {
                    mutableStateOf(event?.get(2) ?: "")
                }
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .wrapContentHeight(),
                    value = textStateDescEvents,
                    onValueChange = {
                        textStateDescEvents = it
                    },
                    label = { Text("Описание мероприятие") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                )
                var textStateCityEvents by remember {
                    mutableStateOf(event?.get(7) ?: "")
                }
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .wrapContentHeight(),
                    value = textStateCityEvents,
                    onValueChange = {
                        textStateCityEvents = it
                    },
                    label = { Text("Город") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                )
                var textStateAddressEvents by remember {
                    mutableStateOf(event?.get(8) ?: "")
                }
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .wrapContentHeight(),
                    value = textStateAddressEvents,
                    onValueChange = {
                        textStateAddressEvents = it
                    },
                    label = { Text("Адрес") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                )
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        val codeOperation: Int
                        if (textFieldsIsValid(
                                textStateNameEvents,
                                textStateDescEvents,
                                textStateCityEvents,
                                textStateAddressEvents,
                                textStateStartTime,
                                textStateEndTime,
                            )
                        ) {
                            val timeHourS = textStateStartTime.substring(0, 2).toInt()
                            val timeMinS = textStateStartTime.substring(2, 4).toInt()
                            val timeHourE = textStateEndTime.substring(0, 2).toInt()
                            val timeMinE = textStateEndTime.substring(2, 4).toInt()
                            codeOperation = datesIsValid(
                                timeHourS,
                                timeMinS,
                                timeHourE,
                                timeMinE,
                                startDate,
                                endDate,
                            )
                        } else
                            codeOperation = 100
                        var textSuccess = ""
                        when (codeOperation) {
                            0 -> {
                                if (event == null)
                                    viewModel.createEvents(
                                        enterEvents =
                                        EnterEvents(
                                            name = textStateNameEvents,
                                            description = textStateDescEvents,
                                            city = textStateCityEvents,
                                            address = textStateAddressEvents,
                                            timeStart = textStateStartTime,
                                            timeEnd = textStateEndTime,
                                            dateStart =
                                            startDate.first.toString() + '.' +
                                                    startDate.second.toString() + '.' +
                                                    startDate.third.toString(),
                                            dateEnd = endDate.first.toString() + '.' +
                                                    endDate.second.toString() + '.' +
                                                    endDate.third.toString()
                                        )
                                    )
                                else {
                                    viewModel.changeEvent(
                                        EnterEvents(
                                            name = textStateNameEvents,
                                            description = textStateDescEvents,
                                            city = textStateCityEvents,
                                            address = textStateAddressEvents,
                                            timeStart = textStateStartTime,
                                            timeEnd = textStateEndTime,
                                            dateStart =
                                            startDate.first.toString() + '.' +
                                                    startDate.second.toString() + '.' +
                                                    startDate.third.toString(),
                                            dateEnd = endDate.first.toString() + '.' +
                                                    endDate.second.toString() + '.' +
                                                    endDate.third.toString()
                                        ),
                                      id= event[0]
                                    )
                                }
                                textSuccess =
                                    "Успешно"
                            }
                            100 -> textSuccess =
                                "Не все поля заполнены"
                            901 -> textSuccess =
                                "Некорректное время"
                            902 -> textSuccess =
                                "Дата начала не может быть позже Даты конца"
                            903 ->
                                textSuccess =
                                    "Время мероприятие должно быть не менее 5 минут"
                            904 -> textSuccess =
                                "Время начала мероприятия не должно быть позже его конца"
                        }

                        Toast.makeText(
                            context.applicationContext,
                            textSuccess,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                ) {
                    Text("Сохранить")
                }
            }
        }
    }
}

@Composable
fun ChangeEvent(event: Array<String>?, navController: NavController) {
    CreateEvent(navController = navController, event = event)
}

fun timeFilter(text: AnnotatedString): TransformedText {

    val mask = "12:00"
    val trimmed = if (text.text.length >= 4)
        text.text.substring(0, 4)
    else
        text.text
    val annotatedString = AnnotatedString.Builder().run {
        for (i in trimmed.indices) {
            append(trimmed[i])
            if (i % 2 == 1 && i != 3) {
                append(":")
            }
        }
        pushStyle(SpanStyle(color = Color.LightGray))
        append(mask.takeLast((mask.length - length)))
        toAnnotatedString()
    }

    return TransformedText(annotatedString, offsetMapping = OffsetMapping.Identity)
}

internal fun textFieldsIsValid(
    nameEvents: String,
    descEvents: String,
    cityEvents: String,
    addressEvents: String,
    timeStart: String,
    timeEnd: String
): Boolean {
    return (nameEvents != "" && descEvents != "" && cityEvents != ""
            && addressEvents != "" && timeStart != "" && timeEnd != "")
}

internal fun datesIsValid(
    timeHourS: Int,
    timeMinS: Int,
    timeHourE: Int,
    timeMinE: Int,
    startDate: Triple<Int, Int, Int>,
    endDate: Triple<Int, Int, Int>,
): Int {
    if (timeHourS > 23 || timeHourE > 23 || timeMinE > 59 || timeMinS > 59) {
        return 901
    } else if (startDate.third > endDate.third) {
        return 902
    } else if (startDate.third == endDate.third && startDate.second > endDate.second) {
        return 902
    } else if (startDate.third == endDate.third && startDate.second == endDate.second
        && startDate.first > endDate.first
    ) {
        return 902
    } else if (startDate.third == endDate.third && startDate.second == endDate.second
        && startDate.first == endDate.first && timeHourS > timeHourE
    ) {
        return 902
    } else if (startDate.third == endDate.third && startDate.second == endDate.second
        && startDate.first == endDate.first && timeHourS == timeHourE && timeMinS > timeMinE
    ) {
        return 902
    } else if (startDate.third == endDate.third && startDate.second == endDate.second
        && startDate.first == endDate.first && timeHourS == timeHourE
    ) {
        if (timeMinE - timeHourS < 5)
            return 903
        if (timeMinS > timeMinE)
            return 904
        return 0
    } else {
        return 0
    }
}

