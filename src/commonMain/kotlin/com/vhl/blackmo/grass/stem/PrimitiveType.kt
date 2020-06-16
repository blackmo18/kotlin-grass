package com.vhl.blackmo.grass.stem

/**
 *
 * @author balckmo18
 */
internal class PrimitiveType {
    companion object {
        val toShort = fun (value: String) = value.toShort()
        val toInt = fun(value: String) = value.toInt()
        val toLong = fun(value: String) = value.toLong()
        val toFloat = fun(value: String) = value.toFloat()
        val toDouble = fun(value: String) = value.toDouble()
        val toBoolean = fun(value: String) = value.toBoolean()
        val string = fun(value: String) = value
    }
}