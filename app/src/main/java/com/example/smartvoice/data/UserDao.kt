package com.example.smartvoice.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) // ✅ Prevents duplicate users by replacing conflicts
    suspend fun insert(entity: User)

    @Query("SELECT * FROM user WHERE email = :email AND password = :password LIMIT 1")
    suspend fun getUserByEmailAndPassword(email: String, password: String): User?  // ✅ Allows login validation

    @Query("SELECT COUNT(*) FROM user WHERE email = :email")
    suspend fun checkIfEmailExists(email: String): Int  // ✅ Checks if an email is already registered
}