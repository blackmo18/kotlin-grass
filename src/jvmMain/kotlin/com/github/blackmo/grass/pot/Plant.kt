package com.github.blackmo.grass.pot

import com.github.blackmo.grass.context.GrassParserContext
import com.github.blackmo.grass.vein.DateTimeTypes
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KClass
import kotlin.reflect.KType

/**
 * Top level implementation of conversion engine of csv contents to **data class**
 * @param ctx context configuration [GrassParserContext]
 * @param type data class definition
 * @author blackmo18
 */
@ExperimentalStdlibApi
actual class Plant<T> actual constructor(val ctx: com.github.blackmo.grass.context.GrassParserContext, type: KClass<*>)
    : Stem<T>(type, ctx.trimWhiteSpace, ctx.ignoreUnknownFields, ctx.customKeyMap, ctx.customKeyMapDataProperty) {

    actual val dateTimeTypes = DateTimeTypes(ctx.dateFormat, ctx.timeFormat, ctx.dateTimeSeparator)

    override fun getType(type: KType) = when {
        dateTimeTypes.mapTypes.containsKey(type) -> dateTimeTypes.mapTypes[type]
        else -> super.getType(type)
    }

    /**
     * @return [List&lt;T&gt;] where T is the target data class
     */
    actual fun harvest(seed: List<Map<String, String>>): List<T> {
        return harvestData(seed)
    }

    /**
     * @return [Sequence&lt;T&gt;] where T is the target data class
     */
    actual fun harvest(seed: Sequence<Map<String, String>>): Sequence<T> {
        return harvestData(seed)
    }

    actual suspend fun harvest(seed: Flow<Map<String, String>>): Flow<T> {
        return harvestData(seed)
    }
}