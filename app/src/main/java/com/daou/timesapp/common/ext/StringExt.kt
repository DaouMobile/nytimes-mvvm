package com.daou.timesapp.common.ext

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String?.convertMilliSec(): Long {
    if (this.isNullOrBlank()) return 0
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZ", Locale.US)
    return try {
        val date: Date = format.parse(this)
        date.time
    } catch (e: ParseException) {
        0
    }
}