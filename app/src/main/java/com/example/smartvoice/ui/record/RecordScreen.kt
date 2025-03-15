package com.example.smartvoice.ui.record

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartvoice.R
import com.example.smartvoice.SmartVoiceTopAppBar
import com.example.smartvoice.ui.AppViewModelProvider
import com.example.smartvoice.ui.History.HistoryDestination
import com.example.smartvoice.ui.navigation.NavigationDestination
import androidx.compose.foundation.background
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartvoice.SmartVoiceTopAppBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartvoice.SmartVoiceTopAppBar
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartvoice.SmartVoiceTopAppBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.remember
//import androidx.activity.compose.rememberPermissionState
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.smartvoice.data.DiagnosisDao
import com.example.smartvoice.data.DiagnosisTable
import java.io.File
import java.io.FileWriter
import java.util.Date

// Request code for external storage permission
const val PERMISSION_REQUEST_CODE = 100

object RecordDestination : NavigationDestination {
    override val route = "Record"
    override val titleRes = R.string.record
}

@Composable
fun RecordScreen(
    navigateToScreenOption: (NavigationDestination) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModelFactory: ViewModelProvider.Factory, // Pass ViewModelFactory explicitly
    /*navigateToScreenOption = navigateToScreenOption,
    navigateBack = navigateBack,
    modifier = modifier,
    viewModelFactory = viewModelFactory,
    diagnosisDao = diagnosisDao // Pass diagnosisDao explicitly */
){
    val viewModel = viewModel<RecordViewModel>(factory = viewModelFactory) // Use the provided factory
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted, start recording
                viewModel.startRecording()
            } else {
                // Handle denied permission
            }
        }

    val permissionState by rememberUpdatedState(newValue = requestPermissionLauncher)

    Scaffold(
        topBar = {
            SmartVoiceTopAppBar(
                title = stringResource(id = RecordDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
            )
        }
    ) { innerpadding ->
        RecordBody(
            onScreenOptionClick = navigateToScreenOption,
            modifier = modifier.padding(innerpadding),
            viewModel = viewModel,
            //context = context, // Pass the context down to RecordBody
            //permissionState = permissionState
        )
    }
}

@Composable
private fun RecordBody(
    onScreenOptionClick: (NavigationDestination) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecordViewModel,
    //context: Context, // Add this parameter
    //permissionState: PermissionState
) {
    var isClicked by remember { mutableStateOf(false) }
    var originalColor by remember { mutableStateOf(Color.Blue) }
    var currentColor by remember { mutableStateOf(originalColor) }

    val coroutineScope = rememberCoroutineScope()

    fun changeColorForThreeSeconds() {
        coroutineScope.launch {
            currentColor = Color.Red
            delay(3000)
            currentColor = originalColor
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
            onClick = {
                isClicked = !isClicked
                currentColor = if (isClicked) Color.Red else originalColor
                changeColorForThreeSeconds()
            },
            modifier = Modifier
                .widthIn(min = 275.dp)
                .padding(16.dp)
                .background(currentColor)
        ) {
            Text(
                text = stringResource(id = R.string.record),
                style = MaterialTheme.typography.h6,
                color = Color.White
            )
        }

        Button(
            onClick = {
                    val patientName: String = "Patient McPatientFace"
                    val patientChi: String = "1234567891"
                    val diagnosis: String = "You have RRP"
                    val recordingDate: String = "2022-12-13" // Use current date
                    val recordingLength: String = "01:12"

                    // Insert values into the database
                    val diagnosisTable = DiagnosisTable(
                        patientchi = patientChi,
                        patientName = patientName,
                        diagnosis = diagnosis,
                        recordingDate = recordingDate,
                        recordingLength = recordingLength
                    )
                    viewModel.insertDiagnosis(diagnosisTable) // Call ViewModel function to insert into database
                },
                modifier = modifier.widthIn(min = 275.dp),
                enabled = true,
                )  {
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