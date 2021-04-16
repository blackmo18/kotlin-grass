package io.github.blackmo18.grass.pot

import io.github.blackmo18.grass.context.GrassParserContext
import io.github.blackmo18.grass.vein.DateTimeTypes
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
    val dateTimeTypes : DateTimeTypes
   /**
    * @return [List&lt;T&gt;] where T is the target data class
    */
   fun harvest(seed: List<Map<String, String>>): List<T>
   /**
    * @return [Sequence&lt;T&gt;] where T is the target data class
    */
   fun harvest(seed: Sequence<Map<String, String>>): Sequence<T>

   suspend fun harvest(seed: Flow<Map<String, String>>): Flow<T>
}