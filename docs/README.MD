<h1 align="center">kotlin-grass</h1>
<p>
  <a href='https://bintray.com/blackmo18/kotlin-libraries/kotlin-grass/_latestVersion'>
    <img src='https://api.bintray.com/packages/blackmo18/kotlin-libraries/kotlin-grass/images/download.svg'>
    </a>
  <a href="https://github.com/blackmo18/kotlin-grass/blob/master/LICENSE">
    <img alt="License: Apache License 2.0" src="https://img.shields.io/badge/License-Apache License 2.0-yellow.svg" target="_blank" />
  </a>
  <a href="https://github.com/blackmo18/kotlin-grass/workflows/build/badge.svg?branch=master">
    <img src="https://github.com/blackmo18/kotlin-grass/workflows/build/badge.svg?branch=master" alt="CodeFactor" />
  </a>
  <a href="https://www.codefactor.io/repository/github/blackmo18/kotlin-grass">
    <img src="https://www.codefactor.io/repository/github/blackmo18/kotlin-grass/badge" alt="CodeFactor" />
  </a>
  <a href="https://codecov.io/gh/blackmo18/kotlin-grass">
    <img src="https://codecov.io/gh/blackmo18/kotlin-grass/branch/master/graph/badge.svg" />
  </a>
</p>

>Csv File to Kotlin  Data Class Parser<br />
>Currently, it requires to have  `@ExperimentalStdlibApi` on the class/method  using  this Library.<br />
>Requires [kotlin-csv by doyaaaaaken](https://github.com/doyaaaaaken/kotlin-csv/) for reading csv file

# Features

### 1. Simple And Direct
  * No hard configuration
  * No invasive annotations to data class
  * Custom mapping
  * Nullable Data Types
### 2. Primitive Types
  * Short
  * Int
  * Long
  * Float
  * Double
  * Boolean
  * String
### 3. Support for Java 8 Date Time Apis
  * LocalTime
  * LocalDateTime
  * LocalDate
  * Custom Formatting


# Usage
#### Gradle DSL:
```kotlin
//doyaaaaaken's kotlin-csv
implementation("com.github.doyaaaaaken:kotlin-csv-jvm:0.11.0")
//kotlin-grass
implementation("com.vhl.blackmo:kotlin-grass-jvm:0.4.0")
```

### Prior to Kotlin 1.4.0
Please use the following version
```kotlin
//doyaaaaaken's kotlin-csv
implementation("com.github.doyaaaaaken:kotlin-csv-jvm:0.10.4")
//kotlin-grass
implementation("com.vhl.blackmo:kotlin-grass-jvm:0.3.0")
```


## Examples
#### CSV file
|short|int|long|float|double|boolean|string
|---------------|---------------|---------------|---------------|---------------|---------------|---------------|
|0|1|2|3.0|4.0|true|hello|
#### Declaring data class
```kotlin
    data class PrimitiveTypes(
        val short: Short,
        val int: Int,
        val long: Long,
        val float: Float,
        val double: Double,
        val boolean: Boolean,
        val string: String
    )
```

#### Nullable Data Types
If a variable in your data class is a nullable, all you have to do is mark it with  `?`
```kotlin
    data class NullableData(
        val nullableString: String?,
        val nullableInt: Int? = null,
        ...
    )
```

#### Parsing  to data class
```kotlin
    val csvContents = csvReader().readAllWithHeader(file)
    val dataClasses = grass<PrimitiveTypes>().harvest(csvContents)
```

# Custom Configuration
| Option | default value | description                         |
|------------|---------------|-------------------------------------|
| dateFormat |`yyyy-MM-dd`| date format |
| timeFormat | `HH:mm` | time format |
| dateTimeSeparator | `(space)` | date time separator |
| trimWhiteSpace | `true` | trims white spaces on csv entries |
| customKeyMap | `null` |`Map<String,String>` custom key mapping  |

# Java Date Time API Support
#### csv file
|time|datetime|date
|---------------|---------------|---------------|
|12:00|2020-12-31 12:00|2020-12-31|
#### Date and Time Types
```kotlin
    data class DateTimeTypes(
        val time: LocalTime,
        val datetime: LocalDateTime,
        val date: LocalDate,
    )
```

#### Customize Formatting
```kotlin
    val grass = grass<DateTimeTypes> {
        dateFormat = "MM-dd-yyyy"
        timeFormat = "HH:mm:ss"
        dateTimeSeparator = "/"
    }
```
# Custom Mapping Support
#### CSV file
|hour|birthdate|
|---------------|---------------|
|12:00|2020-12-31|

#### Code
```kotlin
    data class DateTime(
        val time: LocalTime,
        val date: LocalDate,
    )

    val grass = grass<DateTimeTypes> {
        customKeyMap = mapOf("hour" to "time", "birthdate" to "date")
    }
```

## 🤝 Contributing

Contributions, issues and feature requests are welcome!<br />
Feel free to check [issues page](https://github.com/blackmo18/kotlin-grass/issues).<br />

## Show your support

Give a ⭐️ if this project helped you!
## 📝 License
Copyright © 2020 [blackmo18](https://github.com/blackmo18). <br />
This project is [Apache License 2.0](https://github.com/blackmo18/kotlin-grass/blob/master/LICENSE) licensed.
***
This project inspired ❤️ by [kotlin-csv](https://github.com/doyaaaaaken/kotlin-csv)