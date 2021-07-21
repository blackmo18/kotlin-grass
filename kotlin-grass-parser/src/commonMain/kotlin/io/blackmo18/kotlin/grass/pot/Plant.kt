package io.blackmo18.kotlin.grass.pot

import io.blackmo18.kotlin.grass.context.GrassParserContext
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KClass

/**
 * Top level implementation of conversion engine of csv contents to **data class**
 * @param ctx context configuration [GrassParserContext]
 * @param type data class definition
 * @author blackmo18
 */
@ExperimentalStdlibApi
 expect  class Plant<T> actual constructor(ctx: GrassParserContext, type: KClass<*>):
    Stem<T> {
    val customType : io.blackmo18.kotlin.grass.core.CustomDataTypes

    /**
     * @return [List&lt;T&gt;] where T is the target data class
     */
    fun harvest(seed: List<Map<String, String>>): List<T>
    /**
     * @return [Sequence&lt;T&gt;] where T is the target data class
     */
    fun harvest(seed: Sequence<Map<String, String>>): Sequence<T>

   /**
    * @return [Flow&lt;T&gt;] where T is the target data class
    */
   suspend fun harvest(seed: Flow<Map<String, String>>): Flow<T>
}