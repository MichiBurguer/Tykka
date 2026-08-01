package com.example.tykka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tykka.data.AppDatabase
import com.example.tykka.data.DataStoreManager
import com.example.tykka.data.ReceiptRepository
import com.example.tykka.ui.AppNavigation
import com.example.tykka.viewmodel.ReceiptViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(applicationContext)
        val dataStoreManager = DataStoreManager(applicationContext)
        val repository = ReceiptRepository(database.receiptDao(), dataStoreManager)

        setContent {
            val viewModel: ReceiptViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    @Suppress("UNCHECKED_CAST")
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return ReceiptViewModel(repository) as T
                    }
                }
            )

            val isDarkMode by viewModel.isDarkMode.collectAsState()

            MaterialTheme(
                colorScheme = if (isDarkMode) darkColorScheme() else lightColorScheme()
            ) {
                AppNavigation(viewModel = viewModel)
            }
        }
    }
}