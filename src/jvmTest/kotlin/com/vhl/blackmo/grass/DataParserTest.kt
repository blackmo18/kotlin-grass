package com.vhl.blackmo.grass
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.vhl.blackmo.grass.data.DateAndTime
import com.vhl.blackmo.grass.data.DateTime
import com.vhl.blackmo.grass.data.NullableDataTypes
import com.vhl.blackmo.grass.data.PrimitiveTypes
import com.vhl.blackmo.grass.dsl.grass
import io.kotlintest.TestCase
import io.kotlintest.TestResult
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
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
        "Parse into Data Class" should {
            "parse to primitive data type" {
                val expected =
                    PrimitiveTypes(0, 1, 2, 3.0f, 4.0, true, "hello")
                val contents = readTestFile("/primitive.csv").asSequence()
                val parsed = grass<PrimitiveTypes>().harvest(contents)
                val actual = parsed.first()

                assertTrue { expected == actual }
            }

            "parse data as list" {
                val expected =
                    PrimitiveTypes(0, 1, 2, 3.0f, 4.0, true, "hello")
                val contents = readTestFile("/primitive.csv")
                val parsed = grass<PrimitiveTypes>().harvest(contents)
                val actual = parsed.first()

                assertTrue { expected == actual }
            }

            "parse multiple rows" {
                val expected0 =
                    PrimitiveTypes(0, 1, 2, 3.0f, 4.0, true, "hello")
                val expected1 =
                    PrimitiveTypes(5, 6, 7, 8.0f, 9.0, true, "hi")
                val contents = readTestFile("/primitive.csv")
                val parsed = grass<PrimitiveTypes>().harvest(contents)

                assertTrue { expected0 == parsed[0] }
                assertTrue { expected1 == parsed[1] }
            }

            "parse nullable data class" {
                val expected0 = NullableDataTypes(0, 1, 2, 3.0f, 4.0, true, "hello")
                val expected1 = NullableDataTypes(5, 6, 7, 8.0f, 9.0, true, "hi")
                val contents = readTestFile("/primitive.csv")
                val parsed = grass<NullableDataTypes>().harvest(contents)

                assertTrue { expected0 == parsed[0] }
                assertTrue { expected1 == parsed[1] }
            }
            "parse null values" {
                val expected0 = NullableDataTypes(null, null, null, null, null, null, null)
                val contents = readTestFile("/primitive-empty.csv")
                val parsed = grass<NullableDataTypes>().harvest(contents)

                assertTrue { expected0 == parsed.first() }
            }

            "parse with white space" {
                val expected =
                        PrimitiveTypes(0, 1, 2, 3.0f, 4.0, true, "hello")
                val contents = readTestFile("/primitive-with-white-space.csv")
                val parsed = grass<PrimitiveTypes>().harvest(contents)
                val actual = parsed.first()

                assertTrue { expected == actual }
            }
        }


        "parse java date and time" should {
            "parse default time and date format" {
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
                val datetime = LocalDateTime.of(2020, 12, 1, 11, 12, 13)
                val time = LocalTime.of(11, 12,13)

                val expected = DateTime(datetime,time)
                val contents = readTestFile("date-time-and-seconds.csv").asSequence()
                val actual = grass<DateTime>{
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
                val expected = PrimitiveTypes(0, 1, 2, 3.0f, 4.0, true, "hello")
                val contents = readTestFile("/primitive-missmatched.csv").asSequence()
                val parsed = grass<PrimitiveTypes>{
                    customKeyMap = mapOf("longX" to "long", "floatX" to "float")
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


