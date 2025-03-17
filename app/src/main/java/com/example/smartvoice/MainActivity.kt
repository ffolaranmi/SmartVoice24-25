package com.example.smartvoice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.smartvoice.ui.theme.SmartVoiceTheme
import com.example.smartvoice.ui.navigation.SmartVoiceNavHost
import com.example.smartvoice.ui.viewModel.UserSessionViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartVoiceTheme {
                SmartVoiceApp()
            }
        }
    }
}

