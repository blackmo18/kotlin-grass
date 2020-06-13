package com.vhl.blackmo.grass.dsl

import com.vhl.blackmo.grass.pot.Plant
import com.vhl.blackmo.grass.context.GrassParserContext

/**
 * @author blackmo18
 */
@ExperimentalStdlibApi
inline fun <reified T> grass(init: GrassParserContext.()-> Unit = {}): Plant<T> {
    val context = GrassParserContext().apply(init)
    return Plant(context, T::class)
}