package com.example.smartvoice.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DiagnosisDao {
    @Insert
    suspend fun insertNewDiagnosis(entity: DiagnosisTable)

    @Query("SELECT * FROM diagnosis ORDER BY recordingDate DESC")
    suspend fun getAllEntities(): List<DiagnosisTable>

    @Query("DELETE FROM diagnosis")
    suspend fun clearAll()

    @Query("DELETE FROM diagnosis")
    suspend fun clearAllDiagnoses()


}
