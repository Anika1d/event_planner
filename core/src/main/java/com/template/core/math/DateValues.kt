package com.template.core.math

 const val MILLIS_IN_SECOND = 1000L
const val SECONDS_IN_MINUTE = 60
 const val MINUTES_IN_HOUR = 60
 const val HOURS_IN_DAY = 24
 const val DAYS_IN_YEAR = 365
const val MILLISECONDS_IN_DAY =
    MILLIS_IN_SECOND* SECONDS_IN_MINUTE* MINUTES_IN_HOUR* HOURS_IN_DAY

 const val MILLISECONDS_IN_YEAR =
    (MILLIS_IN_SECOND * SECONDS_IN_MINUTE * MINUTES_IN_HOUR * HOURS_IN_DAY * DAYS_IN_YEAR).toLong()
