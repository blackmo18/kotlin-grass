package com.vhl.blackmo.grass.pot

import com.vhl.blackmo.grass.errors.MissMatchedNumberOfFieldsException
import com.vhl.blackmo.grass.errors.MissMatchedFieldNameException
import com.vhl.blackmo.grass.vein.PrimitiveType
import kotlin.reflect.*

/**
 * @author blackmo18
 */
@Suppress("UNCHECKED_CAST")
@ExperimentalStdlibApi
abstract class Root<out T>(
    private val type: KClass<*>,
    private val trim: Boolean,
    private val receivedKeyMap: Map<String, String>?
) {

    private val paramNTypes = mutableMapOf<String?, ((String) -> Any)? >()
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
            val key = mapRow.key.trim()
            val value = mapRow.value.trimOrNot(trim)
            val hasKey = paramNTypes.containsKey(key)
            when {
                hasKey && mapRow.value.isNotBlank() -> {
                    val index = paramNIndex[key]!!
                    actualParams[index] = paramNTypes[key]!!.invoke(value)
                }
                hasKey && mapRow.value.isBlank() -> {
                    val index = paramNIndex[key]!!
                    actualParams[index] = null
                }
                else -> {
                    if (customKeyMap.isNotEmpty()) {
                        if (customKeyMap.containsKey(key)) {
                            val mappedKey = customKeyMap[key]?.trim()
                            if(paramNTypes.containsKey(mappedKey)) {
                                customKeyMap.remove(mappedKey)
                                val index = paramNIndex[mappedKey]!!
                                actualParams[index] = paramNTypes[mappedKey]!!.invoke(mapRow.value)
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

    private fun String.trimOrNot(boolean: Boolean): String = when {
        boolean -> this.trim()
        else -> this
    }

    private fun validateNumberOfFields(csvLength: Int, dataClassFieldLength: Int) {
        if (csvLength != dataClassFieldLength) {
            throw MissMatchedNumberOfFieldsException(csvLength, dataClassFieldLength)
        }
    }

    open fun getType(type: KType)  = PrimitiveType.mapTypes[type]
}