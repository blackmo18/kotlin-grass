package com.github.blackmo.grass.context

import kotlin.reflect.KProperty

/**
 * Interface Customization Configuration Context
 *  * date formatting
 *  * time formatting
 *  * date-time separator
 *  * trim white space
 *  * custom key mapping
 */
interface GrassParserCtx {
    val dateFormat: String
    val timeFormat: String
    val dateTimeSeparator: String
    val trimWhiteSpace: Boolean
    var ignoreUnknownFields: Boolean
    val customKeyMap: Map<String, String>?
    val customKeyMapDataProperty: Map<String, KProperty<*>>?
}