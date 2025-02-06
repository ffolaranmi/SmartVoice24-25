package com.example.smartvoice.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
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
import com.example.smartvoice.ui.AppViewModelProvider
import com.example.smartvoice.ui.home.HomeDestination
import com.example.smartvoice.ui.navigation.NavigationDestination

object LoginDestination : NavigationDestination {
    override val route = "login"
    override val titleRes = R.string.app_name
}

@Composable
fun LoginScreen(
    navigateToScreenOption: (NavigationDestination) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory),
){
    //val loginUiState by viewModel.loginUiState.collectAsState()
    Scaffold { innerpadding ->
        LoginBody(
            onScreenOptionClick = navigateToScreenOption,
            modifier = modifier.padding(innerpadding)
        )
    }
}

@Composable
private fun LoginBody(
    onScreenOptionClick: (NavigationDestination) -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "SmartVoice", style = MaterialTheme.typography.h3,
        modifier = modifier.padding(24.dp))
        Button(onClick = { onScreenOptionClick (HomeDestination) },
            modifier = modifier.widthIn(min = 300.dp).padding(top = 16.dp, bottom = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.h6,
            )
        }
        Button(onClick = { },
            modifier = modifier.widthIn(min = 300.dp)
        ) {
            Text(
                text = stringResource(id = R.string.register),
                style = MaterialTheme.typography.h6,
            )
        }
    }
}