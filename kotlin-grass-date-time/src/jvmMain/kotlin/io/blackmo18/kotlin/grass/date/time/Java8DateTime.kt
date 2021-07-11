package io.blackmo18.kotlin.grass.date.time

import io.blackmo18.kotlin.grass.core.datetime.DateTimeFormats
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.reflect.typeOf

@ExperimentalStdlibApi
object Java8DateTime: io.blackmo18.kotlin.grass.core.DateTimeTypes() {
    override val formatDate = fun (value: String): Any? {
        val dateFormatter = DateTimeFormatter.ofPattern(DateTimeFormats.dateFormat)
        return LocalDate.parse(value, dateFormatter)
    }

    override val formatDateTime = fun(value: String): Any? {
        val dateTimeFormatter = DateTimeFormatter.ofPattern("${DateTimeFormats.dateFormat}${DateTimeFormats.dateTimeSeparator}${DateTimeFormats.timeFormat}")
        return LocalDateTime.parse(value, dateTimeFormatter)
    }

    override val formatTime = fun(value: String): Any? {
       val timeFormatter = DateTimeFormatter.ofPattern(DateTimeFormats.timeFormat)
        return LocalTime.parse(value, timeFormatter)
    }

    override val mapTypes = mapOf(
            typeOf<LocalDate>()  to formatDate,
            typeOf<LocalDateTime>() to formatDateTime,
            typeOf<LocalTime>() to formatTime,
            //-- nullable types
            typeOf<LocalDate?>()  to formatDate,
            typeOf<LocalDateTime?>() to formatDateTime,
            typeOf<LocalTime?>() to formatTime
    )
}
