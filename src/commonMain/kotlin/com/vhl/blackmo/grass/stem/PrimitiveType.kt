package com.vhl.blackmo.grass.stem

import kotlin.reflect.typeOf

/**
 *
 * @author balckmo18
 */
internal class PrimitiveType {
    @ExperimentalStdlibApi
    companion object {
        val short = fun (value: String) = value.toShort()
        val int = fun(value: String) = value.toInt()
        val long = fun(value: String) = value.toLong()
        val float = fun(value: String) = value.toFloat()
        val double = fun(value: String) = value.toDouble()
        val boolean = fun(value: String) = value.toBoolean()
        val string = fun(value: String) = value

        val mapTypes = mapOf(
                typeOf<Short>() to short,
                typeOf<Int>() to int,
                typeOf<Long>() to long,
                typeOf<Float>() to float,
                typeOf<Double>() to double,
                typeOf<Boolean>() to boolean,
                typeOf<String>() to string,
                //-- nullable
                typeOf<Short?>() to short,
                typeOf<Int?>() to int,
                typeOf<Long?>() to long,
                typeOf<Float?>() to float,
                typeOf<Double?>() to double,
                typeOf<Boolean?>() to boolean,
                typeOf<String?>() to string
        )
    }
}