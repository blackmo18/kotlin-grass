package io.blackmo18.kotlin.grass.core

import kotlin.reflect.KType

/**
 * Data Type Interface
 */
interface DataTypes {
    val mapTypes: Map<KType, (String) -> Any?>
}