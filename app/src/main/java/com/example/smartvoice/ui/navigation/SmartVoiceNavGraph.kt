package com.example.smartvoice.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smartvoice.SmartVoiceApplication
import com.example.smartvoice.data.DiagnosisDao
import com.example.smartvoice.ui.AppViewModelProvider
import com.example.smartvoice.ui.History.HistoryDestination
import com.example.smartvoice.ui.History.HistoryScreen
import com.example.smartvoice.ui.about.AboutScreen
import com.example.smartvoice.ui.about.AboutDestination
import com.example.smartvoice.ui.home.HomeDestination
import com.example.smartvoice.ui.home.HomeScreen
import com.example.smartvoice.ui.login.LoginDestination
import com.example.smartvoice.ui.login.LoginScreen
import com.example.smartvoice.ui.record.RecordDestination
import com.example.smartvoice.ui.record.RecordScreen
import com.example.smartvoice.ui.AppViewModelProvider.Factory
//import com.example.smartvoice.com.example.smartvoice.MainActivity

/**
 * Provides Navigation graph for the application.
 */
@Composable
fun SmartVoiceNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    //val smartVoiceDatabase = com.example.smartvoice.MainActivity.getSmartVoiceDatabase()
    //val diagnosisDao: DiagnosisDao = smartVoiceDatabase.diagnosisDao()

    NavHost(navController = navController,
        startDestination = LoginDestination.route,
        modifier = modifier)
    {
        composable(route = LoginDestination.route) { destination ->
            LoginScreen(
                navigateToScreenOption = { navController.navigate("${it.route}") },
                viewModelFactory = AppViewModelProvider.Factory(application = SmartVoiceApplication())
            )
        }
        composable(route = HomeDestination.route) {
            HomeScreen(

                navigateToScreenOption = { navController.navigate("${it.route}") },
                navigateToLogin = {
                    navController.navigate(LoginDestination.route) {
                        popUpTo(HomeDestination.route) { inclusive = true } // ✅ Clears backstack
                    }
                },
                viewModelFactory = AppViewModelProvider.Factory(application = SmartVoiceApplication())
            )
        }

        composable(route = HistoryDestination.route) {
            HistoryScreen(
                navigateBack = { navController.popBackStack() },
                viewModelFactory = AppViewModelProvider.Factory(application = SmartVoiceApplication())
            )
        }
        composable(route = RecordDestination.route) { destination ->
            RecordScreen(
                navigateToScreenOption = { navController.navigate("${it.route}") },
                navigateBack = { navController.popBackStack() },
                viewModelFactory = AppViewModelProvider.Factory(application = SmartVoiceApplication()),
            )
        }
        composable(route = AboutDestination.route) {
            AboutScreen(
                navigateToScreenOption = { navController.navigate("${it.route}") },
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}
