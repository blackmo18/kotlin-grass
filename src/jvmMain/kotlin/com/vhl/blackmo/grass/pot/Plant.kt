package com.vhl.blackmo.grass.pot

import com.vhl.blackmo.grass.context.GrassParserContext
import com.vhl.blackmo.grass.vien.DateTimeTypes
import kotlin.reflect.KClass
import kotlin.reflect.KType

/**
 * @author blackmo18
 */
@ExperimentalStdlibApi
actual class Plant<T> actual constructor(val ctx: GrassParserContext, type: KClass<*>) : Root<T>(type, ctx.customKeyMap) {

    actual val dateTimeTypes = DateTimeTypes(ctx.dateFormat, ctx.timeFormat, ctx.dateTimeSeparator)

    override fun getType(type: KType) = when {
        dateTimeTypes.mapTypes.containsKey(type) -> dateTimeTypes.mapTypes[type]
        else -> super.getType(type)
    }

actual fun harvest(seed: List<Map<String, String>>): List<T> {
        return harvestData(seed)
    }

    actual fun harvest(seed: Sequence<Map<String, String>>): Sequence<T> {
        return harvestData(seed)
    }
}