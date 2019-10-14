package com.example.rockpaperscissor

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "rockPaperScissorsTable")
data class Game(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null,

    @ColumnInfo(name = "result")
    var result: String,

    @ColumnInfo(name = "date")
    var date: Date,

    @ColumnInfo(name = "humanChoice")
    var humanChoice: Choice,

    @ColumnInfo(name = "computerChoice")
    var computerChoice: Choice
)