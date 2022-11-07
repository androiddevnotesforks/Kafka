/*
 * Copyright (C) 2018, Alashov Berkeli
 * All rights reserved.
 */
package com.kafka.data.date

import android.content.res.Resources
import com.kafka.data.R
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.Month
import org.threeten.bp.format.DateTimeFormatter
import java.util.Locale

fun String?.toLocalizedDateFromApi(
    resources: Resources,
    withYear: Boolean = true,
    withMonth: Boolean = true,
    withTime: Boolean = false,
    withWeek: Boolean = false,
    shortMonth: Boolean = false
): String = apiDate().toLocalDateTime()
    .localized(resources, withYear, withMonth, withTime, withWeek, shortMonth)

/**
 * @param resources Resources
 * @param withYear should include year in result or not
 * @param withYear should include month in result or not
 * @param withTime should include time in result or not
 * @param withWeek should include week day in result or not
 * @param shortMonth short month or not, (Jan vs January)
 * @return "01 Ýan 2015 22:15" like date
 */
fun LocalDateTime.localized(
    resources: Resources,
    withYear: Boolean = true,
    withMonth: Boolean = true,
    withTime: Boolean = false,
    withWeek: Boolean = false,
    shortMonth: Boolean = false
): String {
    var result = "$dayOfMonth"

    if (withMonth) {
        result += " ${resources.getMonthName(month, shortMonth)}"
    }
    if (withYear) {
        result += ", $year"
    }
    if (withTime) {
        result += " ${HOUR_MINUTES_FORMAT.format(this)}"
    }
    if (withWeek) {
        result = "${resources.getWeekDayShort(this)}, $result"
    }

    return result
}

fun LocalDate.localized(
    resources: Resources,
    withYear: Boolean = true,
    withMonth: Boolean = true,
    withWeek: Boolean = false,
    shortMonth: Boolean = false
): String = atStartOfDay().localized(resources, withYear, withMonth, false, withWeek, shortMonth)

/**
 * @param duration duration in milliseconds
 */
fun Resources.localizeDuration(duration: Long): String {
    val hours = ((duration / 1000) / 60) / 60
    val minutes = ((duration / 1000) / 60) % 60

    return when (hours > 0) {
        true -> getString(R.string.time_duration, hours, minutes)
        else -> getString(R.string.time_duration_minutes, minutes)
    }
}

/**
 * @param short short or not, (Jan vs January)
 */
fun Resources.getMonthName(month: Month, short: Boolean = false): String =
    getStringArray((if (short) R.array.months_short else R.array.months))[month.value - 1]

fun Resources.getWeekDayShort(weekDay: DayOfWeek): String =
    getStringArray(R.array.weeks_short)[weekDay.value - 1]

fun Resources.getWeekDayShort(date: LocalDate) = getWeekDayShort(date.dayOfWeek)
fun Resources.getWeekDayShort(date: LocalDateTime) = getWeekDayShort(date.dayOfWeek)

fun formatDate(
    resources: Resources,
    date: String?,
    dateFormat: String?,
    withYear: Boolean?,
    withMonth: Boolean?,
    withTime: Boolean?,
    withWeek: Boolean?,
    shortMonth: Boolean?
): String = when {
    dateFormat != null -> date.toFormattedDateFromApi(
        DateTimeFormatter.ofPattern(
            dateFormat,
            Locale.getDefault()
        )
    )
    else -> date.toLocalizedDateFromApi(
        resources, (withYear ?: true), (withMonth ?: true), (withTime ?: false),
        (withWeek ?: false), (shortMonth ?: false)
    )
}

fun timeAddZeros(number: Int?, ifZero: String = ""): String {
    return when (number) {
        0 -> ifZero
        1, 2, 3, 4, 5, 6, 7, 8, 9 -> "0$number"
        else -> number.toString()
    }
}

fun Long.millisToDuration(): String {
    val seconds = (this / 1000).toInt() % 60
    val minutes = (this / (1000 * 60) % 60).toInt()
    val hours = (this / (1000 * 60 * 60) % 24).toInt()
    "${timeAddZeros(hours)}:${timeAddZeros(minutes, "0")}:${timeAddZeros(seconds, "00")}".apply {
        return if (startsWith(":")) replaceFirst(":", "") else this
    }
}
