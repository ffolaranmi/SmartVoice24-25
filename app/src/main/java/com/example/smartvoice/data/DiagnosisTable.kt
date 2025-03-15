package com.example.smartvoice.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import java.util.Date

@Entity(tableName = "diagnosis",
    /*foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["chinum"],
            childColumns = ["patientchi"],
            onDelete = ForeignKey.CASCADE // Optional: Define behavior on deletion
        )
    ]*/
)
data class DiagnosisTable(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val patientchi: String, //foreign key
    val patientName: String,
    val diagnosis: String,
    val recordingDate: String,
    val recordingLength: String
    )