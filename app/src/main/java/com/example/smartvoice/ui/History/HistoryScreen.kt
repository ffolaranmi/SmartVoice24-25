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
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import com.example.smartvoice.ui.viewModel.UserSessionViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
object HistoryDestination : NavigationDestination {
    override val route = "History"
    override val titleRes = R.string.history
}

@Composable
fun HistoryScreen(
    userSessionViewModel: UserSessionViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateBack: () -> Unit
) {
    val userEmail = userSessionViewModel.email.collectAsState().value  // Fix error

    Scaffold(
        topBar = {
            SmartVoiceTopAppBar(
                title = "History",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        HistoryBody(
            nameOfUser = userEmail,  // Pass user email
            modifier = Modifier.padding(innerPadding)
        )
    }
}


@Composable
private fun HistoryBody(
    nameOfUser: String, // Receive the user's email
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column {
            VoiceSampleList.forEach {
                VoiceSampleCard(
                    nameOfUser = nameOfUser, // Use user's email instead of "Rory"
                    createdAt = it.createdAt,
                    classification = it.classification
                )
            }
        }
    }
}

@Composable
private fun VoiceSampleCard(
    nameOfUser: String,  // Add nameOfUser parameter
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
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "User: $nameOfUser",  // Display user name
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                text = "Voice Sample $createdAt",
                style = MaterialTheme.typography.h6
            )
            Text(
                text = classification.toString()
            )
        }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Results for $nameOfUser") },  // Include name in the dialog title
            text = {
                Text(
                    if (classification == Classification.PROCESSED)
                        "$nameOfUser, you are at low risk of having RRP."
                    else
                        "Your results are still being processed, please check back later."
                )
            },
            confirmButton = {
                Button(onClick = { showDialog.value = false }) {
                    Text("OK")
                }
            }
        )
    }
}



private data class VoiceSampleHeader(val nameOfUser: String, val createdAt: String, val classification: Classification)

// Temporary data entries
private val VoiceSampleList = mutableListOf(
    VoiceSampleHeader(nameOfUser = "Rory", createdAt = "2024-07-12 20:37:28", classification = Classification.PROCESSED),
    VoiceSampleHeader(nameOfUser = "Rory", createdAt = "2025-01-15 13:24:29", classification = Classification.PROCESSING),
    VoiceSampleHeader(nameOfUser = "Rory", createdAt = "2025-02-12 16:27:11", classification = Classification.PROCESSING),
)

// Add a new voice record
///fun addVoiceSample() {
///    VoiceSampleList.add(
///        VoiceSampleHeader(
 ///           createdAt = "2025-03-01 10:00:00",
 ///           classification = Classification.PROCESSED
 ///       )
///    )
///}
