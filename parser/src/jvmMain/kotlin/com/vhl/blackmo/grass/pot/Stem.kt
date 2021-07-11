package com.vhl.blackmo.grass.pot

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

/**
 * Implementation **class** of [Root] Conversion Engine from csv contents to **data class** definition
 * @param type data **class** definition
 * @param trim removes white spaces defined within csv column entry
 * @param caseSensitive case sensitive header matching
 * @param ignoreUnknownFields ignores unknown fields
 * @param receivedKeyMap custom user defined key mapping values
 * @author blackmo18
 */
@Suppress("UNCHECKED_CAST")
@ExperimentalStdlibApi
actual open class Stem<out T> actual constructor(
    type: KClass<*>,
    trim: Boolean,
    ignoreUnknownFields: Boolean,
    private val caseSensitive: Boolean,
    private val receivedKeyMap: Map<String, String>?,
    private val receivedKeyMapDataProperty: Map<String, KProperty<*>>?
): Root<T>(type, trim, ignoreUnknownFields, caseSensitive) {
    /**
     * @return converted sequence of data [T]
     */
    actual fun harvestData(seed: Sequence<Map<String, String>>): Sequence<T> {
        initOnMethod()
        val constructor = type.constructors.first()
        return sequence {
            seed.forEach { entry ->
                val params = createObject(entry)
                val obj = constructor.call(*params)
                yield(obj as T)
            }
        }
    }
    /**
     * @return converted list of data [T]
     */
    actual fun harvestData(seed: List<Map<String, String>>): List<T> {
        initOnMethod()
        val constructor = type.constructors.first()
        return seed.map { entry ->
            val params = createObject(entry)
            constructor.call(*params) as T
        }
    }

    actual suspend fun harvestData(seed: Flow<Map<String, String>>): Flow<T> {
        initOnMethod()
        val constructor = type.constructors.first()
        return seed.map { entry ->
            val params = createObject(entry)
            constructor.call(*params) as T
        }
    }

    private fun initOnMethod() {
        if (caseSensitive) {
            onCaseSensitive()
        } else {
            onNotCaseSensitive()
        }
    }

    private fun onCaseSensitive() {
        type.constructors.first().parameters.forEach { kParam ->
            paramNTypes[kParam.name] = getType(kParam.type) as ((String) -> Any)?
            paramNIndex[kParam.name] = kParam.index
            if (!receivedKeyMap.isNullOrEmpty()) {
                receivedKeyMap.let { pair ->
                    customKeyMap.putAll(pair)
                }
            } else {
                receivedKeyMapDataProperty?.forEach {
                    customKeyMap[it.key] = it.value.name
                }
            }
        }
    }

    private fun onNotCaseSensitive() {
        type.constructors.first().parameters.forEach { kParam ->
            paramNTypes[kParam.name?.toUpperCase()] = getType(kParam.type) as ((String) -> Any)?
            paramNIndex[kParam.name?.toUpperCase()] = kParam.index
            if (!receivedKeyMap.isNullOrEmpty()) {
                receivedKeyMap.let { pair ->
                    pair.forEach { (key, value) ->
                        customKeyMap[key.toUpperCase()] = value.toUpperCase()
                    }
                }
            } else {
                receivedKeyMapDataProperty?.forEach {
                    customKeyMap[it.key.toUpperCase()] = it.value.name.toUpperCase()
                }
            }
        }
    }
}
