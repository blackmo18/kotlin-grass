package com.vhl.blackmo.grass.pot

import kotlin.reflect.KClass

/**
 * @author blackmo18
 */
@ExperimentalStdlibApi
expect open class Stem<out T> actual  constructor(
        type: KClass<*>,
        trim: Boolean,
        receivedKeyMap: Map<String, String>?
): Root<T> {
    fun harvestData(seed: Sequence<Map<String, String>>): Sequence<T>
    fun harvestData(seed: List<Map<String, String>>): List<T>
}
