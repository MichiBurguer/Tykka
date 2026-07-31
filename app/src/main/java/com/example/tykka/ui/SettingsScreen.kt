package com.example.tykka.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    currencySymbol: String,
    onSaveCurrency: (String) -> Unit,
    onBack: () -> Unit
) {
    // Estado local para el campo de texto
    var tempCurrency by remember(currencySymbol) { mutableStateOf(currencySymbol) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ajustes de Preferencias") },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("← Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Configuración General",
                style = MaterialTheme.typography.titleMedium
            )

            // Selector / Entrada de Símbolo de Moneda
            OutlinedTextField(
                value = tempCurrency,
                onValueChange = { tempCurrency = it },
                label = { Text("Símbolo de Moneda") },
                placeholder = { Text("Ej: $, €, S/, MXN$") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    onSaveCurrency(tempCurrency)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Preferencias")
            }
        }
    }
}