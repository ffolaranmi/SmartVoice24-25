package com.example.smartvoice.ui.record

import android.Manifest
import android.media.MediaRecorder
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.clipRect
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
import java.io.File

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

    var isRecording by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0f) }

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
    )

    val coroutineScope = rememberCoroutineScope()

    // Request microphone permission
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                viewModel.startRecording()
            } else {
                Toast.makeText(context, "Microphone permission is required", Toast.LENGTH_SHORT).show()
            }
        }
    )

    LaunchedEffect(isRecording) {
        if (isRecording) {
            for (i in 0..100) {
                progress = i / 100f
                delay(100)
            }
        } else {
            progress = 0f
        }
    }

    Scaffold(
        topBar = {
            SmartVoiceTopAppBar(
                title = stringResource(id = RecordDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CupWithWater(animatedProgress)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (!isRecording) {
                        permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                    } else {
                        viewModel.stopRecording()
                    }
                    isRecording = !isRecording
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(if (isRecording) "Stop Recording" else "Start Recording")
            }

            Button(
                onClick = {
                    val diagnosisTable = DiagnosisTable(
                        patientchi = "1234567891",
                        patientName = "Patient McPatientFace",
                        diagnosis = "You have RRP",
                        recordingDate = "2022-12-13",
                        recordingLength = "01:12"
                    )
                    viewModel.insertDiagnosis(diagnosisTable)
                },
                modifier = Modifier.widthIn(min = 275.dp)
            ) {
                Text("Save voice sample")
            }

            Button(
                onClick = { navigateToScreenOption(HistoryDestination) },
                modifier = Modifier.widthIn(min = 275.dp)
            ) {
                Text(stringResource(id = HistoryDestination.titleRes))
            }
        }
    }
}

@Composable
fun CupWithWater(progress: Float) {
    Canvas(
        modifier = Modifier
            .size(150.dp, 200.dp)
            .padding(16.dp)
    ) {
        val width = size.width
        val height = size.height

        drawRoundRect(
            color = Color.Gray,
            topLeft = Offset(0f, 0f),
            size = size,
            cornerRadius = CornerRadius(20f, 20f),
            style = androidx.compose.ui.graphics.drawscope.Stroke(
                width = 4.dp.toPx(),
                pathEffect = PathEffect.cornerPathEffect(10f)
            )
        )

        val waterHeight = height * (1 - progress)

        clipRect(left = 0f, top = waterHeight, right = width, bottom = height) {
            drawRoundRect(
                color = Color.Blue.copy(alpha = 0.7f),
                topLeft = Offset(0f, waterHeight),
                size = size.copy(height = height - waterHeight),
                cornerRadius = CornerRadius(20f, 20f)
            )
        }
    }
}