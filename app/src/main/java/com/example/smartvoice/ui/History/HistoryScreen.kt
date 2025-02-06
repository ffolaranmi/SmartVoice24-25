package com.example.smartvoice.ui.History


import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
){
    Card(modifier = Modifier.padding(8.dp), elevation = 4.dp) {
        Column {
            Text(
                text = "Voice Sample " + createdAt,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.h6
            )
            Text(text = classification.toString(),
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}

private data class VoiceSampleHeader(val createdAt: String, val classification: Classification)

// Temporary data entries
private val VoiceSampleList = listOf(
    VoiceSampleHeader(createdAt = "yyyy-MM-dd kk:mm:ss", classification = Classification.PROCESSING),
    VoiceSampleHeader(createdAt = "yyyy-MM-dd kk:mm:ss", classification = Classification.PROCESSING),
    VoiceSampleHeader(createdAt = "yyyy-MM-dd kk:mm:ss", classification = Classification.PROCESSING),
)
