package com.vhl.blackmo.grass.context

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
    val customKeyMap: Map<String, String>?
}