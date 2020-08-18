package com.vhl.blackmo.grass.pot

import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
@ExperimentalStdlibApi
actual open class Stem<out T> actual constructor(
        type: KClass<*>,
        trim: Boolean,
        receivedKeyMap: Map<String, String>?
): Root<T>(type, trim, receivedKeyMap) {
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
            paramNTypes[kParam.name] = getType(kParam.type)
            paramNIndex[kParam.name] = kParam.index
            receivedKeyMap?.let { customKeyMap.putAll(it) }
        }
    }

}