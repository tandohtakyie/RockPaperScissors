package com.example.rockpaperscissor

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Game::class], version = 1, exportSchema = false)
@TypeConverters(DateTimeConvert::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun gameDao() : GameDAO

    companion object {
        private const val DATABASE_NAME = "REMINDER_DATABASE"

        @Volatile
        private var reminderRoomDatabaseInstance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase? {
            if (reminderRoomDatabaseInstance == null) {
                synchronized(AppDatabase::class.java) {
                    if (reminderRoomDatabaseInstance == null) {
                        reminderRoomDatabaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java, DATABASE_NAME
                        )
                            .build()
                    }
                }
            }
            return reminderRoomDatabaseInstance
        }
    }
}