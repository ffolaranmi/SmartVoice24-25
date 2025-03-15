package com.example.smartvoice.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.smartvoice.R
import com.example.smartvoice.SmartVoiceTopAppBar
import com.example.smartvoice.ui.navigation.NavigationDestination
import com.example.smartvoice.ui.History.HistoryDestination
import com.example.smartvoice.ui.about.AboutDestination
import com.example.smartvoice.ui.record.RecordDestination

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@Composable
fun HomeScreen(
    navigateToScreenOption: (NavigationDestination) -> Unit,
    navigateToLogin: () -> Unit,  // ✅ Added logout navigation
    modifier: Modifier = Modifier,
    viewModelFactory: ViewModelProvider.Factory,
) {
    Scaffold(
        topBar = {
            SmartVoiceTopAppBar(
                title = stringResource(id = HomeDestination.titleRes),
                canNavigateBack = false
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween, // ✅ Pushes logout button to bottom
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.weight(1f), // ✅ Ensures buttons take up space above
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                statusBar()
                buttonList(onScreenOptionClick = navigateToScreenOption)
            }

            // ✅ Logout button at the bottom
            LogoutButton(navigateToLogin)
        }
    }
}

@Composable
private fun buttonList(
    onScreenOptionClick: (NavigationDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        buttonList.forEach {
            Button(
                onClick = { onScreenOptionClick(it.buttonRoute) },
                modifier = Modifier.widthIn(min = 275.dp)
            ) {
                Text(
                    text = stringResource(id = it.buttonStringId),
                    style = MaterialTheme.typography.h6
                )
            }
        }
    }
}

@Composable
private fun statusBar(modifier: Modifier = Modifier) {
    Column {
        Text(text = "Status", style = MaterialTheme.typography.h4)
        Text(text = "Analysis Pending...", style = MaterialTheme.typography.h6)
    }
}

// ✅ New Logout Button Component
@Composable
fun LogoutButton(navigateToLogin: () -> Unit) {
    Button(
        onClick = { navigateToLogin() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
    ) {
        Text("Logout", style = MaterialTheme.typography.h6)
    }
}

// Define Button List
private data class ButtonHeader(@StringRes val buttonStringId: Int, val buttonRoute: NavigationDestination)

private val buttonList = listOf(
    ButtonHeader(buttonStringId = R.string.account_information, buttonRoute = HistoryDestination),
    ButtonHeader(buttonStringId = R.string.record, buttonRoute = RecordDestination),
    ButtonHeader(buttonStringId = R.string.history, buttonRoute = HistoryDestination),
    ButtonHeader(buttonStringId = R.string.find_medical_help, buttonRoute = HistoryDestination),
    ButtonHeader(buttonStringId = R.string.what_is_smartvoice, buttonRoute = AboutDestination)
)