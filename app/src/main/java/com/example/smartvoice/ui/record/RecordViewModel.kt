package com.example.smartvoice.ui.record

import com.example.smartvoice.data.User
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartvoice.data.DiagnosisTable
import com.example.smartvoice.data.SmartVoiceDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.media.MediaRecorder
import android.os.Environment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class RecordViewModel(private val smartVoiceDatabase: SmartVoiceDatabase) : ViewModel() {
    private var mediaRecorder: MediaRecorder? = null
    private var outputFile: String = ""

    suspend fun startRecording() {
        val currentUser = getCurrentUser()
        if (currentUser != null) {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val fileName = "${currentUser.chinum}_$timeStamp.3gp"
            outputFile = File(Environment.getExternalStorageDirectory(), fileName).absolutePath

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

    suspend fun getCurrentUser(): User? {
        return withContext(Dispatchers.IO) {
            smartVoiceDatabase.userDao().getUser()
        }
    }
}