package com.vhl.blackmo.grass.context

/**
 * @author blackmo18
 */
class GrassParserContext : GrassParserCtx {
    override var dateFormat: String = "yyyy-MM-dd"
    override var timeFormat: String = "HH:mm"
    override var dateTimeSeparator: String = " "
    override var customKeyMap: Map<String, String>? = null
}