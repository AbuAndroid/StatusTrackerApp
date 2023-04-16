package com.example.statustracker.warehouse

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    @SuppressLint("SimpleDateFormat")
    fun getUtcTime(date: String?): String {
        try {
            date?.let {

                val utcFormate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                utcFormate.timeZone = TimeZone.getTimeZone("UTC")
                val utcDate = utcFormate.parse(date)

                val localDateFormat = SimpleDateFormat("dd-MM-yyyy")
                localDateFormat.timeZone = TimeZone.getDefault()

                return localDateFormat.format(utcDate)
            }
        } catch (exp: java.lang.Exception) {
            exp.printStackTrace()
        }
        return ""
    }
}