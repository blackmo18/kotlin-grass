package io.github.blackmo18.grass.context

import kotlin.reflect.KProperty

/**
 * Interface Customization Configuration Context
 *  * date formatting
 *  * time formatting
 *  * date-time separator
 *  * trim white space
 *  * ignore unknown fields
 *  * case sensitive
 *  * custom key mapping
 *  * custom key mapping class property
 */
interface GrassParserCtx {
    val dateFormat: String
    val timeFormat: String
    val dateTimeSeparator: String
    val trimWhiteSpace: Boolean
    var ignoreUnknownFields: Boolean
    var caseSensitve: Boolean
    val customKeyMap: Map<String, String>?
    val customKeyMapDataProperty: Map<String, KProperty<*>>?
}