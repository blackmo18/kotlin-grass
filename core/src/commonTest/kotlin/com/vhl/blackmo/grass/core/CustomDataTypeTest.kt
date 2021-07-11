package com.vhl.blackmo.grass.core

import com.vhl.blackmo.grass.core.DataTypes
import com.vhl.blackmo.grass.core.PrimitiveType
import kotlin.reflect.KType

class CustomDataTypeTest: DataTypes {
    @ExperimentalStdlibApi
    override val mapTypes: Map<KType, (String) -> Any?> = PrimitiveType.mapTypes
}