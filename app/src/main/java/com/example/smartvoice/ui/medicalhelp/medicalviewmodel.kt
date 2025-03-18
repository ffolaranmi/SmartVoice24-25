package com.example.smartvoice.ui.medicalhelp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FindMedicalHelpViewModel : ViewModel() {
    private val _infoMessages = MutableStateFlow(
        listOf(
            Pair("If it's an emergency, call ", "999"),
            Pair("If it's a non-emergency, call ", "111"),
            Pair("For more medical information, visit:", " https://www.scot.nhs.uk/")
        )
    )

    val infoMessages: StateFlow<List<Pair<String, String>>> = _infoMessages
}
