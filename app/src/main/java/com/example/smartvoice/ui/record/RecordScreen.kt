package com.example.recordscreen
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import android.os.Handler
import android.util.Log //
import android.os.Looper
import androidx.compose.foundation.Canvas
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.smartvoice.R
import com.example.smartvoice.SmartVoiceTopAppBar
import com.example.smartvoice.ui.History.HistoryDestination
import com.example.smartvoice.ui.navigation.NavigationDestination
import java.util.Locale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.smartvoice.ui.record.Timer
import com.example.smartvoice.ui.record.WaveformView
import androidx.compose.ui.graphics.Path
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.coroutines.NonDisposableHandle.parent

private fun checkPermissions(activity: Activity, permissions: Array<String>, requestCode: Int) {
    val microphonePermissionState = ActivityCompat.checkSelfPermission(
        activity,
        permissions[0]
    ) == PackageManager.PERMISSION_GRANTED

    if (!microphonePermissionState) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode)
    }
}

object RecordDestination : NavigationDestination {
    override val route = "record"
    override val titleRes = R.string.record
}

@Composable
fun RecordScreen(
    navigateToScreenOption: (NavigationDestination) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
){
    Scaffold(
        topBar = {
            SmartVoiceTopAppBar(
                title = stringResource(id = RecordDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
            )
        }
    ) { innerpadding ->
        Box(
            modifier = modifier.padding(innerpadding)
        ) {
            RecordBody(
                navigateToScreenOption = navigateToScreenOption,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
@Composable
fun GlassWaterProgressBar(
    progress: Float,
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val strokeWidth = 4f
        val glassColor = Color.Gray
        val waterColor = color
        val bottomWidthRatio = 0.7f // Ratio of bottom width to top width

        // Draw the glass
        val path = Path()
        path.moveTo(size.width * (1 - bottomWidthRatio) / 2, size.height)
        path.lineTo(size.width * (1 + bottomWidthRatio) / 2, size.height)
        path.lineTo(size.width, 0f)
        path.lineTo(0f, 0f)
        path.close()
        drawPath(path, glassColor, style = Stroke(width = strokeWidth))

        // Draw the water
        val waterHeight = size.height * progress
        val waterTop = size.height - waterHeight
        val waterBottomWidth = size.width * (1 - progress * (1 - bottomWidthRatio))
        val waterPath = Path()
        waterPath.moveTo((size.width - waterBottomWidth) / 2, size.height)
        waterPath.lineTo((size.width + waterBottomWidth) / 2, size.height)
        waterPath.lineTo(size.width, waterTop)
        waterPath.lineTo(0f, waterTop)
        waterPath.close()
        drawPath(waterPath, waterColor)
    }
}
@Composable
private fun RecordBody(
    navigateToScreenOption: (NavigationDestination) -> Unit,
    modifier: Modifier = Modifier
){
    val context = LocalContext.current
    val activity = context as Activity
    val permissions = arrayOf(Manifest.permission.RECORD_AUDIO)
    val REQUEST_CODE = 200
    val handler = remember { Handler(Looper.getMainLooper()) }
    var isRecorded by remember { mutableStateOf(false) }
    checkPermissions(activity, permissions, REQUEST_CODE)
    var showMessage by remember { mutableStateOf(false)}
        var isRecording by remember { mutableStateOf(false) }
    var isSaving by remember { mutableStateOf(false) }
    val mediaRecorder = remember { MediaRecorder() }
    var progress by remember { mutableStateOf(0f) }
    val waveformView = remember { WaveformView(context, null) }
  val outputDirectory = remember { File(context.getExternalFilesDir(null)?.absolutePath ?: "", "recordings") }
    if (!outputDirectory.exists()) {
        outputDirectory.mkdirs()
    }
    var showProgressBar by remember { mutableStateOf(false) }
    val dateFormat = remember { SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()) }
    val outputFile = remember { File(outputDirectory, "recording_${dateFormat.format(Date())}.mp4") }
    var elapsedTime by remember { mutableStateOf("00:00.00") } // Initialize with default value
    val timer = remember {
        Timer(object : Timer.OnTimerTickListener {
            override fun onTimerTick(duration: String) {
                elapsedTime = duration
            }
        })
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(25.dp),
        contentAlignment = Alignment.Center
    ) {
        // Add this Box to create the progress bar effect
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top=250.dp)
                    .fillMaxHeight()// Increase the height by 2dp
                    .align(Alignment.BottomStart)
                     // Rotate the box
                    .background(Color.White),

                // Set the background to black
            ) {
                if (showProgressBar) {
                    GlassWaterProgressBar(
                        progress = progress,
                        color = Color(0xFFADD8E6), // Set the color of the water to a lighter blue
                        modifier = Modifier
                            .width(120.dp) // Set a specific width to make the progress bar shorter
                            .height(100.dp) // Make the progress bar as thick as the width of the screen
                            .align(Alignment.Center) // Center the progress bar
                    )
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display the elapsed time with increased font size
            Text(text = elapsedTime, fontSize = 60.sp)

            Spacer(modifier = Modifier.weight(1f))

            if (showMessage) {
                Text(text = "Say AAA until the glass is full", fontSize = 24.sp)
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 80.dp)
            ) {

                Button(
                    onClick = {
                        if (isRecorded) {
                            mediaRecorder.reset()
                            if (outputFile.exists()) {
                                outputFile.delete()
                            }
                            Toast.makeText(context, "Recording deleted", Toast.LENGTH_SHORT).show()
                            isRecorded = false
                            isSaving = false
                            timer.stop() // Reset the timer
                            elapsedTime = "00:00.00" // Reset the displayed time
                            waveformView.clear() // Clear the waveform
                            progress = 0f // Reset the progress bar
                            showMessage = false
                            showProgressBar = false // Hide the message when the delete button is pressed
                        }
                    },
                    shape = CircleShape,
                    modifier = Modifier.size(60.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
                ) {
                    Icon(Icons.Filled.Close, contentDescription = "Delete Recording")
                }

                Spacer(modifier = Modifier.width(45.dp))

                Button(
                    onClick = {
                        if (isRecording) {
                            Log.d("RecordButton", "isRecording is true")
                            try {
                                mediaRecorder.stop()
                                showMessage = false
                                timer.stop()
                                isRecording = false
                                isSaving = true
                                isRecorded = true
                            } catch (e: IllegalStateException) {
                                e.printStackTrace()
                                mediaRecorder.reset() // Reset the MediaRecorder
                                isRecording = false
                            }
                            showProgressBar = false // Hide the progress bar when the recording is done
                        } else {
                            showMessage = true
                            showProgressBar = true
                            Log.d("RecordButton", "isRecording is false")
                            if (!isRecorded) {
                                Log.d("RecordButton", "isRecorded is false")
                                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
                                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                                mediaRecorder.setOutputFile(outputFile.absolutePath)
                                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

                                try {
                                    mediaRecorder.prepare()
                                } catch (e: IOException) {
                                    e.printStackTrace()
                                }
                                try {
                                    mediaRecorder.start()
                                    timer.start()
                                    isRecording = true

                                    // Start a new thread to read the amplitude from the MediaRecorder
                                    Thread {
                                        val recordingStartTime = System.currentTimeMillis()
                                        while (isRecording) {
                                            val amplitude = mediaRecorder.maxAmplitude.toFloat() * 10 // Multiply the amplitude by a scaling factor
                                            waveformView.addAmplitude(amplitude)
                                            val elapsedTimeMillis = System.currentTimeMillis() - recordingStartTime
                                            progress = elapsedTimeMillis / 5300f // Update the progress
                                            Thread.sleep(100)
                                        }
                                    }.start()
                                    handler.postDelayed({
                                        if (isRecording) {
                                            mediaRecorder.stop()
                                            timer.stop()
                                            isRecording = false
                                            isSaving = true
                                            isRecorded = true
                                        }
                                    }, 5300) // Delay in milliseconds
                                } catch (e: IllegalStateException) {
                                    e.printStackTrace()
                                    isRecording = false
                                }
                            }
                        }
                    },
                    shape = CircleShape,
                    modifier = Modifier.size(80.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = if (isRecording) Color(0xFF800080) else Color.Red)
                ){
                    if (isRecording) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(color = Color.White)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(45.dp))

                Button(
                    onClick = {
                        if (isSaving) {
                            if (!outputFile.exists()) {
                                Toast.makeText(context, "No recording to save", Toast.LENGTH_SHORT).show()
                            } else {
                                val savedFile = File(outputDirectory, "saved_recording_${dateFormat.format(Date())}.mp4")
                                val isSaved = outputFile.renameTo(savedFile)
                                if (isSaved) {
                                    Toast.makeText(context, "Recording saved", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "Failed to save recording", Toast.LENGTH_SHORT).show()
                                }
                            }
                            isSaving = false
                            isRecorded = false
                            timer.stop() // Reset the timer
                            elapsedTime = "00:00.00" // Reset the displayed time
                            waveformView.clear() // Clear the waveform
                            progress = 0f // Reset the progress bar
                            showMessage = false // Hide the message when the save button is pressed
                        } else {
                            navigateToScreenOption(HistoryDestination)
                        }
                    },
                    shape = CircleShape,
                    modifier = Modifier.size(60.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = LightGray)
                ) {
                    Icon(
                        imageVector = if (isSaving) Icons.Filled.Check else Icons.Filled.Menu,
                        contentDescription = if (isSaving) "Save Recording" else "Go to History"
                    )
                }
            }
        }
        AndroidView({ waveformView }, modifier = Modifier.align(Alignment.BottomCenter).padding(top = 250.dp))
    }
}