package io.blackmo18.kotlin.grass.context

import io.blackmo18.kotlin.grass.core.datetime.DateTimeCtx
import kotlin.reflect.KProperty

/**
 * Interface Customization Configuration Context
 *  * date formatting
 *  * time formatting
 *  * date-time separator
 *  * trim white space
 *  * custom key mapping
 */
interface GrassParserCtx: DateTimeCtx {
    override val dateFormat: String
    override val timeFormat: String
    override val dateTimeSeparator: String
    val trimWhiteSpace: Boolean
    var ignoreUnknownFields: Boolean
    var caseSensitive: Boolean
    val customKeyMap: Map<String, String>?
    val customDataTypes: List<io.blackmo18.kotlin.grass.core.DataTypes>?
    val customKeyMapDataProperty: Map<String, KProperty<*>>?
}