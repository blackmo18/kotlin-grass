package com.github.blackmo.grass.vein

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.reflect.typeOf

/**
 * Class that defines formatting functions for Java8 Date Time Apis
 * @author blackmo18
 * @param dateFormat   Date Format eg. "MM-dd-yyyy"
 * @param timeFormat  Time Format eg. "HH:mm:ss"
 */
@ExperimentalStdlibApi
actual class DateTimeTypes actual constructor(dateFormat: String, timeFormat: String, dateTimeSeparator: String) {
    private val dateFormatter = DateTimeFormatter.ofPattern(dateFormat)
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("${dateFormat}${dateTimeSeparator}${timeFormat}")
    private val timeFormatter = DateTimeFormatter.ofPattern(timeFormat)

    actual  val formatDate = fun (value: String): Any? = when {
        value.isNotBlank() -> LocalDate.parse(value, dateFormatter)
        else -> null
    }

    actual val formatDateTime = fun (value: String): Any? = when {
        value.isNotBlank() -> LocalDateTime.parse(value, dateTimeFormatter)
        else -> null
    }

    actual  val formatTime = fun(value: String): Any? = when {
        value.isNotBlank() -> LocalTime.parse(value, timeFormatter)
        else -> null
    }

    actual val mapTypes = mapOf(
            typeOf<LocalDate>()  to formatDate,
            typeOf<LocalDateTime>() to formatDateTime,
            typeOf<LocalTime>() to formatTime,
            //-- nullable types
            typeOf<LocalDate?>()  to formatDate,
            typeOf<LocalDateTime?>() to formatDateTime,
            typeOf<LocalTime?>() to formatTime
    )
}