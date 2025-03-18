package com.example.smartvoice.ui.record

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartvoice.R
import com.example.smartvoice.SmartVoiceTopAppBar
import com.example.smartvoice.data.DiagnosisTable
import com.example.smartvoice.ui.History.HistoryDestination
import com.example.smartvoice.ui.navigation.NavigationDestination
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

object RecordDestination : NavigationDestination {
    override val route = "Record"
    override val titleRes = R.string.record
}

@Composable
fun RecordScreen(
    navigateToScreenOption: (NavigationDestination) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModelFactory: ViewModelProvider.Factory
) {
    val viewModel = viewModel<RecordViewModel>(factory = viewModelFactory)
    val context = LocalContext.current

    Scaffold(
        topBar = {
            SmartVoiceTopAppBar(
                title = stringResource(id = RecordDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
            )
        }
    ) { innerPadding ->
        RecordBody(
            onScreenOptionClick = navigateToScreenOption,
            modifier = modifier.padding(innerPadding),
            viewModel = viewModel
        )
    }
}

@Composable
private fun RecordBody(
    onScreenOptionClick: (NavigationDestination) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecordViewModel,
) {
    val coroutineScope = rememberCoroutineScope()
    var isRecording by remember { mutableStateOf(false) }
    var buttonColor by remember { mutableStateOf(Color.Blue) }

    fun startRecordingWithEffect() {
        coroutineScope.launch {
            isRecording = true
            buttonColor = Color.Red
            viewModel.startRecording()
            delay(3000)
            isRecording = false
            buttonColor = Color.Blue
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Press the record button and speak into the microphone",
            style = MaterialTheme.typography.h4,
            modifier = modifier.padding(16.dp)
        )

        Button(
            onClick = { startRecordingWithEffect() },
            modifier = Modifier
                .widthIn(min = 275.dp)
                .padding(16.dp)
                .background(buttonColor)
        ) {
            Text(
                text = stringResource(id = R.string.record),
                style = MaterialTheme.typography.h6,
                color = Color.White
            )
        }

        Button(
            onClick = {
                coroutineScope.launch {
                    val currentUser = viewModel.getCurrentUser()
                    val currentDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())

                    if (currentUser != null) {
                        val diagnosisTable = DiagnosisTable(
                            patientchi = currentUser.chinum,
                            patientName = currentUser.patientName,
                            diagnosis = "You have RRP",  // You can make this dynamic later
                            recordingDate = currentDateTime,
                            recordingLength = "00:03" // Optional placeholder
                        )

                        viewModel.insertDiagnosis(diagnosisTable)
                    } else {
                        // Optional: Show feedback that user data was not found
                    }
                }
            },
            modifier = modifier.widthIn(min = 275.dp)
        ) {
            Text(
                text = "Save voice sample",
                style = MaterialTheme.typography.h6,
            )
        }


        Button(
            onClick = { onScreenOptionClick(HistoryDestination) },
            modifier = modifier.widthIn(min = 275.dp),
        ) {
            Text(
                text = stringResource(id = HistoryDestination.titleRes),
                style = MaterialTheme.typography.h6,
            )
        }
    }
}
