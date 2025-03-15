package com.example.smartvoice.ui.record

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartvoice.SmartVoiceApplication
import com.example.smartvoice.data.DiagnosisTable
import com.example.smartvoice.data.SmartVoiceDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.media.MediaRecorder
import android.os.Environment
import java.io.IOException

class RecordViewModel(private val smartVoiceDatabase: SmartVoiceDatabase) : ViewModel() {
    private var mediaRecorder: MediaRecorder? = null
    private val outputFile: String = "${Environment.getExternalStorageDirectory().absolutePath}/audio_record.3gp"

    fun startRecording() {
        try {
            mediaRecorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                setOutputFile(outputFile)
                setMaxDuration(3000) // 3 seconds
                prepare()
                start()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun stopRecording() {
        mediaRecorder?.apply {
            stop()
            release()
        }
    }

    fun insertDiagnosis(diagnosisTable: DiagnosisTable) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                smartVoiceDatabase.diagnosisDao().insertNewDiagnosis(diagnosisTable)
            }
        }
    }
}
