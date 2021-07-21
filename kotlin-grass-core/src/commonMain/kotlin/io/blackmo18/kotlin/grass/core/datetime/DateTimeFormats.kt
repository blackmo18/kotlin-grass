package io.blackmo18.kotlin.grass.core.datetime

/**
 * Global Date Time Default formats
 */
object DateTimeFormats: DateTimeCtx {
    /**
     *  date formatting
     *  default value: **yyyy-MM-dd**
     */
    override var dateFormat: String = "yyyy-MM-dd"

    /**
     * time formatting
     * default value: **HH:mm**
     */
    override var timeFormat: String = "HH:mm"

    /**
     * date time separator
     * default value: **(space)**
     */
    override var dateTimeSeparator: String = " "
}