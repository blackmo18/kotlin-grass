package com.vhl.blackmo.grass.pot

import com.vhl.blackmo.grass.context.GrassParserContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.typeOf

/**
 * @author blackmo18
 */
@ExperimentalStdlibApi
actual class Plant<T> actual constructor(val ctx: GrassParserContext, type: KClass<*>) : Root<T>(type, ctx.customKeyMap) {
    private val dateFormatter = DateTimeFormatter.ofPattern(ctx.dateFormat)
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("${ctx.dateFormat}${ctx.dateTimeSeparator}${ctx.timeFormat}")
    private val timeFormatter = DateTimeFormatter.ofPattern(ctx.timeFormat)

    override fun getType(type: KType, value: String): Any = when(type) {
        typeOf<LocalTime>() -> formatTime(value)
        typeOf<LocalDateTime>() -> formatDateTime(value)
        typeOf<LocalDate>() -> formatDate(value)
        else -> super.getType(type, value)
    }

    override fun typeNullable(type: KType, value: String): Any = when(type) {
        typeOf<LocalTime?>() -> formatTime(value)
        typeOf<LocalDateTime?>() -> formatDateTime(value)
        typeOf<LocalDate?>() -> formatDate(value)
        else -> super.typeNullable(type, value)
    }

    actual fun harvest(seed: List<Map<String, String>>): List<T> {
        return harvestData(seed)
    }

    actual fun harvest(seed: Sequence<Map<String, String>>): Sequence<T> {
        return harvestData(seed)
    }

    private fun formatDate(value: String): LocalDate {
        return LocalDate.parse(value, dateFormatter)
    }

    private fun formatDateTime(value: String): LocalDateTime {
        return LocalDateTime.parse(value, dateTimeFormatter)
    }

    private fun formatTime(value: String): LocalTime {
        return LocalTime.parse(value, timeFormatter)
    }
}