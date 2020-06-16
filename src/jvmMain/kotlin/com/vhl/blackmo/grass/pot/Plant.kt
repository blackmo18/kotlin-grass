package com.vhl.blackmo.grass.pot

import com.vhl.blackmo.grass.context.GrassParserContext
import com.vhl.blackmo.grass.stem.DateTimeTypes
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.typeOf

/**
 * @author blackmo18
 */
@ExperimentalStdlibApi
actual class Plant<T> actual constructor(val ctx: GrassParserContext, type: KClass<*>) : Root<T>(type, ctx.customKeyMap) {

    actual val dateTimeTypes = DateTimeTypes(ctx.dateFormat, ctx.timeFormat, ctx.dateTimeSeparator)

    override fun getType(type: KType): (String)-> Any = when(type) {
        typeOf<LocalDate>() ->   dateTimeTypes.formatDate
        typeOf<LocalDateTime>() ->  dateTimeTypes.formatDateTime
        typeOf<LocalTime>() ->  dateTimeTypes.formatTime
        else -> super.getType(type)
    }

    override fun typeNullable(type: KType) = when(type) {
        typeOf<LocalDate?>() ->   dateTimeTypes.formatDate
        typeOf<LocalDateTime?>() ->  dateTimeTypes.formatDateTime
        typeOf<LocalTime?>() ->  dateTimeTypes.formatTime
        else -> super.typeNullable(type)
    }

    actual fun harvest(seed: List<Map<String, String>>): List<T> {
        return harvestData(seed)
    }

    actual fun harvest(seed: Sequence<Map<String, String>>): Sequence<T> {
        return harvestData(seed)
    }
}