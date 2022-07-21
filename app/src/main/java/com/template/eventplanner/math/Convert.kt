package com.template.eventplanner.math

import android.annotation.SuppressLint
import com.template.eventplanner.R
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("SimpleDateFormat")
fun convertStringToTripleDateFormat(date: String): Triple<Int, Int, Int> {
    val listt = date.split('.')
    return Triple(
        listt[0].toInt(),
        listt[1].format(date).toInt(),
        listt[2].format(date).toInt()
    )
}


@SuppressLint("SimpleDateFormat")
fun convertLongToTripleTime(time: Long): Triple<Int, Int, Int> {
    val date = Date(time)
    val formatDay = SimpleDateFormat("dd")
    val formatMonth = SimpleDateFormat("MM")
    val formatYear = SimpleDateFormat("yyyy")
    return Triple<Int, Int, Int>(
        formatDay.format(date).toInt(),
        formatMonth.format(date).toInt(),
        formatYear.format(date).toInt())
}

val iconIdToIcon = mapOf(
    Pair("01d", R.drawable.ic_01d),
    Pair("01n", R.drawable.ic_01n),
    Pair("02d", R.drawable.ic_2d),
    Pair("02n", R.drawable.ic_02n),
    Pair("03d", R.drawable.ic_03),
    Pair("03n", R.drawable.ic_03),
    Pair("04d", R.drawable.ic_04),
    Pair("04n", R.drawable.ic_04),
    Pair("09d", R.drawable.ic_9d),
    Pair("09n", R.drawable.ic_09n),
    Pair("10d", R.drawable.ic_10d),
    Pair("10n", R.drawable.ic_10n),
    Pair("11d", R.drawable.ic_11d),
    Pair("11n", R.drawable.ic_11n),
    Pair("13d", R.drawable.ic_13d),
    Pair("13n", R.drawable.ic_13n),
    Pair("50d", R.drawable.ic_50),
    Pair("50n", R.drawable.ic_50),
)