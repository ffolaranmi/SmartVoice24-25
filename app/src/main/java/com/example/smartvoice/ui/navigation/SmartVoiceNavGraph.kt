package com.example.smartvoice.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smartvoice.SmartVoiceApplication
import com.example.smartvoice.data.SmartVoiceDatabase
import com.example.smartvoice.ui.AppViewModelProvider
import com.example.smartvoice.ui.History.HistoryScreen
import com.example.smartvoice.ui.about.AboutScreen
import com.example.smartvoice.ui.home.HomeScreen
import com.example.smartvoice.ui.login.LoginScreen
import com.example.smartvoice.ui.record.RecordScreen
import com.example.smartvoice.ui.register.RegisterScreen

@Composable
fun SmartVoiceNavHost(
    navController: NavHostController,
    application: SmartVoiceApplication,
    database: SmartVoiceDatabase, // ✅ Ensure database is passed
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {
        // ✅ LOGIN SCREEN
        composable("login") {
            LoginScreen(
                navigateToScreenOption = { navController.navigate("home") },
                navigateToRegister = { navController.navigate("register") },
                application = application, // ✅ Fix: Only pass application
                database = application.smartVoiceDatabase
            )
        }

        // ✅ REGISTER SCREEN
        composable("register") {
            RegisterScreen(
                navigateToLogin = { navController.navigate("login") },
                application = application,
                database = database
            )
        }

        // ✅ HOME SCREEN
        composable("home") {
            HomeScreen(
                navigateToScreenOption = { navController.navigate(it.route) },
                navigateToLogin = {
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                viewModelFactory = AppViewModelProvider.Factory(application) // ✅ Ensure ViewModelFactory uses database
            )
        }

        // ✅ HISTORY SCREEN
        composable("history") {
            HistoryScreen(
                navigateBack = { navController.popBackStack() },
                viewModelFactory = AppViewModelProvider.Factory(application)
            )
        }

        // ✅ RECORD SCREEN
        composable("record") {
            RecordScreen(
                navigateToScreenOption = { navController.navigate(it.route) },
                navigateBack = { navController.popBackStack() },
                viewModelFactory = AppViewModelProvider.Factory(application)
            )
        }

        // ✅ ABOUT SCREEN
        composable("about") {
            AboutScreen(
                navigateToScreenOption = { navController.navigate(it.route) },
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}