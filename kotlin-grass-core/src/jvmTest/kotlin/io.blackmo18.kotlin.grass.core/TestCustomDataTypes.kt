package io.blackmo18.kotlin.grass.core

import io.kotlintest.specs.WordSpec
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@ExperimentalStdlibApi
class TestCustomDataTypes: WordSpec() {
    private val dataTypes = CustomDataTypeTest()
    @BeforeTest
    fun cleanUp() {
        CustomDataTypes.clearMapTypes()
    }

    init {
       "validate custom data types" should {
          "retrieve data"  {
              CustomDataTypes.addDataTypes(dataTypes)
              assertNotEquals(0, dataTypes.mapTypes.size)
          }
          "clear data" {
              CustomDataTypes.addDataTypes(dataTypes)
              CustomDataTypes.clearMapTypes()
              assertEquals(0, CustomDataTypes.mapType.size)
          }
       }
    }
}