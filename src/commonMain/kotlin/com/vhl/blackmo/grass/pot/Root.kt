package com.vhl.blackmo.grass.pot

import com.vhl.blackmo.grass.errors.MissMatchedNumberOfFieldsException
import com.vhl.blackmo.grass.errors.MissMatchedFieldNameException
import kotlin.reflect.*

/**
 * @author blackmo18
 */
@Suppress("UNCHECKED_CAST")
@ExperimentalStdlibApi
abstract class Root<out T>(
    private val type: KClass<*>,
    private val customKeyMap: Map<String, String>?
) {

    private val processedKey = mutableSetOf<String>()

    fun harvestData(rows: Sequence<Map<String, String>>): Sequence<T> {
        val constructor = type.constructors.first()
        return sequence {
            rows.forEach { entry ->
                val params = createObject(constructor, entry)
                val obj = constructor.call(*params)
                yield(obj as T)
            }
        }
    }

    fun harvestData(rows: List<Map<String, String>>): List<T> {
        val constructor = type.constructors.first()
        val listObject = mutableListOf<T>()
        rows.forEach { entry ->
            val params = createObject(constructor, entry)
            val obj = constructor.call(*params)
            listObject.add(obj as T)
        }
        return listObject
    }

    private fun createObject(constructor: KFunction<*>, row: Map<String, String>): Array<Any?> {
        val paramSize =  constructor.parameters.size
        val actualParams = Array<Any?>(paramSize) {}

        validateNumberOfFields(row.keys.size, paramSize)
        row.forEach { keyValue ->
            val kParams = constructor.parameters
            assignValueToField(actualParams, kParams, keyValue.key to keyValue.value)
        }
        return actualParams
    }

    private fun assignValueToField(actualParams: Array<Any?>, kParams: List<KParameter>, pair: Pair<String, String>) {
        val  field = kParams.findLast { it.name == pair.first }
        when {
            field!=null && pair.second.isNotBlank() -> actualParams[field.index] = getType(field.type, pair.second)
            field!=null && pair.second.isBlank() -> actualParams[field.index] = null
            customKeyMap != null -> {
                if (customKeyMap.isNotEmpty()) {
                    if (customKeyMap.containsKey(pair.first) && !processedKey.contains(pair.first)) {
                        processedKey.add(pair.first)
                        assignValueToField(actualParams, kParams,  (customKeyMap[pair.first] ?: error("Null key value pair")) to pair.second)
                    } else {
                        throw MissMatchedFieldNameException(pair.first)
                    }
                } else {
                    throw MissMatchedFieldNameException(pair.first)
                }
            }
            else ->  throw MissMatchedFieldNameException(pair.first)
        }
    }

    private fun validateNumberOfFields(csvLength: Int, dataClassFieldLength: Int) {
        if (csvLength != dataClassFieldLength) {
            throw MissMatchedNumberOfFieldsException(csvLength, dataClassFieldLength)
        }
    }

    open fun getType(type: KType, value: String): Any = when(type) {
        typeOf<Short>() -> value.toShort()
        typeOf<Int>() -> value.toInt()
        typeOf<Long>() -> value.toLong()
        typeOf<Float>() -> value.toFloat()
        typeOf<Double>() -> value.toDouble()
        typeOf<Boolean>() -> value.toBoolean()
        typeOf<String>() -> value
        else -> typeNullable(type,value)
    }

    open fun typeNullable(type: KType, value: String): Any = when (type) {
        typeOf<Short?>() -> value.toShort()
        typeOf<Int?>() -> value.toInt()
        typeOf<Long?>() -> value.toLong()
        typeOf<Float?>() -> value.toFloat()
        typeOf<Double?>() -> value.toDouble()
        typeOf<Boolean?>() -> value.toBoolean()
        else -> value
    }
}