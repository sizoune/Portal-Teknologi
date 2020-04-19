package com.example.portalteknologi

import android.text.format.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun convertStringToEEEDDMMMYYYY(strDate: String): String {
    var date: Date? = null
    try {
        date = SimpleDateFormat("yyyy-MM-dd").parse(strDate)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return DateFormat.format("EEE, dd MMM yyyy", date).toString()
}