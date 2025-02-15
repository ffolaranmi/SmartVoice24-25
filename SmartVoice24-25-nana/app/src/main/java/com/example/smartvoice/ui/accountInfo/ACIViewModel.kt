package com.example.smartvoice.ui.accountInfo

import androidx.lifecycle.ViewModel
import com.example.smartvoice.ui.navigation.NavigationDestination
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class ACIViewModel : ViewModel(){
    //val historyUiState: StateFlow<HistoryUiState> =

    private val _accountInfo = MutableLiveData<AccountInfo>()
    val accountInfo: LiveData<AccountInfo> = _accountInfo

    init {
        loadAccountInfo()
    }

    fun loadAccountInfo() {
        // Load the account information from the repository or API
        // For example:
        val info = AccountInfo("username", "email@example.com", "1234567890", "123 Street, City, Country")
        _accountInfo.value = info
    }
}

/**
 * Ui State for HistoryScreen
 */
data class ACIUiState(val buttonOptionsList: List<NavigationDestination> = listOf())