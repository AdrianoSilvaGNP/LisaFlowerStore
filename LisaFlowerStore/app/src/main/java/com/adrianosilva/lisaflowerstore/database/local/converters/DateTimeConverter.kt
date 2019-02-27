package com.adrianosilva.lisaflowerstore.database.local.converters

import androidx.room.TypeConverter
import org.joda.time.DateTime

class DateTimeConverter {

    @TypeConverter
    fun toDateTime(dateTimeLong: Long): DateTime {
        return DateTime(dateTimeLong)
    }

    @TypeConverter
    fun fromDate(dateTime: DateTime): Long {
        return dateTime.millis
    }
}