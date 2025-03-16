package com.example.smartvoice.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey val id: Long = 0,
    val chinum: String,
    val patientName: String,
    val age: Int,
    val currentstatus: String,
    val email: String,   // ✅ Store email
    val password: String // ✅ Store password
)