package io.github.blackmo18.grass.vein

import kotlin.reflect.typeOf

/**
 * Defines Primitive data types in kotlin
 *  * [Short]
 *  * [Int]
 *  * [Long]
 *  * [Float]
 *  * [Double]
 *  * [Boolean]
 *  * [String]
 *
 * @author blackmo18
 */
internal class PrimitiveType {
    @ExperimentalStdlibApi
    companion object {
        private val short = fun (value: String) = if (value.isNotBlank()) value.toShort() else null
        private val int = fun(value: String) = if(value.isNotBlank()) value.toInt() else null
        private val long = fun(value: String) = if(value.isNotBlank()) value.toLong() else null
        private val float = fun(value: String) = if(value.isNotBlank()) value.toFloat() else null
        private val double = fun(value: String) = if(value.isNotBlank()) value.toDouble() else null
        private val boolean = fun(value: String) = if(value.isNotBlank()) value.toBoolean() else null
        private val string = fun(value: String) = if(value.isNotBlank()) value else null


        /**
         * Returns primitive data type mapping
         */
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