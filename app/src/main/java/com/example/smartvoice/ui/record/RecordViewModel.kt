package com.example.smartvoice.ui.record

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecordViewModel : ViewModel() {

    // State for the timer text
    private val _timerText = MutableStateFlow("00:00:00")
    val timerText: StateFlow<String> = _timerText

    // State for the recording status
    private val _isRecording = MutableStateFlow(false)
    val isRecording: StateFlow<Boolean> = _isRecording

    // Function to start recording
    fun startRecording() {
        viewModelScope.launch {
            _isRecording.value = true
            // Add your recording logic here
        }
    }

    // Function to stop recording
    fun stopRecording() {
        viewModelScope.launch {
            _isRecording.value = false
            // Add your logic to stop recording here
        }
    }

    // Function to update timer text
    fun updateTimerText(newText: String) {
        _timerText.value = newText
    }
}