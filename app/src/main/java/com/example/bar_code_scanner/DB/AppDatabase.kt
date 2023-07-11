package com.example.bar_code_scanner.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bar_code_scanner.Model.ScannedData

@Database(entities = [ScannedData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun yourDao(): QRDataDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "your_database_name"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

