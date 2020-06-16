package com.vhl.blackmo.grass.stem

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * Class that defines formatting functions for Java8 Date Time Apis
 * @author blackmo18
 * @param dateFormat   Date Format eg. "MM-dd-yyyy"
 * @param timeFormat  Time Format eg. "HH:mm:ss"
 */
actual class DateTimeTypes actual constructor(dateFormat: String, timeFormat: String, dateTimeSeparator: String) {
    private val dateFormatter = DateTimeFormatter.ofPattern(dateFormat)
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("${dateFormat}${dateTimeSeparator}${timeFormat}")
    private val timeFormatter = DateTimeFormatter.ofPattern(timeFormat)

    actual  val formatDate = fun (value: String): Any {
        return LocalDate.parse(value, dateFormatter)
    }

    actual val formatDateTime = fun (value: String): Any {
        return LocalDateTime.parse(value, dateTimeFormatter)
    }

    actual  val formatTime = fun(value: String): Any {
        return LocalTime.parse(value, timeFormatter)
    }
}