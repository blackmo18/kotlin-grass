package com.vhl.blackmo.grass

import com.vhl.blackmo.grass.data.MissMatch
import com.vhl.blackmo.grass.data.PrimitiveTypes
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
                val contents = readTestFile("/primitive-missmatched.csv").asSequence()
                val parsed = grass<PrimitiveTypes>().harvest(contents)
                shouldThrow<MissMatchedFieldNameException> {
                    parsed.first()
                }
            }
            "throw Miss Matched Field Name Exception at dirty mapping"  {
                val contents = readTestFile("/primitive-missmatched.csv").asSequence()
                val parsed = grass<PrimitiveTypes>{
                    customKeyMap = mapOf("long" to "long", "float" to "float")
                }.harvest(contents)
                shouldThrow<MissMatchedFieldNameException> {
                    parsed.first()
                }
            }
            "throw miss matched number of fields" {
                val contents = readTestFile("/primitive.csv").asSequence()
                val parsed = grass<MissMatch>().harvest(contents)
                shouldThrow<MissMatchedNumberOfFieldsException> {
                    parsed.first()
                }
            }
            "throw Miss Match Field Name Exception on white space " {
                val contents = readTestFile("/primitive-with-white-space.csv").asSequence()
                val parsed = grass<PrimitiveTypes>{
                    trimWhiteSpace = false
                }.harvest(contents)
                shouldThrow<Exception> {
                    parsed.first()
                }
            }
        }
    }
}

