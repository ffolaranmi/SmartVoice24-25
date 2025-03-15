package com.example.smartvoice.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [VoiceSample::class], version = 1, exportSchema = false)
abstract class VoiceSampleDatabase: RoomDatabase() {
    abstract fun voiceSampleDao(): VoiceSampleDAO

    companion object {
        @Volatile
        private var Instance: VoiceSampleDatabase ?= null
        fun getDatabase(context: Context): VoiceSampleDatabase{
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, VoiceSampleDatabase::class.java, "voiceSample_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}