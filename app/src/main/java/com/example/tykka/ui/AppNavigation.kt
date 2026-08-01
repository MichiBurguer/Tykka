package com.example.tykka.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tykka.viewmodel.ReceiptViewModel

@Composable
fun AppNavigation(viewModel: ReceiptViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onNavigateToAdd = { navController.navigate("add") },
                onNavigateToSettings = { navController.navigate("settings") }
            )
        }
        composable("add") {
            AddReceiptScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable("settings") {
            SettingsScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}