package com.github.blackmo.grass.dsl

import com.github.blackmo.grass.context.GrassParserContext
import com.github.blackmo.grass.pot.Plant

/**
 * @param init default Customization Configuration [GrassParserContext]
 * @return Conversion engine [Plant] implementation of csv contents to **data class**
 * @author blackmo18
 */
@ExperimentalStdlibApi
inline fun <reified T> grass(init: com.github.blackmo.grass.context.GrassParserContext.()-> Unit = {}): Plant<T> {
    val context = com.github.blackmo.grass.context.GrassParserContext().apply(init)
    return Plant(context, T::class)
}