package com.example.smartvoice.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

object AccountInfoDestination : NavigationDestination {
    override val route = "accountInfo"
    override val titleRes = R.string.account_information
}

object FindMedicalHelpDestination : NavigationDestination {
    override val route = "findMedicalHelp"
    override val titleRes = R.string.find_medical_help
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToScreenOption: (NavigationDestination) -> Unit,
    navigateToLogin: () -> Unit,
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
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "WELCOME TO SMARTVOICE!",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.padding(12.dp)
                )

                buttonList(navigateToScreenOption)
            }

            LogoutButton(navigateToLogin)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun buttonList(
    onScreenOptionClick: (NavigationDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        buttonList.forEach {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xEDE6EAF8)), // soft lilac
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(22.dp),
                onClick = { onScreenOptionClick(it.buttonRoute) }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = it.buttonStringId).uppercase(),
                        color = Color(0xFF5E35B1), // deep purple
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}


@Composable
fun LogoutButton(navigateToLogin: () -> Unit) {
    Button(
        onClick = { navigateToLogin() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB71C1C)),
        shape = RoundedCornerShape(50.dp)
    ) {
        Text("Logout", style = MaterialTheme.typography.bodyLarge.copy(color = Color.White))
    }
}

private data class ButtonHeader(@StringRes val buttonStringId: Int, val buttonRoute: NavigationDestination)

private val buttonList = listOf(
    ButtonHeader(buttonStringId = R.string.account_information, buttonRoute = AccountInfoDestination),
    ButtonHeader(buttonStringId = R.string.record, buttonRoute = RecordDestination),
    ButtonHeader(buttonStringId = R.string.history, buttonRoute = HistoryDestination),
    ButtonHeader(buttonStringId = R.string.find_medical_help, buttonRoute = FindMedicalHelpDestination),
    ButtonHeader(buttonStringId = R.string.what_is_smartvoice, buttonRoute = AboutDestination)
)
