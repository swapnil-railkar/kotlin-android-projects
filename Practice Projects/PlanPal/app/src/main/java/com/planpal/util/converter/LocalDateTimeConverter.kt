package com.planpal.util.converter

import androidx.room.TypeConverter
import java.time.LocalDateTime

object LocalDateTimeConverter {

    @TypeConverter
    fun toString(date: LocalDateTime?): String? {
        return date?.toString()
    }

    @TypeConverter
    fun toLocalDateTime(date: String?): LocalDateTime? {
        return if (date.isNullOrBlank() || date.isEmpty()) null else LocalDateTime.parse(date)
    }
}