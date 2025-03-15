package com.example.smartvoice.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.smartvoice.SmartVoiceApplication
import com.example.smartvoice.ui.History.HistoryViewModel
import com.example.smartvoice.ui.home.HomeViewModel
import com.example.smartvoice.ui.login.LoginViewModel
import com.example.smartvoice.ui.record.RecordViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.smartvoice.data.DiagnosisDao
/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
        fun Factory(application: SmartVoiceApplication) = viewModelFactory {
            // Initialize HomeViewModel
            initializer<HomeViewModel> { HomeViewModel() }

            // Initialize HistoryViewModel
            initializer<HistoryViewModel> { HistoryViewModel(application.smartVoiceDatabase) }

            // Initialize RecordViewModel with smartVoiceDatabase
            initializer<RecordViewModel> { RecordViewModel(application.smartVoiceDatabase) }

            // Initialize LoginViewModel
            initializer<LoginViewModel> { LoginViewModel() }
        }
    }


/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.smartVoiceApplication(): SmartVoiceApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SmartVoiceApplication)