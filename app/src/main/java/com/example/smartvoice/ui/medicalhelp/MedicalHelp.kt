package com.example.smartvoice.ui.help

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

class FindMedicalHelpViewModel : ViewModel() {
    val infoMessages = listOf(
        Pair("IF IT'S AN EMERGENCY, CALL", "999"),
        Pair("IF IT'S A NON-EMERGENCY, CALL", "111"),
        Pair("FOR MORE MEDICAL INFORMATION, VISIT", "https://www.scot.nhs.uk/")
    )
}

class FindMedicalHelpViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FindMedicalHelpViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FindMedicalHelpViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindMedicalHelpScreen(
    navigateBack: () -> Unit,
    viewModel: FindMedicalHelpViewModel = viewModel(factory = FindMedicalHelpViewModelFactory())
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Find Medical Help") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            viewModel.infoMessages.forEach { (label, value) ->
                InfoTile(
                    label = label,
                    value = if (value.startsWith("https")) value else value,
                    isLink = value.startsWith("https"),
                    linkUrl = value,
                    context = context
                )
            }
        }
    }
}

@Composable
fun InfoTile(label: String, value: String, isLink: Boolean = false, linkUrl: String = "", context: android.content.Context) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(enabled = isLink) {
                if (isLink) {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(linkUrl))
                    context.startActivity(browserIntent)
                }
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = if (!isLink) FontWeight.Bold else FontWeight.Normal,
                color = if (isLink) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
