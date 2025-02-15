package com.example.smartvoice.ui.about

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.smartvoice.ui.about.WhatViewModel

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
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
import com.example.smartvoice.SmartVoiceTopAppBar
import com.example.smartvoice.data.Classification
import com.example.smartvoice.ui.AppViewModelProvider
import com.example.smartvoice.ui.navigation.NavigationDestination
import androidx.compose.foundation.text.ClickableText
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextDecoration.Companion.Underline

object WhatDestination : NavigationDestination {
    override val route = "What"
    override val titleRes = R.string.what_is_smartvoice
}

@Composable
fun WhatScreen(
    modifier: Modifier = Modifier,
    viewModel: WhatViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateBack: () -> Unit,
    navigateToScreenOption: (NavigationDestination) -> Unit,
){
    val context = LocalContext.current
    Scaffold(
        topBar = {
            SmartVoiceTopAppBar(
                title = stringResource(id = WhatDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
            )
        }
    ) { innerpadding ->
        WhatBody(
            modifier = modifier.padding(innerpadding),
            context = context
        )
    }
}

@Composable
private fun WhatBody(
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
        Text(text = "What is Smart Voice?", style = MaterialTheme.typography.h4)
        Text(text = "Smart Voice is an app developed to use AI to listen to a recording of someone's voice and help a doctor to identify if they have a condition called Recurrent Respiratory Papillomatosis.", style = MaterialTheme.typography.body1)
        Text(text = "For more information on RRP please visit: ", style = MaterialTheme.typography.body1)
        ClickableText(
            text = AnnotatedString(
                text = "https://rrpf.org/",
                spanStyles = listOf(
                    AnnotatedString.Range(
                        item = SpanStyle(textDecoration = Underline),
                        start = 0,
                        end = "https://rrpf.org/".length
                    )
                )
            ),
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://rrpf.org/"))
                context.startActivity(intent)
            }
        )
    }
}