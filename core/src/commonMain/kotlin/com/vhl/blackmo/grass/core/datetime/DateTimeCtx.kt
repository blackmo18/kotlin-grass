package com.vhl.blackmo.grass.core.datetime

/**
 * Interface Customization Configuration Context Date and Time
 *  * date formatting
 *  * time formatting
 *  * date-time separator
 */
interface DateTimeCtx {
    val dateFormat: String
    val timeFormat: String
    val dateTimeSeparator: String
}