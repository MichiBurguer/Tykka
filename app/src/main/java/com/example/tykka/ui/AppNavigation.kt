package com.example.tykka.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tykka.viewmodel.ReceiptViewModel
@Composable
fun AppNavigation(viewModel: ReceiptViewModel) {
    val navController = rememberNavController()

    // Leemos el símbolo de moneda desde DataStore
    val currencySymbol by viewModel.currencySymbol.collectAsState()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onNavigateToAdd = { navController.navigate("add") },
                onNavigateToSettings = { navController.navigate("settings") }
            )
        }
        composable("add") {
            AddReceiptScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() })
        }
        composable("settings") {
            SettingsScreen(
                currencySymbol = currencySymbol,
                onSaveCurrency = { newSymbol ->
                    viewModel.setCurrency(newSymbol)
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}