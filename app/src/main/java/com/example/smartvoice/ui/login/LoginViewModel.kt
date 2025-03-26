package com.example.smartvoice.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartvoice.data.SmartVoiceDatabase
import kotlinx.coroutines.launch

class LoginViewModel(private val database: SmartVoiceDatabase) : ViewModel() {

    fun loginUser(email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = database.userDao().getUserByEmailAndPassword(email, password)
            onResult(user != null)  // Returns true if user exists, false otherwise
        }
    }
}