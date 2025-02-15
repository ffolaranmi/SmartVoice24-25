package com.example.smartvoice.ui.History

import androidx.lifecycle.ViewModel
import com.example.smartvoice.ui.navigation.NavigationDestination

class HistoryViewModel : ViewModel(){
    //val historyUiState: StateFlow<HistoryUiState> =
}

/**
 * Ui State for HistoryScreen
 */
data class HistoryUiState(val buttonOptionsList: List<NavigationDestination> = listOf())