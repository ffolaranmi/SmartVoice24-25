package com.example.smartvoice

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.smartvoice.ui.navigation.SmartVoiceNavHost
import com.example.smartvoice.ui.viewModel.UserSessionViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Top level composable that represents screens for the application.
 */
//@Composable
//fun SmartVoiceApp(navController: NavHostController = rememberNavController()) {
//    SmartVoiceNavHost(navController = navController)
//}

@Composable
fun SmartVoiceApp() {
    val navController = rememberNavController()

    // Create the ViewModel at the app level
    val userSessionViewModel: UserSessionViewModel = viewModel()

    SmartVoiceNavHost(
        navController = navController,
        userSessionViewModel = userSessionViewModel // ✅ Pass ViewModel to NavHost
    )
}

/**
 * App bar to display title and conditionally display the back navigation.
 */
@Composable
fun SmartVoiceTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {}
) {
    if (canNavigateBack) {
        TopAppBar(
            title = { Text(title) },
            modifier = modifier,
            navigationIcon = {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        )
    } else {
        TopAppBar(title = { Text(title) }, modifier = modifier)
    }
}