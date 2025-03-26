package com.example.smartvoice.ui.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartvoice.data.SmartVoiceDatabase
import com.example.smartvoice.data.User
import kotlinx.coroutines.launch

class RegisterViewModel(private val database: SmartVoiceDatabase) : ViewModel() {

    fun registerUser(
        chinum: String,
        patientName: String,
        age: Int,
        currentStatus: String,
        email: String,
        password: String,
        onResult: (Boolean) -> Unit // Callback to notify success/failure
    ) {
        if (chinum.isBlank() || patientName.isBlank() || currentStatus.isBlank() || email.isBlank() || password.isBlank()) {
            Log.e("RegisterViewModel", "Error: One or more fields are empty.")
            onResult(false) // Fail if any field is blank
            return
        }

        if (age <= 0) {
            Log.e("RegisterViewModel", "Error: Age must be a positive number.")
            onResult(false) // Fail if age is not valid
            return
        }

        viewModelScope.launch {
            try {
                val newUser = User(
                    chinum = chinum,
                    patientName = patientName,
                    age = age,
                    currentstatus = currentStatus,
                    email = email,
                    password = password
                )

                database.userDao().insert(newUser) // Insert into Room Database
                Log.d("RegisterViewModel", "User registered successfully: $newUser")
                onResult(true) // Success

            } catch (e: Exception) {
                Log.e("RegisterViewModel", "Error registering user", e)
                onResult(false) // Fail on exception
            }
        }
    }
}