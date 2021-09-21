package com.riyazuddin.noteit.common

import java.text.SimpleDateFormat
import java.util.*

fun getDate(date: Long): String {
    val sdf = SimpleDateFormat("dd-mm-yy hh:mm", Locale.US)
    return "Date: ${sdf.format(date)}"
}