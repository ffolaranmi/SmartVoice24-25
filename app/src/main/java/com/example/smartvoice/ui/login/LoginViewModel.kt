package com.example.smartvoice.ui.login

import androidx.lifecycle.ViewModel
import com.example.smartvoice.ui.navigation.NavigationDestination

class LoginViewModel : ViewModel(){
    //val loginUiState: StateFlow<LoginUiState> =
}

/**
 * Ui State for LoginScreen
 */
data class LoginUiState(val buttonOptionsList: List<NavigationDestination> = listOf())