package com.vhl.blackmo.grass.errors

class MissMatchedFieldNameException(fieldName: String):
    Exception("Miss matched field Exception: [$fieldName] doest not exist in data class fields")

class MissMatchedNumberOfFieldsException(csvLength: Int, dataClassLength: Int):
    Exception("Miss matched number of field lengths, csv length: $csvLength," +
            " data class number of fields: $dataClassLength")