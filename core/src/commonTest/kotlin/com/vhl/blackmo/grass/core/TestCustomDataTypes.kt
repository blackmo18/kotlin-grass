package com.vhl.blackmo.grass.core

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@ExperimentalStdlibApi
class TestCustomDataTypes {

    private val dataTypes = CustomDataTypeTest()

    @ExperimentalStdlibApi
    @BeforeTest
    fun cleanUp() {
       CustomDataTypes.clearMapTypes()
    }
    @Test
    fun testAdd() {
        CustomDataTypes.addDataTypes(dataTypes)
        assertNotEquals(0, dataTypes.mapTypes.size)
    }

    @Test
    fun testGet() {
        CustomDataTypes.addDataTypes(dataTypes)
        assertEquals(CustomDataTypes.mapType, PrimitiveType.mapTypes)

    }
     @Test
     fun testClear() {
         CustomDataTypes.addDataTypes(dataTypes)
         CustomDataTypes.clearMapTypes()
         assertEquals(0, CustomDataTypes.mapType.size)
     }
}