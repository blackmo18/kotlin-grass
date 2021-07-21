package io.blackmo18.kotlin.grass.date.time.data

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class DateAndTime(
    val date: LocalDate,
    val datetime: LocalDateTime,
    val time: LocalTime
)
