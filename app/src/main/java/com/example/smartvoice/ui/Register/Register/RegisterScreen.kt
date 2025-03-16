package com.example.smartvoice.ui.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartvoice.SmartVoiceApplication
import com.example.smartvoice.data.SmartVoiceDatabase
import com.example.smartvoice.ui.AppViewModelProvider

@Composable
fun RegisterScreen(
    navigateToLogin: () -> Unit,
    application: SmartVoiceApplication,
    database: SmartVoiceDatabase, // ✅ Ensure database is passed
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = viewModel(factory = AppViewModelProvider.Factory(application)) // ✅ Fix ViewModel instantiation
) {
    var chinum by remember { mutableStateOf("") }
    var patientName by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var currentStatus by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Register", style = MaterialTheme.typography.h4, modifier = Modifier.padding(16.dp))

        OutlinedTextField(value = chinum, onValueChange = { chinum = it }, label = { Text("CHI Number") }, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth().padding(8.dp))

        OutlinedTextField(value = patientName, onValueChange = { patientName = it }, label = { Text("Patient Name") }, modifier = Modifier.fillMaxWidth().padding(8.dp))

        OutlinedTextField(value = age, onValueChange = { age = it }, label = { Text("Age") }, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth().padding(8.dp))

        OutlinedTextField(value = currentStatus, onValueChange = { currentStatus = it }, label = { Text("Current Status") }, modifier = Modifier.fillMaxWidth().padding(8.dp))

        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email), modifier = Modifier.fillMaxWidth().padding(8.dp))

        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password), modifier = Modifier.fillMaxWidth().padding(8.dp))

        OutlinedTextField(value = confirmPassword, onValueChange = { confirmPassword = it }, label = { Text("Confirm Password") }, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password), modifier = Modifier.fillMaxWidth().padding(8.dp))

        if (errorMessage.isNotEmpty()) {
            Text(errorMessage, color = MaterialTheme.colors.error, modifier = Modifier.padding(8.dp))
        }

        Button(
            onClick = {
                if (chinum.isBlank() || patientName.isBlank() || age.isBlank() || currentStatus.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                    errorMessage = "All fields are required!"
                } else if (password != confirmPassword) {
                    errorMessage = "Passwords do not match!"
                } else {
                    val ageInt = age.toIntOrNull()
                    if (ageInt == null) {
                        errorMessage = "Age must be a valid number"
                    } else {
                        viewModel.registerUser(chinum, patientName, ageInt, currentStatus, email, password) { success ->
                            if (success) {
                                navigateToLogin() // ✅ Navigate only if registration is successful
                            } else {
                                errorMessage = "Registration failed. Please try again."
                            }
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text("Register")
        }

        TextButton(onClick = navigateToLogin) {
            Text("Already have an account? Login")
        }
    }
}