package com.vhl.blackmo.grass
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.vhl.blackmo.grass.dsl.grass
import io.kotlintest.TestCase
import io.kotlintest.TestResult
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * @author blackmo18
 */
@ExperimentalStdlibApi
class DataParserTest: WordSpec() {
    override fun afterTest(testCase: TestCase, result: TestResult) {
        super.afterTest(testCase, result)
    }
    init {
        "test parse" should {
            "parse to primitive data type" {
                data class Grass(
                        val short: Short,
                        val int: Int,
                        val long: Long,
                        val float: Float,
                        val double: Double,
                        val boolean: Boolean,
                        val string: String
                )

                val expected = Grass(0, 1, 2, 3.0f, 4.0, true, "hello")
                val contents = readTestFile("/primitive.csv").asSequence()
                val parsed = grass<Grass>().harvest(contents)
                val actual = parsed.first()

                assertTrue { expected == actual }
            }
        }

        "parse java date and time" should {
            "parse default time and date format" {
                data class DateAndTime(
                    val date: LocalDate,
                    val datetime: LocalDateTime,
                    val time: LocalTime
                )
                val date = LocalDate.of(2020, 12, 1)
                val dateTime = LocalDateTime.of(2020, 12, 1, 12, 0)
                val time = LocalTime.of(11, 12)

                val expected = DateAndTime(date,dateTime,time)
                val contents = readTestFile("date-and-time.csv").asSequence()
                val actual = grass<DateAndTime>().harvest(contents).first()

                assertTrue { actual.date.isEqual(expected.date) }
                assertTrue { actual.datetime.isEqual(expected.datetime) }
                actual.time.hour shouldBe expected.time.hour
                actual.time.minute shouldBe expected.time.minute
            }

            "parse custom date, time, and separator format" {
                data class DateAndTime(
                    val datetime: LocalDateTime,
                    val time: LocalTime
                )
                val datetime = LocalDateTime.of(2020, 12, 1, 11, 12, 13)
                val time = LocalTime.of(11, 12,13)

                val expected = DateAndTime(datetime,time)
                val contents = readTestFile("date-time-and-seconds.csv").asSequence()
                val actual = grass<DateAndTime>{
                    dateFormat = "MM-dd-yyyy"
                    timeFormat = "HH:mm:ss"
                    dateTimeSeparator = "/"
                }.harvest(contents).first()

                assertTrue { actual.datetime.isEqual(expected.datetime) }
                actual.time.hour shouldBe expected.time.hour
                actual.time.minute shouldBe expected.time.minute
                actual.time.second shouldBe expected.time.second
            }
        }

        "custom key value" should {
            "able to map custom key" {
                data class Grass(
                    val short: Short,
                    val int: Int,
                    val longX: Long,
                    val floatX: Float,
                    val double: Double,
                    val boolean: Boolean,
                    val string: String
                )

                val expected = Grass(0, 1, 2, 3.0f, 4.0, true, "hello")
                val contents = readTestFile("/primitive.csv").asSequence()
                val parsed = grass<Grass>{
                    customKeyMap = mapOf("long" to "longX", "float" to "floatX")
                }.harvest(contents)
                val actual = parsed.first()

                assertTrue { actual == expected }
            }
        }
    }
}

fun readTestFile(fileName: String): List<Map<String, String>> {
    val file =  File("src/jvmTest/resources/$fileName")
    return csvReader().readAllWithHeader(file)
}


