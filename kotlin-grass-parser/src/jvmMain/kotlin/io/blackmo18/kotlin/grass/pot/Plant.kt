package io.blackmo18.kotlin.grass.pot

import io.blackmo18.kotlin.grass.context.GrassParserContext
import io.blackmo18.kotlin.grass.core.datetime.DateTimeFormats
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
actual class Plant<T> actual constructor(val ctx: GrassParserContext, type: KClass<*>)
    : Stem<T>(type, ctx.trimWhiteSpace, ctx.ignoreUnknownFields, ctx.caseSensitive, ctx.customKeyMap, ctx.customKeyMapDataProperty) {

    init {
        DateTimeFormats.dateFormat = ctx.dateFormat
        DateTimeFormats.dateTimeSeparator = ctx.dateTimeSeparator
        DateTimeFormats.timeFormat = ctx.timeFormat

        ctx.customDataTypes?.run {
            for (dataTypes in this) {
                io.blackmo18.kotlin.grass.core.CustomDataTypes.addDataTypes(dataTypes)
            }
        }
    }

    actual val customType = io.blackmo18.kotlin.grass.core.CustomDataTypes

    override fun getType(type: KType) = when {
        customType.mapType.containsKey(type) -> customType.mapType[type]
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
    /**
     * @return [Flow&lt;T&gt;] where T is the target data class
     */
    actual suspend fun harvest(seed: Flow<Map<String, String>>): Flow<T> {
        return harvestData(seed)
    }
}