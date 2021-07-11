package com.vhl.blackmo.grass.context

import com.vhl.blackmo.grass.core.DataTypes

import kotlin.reflect.KProperty

/**
 * Customization Configuration Context
 *  * date formatting
 *  * time formatting
 *  * date-time separator
 *  * trim white space
 *  * custom key mapping
 * @author blackmo18
 */
class GrassParserContext : GrassParserCtx {
    /**
     *  date formatting
     *  default value: **yyyy-MM-dd**
     */
    override var dateFormat: String = "yyyy-MM-dd"

    /**
     * time formatting
     * default value: **HH:mm**
     */
    override var timeFormat: String = "HH:mm"

    /**
     * date time separator
     * default value: **(space)**
     */
    override var dateTimeSeparator: String = " "

    /**
     * trim white spaces in csv column content
     */
    override var trimWhiteSpace: Boolean = true

    /**
     * ignore unknown (unmapped) fields in input
     */
    override var ignoreUnknownFields: Boolean = false

    /**
     * header case sensitive
     */
    override var caseSensitive: Boolean = true

    /**
     * custom key mapping from csv header to **data class** field name
     */
    override var customKeyMap: Map<String, String>? = null

    /**
     * custom key mapping from csv header to **data class** property name
     */
    override var customKeyMapDataProperty: Map<String, KProperty<*>>? = null

    /**
     * custom data types
     */
    override var customDataTypes: List<DataTypes>? = null
}
