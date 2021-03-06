package io.blackmo18.kotlin.grass
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import io.blackmo18.kotlin.grass.data.NullableDataTypes
import io.blackmo18.kotlin.grass.data.NullableDataTypesCustomNames
import io.blackmo18.kotlin.grass.data.PrimitiveTypes
import io.blackmo18.kotlin.grass.data.WhiteSpacedStrings
import io.blackmo18.kotlin.grass.dsl.grass
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
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

            "parse to primitive data type with unmapped field" {
                val expected =
                    PrimitiveTypes(0, 1, 2, 3.0f, 4.0, true, "hello")
                val contents = readTestFile("/primitive-with-unmapped-field.csv").asSequence()
                val grass = grass<PrimitiveTypes>() {
                    ignoreUnknownFields = true
                }
                val parsed = grass.harvest(contents)
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

            "parse data as flow" {
                val expected =
                    PrimitiveTypes(0, 1, 2, 3.0f, 4.0, true, "hello")
                val contents = openFileAsStream("/primitive.csv")
                val actual = csvReader().openAsync(contents) {
                    val data = readAllWithHeaderAsSequence().asFlow()
                    grass<PrimitiveTypes>().harvest(data).first()
                }
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
            "parse with white space and trim" {
                val expected =
                    WhiteSpacedStrings(" 0", " 1", " 2", " 3.0", " 4.0", " true", " hello")
                val contents = readTestFile("/primitive-with-white-space.csv")
                val grass = grass<WhiteSpacedStrings> {
                    trimWhiteSpace = false
                }
                val parsed = grass.harvest(contents)
                val actual = parsed.first()

                assertTrue { expected == actual }
            }
            "parse null values with custom names"  {
                val expected0 = NullableDataTypesCustomNames(null, null, null, null, null, null, null)
                val contents = readTestFile("/primitive-empty.csv")
                val parser = grass<NullableDataTypesCustomNames> {
                    customKeyMap = mapOf(
                        "short" to "shortCustom", "int" to "intCustom", "long" to "longCustom",
                        "float" to "floatCustom", "double" to "doubleCustom", "boolean" to "booleanCustom",
                        "string" to "stringCustom"
                    )
                }
                val parsed = parser.harvest(contents)

                assertTrue { expected0 == parsed.first() }
            }
            "parse null values with custom names using data class property"  {
                val expected = NullableDataTypesCustomNames(null, null, null, null, null, null, null)
                val contents = readTestFile("/primitive-empty.csv")
                val parser = grass<NullableDataTypesCustomNames> {
                    customKeyMapDataProperty = mapOf(
                        "short" to NullableDataTypesCustomNames::shortCustom, "int" to NullableDataTypesCustomNames::intCustom, "long" to NullableDataTypesCustomNames::longCustom,
                        "float" to NullableDataTypesCustomNames::floatCustom, "double" to NullableDataTypesCustomNames::doubleCustom, "boolean" to NullableDataTypesCustomNames::booleanCustom,
                        "string" to NullableDataTypesCustomNames::stringCustom
                    )
                }
                val parsed = parser.harvest(contents)

                assertTrue { expected == parsed.first() }
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
            "parse not case sensitive keys" {
                val expected = PrimitiveTypes(0, 1, 2, 3.0f, 4.0, true, "hello")
                val contents = readTestFile("/primitive-uppercase.csv").asSequence()
                val parsed = grass<PrimitiveTypes>{
                    caseSensitive = false
                }.harvest(contents)
                val actual = parsed.first()
                assertTrue { actual == expected }
            }
            "parse not case sensitive with custom key mapping default" {
                val expected = PrimitiveTypes(0, 1, 2, 3.0f, 4.0, true, "hello")
                val contents = readTestFile("/primitive-missmatched.csv").asSequence()
                val parsed = grass<PrimitiveTypes>{
                    customKeyMap = mapOf("LONGX" to "LONG", "floatX" to "float")
                    caseSensitive = false
                }.harvest(contents)
                val actual = parsed.first()
                assertTrue { actual == expected }
            }
            "parse not case sensitive with custom key map data class property" {
                val expected = NullableDataTypesCustomNames(null, null, null, null, null, null, null)
                val contents = readTestFile("/primitive-empty.csv")
                val parser = grass<NullableDataTypesCustomNames> {
                    customKeyMapDataProperty = mapOf(
                        "SHORT" to NullableDataTypesCustomNames::shortCustom, "int" to NullableDataTypesCustomNames::intCustom, "LONG" to NullableDataTypesCustomNames::longCustom,
                        "float" to NullableDataTypesCustomNames::floatCustom, "double" to NullableDataTypesCustomNames::doubleCustom, "BOOLEAN" to NullableDataTypesCustomNames::booleanCustom,
                        "STRING" to NullableDataTypesCustomNames::stringCustom
                    )
                    caseSensitive = false
                }
                val parsed = parser.harvest(contents)

                assertTrue { expected == parsed.first() }
            }
        }
    }
}

fun readTestFile(fileName: String): List<Map<String, String>> {
    val file =  File("src/jvmTest/resources/$fileName")
    return csvReader().readAllWithHeader(file)
}

fun openFileAsStream(fileName: String) = File("src/jvmTest/resources/$fileName").inputStream()


