package com.vhl.blackmo.grass.pot

import kotlin.reflect.KClass

/**
 * Implementation **class** of [Root] Conversion Engine from csv contents to **data class** definition
 * @param type data **class** definition
 * @param trim removes white spaces defined within csv column entry
 * @param ignoreUnknownFields ignores unknown fields
 * @param receivedKeyMap custom user defined key mapping values
 * @author blackmo18
 */
@ExperimentalStdlibApi
expect open class Stem<out T> actual  constructor(
        type: KClass<*>,
        trim: Boolean,
        ignoreUnknownFields: Boolean,
        receivedKeyMap: Map<String, String>?
): Root<T> {
    fun harvestData(seed: Sequence<Map<String, String>>): Sequence<T>
    fun harvestData(seed: List<Map<String, String>>): List<T>
}
