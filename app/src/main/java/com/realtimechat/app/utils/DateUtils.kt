package com.realtimechat.app.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun getCurrentDate(): Date {
        val calendar = Calendar.getInstance().time
        return calendar
    }

    fun getStringDateFormat(date: Date, format: String): String {
        val simpleDateFormat = SimpleDateFormat(format, Locale.ENGLISH)
        val dformat = simpleDateFormat.format(date)
        return dformat
    }

    fun FromISOFormat(format: String, dateValue: String): String? {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        val outputFormat = SimpleDateFormat(format, Locale.ENGLISH)
        // use UTC as timezone
        //sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            val date = sdf.parse(dateValue)
            outputFormat.timeZone = TimeZone.getTimeZone("Asia/Jakarta")
            return outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            return null
        }
    }

    fun getMonthLabelShort(posisi: Int): String {

        val titles = arrayOf("Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul", "Agu", "Sep", "Okt", "Nov", "Des")

        return titles[posisi]
    }

    fun getDayLabelShort(posisi: Int): String {

        val titles = arrayOf("Min", "Sen", "Sel", "Rab", "Kam", "Jum", "Sab")

        return titles[posisi]
    }

    fun getDayFromDateString(stringDate: String, dateTimeFormat: String): String {
        val daysArray = arrayOf("Min", "Sen", "Sel", "Rab", "Kam", "Jum", "Sab")
        var day = ""

        var dayOfWeek = 0
        //dateTimeFormat = yyyy-MM-dd HH:mm:ss
        val formatter = SimpleDateFormat(dateTimeFormat)
        val date: Date
        try {
            date = formatter.parse(stringDate)
            val c = Calendar.getInstance()
            c.time = date
            dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1
            if (dayOfWeek < 0) {
                dayOfWeek += 7
            }
            day = daysArray[dayOfWeek]
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return day
    }
}