package com.example.smartvoice.ui.home

import androidx.lifecycle.ViewModel
import com.example.smartvoice.ui.navigation.NavigationDestination
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel(){
    //val homeUiState: StateFlow<HomeUiState> =
}

/**
 * Ui State for HomeScreen
 */
data class HomeUiState(val buttonOptionsList: List<NavigationDestination> = listOf())