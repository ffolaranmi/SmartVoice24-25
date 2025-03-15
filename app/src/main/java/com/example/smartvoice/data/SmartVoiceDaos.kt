package com.example.smartvoice.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SmartVoiceDaos {
    @Insert
    suspend fun insert(entity: DiagnosisTable)

    @Query("SELECT * FROM diagnosis")
    suspend fun getAllEntities(): List<DiagnosisTable>
}