package com.vhl.blackmo.grass.pot

import kotlin.reflect.KClass

/**
 * Implementation **class** of [Root] Conversion Engine from csv contents to **data class** definition
 * @param type data **class** definition
 * @param trim removes white spaces defined within csv column entry
 * @param receivedKeyMap custom user defined key mapping values
 * @author blackmo18
 */
@Suppress("UNCHECKED_CAST")
@ExperimentalStdlibApi
actual open class Stem<out T> actual constructor(
        type: KClass<*>,
        trim: Boolean,
        receivedKeyMap: Map<String, String>?
): Root<T>(type, trim, receivedKeyMap) {
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
        val listObject = mutableListOf<T>()
        seed.forEach { entry ->
            val params = createObject(entry)
            val obj = constructor.call(*params)
            listObject.add(obj as T)
        }
        return listObject
    }

    private fun initOnMethod() {
        type.constructors.first().parameters.forEach { kParam ->
            paramNTypes[kParam.name] = getType(kParam.type) as ((String) -> Any)?
            paramNIndex[kParam.name] = kParam.index
            receivedKeyMap?.let { customKeyMap.putAll(it) }
        }
    }
}