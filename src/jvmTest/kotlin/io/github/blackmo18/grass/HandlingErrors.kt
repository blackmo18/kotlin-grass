package io.github.blackmo18.grass

import io.github.blackmo18.grass.data.MissMatch
import io.github.blackmo18.grass.data.PrimitiveTypes
import io.github.blackmo18.grass.dsl.grass
import io.github.blackmo18.grass.errors.MissMatchedFieldNameException
import io.github.blackmo18.grass.errors.MissMatchedNumberOfFieldsException
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

