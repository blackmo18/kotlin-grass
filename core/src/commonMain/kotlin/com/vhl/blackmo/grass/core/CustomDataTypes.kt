package com.vhl.blackmo.grass.core

import kotlin.reflect.KType

/**
 * Object that contains mapping of custom data types
 * @author blackmo18
 */
object CustomDataTypes {
    private val currentMaps =  mutableMapOf<KType, (String) -> Any?>()

    /**
     * Map of Custom Data Types
     */
    val mapType: Map<KType, (String) -> Any?> = currentMaps

    /**
     * Adds custom Data Types mapping
     */
    fun addDataTypes(dataTypes: DataTypes) {
        currentMaps.putAll(dataTypes.mapTypes)
    }

    /**
     * Clear content of custom data Type
     */
    fun clearMapTypes() {
        currentMaps.clear()
    }
}