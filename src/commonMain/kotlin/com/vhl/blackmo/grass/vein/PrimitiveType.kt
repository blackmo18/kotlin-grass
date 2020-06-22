package com.vhl.blackmo.grass.vein

import kotlin.reflect.typeOf

/**
 *
 * @author blackmo18
 */
internal class PrimitiveType {
    @ExperimentalStdlibApi
    companion object {
        private val short = fun (value: String) = value.toShort()
        private val int = fun(value: String) = value.toInt()
        private val long = fun(value: String) = value.toLong()
        private val float = fun(value: String) = value.toFloat()
        private val double = fun(value: String) = value.toDouble()
        private val boolean = fun(value: String) = value.toBoolean()
        private val string = fun(value: String) = value

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