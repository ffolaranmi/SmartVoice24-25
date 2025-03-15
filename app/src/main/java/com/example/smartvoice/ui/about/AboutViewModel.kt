package com.example.smartvoice.ui.about

import androidx.lifecycle.ViewModel
import com.example.smartvoice.ui.navigation.NavigationDestination

class AboutViewModel(): ViewModel(){
    //val AboutUiState: StateFlow<HistoryUiState> =
}

/**
 * Ui State for AboutScreen
 */
data class AboutUiState(val buttonOptionsList: List<NavigationDestination> = listOf())