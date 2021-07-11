package com.vhl.blackmo.grass.core

import kotlin.reflect.KType

/**
 * Class that defines formatting functions for Date Time Apis
 * @author blackmo18
 * @param dateFormat   Date Format eg. "MM-dd-yyyy"
 * @param timeFormat  Time Format eg. "HH:mm:ss"
 */
abstract class DateTimeTypes: DateTime {
    abstract override val formatDate: (String) -> Any?
    abstract override val formatDateTime: (String) -> Any?
    abstract override val formatTime: (String) -> Any?

    abstract override val mapTypes: Map<KType, (String) ->Any?>
}

interface DateTime: DataTypes {
    val formatDate: (String) -> Any?
    val formatDateTime: (String) -> Any?
    val formatTime: (String) -> Any?

    override val mapTypes: Map<KType, (String) -> Any?>
}
