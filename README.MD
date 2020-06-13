<h1 align="center">kotlin-grass</h1>
<p>
  <img alt="Version" src="https://img.shields.io/badge/version-0.0.1-blue.svg?cacheSeconds=2592000" />
  <a href="https://github.com/blackmo18/kotlin-grass/blob/master/LICENSE">
    <img alt="License: Apache License 2.0" src="https://img.shields.io/badge/License-Apache License 2.0-yellow.svg" target="_blank" />
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
```
//todo to be publish
//gradle kotlin DSL
implementation("com.vhl.blackmo:kotlin-grass:0.0.1")

//gradle groovy DSL
implementation 'com.vhl.blackmo:kotlin-grass:0.0.1'
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
This project is inspired ❤️ by [kotlin-csv](https://github.com/doyaaaaaken/kotlin-csv)