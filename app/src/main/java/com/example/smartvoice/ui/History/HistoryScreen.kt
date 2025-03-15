package com.example.smartvoice.ui.History


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartvoice.R
import com.example.smartvoice.SmartVoiceTopAppBar
import com.example.smartvoice.data.Classification
import com.example.smartvoice.ui.AppViewModelProvider
import com.example.smartvoice.ui.navigation.NavigationDestination
import com.example.smartvoice.data.DiagnosisTable


object HistoryDestination : NavigationDestination {
    override val route = "History"
    override val titleRes = R.string.history
}

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModelFactory: ViewModelProvider.Factory, // Provide ViewModel factory
    navigateBack: () -> Unit,
){
    val viewModel: HistoryViewModel = viewModel(factory = viewModelFactory) // Use provided factory

    val diagnosesState = remember { mutableStateOf<List<DiagnosisTable>>(emptyList()) }

    LaunchedEffect(key1 = Unit) {
        val diagnoses = viewModel.getAllDiagnoses()
        diagnosesState.value = diagnoses
    }

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
            modifier = modifier.padding(innerpadding),
            diagnoses = diagnosesState.value
        )
    }
}

@Composable
private fun HistoryBody(
    modifier: Modifier = Modifier,
    diagnoses: List<DiagnosisTable>
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(), // Fill the available space
        contentPadding = PaddingValues(16.dp) // Padding around the content
    ) {
        items(diagnoses) { diagnosis ->
            VoiceSampleCard(
                createdAt = diagnosis.recordingDate,
                patientName = diagnosis.patientName,
                classification = diagnosis.diagnosis
            )
        }
    }
}


@Composable
private fun VoiceSampleCard(
     createdAt: String,
     patientName: String,
     classification: String
){
    Card(modifier = Modifier.padding(8.dp), elevation = 4.dp) {
        Column {
            Text(
                text = "Voice Sample " + createdAt,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.h6
            )
            Text(
                text = "Name: " + patientName,
                modifier = Modifier.padding(16.dp),
                )
            Text(text = classification,
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}

private data class VoiceSampleHeader(val createdAt: String, val patientName: String, val classification: String)

//// Temporary data entries
////we could loop through CSV file or database here instead to retrieve the values
////i will write pseudocode for this and figure it out
//private val VoiceSampleList = listOf(
//    VoiceSampleHeader(createdAt = "yyyy-MM-dd kk:mm:ss", classification = "You Have RRP"),
//    VoiceSampleHeader(createdAt = "yyyy-MM-dd kk:mm:ss", classification = "You Have RRP"),
//    VoiceSampleHeader(createdAt = "2024-12-23 00:00:02", classification = "You Have RRP"),
//)
