package com.example.statustracker.warehouse

import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun getUtcTime(date: String?): String {
        try {
            date?.let {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                dateFormat.timeZone = TimeZone.getTimeZone("UTC")
                val utcDate = dateFormat.parse(date)
                utcDate?.let {
                    dateFormat.timeZone = TimeZone.getDefault()
                    return dateFormat.format(utcDate)
                }
            }
        } catch (exp: java.lang.Exception) {
            exp.printStackTrace()
        }
        return ""
    }
}