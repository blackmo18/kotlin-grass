package io.github.blackmo18.grass.dsl

import io.github.blackmo18.grass.context.GrassParserContext
import io.github.blackmo18.grass.pot.Plant

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