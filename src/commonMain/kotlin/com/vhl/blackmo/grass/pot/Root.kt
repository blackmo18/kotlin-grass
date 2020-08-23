package com.vhl.blackmo.grass.pot

import com.vhl.blackmo.grass.errors.MissMatchedNumberOfFieldsException
import com.vhl.blackmo.grass.errors.MissMatchedFieldNameException
import com.vhl.blackmo.grass.vein.PrimitiveType
import kotlin.reflect.*

/**
 * Abstract **class** Conversion Engine of csv contents to **data** class definition
 * @param type data **class** definition
 * @param trim removes white spaces defined within csv column entry
 * @param receivedKeyMap custom user defined key mapping values
 * @author blackmo18
 */
@ExperimentalStdlibApi
open class Root<out T>(
        val type: KClass<*>,
        private val trim: Boolean,
        val receivedKeyMap: Map<String, String>?
) {
    /**
     * Key-value pair containing the expression on converting from from data class property name
     * to actual type(class property definition)
     */
    protected val paramNTypes = mutableMapOf<String?, ((String) -> Any)? >()

    /**
     * Key value pair index(order) of the  data class property vs property name
     */
    protected val paramNIndex = mutableMapOf<String?, Int >()

    /**
     * User custom key mapping input
     */
    protected val customKeyMap = mutableMapOf<String,String>()

    /**
     * Converts csv row entry to specified **data class** fields
     * @param row Map of  csv column-header to row-column
     * @return array of converted columns into defined data class field type
     */
    protected fun createObject( row: Map<String, String>): Array<Any?> {

        val actualParams = Array<Any?>(paramNTypes.size){}
        validateNumberOfFields(row.keys.size, paramNTypes.size)

        loop@ for (mapRow in row) {
            val key = mapRow.key.trim()
            val value = mapRow.value.trimOrNot(trim)
            val hasKey = paramNTypes.containsKey(key)
            when {
                hasKey && mapRow.value.isNotBlank() -> {
                    val index = paramNIndex[key]!!
                    actualParams[index] = paramNTypes[key]!!.invoke(value)
                }
                hasKey && value.isBlank() -> {
                    val index = paramNIndex[key]!!
                    actualParams[index] = null
                }
                else -> {
                    if (customKeyMap.isNotEmpty()) {
                        if (customKeyMap.containsKey(key)) {
                            val mappedKey = customKeyMap[key]?.trim()
                            if(paramNTypes.containsKey(mappedKey)) {
                                customKeyMap.remove(mappedKey)
                                val index = paramNIndex[mappedKey]!!
                                actualParams[index] = paramNTypes[mappedKey]!!.invoke(mapRow.value)
                                continue@loop
                            }
                        }
                    }
                    throw MissMatchedFieldNameException(mapRow.key)
                }
            }
        }
        return actualParams
    }

    private fun String.trimOrNot(boolean: Boolean): String = when {
        boolean -> this.trim()
        else -> this
    }

    private fun validateNumberOfFields(csvLength: Int, dataClassFieldLength: Int) {
        if (csvLength != dataClassFieldLength) {
            throw MissMatchedNumberOfFieldsException(csvLength, dataClassFieldLength)
        }
    }

    /**
     * Returns type conversion function to specific data type
     * @param type field definition
     * @return callable conversion function
     */
    open fun getType(type: KType)  = PrimitiveType.mapTypes[type]
}