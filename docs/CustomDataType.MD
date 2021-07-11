# Custom Data Type

### Creating Your Custom Data  Type

1. Add kotlin grass core to your project dependencies
   `implementation("com.vhl.blackmo:kotlin-grass-core-jvm:1.0.0")`
2. implement `Datatype` interface to your custom `data type class` <br />
   a. Create Necessary Transform Functions. This `transform function` will handle the conversion of
   string values into desired data type
   
   b. Transform function must take into the form of:  
   
   `fun fx(value: String): Any?`
   
   where the return is the desired value of data type

   c.  Add the mapping into the `mapTypes` including the null value
 
```kotlin
 object Java8DateTime: DataTypes {
   
   // transforms functions
   override val formatDate = fun (value: String): Any? {
      val dateFormatter = DateTimeFormatter.ofPattern(DateTimeFormats.dateFormat)
      return LocalDate.parse(value, dateFormatter)
   }

   // transforms functions
   override val formatDateTime = fun(value: String): Any? {
      val dateTimeFormatter = DateTimeFormatter.ofPattern("${DateTimeFormats.dateFormat}${DateTimeFormats.dateTimeSeparator}${DateTimeFormats.timeFormat}")
      return LocalDateTime.parse(value, dateTimeFormatter)
   }

   // transforms functions
   override val formatTime = fun(value: String): Any? {
      val timeFormatter = DateTimeFormatter.ofPattern(DateTimeFormats.timeFormat)
      return LocalTime.parse(value, timeFormatter)
   }

   override val mapTypes = mapOf(
      typeOf<LocalDate>()  to formatDate,
      typeOf<LocalDateTime>() to formatDateTime,
      typeOf<LocalTime>() to formatTime,
      //-- nullable types
      typeOf<LocalDate?>()  to formatDate,
      typeOf<LocalDateTime?>() to formatDateTime,
      typeOf<LocalTime?>() to formatTime
   )
}
```

3. Declare you Custom Data Type Handler
```kotlin 

 val actual = grass<DateTimeOnly> {
     dateFormat = "MM-dd-yyyy"
     timeFormat = "HH:mm:ss"
     dateTimeSeparator = "/"
     customDataTypes = arrayListOf(Java8DateTime)
 }.harvest(contents).first()
```