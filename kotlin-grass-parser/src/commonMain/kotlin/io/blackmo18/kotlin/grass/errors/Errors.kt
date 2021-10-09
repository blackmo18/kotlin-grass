package io.blackmo18.kotlin.grass.errors

class MissMatchedFieldNameException(fieldName: String):
    Exception("Mismatched field exception: [$fieldName] does not exist in data class fields")

class MissMatchedNumberOfFieldsException(csvLength: Int, dataClassLength: Int):
    Exception("Mismatched number of field length, CSV length: $csvLength," +
            " data class number of fields: $dataClassLength")
