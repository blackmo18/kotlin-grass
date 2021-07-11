package io.blackmo18.kotlin.grass.dsl

import io.blackmo18.kotlin.grass.pot.Plant
import io.kotlintest.matchers.types.shouldBeTypeOf
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

@ExperimentalStdlibApi
class GrassDsl: StringSpec({
    data class Sample(val sample: String)
    "instantiate grass parser globally" {
        val grass = grass<Sample>()
        grass.shouldBeTypeOf<Plant<Sample>>()
    }

    "able to input settings in dsl" {
        val grass = grass<Sample>{
            timeFormat = "HH-mm-ss"
            dateFormat = "MM-dd-yyyy"
            trimWhiteSpace = false
            dateTimeSeparator = "-"
            ignoreUnknownFields = true
            caseSensitive = false
        }
        grass.ctx.timeFormat shouldBe  "HH-mm-ss"
        grass.ctx.dateFormat shouldBe  "MM-dd-yyyy"
        grass.ctx.dateTimeSeparator shouldBe  "-"
        grass.ctx.trimWhiteSpace shouldBe  false
        grass.ctx.ignoreUnknownFields shouldBe true
        grass.ctx.caseSensitive shouldBe false
    }
})
