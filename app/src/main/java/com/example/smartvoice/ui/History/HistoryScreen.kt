package com.example.smartvoice.ui.History

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartvoice.R
import com.example.smartvoice.SmartVoiceTopAppBar
import com.example.smartvoice.data.DiagnosisTable
import com.example.smartvoice.ui.navigation.NavigationDestination

object HistoryDestination : NavigationDestination {
    override val route = "History"
    override val titleRes = R.string.history
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModelFactory: ViewModelProvider.Factory,
    navigateBack: () -> Unit,
) {
    val viewModel: HistoryViewModel = viewModel(factory = viewModelFactory)
    val diagnoses by viewModel.diagnoses.collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        viewModel.loadDiagnoses()
    }

    Scaffold(
        topBar = {
            SmartVoiceTopAppBar(
                title = stringResource(id = HistoryDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.clearAllDiagnoses() },
                containerColor = Color(0xFFB71C1C), // Deep red
                modifier = Modifier
                    .height(56.dp)
                    .width(180.dp)
                    .padding(8.dp)
                    .clip(MaterialTheme.shapes.extraLarge) // Large rounded corners
            ) {
                Text(
                    text = "Clear All",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

        }
    ) { innerPadding ->
        if (diagnoses.isEmpty()) {
            Text(
                text = "No voice samples currently available.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                color = MaterialTheme.colorScheme.onSurface
            )

        } else {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(diagnoses.reversed()) { diagnosis ->

                VoiceSampleBubble(
                        recordingDate = diagnosis.recordingDate,
                        patientName = diagnosis.patientName,
                        diagnosis = diagnosis.diagnosis
                    )
                }
            }
        }
    }
}

@Composable
fun VoiceSampleBubble(
    recordingDate: String,
    patientName: String,
    diagnosis: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEDE7F6)) // This is a perfect soft lilac
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "VOICE SAMPLE DATE: $recordingDate",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color(0xFF512DA8) // Deep purple text
            )
            Text(
                text = "PATIENT NAME: $patientName",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color(0xFF512DA8),
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = diagnosis,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}



