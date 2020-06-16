package com.vhl.blackmo.grass.pot

import com.vhl.blackmo.grass.errors.MissMatchedNumberOfFieldsException
import com.vhl.blackmo.grass.errors.MissMatchedFieldNameException
import com.vhl.blackmo.grass.stem.PrimitiveType
import kotlin.reflect.*

/**
 * @author blackmo18
 */
@Suppress("UNCHECKED_CAST")
@ExperimentalStdlibApi
abstract class Root<out T>(
    private val type: KClass<*>,
    private val receivedKeyMap: Map<String, String>?
) {

    private val paramNTypes = mutableMapOf<String?, (value: String) -> Any >()
    private val paramNIndex = mutableMapOf<String?, Int >()
    private val paramNames = mutableListOf<String?>()
    private val processedKey = mutableSetOf<String>()
    private val customKeyMap = mutableMapOf<String,String>()

    private fun initOnMethod() {
        type.constructors.first().parameters.forEach {
            paramNTypes[it.name] = getType(it.type)
            paramNIndex[it.name] = it.index
            paramNames.add(it.name)
            receivedKeyMap?.let {
                customKeyMap.putAll(it)
            }
        }
    }

    fun harvestData(rows: Sequence<Map<String, String>>): Sequence<T> {
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

    fun harvestData(rows: List<Map<String, String>>): List<T> {
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

    private fun createObject( row: Map<String, String>): Array<Any?> {

        val actualParams = Array<Any?>(paramNTypes.size){}
        validateNumberOfFields(row.keys.size, paramNTypes.size)

        loop@ for (mapRow in row) {
            when {
                paramNTypes.containsKey(mapRow.key) && mapRow.value.isNotBlank() -> {
                    val index = paramNIndex[mapRow.key]!!
                    actualParams[index] = paramNTypes[mapRow.key]!!.invoke(mapRow.value)
                }
                paramNTypes.containsKey(mapRow.key) && mapRow.value.isBlank() -> {
                    val index = paramNIndex[mapRow.key]!!
                    actualParams[index] = null
                }
                else -> {
                    if (customKeyMap.isNotEmpty()) {
                        if (customKeyMap.containsValue(mapRow.key)) {
                            val mappedKey = customKeyMap[mapRow.key] ?: throw error("")
                            if(paramNTypes.containsKey(mappedKey)) {
                                customKeyMap.remove(mappedKey)
                                val index = paramNIndex[mapRow.key]!!
                                actualParams[index] = paramNTypes[mapRow.key]!!.invoke(mapRow.value)
                                continue@loop
                            }
                        }
                    }
                    throw MissMatchedFieldNameException(mapRow.key)
                }
            }
        }
        return actualParams
    }

    private fun validateNumberOfFields(csvLength: Int, dataClassFieldLength: Int) {
        if (csvLength != dataClassFieldLength) {
            throw MissMatchedNumberOfFieldsException(csvLength, dataClassFieldLength)
        }
    }

    open fun getType(type: KType)  = when(type) {
        typeOf<Short>() -> PrimitiveType.toShort
        typeOf<Int>() -> PrimitiveType.toInt
        typeOf<Long>() -> PrimitiveType.toLong
        typeOf<Float>() -> PrimitiveType.toFloat
        typeOf<Double>() -> PrimitiveType.toDouble
        typeOf<String>() -> PrimitiveType.string
        typeOf<Boolean>() -> PrimitiveType.toBoolean
        else -> typeNullable(type)
    }


    open fun typeNullable(type: KType)  = when(type) {
        typeOf<Short?>() -> PrimitiveType.toShort
        typeOf<Int?>() -> PrimitiveType.toInt
        typeOf<Long?>() -> PrimitiveType.toLong
        typeOf<Float?>() -> PrimitiveType.toFloat
        typeOf<Double?>() -> PrimitiveType.toDouble
        typeOf<Boolean?>() -> PrimitiveType.toBoolean
        else -> PrimitiveType.string
    }
}