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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartvoice.R
import com.example.smartvoice.ui.home.HomeDestination
import com.example.smartvoice.ui.navigation.NavigationDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.smartvoice.ui.viewModel.UserSessionViewModel

//class UserSessionViewModel : ViewModel() {
 //   private val _email = MutableStateFlow("")
 //   val email: StateFlow<String> = _email

  //  fun setEmail(newEmail: String) {
   //     _email.value = newEmail
 //   }
//}

object AppViewModelProvider {
    val Factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return when {
                modelClass.isAssignableFrom(UserSessionViewModel::class.java) -> UserSessionViewModel() as T
                else -> throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}


object LoginDestination : NavigationDestination {
    override val route = "login"
    override val titleRes = R.string.app_name
}

@Composable
fun LoginScreen(
    navigateToScreenOption: (NavigationDestination) -> Unit,
    modifier: Modifier = Modifier,
    userSessionViewModel: UserSessionViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    Scaffold { innerPadding ->
        LoginBody(
            onScreenOptionClick = navigateToScreenOption,
            userSessionViewModel = userSessionViewModel,
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun LoginBody(
    onScreenOptionClick: (NavigationDestination) -> Unit,
    userSessionViewModel: UserSessionViewModel,
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
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = MaterialTheme.colors.error)
            Spacer(modifier = Modifier.height(10.dp))
        }

        Button(
            onClick = {
                if (email.isNotBlank() && password.isNotBlank()) {
                    userSessionViewModel.setEmail(email) // Save email globally
                    onScreenOptionClick(HomeDestination) // Navigate to Home
                } else {
                    errorMessage = "Please enter a valid email and password"
                }
            },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text(text = stringResource(id = R.string.login), style = MaterialTheme.typography.h6)
        }
    }
}