package com.vhl.blackmo.grass.pot

import com.vhl.blackmo.grass.context.GrassParserContext
import com.vhl.blackmo.grass.vein.DateTimeTypes
import kotlin.reflect.KClass

/**
 * @author blackmo18
 */
@ExperimentalStdlibApi
 expect  class Plant<T> actual constructor(ctx: GrassParserContext, type: KClass<*>):
    Root<T> {
    val dateTimeTypes : DateTimeTypes
    fun harvest(seed: List<Map<String, String>>): List<T>
    fun harvest(seed: Sequence<Map<String, String>>): Sequence<T>
}