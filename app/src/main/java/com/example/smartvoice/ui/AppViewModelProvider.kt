package com.example.smartvoice.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.smartvoice.SmartVoiceApplication
import com.example.smartvoice.ui.History.HistoryViewModel
import com.example.smartvoice.ui.about.WhatViewModel
import com.example.smartvoice.ui.accountInfo.ACIViewModel
import com.example.smartvoice.ui.home.HomeViewModel
import com.example.smartvoice.ui.login.LoginViewModel
import com.example.smartvoice.ui.medhelp.HelpViewModel


/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for HomeViewModel
        initializer {
            HomeViewModel()
        }
        // Initializer for HistoryViewModel
        initializer {
            HistoryViewModel()
        }
        // Initializer for RecordViewModel

        // Initializer for LoginViewModel
        initializer {
            LoginViewModel()
        }
        initializer {
            HelpViewModel()
        }
        initializer {
            WhatViewModel()
        }
        initializer {
            ACIViewModel()
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.smartVoiceApplication(): SmartVoiceApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SmartVoiceApplication)