package com.vhl.blackmo.grass.dsl

import com.vhl.blackmo.grass.pot.Plant
import io.kotlintest.matchers.types.shouldBeTypeOf
import io.kotlintest.specs.StringSpec

@ExperimentalStdlibApi
class GradeDsl: StringSpec({
    data class Sample(val sample: String)
    "instantiate grass parser globally" {
        val grass = grass<Sample>()
        grass.shouldBeTypeOf<Plant<Sample>>()
    }

    "able to input setting in dsl" {
        val grass = grass<Sample>{
            timeFormat = "HH-mm-ss"
            dateFormat = "MM-dd-yyyy"
            dateTimeSeparator = "-"
        }
        grass.ctx.timeFormat = "HH-mm-ss"
        grass.ctx.dateFormat = "MM-dd-yyyy"
        grass.ctx.dateTimeSeparator = "-"
    }
})