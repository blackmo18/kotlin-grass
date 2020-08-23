package com.vhl.blackmo.grass.dsl

import com.vhl.blackmo.grass.pot.Plant
import com.vhl.blackmo.grass.context.GrassParserContext

/**
 * @param init default Customization Configuration [GrassParserContext]
 * @return Conversion engine [Plant] implementation of csv contents to **data class**
 * @author blackmo18
 */
@ExperimentalStdlibApi
inline fun <reified T> grass(init: GrassParserContext.()-> Unit = {}): Plant<T> {
    val context = GrassParserContext().apply(init)
    return Plant(context, T::class)
}