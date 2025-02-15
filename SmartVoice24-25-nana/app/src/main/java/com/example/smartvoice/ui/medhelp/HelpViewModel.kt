package com.example.smartvoice.ui.medhelp



import androidx.lifecycle.ViewModel
import com.example.smartvoice.ui.navigation.NavigationDestination
import kotlinx.coroutines.flow.StateFlow

class HelpViewModel : ViewModel(){
    //val historyUiState: StateFlow<HistoryUiState> =
}

/**
 * Ui State for HistoryScreen
 */
data class HelpUiState(val buttonOptionsList: List<NavigationDestination> = listOf())