package com.example.smartvoice.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartvoice.R
import com.example.smartvoice.SmartVoiceApplication
import com.example.smartvoice.data.SmartVoiceDatabase
import com.example.smartvoice.ui.AppViewModelProvider
import com.example.smartvoice.ui.home.HomeDestination
import com.example.smartvoice.ui.register.RegisterDestination

@Composable
fun LoginScreen(
    navigateToScreenOption: (String) -> Unit,
    navigateToRegister: () -> Unit,
    application: SmartVoiceApplication,
    database: SmartVoiceDatabase, // ✅ Fix: Pass database
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory(application))
) {
    Scaffold { innerPadding ->
        LoginBody(
            onLoginSuccess = { navigateToScreenOption("home") }, // ✅ Fix: Pass correct route
            onRegisterClick = navigateToRegister,
            viewModel = viewModel,
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun LoginBody(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit,
    viewModel: LoginViewModel,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "SmartVoice",
            style = MaterialTheme.typography.h3,
            modifier = Modifier.padding(24.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            isError = email.isNotEmpty() && !email.contains("@")
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            isError = password.isNotEmpty() && password.length < 6
        )

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = MaterialTheme.colors.error)
            Spacer(modifier = Modifier.height(10.dp))
        }

        Button(
            onClick = {
                viewModel.loginUser(email, password) { isSuccess ->
                    if (isSuccess) {
                        onLoginSuccess() // ✅ Redirect to Home
                    } else {
                        errorMessage = "Invalid email or password. Please try again."
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.h6,
            )
        }

        Button(
            onClick = onRegisterClick,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.register),
                style = MaterialTheme.typography.h6,
            )
        }
    }
}