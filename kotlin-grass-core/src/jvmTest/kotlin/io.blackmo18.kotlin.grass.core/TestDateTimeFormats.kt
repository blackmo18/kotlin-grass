package io.blackmo18.kotlin.grass.core

import io.blackmo18.kotlin.grass.core.datetime.DateTimeFormats
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class TestDateTimeFormats: WordSpec() {
    init {
        "validate format customization" should {
            "reflect changes" {
                DateTimeFormats.dateFormat = "MM-dd-YYYY"
                DateTimeFormats.timeFormat = "HH-mm-ss"
                DateTimeFormats.dateTimeSeparator = "-"

                DateTimeFormats.dateFormat shouldBe   "MM-dd-YYYY"
                DateTimeFormats.timeFormat shouldBe "HH-mm-ss"
                DateTimeFormats.dateTimeSeparator shouldBe "-"
            }
        }
    }
}