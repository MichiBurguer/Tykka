package com.example.tykka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tykka.data.AppDatabase
import com.example.tykka.data.DataStoreManager
import com.example.tykka.data.ReceiptRepository
import com.example.tykka.ui.AppNavigation
import com.example.tykka.viewmodel.ReceiptViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.Composable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(applicationContext)
        val dataStoreManager = DataStoreManager(applicationContext)
        val repository = ReceiptRepository(database.receiptDao(), dataStoreManager)

        setContent {
            // Creamos u obtenemos el ViewModel directamente aquí
            val viewModel: ReceiptViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    @Suppress("UNCHECKED_CAST")
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return ReceiptViewModel(repository) as T
                    }
                }
            )

            AppNavigation(viewModel = viewModel)
        }
    }
}