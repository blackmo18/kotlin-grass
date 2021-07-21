package io.blackmo18.kotlin.grass.date.time

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import io.blackmo18.kotlin.grass.core.CustomDataTypes
import io.blackmo18.kotlin.grass.date.time.data.DateAndTime
import io.blackmo18.kotlin.grass.date.time.data.DateTime
import io.blackmo18.kotlin.grass.dsl.grass
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.test.assertTrue

@ExperimentalStdlibApi
class Java8DateTimeTest: WordSpec() {
    init {
        "parse java date and time" should {
            "parse default time and date format" {
                val date = LocalDate.of(2020, 12, 1)
                val dateTime = LocalDateTime.of(2020, 12, 1, 12, 0)
                val time = LocalTime.of(11, 12)
                val expected = DateAndTime(date,dateTime,time)
                val contents = readTestFile("date-and-time.csv").asSequence()
                CustomDataTypes.addDataTypes(Java8DateTime)
                val actual = grass<DateAndTime>().harvest(contents).first()

                assertTrue { actual.date.isEqual(expected.date) }
                assertTrue { actual.datetime.isEqual(expected.datetime) }
                actual.time.hour shouldBe expected.time.hour
                actual.time.minute shouldBe expected.time.minute
            }

            "parse custom date, time, and separator format" {
                val datetime = LocalDateTime.of(2020, 12, 1, 11, 12, 13)
                val time = LocalTime.of(11, 12,13)

                val expected = DateTime(datetime,time)
                val contents = readTestFile("date-time-and-seconds.csv").asSequence()
                val actual = grass<DateTime>{
                    dateFormat = "MM-dd-yyyy"
                    timeFormat = "HH:mm:ss"
                    dateTimeSeparator = "/"
                    customDataTypes= listOf(Java8DateTime)
                }.harvest(contents).first()

                assertTrue { actual.datetime.isEqual(expected.datetime) }
                actual.time.hour shouldBe expected.time.hour
                actual.time.minute shouldBe expected.time.minute
                actual.time.second shouldBe expected.time.second
            }
        }
    }
}

fun readTestFile(fileName: String): List<Map<String, String>> {
    val file =  File("src/jvmTest/resources/$fileName")
    return csvReader().readAllWithHeader(file)
}
