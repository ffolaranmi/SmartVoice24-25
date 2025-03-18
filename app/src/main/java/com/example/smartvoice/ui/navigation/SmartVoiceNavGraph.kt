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
import com.example.smartvoice.ui.account.AccountInfoScreen
import com.example.smartvoice.ui.help.FindMedicalHelpScreen
import com.example.smartvoice.ui.home.AccountInfoDestination
import com.example.smartvoice.ui.home.HomeScreen
import com.example.smartvoice.ui.login.LoginScreen
import com.example.smartvoice.ui.record.RecordScreen
import com.example.smartvoice.ui.register.RegisterScreen

@Composable
fun SmartVoiceNavHost(
    navController: NavHostController,
    application: SmartVoiceApplication,
    database: SmartVoiceDatabase,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {
        composable("login") {
            LoginScreen(
                navigateToScreenOption = { navController.navigate("home") },
                navigateToRegister = { navController.navigate("register") },
                application = application,
                database = application.smartVoiceDatabase
            )
        }

        composable("register") {
            RegisterScreen(
                navigateToLogin = { navController.navigate("login") },
                application = application,
                database = database
            )
        }

        composable("home") {
            HomeScreen(
                navigateToScreenOption = { navController.navigate(it.route) },
                navigateToLogin = {
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                viewModelFactory = AppViewModelProvider.Factory(application)
            )
        }

        composable("history") {
            HistoryScreen(
                navigateBack = { navController.popBackStack() },
                viewModelFactory = AppViewModelProvider.Factory(application)
            )
        }

        composable("record") {
            RecordScreen(
                navigateToScreenOption = { navController.navigate(it.route) },
                navigateBack = { navController.popBackStack() },
                viewModelFactory = AppViewModelProvider.Factory(application)
            )
        }

        composable("about") {
            AboutScreen(
                navigateToScreenOption = { navController.navigate(it.route) },
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(AccountInfoDestination.route) {
            AccountInfoScreen(
                database = database,
                navController = navController,
                navigateToLogin = {
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }

        composable("findMedicalHelp") {
            FindMedicalHelpScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

    }
}
