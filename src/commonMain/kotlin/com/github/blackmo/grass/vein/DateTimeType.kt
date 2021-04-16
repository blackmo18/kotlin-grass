package com.github.blackmo.grass.vein

import kotlin.reflect.KType

/**
 * Class that defines formatting functions for Java8 Date Time Apis
 * @author blackmo18
 * @param dateFormat   Date Format eg. "MM-dd-yyyy"
 * @param timeFormat  Time Format eg. "HH:mm:ss"
 */
expect class DateTimeTypes(dateFormat: String, timeFormat: String, dateTimeSeparator: String) {
    val formatDate: (String) -> Any?
    val formatDateTime: (String) -> Any?
    val formatTime: (String) -> Any?

    val mapTypes: Map<KType, (String) -> Any?>
}
