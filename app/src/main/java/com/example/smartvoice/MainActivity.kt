package com.example.smartvoice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.smartvoice.ui.theme.SmartVoiceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val application = application as SmartVoiceApplication
        val database = application.smartVoiceDatabase // ✅ Get database instance

        setContent {
            SmartVoiceTheme {
                SmartVoiceApp(application = application, database = database) // ✅ Ensure database is passed
            }
        }
    }
}