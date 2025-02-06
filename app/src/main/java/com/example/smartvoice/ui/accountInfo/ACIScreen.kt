package com.example.smartvoice.ui.accountInfo

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartvoice.R
import com.example.smartvoice.SmartVoiceTopAppBar
import com.example.smartvoice.ui.AppViewModelProvider
import com.example.smartvoice.ui.medhelp.HelpViewModel
import com.example.smartvoice.ui.navigation.NavigationDestination

object ACIDestination : NavigationDestination {
    override val route = "ACI"
    override val titleRes = R.string.account_information
}

@Composable
fun ACIScreen(
    modifier: Modifier = Modifier,
    viewModel: ACIViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateBack: () -> Unit,
    navigateToScreenOption: (NavigationDestination) -> Unit,
){
    val accountInfo by viewModel.accountInfo.observeAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            SmartVoiceTopAppBar(
                title = stringResource(id = ACIDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
            )
        }
    ) { innerpadding ->
        accountInfo?.let {
            Column(modifier = modifier.padding(innerpadding)) {
                ACIBody(accountInfo = it, onEditClick = { showDialog = true })
            }
        }
    }

    if (showDialog) {

        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Edit Account Information") },
            text = { /* Add form fields to edit account information here */ },
            confirmButton = {
                Button(onClick = {
                    // Handle confirm click, update account information
                    showDialog = false
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
@Composable
private fun ACIBody(
    modifier: Modifier = Modifier,
    accountInfo: AccountInfo,
    onEditClick: () -> Unit
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Account Information", style = MaterialTheme.typography.h4)
        Text(text = "Username: ${accountInfo.username}", style = MaterialTheme.typography.body1)
        Text(text = "Email: ${accountInfo.email}", style = MaterialTheme.typography.body1)
        Text(text = "Phone Number: ${accountInfo.phoneNumber}", style = MaterialTheme.typography.body1)
        Text(text = "Address: ${accountInfo.address}", style = MaterialTheme.typography.body1)

        Button(onClick = onEditClick) {
            Text(text = "Edit Account Information")
        }
    }
}