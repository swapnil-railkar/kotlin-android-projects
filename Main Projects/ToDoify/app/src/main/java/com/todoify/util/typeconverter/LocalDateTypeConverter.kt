package com.todoify.util.typeconverter

import androidx.room.TypeConverter
import java.time.LocalDate

object LocalDateTypeConverter {

    @TypeConverter
    fun toString(date: LocalDate?): String? {
        return date?.toString()
    }

    @TypeConverter
    fun toLocalDate(date: String?): LocalDate? {
        return if (date == null) {
            null
        } else {
            LocalDate.parse(date)
        }
    }

}