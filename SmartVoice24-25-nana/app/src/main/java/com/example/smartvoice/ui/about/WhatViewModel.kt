package com.example.smartvoice.ui.about

import androidx.lifecycle.ViewModel
import com.example.smartvoice.ui.navigation.NavigationDestination

class WhatViewModel : ViewModel(){
    //val historyUiState: StateFlow<HistoryUiState> =
}

/**
 * Ui State for HistoryScreen
 */
data class WhatUiState(val buttonOptionsList: List<NavigationDestination> = listOf())
