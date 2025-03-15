package com.example.smartvoice.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insert(entity: User) // Corrected to accept User entities

    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<User> // Corrected query to select from the "user" table
}