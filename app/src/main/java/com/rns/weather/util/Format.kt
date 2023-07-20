package com.rns.weather.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun formatDate(timestamp: Int): String {
    val format = SimpleDateFormat("EEE")
    val date = java.util.Date(timestamp.toLong() * 1000)

    return format.format(date)
}

fun formatDecimals(item: Double): String {
    return " %.0f".format(item)
}