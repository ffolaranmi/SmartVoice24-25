package com.example.smartvoice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.smartvoice.SmartVoiceApplication
import com.example.smartvoice.data.DiagnosisDao
import com.example.smartvoice.data.User
import com.example.smartvoice.data.UserDao
import com.example.smartvoice.ui.theme.SmartVoiceTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    lateinit var userDao: UserDao
    lateinit var diagnosisDao: DiagnosisDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize DAOs
        userDao = (application as SmartVoiceApplication).smartVoiceDatabase.userDao()
        diagnosisDao = (application as SmartVoiceApplication).smartVoiceDatabase.diagnosisDao()

        // Perform database operations as needed

        // Initialize the Room database
        setContent {
            // Content setup
            SmartVoiceTheme {
                SmartVoiceApp()
            }
        }
    }
}
