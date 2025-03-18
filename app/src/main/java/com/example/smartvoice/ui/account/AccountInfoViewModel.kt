package com.example.smartvoice.ui.account

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartvoice.data.SmartVoiceDatabase
import com.example.smartvoice.data.User
import com.example.smartvoice.data.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class AccountInfoViewModel(
    private val database: SmartVoiceDatabase,
    private val context: Context
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> get() = _user

    init {
        loadUserInfo()
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            val userPrefs = UserPreferences(context)
            val savedEmail = userPrefs.getUserEmail().firstOrNull()
            savedEmail?.let { email ->
                val userFromDb = database.userDao().getUserByEmail(email)
                _user.value = userFromDb
            }
        }
    }

    suspend fun resetPassword(email: String, currentPassword: String, newPassword: String): Boolean {
        return try {
            val user = database.userDao().getUserByEmailAndPassword(email, currentPassword)
            if (user != null) {
                val updatedUser = user.copy(password = newPassword)
                database.userDao().insert(updatedUser)
                Log.d("AccountInfoViewModel", "Password updated for user: ${user.email}")
                true
            } else {
                Log.e("AccountInfoViewModel", "Incorrect current password or user not found.")
                false
            }
        } catch (e: Exception) {
            Log.e("AccountInfoViewModel", "Error resetting password", e)
            false
        }
    }
}
