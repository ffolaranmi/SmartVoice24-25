package com.example.smartvoice.ui.medhelp

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.text.ClickableText
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration.Companion.Underline
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartvoice.R
import com.example.smartvoice.SmartVoiceTopAppBar
import com.example.smartvoice.data.Classification
import com.example.smartvoice.ui.AppViewModelProvider
import com.example.smartvoice.ui.navigation.NavigationDestination

object HelpDestination : NavigationDestination {
    override val route = "Help"
    override val titleRes = R.string.help
}

@Composable
fun HelpScreen(
    modifier: Modifier = Modifier,
    viewModel: HelpViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateBack: () -> Unit,
    navigateToScreenOption: (NavigationDestination) -> Unit,
){
    //val historyUiState by viewModel.WhatUiState.collectAsState()
    Scaffold(
        topBar = {
            SmartVoiceTopAppBar(
                title = stringResource(id = HelpDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
            )
        }
    ) { innerpadding ->
        val context = LocalContext.current
        HelpBody(
            modifier = modifier.padding(innerpadding),
            context = context
        )
    }
}

@Composable
private fun HelpBody(
    modifier: Modifier = Modifier,
    context: Context
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Find Medical Help", style = MaterialTheme.typography.h4)
        Text(text = "If you are in need of medical attention please visit the ", style = MaterialTheme.typography.body1)
        ClickableText(
            text = AnnotatedString(
                text = "NHS website",
                spanStyles = listOf(
                    AnnotatedString.Range(
                        item = SpanStyle(textDecoration = Underline),
                        start = 0,
                        end = "NHS website".length
                    )
                )
            ),
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.nhs.uk/"))
                context.startActivity(intent)
            }
        )
        Text(text = ", make an appointment with your gp, or contact ", style = MaterialTheme.typography.body1)
        ClickableText(
            text = AnnotatedString(
                text = "NHS 24/7 on 111",
                spanStyles = listOf(
                    AnnotatedString.Range(
                        item = SpanStyle(textDecoration = Underline),
                        start = 0,
                        end = "NHS 24/7 on 111".length
                    )
                )
            ),
            onClick = {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:111"))
                context.startActivity(intent)
            }
        )
        Text(text = ". If it is an emergency please do not hesitate to call ", style = MaterialTheme.typography.body1)
        ClickableText(
            text = AnnotatedString(
                text = "999",
                spanStyles = listOf(
                    AnnotatedString.Range(
                        item = SpanStyle(textDecoration = Underline),
                        start = 0,
                        end = "999".length
                    )
                )
            ),
            onClick = {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:999"))
                context.startActivity(intent)
            }
        )
    }
}