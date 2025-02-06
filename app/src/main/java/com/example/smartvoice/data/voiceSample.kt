package com.example.smartvoice.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// healthy, functional, structural, neurological
enum class Classification {
    HEALTHY,
    FUNCTIONAL,
    STRUCTURAL,
    NEUROLOGICAL,
    OTHER,
    PROCESSING
}

@Entity(tableName = "voiceSample")
data class VoiceSample(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "file_location")
    val fileLocation: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
    val classification: Classification
)
