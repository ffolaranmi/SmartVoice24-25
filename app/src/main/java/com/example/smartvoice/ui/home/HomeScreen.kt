package com.example.smartvoice.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartvoice.R
import com.example.smartvoice.SmartVoiceTopAppBar
import com.example.smartvoice.ui.AppViewModelProvider
import com.example.smartvoice.ui.History.HistoryDestination
import com.example.smartvoice.ui.navigation.NavigationDestination
import com.example.smartvoice.ui.login.LoginDestination
import com.example.smartvoice.ui.accountInfo.ACIDestination
import com.example.smartvoice.ui.about.WhatDestination
import com.example.smartvoice.ui.medhelp.HelpDestination
import com.example.recordscreen.RecordDestination

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@Composable
fun HomeScreen(
    navigateToScreenOption: (NavigationDestination) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
){
    Scaffold(
        topBar = {
            SmartVoiceTopAppBar(
                title = stringResource(id = HomeDestination.titleRes),
                canNavigateBack = false
            )
        }
    ) { innerpadding ->
        HomeBody(
            onScreenOptionClick = navigateToScreenOption,
            modifier = modifier.padding(innerpadding)
        )
    }
}

@Composable
private fun HomeBody(
    onScreenOptionClick: (NavigationDestination) -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        statusBar()
        buttonList(onScreenOptionClick = onScreenOptionClick)
    }
}

@Composable
private fun buttonList(
    onScreenOptionClick: (NavigationDestination) -> Unit,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        buttonList.forEach {
            Button(onClick = { onScreenOptionClick (it.buttonRoute) },
                modifier = modifier.widthIn(min = 275.dp)
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
private fun statusBar(
    modifier: Modifier = Modifier
){

}

private data class ButtonHeader(@StringRes val buttonStringId: Int, val buttonRoute: NavigationDestination)

private val buttonList = listOf(
    ButtonHeader(buttonStringId = R.string.account_information, buttonRoute = ACIDestination),
    ButtonHeader(buttonStringId = R.string.record, buttonRoute = RecordDestination),
    ButtonHeader(buttonStringId = R.string.history, buttonRoute = HistoryDestination),
    ButtonHeader(buttonStringId = R.string.help, buttonRoute = HelpDestination),
    ButtonHeader(buttonStringId = R.string.what_is_smartvoice, buttonRoute = WhatDestination),
    ButtonHeader(buttonStringId = R.string.logout, buttonRoute = LoginDestination)
)