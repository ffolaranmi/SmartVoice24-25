package com.example.smartvoice.ui.record

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartvoice.R
import com.example.smartvoice.SmartVoiceTopAppBar
import com.example.smartvoice.ui.History.HistoryDestination
import com.example.smartvoice.ui.navigation.NavigationDestination
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

fun generateRandomPercentage(): Int {
    return Random.nextInt(0, 101)
}

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
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var isRecording by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0f) }
    var hasRecorded by remember { mutableStateOf(false) }

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 3000, easing = LinearEasing),
        label = "progressAnim"
    )

    LaunchedEffect(isRecording) {
        if (isRecording) {
            viewModel.startRecording()
            for (i in 0..100) {
                progress = i / 100f
                delay(30)
            }
            viewModel.stopRecording()
            isRecording = false
            hasRecorded = true

            coroutineScope.launch {
                val currentUser = viewModel.getCurrentUser()
                val currentDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
                val randomPercentage = generateRandomPercentage()
                val diagnosisTable = com.example.smartvoice.data.DiagnosisTable(
                    patientchi = currentUser?.chinum ?: "Unknown",
                    patientName = currentUser?.patientName ?: "Unknown",
                    diagnosis = "$randomPercentage",
                    recordingDate = currentDateTime,
                    recordingLength = "00:03"
                )
                viewModel.insertDiagnosis(diagnosisTable)
                Toast.makeText(context, "Recording saved successfully", Toast.LENGTH_SHORT).show()
            }
        } else {
            progress = 0f
        }
    }

    Scaffold(
        topBar = {
            SmartVoiceTopAppBar(
                title = "Record",
                canNavigateBack = true,
                navigateUp = navigateBack,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Press the record button and speak into the microphone",
                style = MaterialTheme.typography.h4,
                modifier = modifier.padding(16.dp)
            )

            CupWithWater(progress = animatedProgress)

            Button(
                onClick = {
                    if (!isRecording) {
                        coroutineScope.launch {
                            isRecording = true
                        }
                    }
                },
                modifier = Modifier
                    .widthIn(min = 275.dp)
                    .padding(16.dp)
            ) {
                Text(
                    text = if (isRecording) "Recording..." else "Start Recording",
                    style = MaterialTheme.typography.h6,
                    color = Color.White
                )
            }

            Button(
                onClick = { navigateToScreenOption(HistoryDestination) },
                modifier = modifier.widthIn(min = 275.dp),
            ) {
                Text(
                    text = "History",
                    style = MaterialTheme.typography.h6,
                )
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
            topLeft = Offset.Zero,
            size = size,
            cornerRadius = CornerRadius(20f, 20f),
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 5f)
        )

        val waterHeight = height * progress

        clipRect(left = 0f, top = height - waterHeight, right = width, bottom = height) {
            drawRoundRect(
                color = Color.Blue.copy(alpha = 0.7f),
                topLeft = Offset(0f, height - waterHeight),
                size = size.copy(height = waterHeight),
                cornerRadius = CornerRadius(20f, 20f)
            )
        }
    }
}
