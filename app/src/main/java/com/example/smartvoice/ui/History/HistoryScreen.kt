package com.example.smartvoice.ui.History


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartvoice.R
import com.example.smartvoice.SmartVoiceTopAppBar
import com.example.smartvoice.data.Classification
import com.example.smartvoice.ui.AppViewModelProvider
import com.example.smartvoice.ui.navigation.NavigationDestination
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.clickable
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.AlertDialog


object HistoryDestination : NavigationDestination {
    override val route = "History"
    override val titleRes = R.string.history
}

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateBack: () -> Unit,
){
    //val historyUiState by viewModel.historyUiState.collectAsState()
    Scaffold(
        topBar = {
            SmartVoiceTopAppBar(
                title = stringResource(id = HistoryDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
            )
        }
    ) { innerpadding ->
        HistoryBody(
            modifier = modifier.padding(innerpadding)
        )
    }
}

@Composable
private fun HistoryBody(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column {
            VoiceSampleList.forEach {
                VoiceSampleCard(createdAt = it.createdAt, classification = it.classification)
            }
        }
    }
}

@Composable
private fun VoiceSampleCard(
    createdAt: String,
    classification: Classification
) {
    var showDialog = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { showDialog.value = true },
        elevation = 4.dp
    ) {
        Column {
            Text(
                text = "Voice Sample $createdAt",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.h6
            )
            Text(
                text = classification.toString(),
                modifier = Modifier.padding(16.dp)
            )
        }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Results") },
            text = {
                Text(
                    if (classification == Classification.PROCESSED)
                        "You are at low risk of having RRP."
                    else
                        "Your results are still being processed, please check back at a later date."
                ) },
            confirmButton = {
                Button(onClick = { showDialog.value = false }) {
                    Text("OK")
                }
            }
        )
    }
}


private data class VoiceSampleHeader(val createdAt: String, val classification: Classification)

// Temporary data entries
private val VoiceSampleList = mutableListOf(
    VoiceSampleHeader(createdAt = "2024-07-12 20:37:28", classification = Classification.PROCESSED),
    VoiceSampleHeader(createdAt = "2025-01-15 13:24:29", classification = Classification.PROCESSING),
    VoiceSampleHeader(createdAt = "2025-02-12 16:27:11", classification = Classification.PROCESSING),
)

// Add a new voice record
fun addVoiceSample() {
    VoiceSampleList.add(
        VoiceSampleHeader(
            createdAt = "2025-03-01 10:00:00",
            classification = Classification.PROCESSED
        )
    )
}
