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
) {
    Scaffold { innerPadding ->
        LoginBody(
            onScreenOptionClick = navigateToScreenOption,
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun LoginBody(
    onScreenOptionClick: (NavigationDestination) -> Unit,
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
                when {
                    !email.contains("@") -> errorMessage = "Invalid email! Must contain '@'"
                    password.length < 6 -> errorMessage = "Password must be at least 6 characters"
                    else -> onScreenOptionClick(HomeDestination) // Navigate if valid
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.h6,
            )
        }

        // Removed the Register button and its related code
    }
}

// Define the NavigationDestination interface
interface NavigaonDestination {
    val route: String
    val titleRes: Int
}