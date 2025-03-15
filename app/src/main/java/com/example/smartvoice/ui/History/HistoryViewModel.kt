package com.example.smartvoice.ui.History

import androidx.lifecycle.ViewModel
import com.example.smartvoice.data.DiagnosisTable
import com.example.smartvoice.data.SmartVoiceDatabase
import com.example.smartvoice.ui.navigation.NavigationDestination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

class HistoryViewModel(private val smartVoiceDatabase: SmartVoiceDatabase): ViewModel(){
    //val historyUiState: StateFlow<HistoryUiState> =
    suspend fun getAllDiagnoses(): List<DiagnosisTable> {
        return withContext(Dispatchers.IO) {
            smartVoiceDatabase.diagnosisDao().getAllEntities()
        }
    }
}

/***
 * Ui State for HistoryScreen yo
 */
data class HistoryUiState(val buttonOptionsList: List<NavigationDestination> = listOf())