package com.example.smartvoice.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {
    companion object {
        private val EMAIL_KEY = stringPreferencesKey("user_email")

    }

    suspend fun saveUserEmail(email: String) {
        context.dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = email
        }
    }

    fun getUserEmail(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[EMAIL_KEY]
        }
    }

    suspend fun clearUserEmail() {
        context.dataStore.edit { preferences ->
            preferences.remove(EMAIL_KEY)
        }
    }
}
