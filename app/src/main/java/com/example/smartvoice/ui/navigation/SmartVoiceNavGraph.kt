package com.example.smartvoice.ui.navigation

import android.view.LayoutInflater
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smartvoice.R
import com.example.smartvoice.ui.History.HistoryDestination
import com.example.smartvoice.ui.History.HistoryScreen
import com.example.smartvoice.ui.home.HomeDestination
import com.example.smartvoice.ui.home.HomeScreen
import com.example.smartvoice.ui.login.LoginDestination
import com.example.smartvoice.ui.login.LoginScreen
import com.example.smartvoice.ui.medhelp.HelpDestination
import com.example.smartvoice.ui.medhelp.HelpScreen
import com.example.smartvoice.ui.about.WhatDestination
import com.example.smartvoice.ui.about.WhatScreen
import com.example.smartvoice.ui.accountInfo.ACIDestination
import com.example.smartvoice.ui.accountInfo.ACIScreen
import com.example.recordscreen.RecordScreen
import com.example.recordscreen.RecordDestination

/**
 * Provides Navigation graph for the application.
 */

@Composable
fun SmartVoiceNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(navController = navController,
        startDestination = LoginDestination.route,
        modifier = modifier)
    {
        composable(route = LoginDestination.route) {
            LoginScreen(
                navigateToScreenOption = { navController.navigate("${it.route}") }
            )
        }
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToScreenOption = { navController.navigate("${it.route}") }
            )
        }
        composable(route = HistoryDestination.route) {
            HistoryScreen(
                navigateBack = { navController.popBackStack() },
            )
        }

        composable(route = RecordDestination.route) {
            RecordScreen(
                navigateToScreenOption = { navController.navigate("${it.route}")},
                navigateBack = { navController.popBackStack() },
            )
        }


        composable(route = HelpDestination.route) {
            HelpScreen(
                navigateToScreenOption = { navController.navigate("${it.route}")},
                navigateBack = { navController.popBackStack() },
            )
        }
        composable(route = WhatDestination.route) {
            WhatScreen(
                navigateToScreenOption = { navController.navigate("${it.route}")},
                navigateBack = { navController.popBackStack() },
            )
        }
        composable(route = ACIDestination.route) {
            ACIScreen(
                navigateToScreenOption = { navController.navigate("${it.route}")},
                navigateBack = { navController.popBackStack() },
            )
        }
    }
}