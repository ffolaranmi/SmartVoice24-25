package com.example.smartvoice.ui.about

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
import com.example.smartvoice.ui.navigation.NavigationDestination

object AboutDestination : NavigationDestination {
    override val route = "About"
    override val titleRes = R.string.about_title
}

@Composable
fun AboutScreen(
    navigateToScreenOption: (NavigationDestination) -> Unit,
    modifier:  Modifier = Modifier,
    //viewModel: AboutViewModel = viewModel(/*factory = AppViewModelProvider.Factory*/),
    navigateBack: () -> Unit,
){
    Scaffold(
    topBar = {
        SmartVoiceTopAppBar(
            title = stringResource(id = AboutDestination.titleRes),
            canNavigateBack = true,
            navigateUp = navigateBack,
        )
    }
    ){
        innerpadding ->
        AboutBody(
            modifier = modifier.padding(innerpadding)
        )
    }
}

@Composable
private fun AboutBody(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.fillMaxSize()
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        //Image(painter = /*idk what to put here i think image name but will have to add later once i figure out how to store that*/)
        Text(
            text ="The objective of Smart Voice is to create a smart phone application that can identify and diagnose recurrent respiratory papilloma (RRP) disease.  RRP is a condition that is characterized by the presence of wart like growths called papilloma in the respiratory retract which causes problems for the airway to function properly. \n" +
                    "\n" +
                    "Deep Learning (DL) techniques are used to analyse voice samples throughout the detection process to determine whether the patient has RRP or not.  ",
            style = MaterialTheme.typography.h6
        )
    }
}