package com.vhl.blackmo.grass.pot

import com.vhl.blackmo.grass.context.GrassParserContext
import com.vhl.blackmo.grass.vein.DateTimeTypes
import kotlin.reflect.KClass
import kotlin.reflect.KType

/**
 * @author blackmo18
 */
@Suppress("UNCHECKED_CAST")
@ExperimentalStdlibApi
actual class Plant<T> actual constructor(val ctx: GrassParserContext, type: KClass<*>)
    : Root<T>(type, ctx.trimWhiteSpace, ctx.customKeyMap) {

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

    override fun initOnMethod() {
        type.constructors.first().parameters.forEach {
            paramNTypes[it.name] = getType(it.type)
            paramNIndex[it.name] = it.index
            receivedKeyMap?.let { customKeyMap.putAll(it) }
        }
    }

    override fun harvestData(rows: Sequence<Map<String, String>>): Sequence<T> {
        initOnMethod()
        val constructor = type.constructors.first()
        return sequence {
            rows.forEach { entry ->
                val params = createObject(entry)
                val obj = constructor.call(*params)
                yield(obj as T)
            }
        }
    }

    override fun harvestData(rows: List<Map<String, String>>): List<T> {
        initOnMethod()
        val constructor = type.constructors.first()
        val listObject = mutableListOf<T>()
        rows.forEach { entry ->
            val params = createObject(entry)
            val obj = constructor.call(*params)
            listObject.add(obj as T)
        }
        return listObject
    }
}