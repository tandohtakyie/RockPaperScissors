package com.example.rockpaperscissor

import androidx.room.TypeConverter
import java.util.*

class DateTimeConvert{
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun ChoiceToInt(choice: Choice ): Int? {
        return choice.value
    }

    @TypeConverter
    fun intToChoice(value: Int): Choice {
        return when (value) {
            0 -> Choice.ROCK
            1 -> Choice.PAPER
            2 -> Choice.SCISSOR
            else -> Choice.ROCK
        }
    }
}