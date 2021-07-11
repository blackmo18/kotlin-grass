package io.blackmo18.kotlin.grass.core

import io.blackmo18.kotlin.grass.core.DataTypes
import io.blackmo18.kotlin.grass.core.PrimitiveType
import kotlin.reflect.KType

class CustomDataTypeTest: io.blackmo18.kotlin.grass.core.DataTypes {
    @ExperimentalStdlibApi
    override val mapTypes: Map<KType, (String) -> Any?> = io.blackmo18.kotlin.grass.core.PrimitiveType.mapTypes
}