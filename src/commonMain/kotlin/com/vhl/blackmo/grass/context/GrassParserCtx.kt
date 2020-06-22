package com.vhl.blackmo.grass.context

/**
 * @author blackmo18
 */
interface GrassParserCtx {
    val dateFormat: String
    val timeFormat: String
    val dateTimeSeparator: String
    val trimWhiteSpace: Boolean
    val customKeyMap: Map<String, String>?
}