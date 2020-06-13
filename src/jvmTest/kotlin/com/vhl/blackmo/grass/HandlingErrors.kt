package com.vhl.blackmo.grass

import com.vhl.blackmo.grass.dsl.grass
import com.vhl.blackmo.grass.errors.MissMatchedNumberOfFieldsException
import com.vhl.blackmo.grass.errors.MissMatchedFieldNameException
import io.kotlintest.shouldThrow
import io.kotlintest.specs.WordSpec

@ExperimentalStdlibApi
class HandlingErrors: WordSpec() {
    init {
        "handle errors" should  {
            "throw Miss Match Field Name Exception" {
                data class Grass(
                    val short: Short,
                    val int: Int,
                    val longX: Long,
                    val float: Float,
                    val double: Double,
                    val boolean: Boolean,
                    val string: String
                )

                val contents = readTestFile("/primitive.csv").asSequence()
                val parsed = grass<Grass>().harvest(contents)
                shouldThrow<MissMatchedFieldNameException> {
                    parsed.first()
                }
            }
            "throw Miss Matched Field Name Exception at dirty mapping"  {
                data class Grass(
                        val short: Short,
                        val int: Int,
                        val longX: Long,
                        val floatX: Float,
                        val double: Double,
                        val boolean: Boolean,
                        val string: String
                )

                val contents = readTestFile("/primitive.csv").asSequence()
                val parsed = grass<Grass>{
                    customKeyMap = mapOf("long" to "long", "float" to "float")
                }.harvest(contents)
                shouldThrow<MissMatchedFieldNameException> {
                    parsed.first()
                }
            }
            "throw miss matched number of fields" {
                data class Grass(
                    val short: Short,
                    val int: Int
                )
                val contents = readTestFile("/primitive.csv").asSequence()
                val parsed = grass<Grass>().harvest(contents)
                shouldThrow<MissMatchedNumberOfFieldsException> {
                    parsed.first()
                }
            }
        }
    }
}

