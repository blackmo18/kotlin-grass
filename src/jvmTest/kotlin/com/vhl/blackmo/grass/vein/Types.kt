package com.vhl.blackmo.grass.vein

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@ExperimentalStdlibApi
class Types: WordSpec() {
   init {
      val dateExpected = LocalDate.of(2020, 6, 17)
      val dateTimeExpected = LocalDateTime.of(2020, 6, 17, 11, 12, 13)
      val timeExpected = LocalTime.of(11, 12,13)
      val dateTimeTypes = DateTimeTypes("MM-dd-yyyy", "HH:mm:ss", " ")

      "Types" should   {
         "validate date" {
            val date = "06-17-2020"
            val resultDate = dateTimeTypes.formatDate(date) as LocalDate

            dateExpected.year shouldBe  resultDate.year
            dateExpected.month shouldBe resultDate.month
            dateExpected.dayOfMonth shouldBe resultDate.dayOfMonth
         }

         "validate time" {
            val time = "11:12:13"
            val resultTime = dateTimeTypes.formatTime(time) as LocalTime

            timeExpected.hour shouldBe resultTime.hour
            timeExpected.minute shouldBe resultTime.minute
            timeExpected.second shouldBe resultTime.second
         }

         "validate date time" {
            val dateTime = "06-17-2020 11:12:13"
            val resultDateTime = dateTimeTypes.formatDateTime(dateTime) as LocalDateTime

            dateTimeExpected.year shouldBe  resultDateTime.year
            dateTimeExpected.month shouldBe resultDateTime.month
            dateTimeExpected.dayOfMonth shouldBe resultDateTime.dayOfMonth
            dateTimeExpected.hour shouldBe resultDateTime.hour
            dateTimeExpected.minute shouldBe resultDateTime.minute
            dateTimeExpected.second shouldBe resultDateTime.second
         }
      }
   }
}