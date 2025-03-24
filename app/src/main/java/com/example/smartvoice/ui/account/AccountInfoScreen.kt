package com.example.smartvoice.ui.account

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.smartvoice.data.SmartVoiceDatabase
import kotlinx.coroutines.launch

fun isPasswordStrong(password: String): Boolean {
    val hasNumber = password.any { it.isDigit() }
    val hasSymbol = password.any { !it.isLetterOrDigit() }
    return password.length >= 8 && hasNumber && hasSymbol
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountInfoScreen(
    database: SmartVoiceDatabase,
    navController: NavController,
    navigateToLogin: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: AccountInfoViewModel = viewModel(
        factory = AccountInfoViewModelFactory(database, context)
    )
    val user by viewModel.user.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val showResetDialog = remember { mutableStateOf(false) }
    val showDeleteDialog = remember { mutableStateOf(false) }

    val currentPassword = remember { mutableStateOf("") }
    val newPassword = remember { mutableStateOf("") }
    val resetErrorMessage = remember { mutableStateOf("") }
    val deleteConfirmationText = remember { mutableStateOf("") }
    val deleteError = remember { mutableStateOf("") }

    val showCurrentPassword = remember { mutableStateOf(false) }
    val showNewPassword = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Account Information") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        user?.let { u ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                InfoTile(label = "CHILD'S FULL NAME", value = u.patientName)
                InfoTile(label = "CHILD'S AGE", value = u.age.toString())
                InfoTile(label = "CHILD'S CHI NUMBER", value = u.chinum)
                InfoTile(label = "PARENT'S NAME", value = u.currentstatus)
                InfoTile(label = "PARENT'S EMAIL", value = u.email)

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { showResetDialog.value = true },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                ) {
                    Text("Reset Password", color = MaterialTheme.colorScheme.onError, fontSize = 18.sp)
                }

                Button(
                    onClick = { showDeleteDialog.value = true },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                ) {
                    Icon(Icons.Filled.Delete, contentDescription = "Delete Account")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Delete Account", color = MaterialTheme.colorScheme.onError, fontSize = 18.sp)
                }

                if (showDeleteDialog.value) {
                    AlertDialog(
                        onDismissRequest = { showDeleteDialog.value = false },
                        icon = { Icon(Icons.Filled.Delete, contentDescription = "Delete Warning") },
                        title = { Text("Confirm Account Deletion") },
                        text = {
                            Column {
                                Text("Type 'DELETE' to confirm account deletion.")
                                OutlinedTextField(
                                    value = deleteConfirmationText.value,
                                    onValueChange = { deleteConfirmationText.value = it },
                                    label = { Text("Enter DELETE") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                if (deleteError.value.isNotEmpty()) {
                                    Text(deleteError.value, color = MaterialTheme.colorScheme.error)
                                }
                            }
                        },
                        confirmButton = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                TextButton(onClick = { showDeleteDialog.value = false }) {
                                    Text("Cancel")
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(
                                    onClick = {
                                        coroutineScope.launch {
                                            if (deleteConfirmationText.value == "DELETE") {
                                                viewModel.user.value?.let { user ->
                                                    database.userDao().delete(user)
                                                }
                                                showDeleteDialog.value = false
                                                navigateToLogin()
                                                deleteError.value = ""
                                            } else {
                                                deleteError.value = "You must type DELETE to confirm."
                                            }
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                                ) {
                                    Text("Confirm Delete", color = MaterialTheme.colorScheme.onError)
                                }
                            }
                        },
                        dismissButton = {}
                    )
                }

                if (showResetDialog.value) {
                    AlertDialog(
                        onDismissRequest = { showResetDialog.value = false },
                        title = { Text("Reset Password") },
                        text = {
                            Column {
                                OutlinedTextField(
                                    value = currentPassword.value,
                                    onValueChange = { currentPassword.value = it },
                                    label = { Text("Current Password") },
                                    modifier = Modifier.fillMaxWidth(),
                                    visualTransformation = if (showCurrentPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
                                    trailingIcon = {
                                        val icon = if (showCurrentPassword.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                                        IconButton(onClick = { showCurrentPassword.value = !showCurrentPassword.value }) {
                                            Icon(imageVector = icon, contentDescription = "Toggle current password visibility")
                                        }
                                    }
                                )
                                OutlinedTextField(
                                    value = newPassword.value,
                                    onValueChange = { newPassword.value = it },
                                    label = { Text("New Password") },
                                    modifier = Modifier.fillMaxWidth(),
                                    visualTransformation = if (showNewPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
                                    trailingIcon = {
                                        val icon = if (showNewPassword.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                                        IconButton(onClick = { showNewPassword.value = !showNewPassword.value }) {
                                            Icon(imageVector = icon, contentDescription = "Toggle new password visibility")
                                        }
                                    }
                                )
                                if (resetErrorMessage.value.isNotEmpty()) {
                                    Text(resetErrorMessage.value, color = MaterialTheme.colorScheme.error)
                                }
                            }
                        },
                        confirmButton = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                TextButton(onClick = { showResetDialog.value = false }) {
                                    Text("Cancel")
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(
                                    onClick = {
                                        coroutineScope.launch {
                                            if (!isPasswordStrong(newPassword.value)) {
                                                resetErrorMessage.value = "Password must be at least 8 characters long, contain a number and a symbol."
                                            } else {
                                                val resetSuccessful = viewModel.resetPassword(
                                                    u.email,
                                                    currentPassword.value,
                                                    newPassword.value
                                                )
                                                if (resetSuccessful) {
                                                    showResetDialog.value = false
                                                    navigateToLogin()
                                                } else {
                                                    resetErrorMessage.value = "Incorrect current password or error updating."
                                                }
                                            }
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                                ) {
                                    Text("Reset", color = MaterialTheme.colorScheme.onError)
                                }
                            }
                        },
                        dismissButton = {}
                    )
                }
            }
        } ?: Text("No user information found.")
    }
}

@Composable
fun InfoTile(label: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Text(text = value, fontSize = 16.sp)
        }
    }
}
