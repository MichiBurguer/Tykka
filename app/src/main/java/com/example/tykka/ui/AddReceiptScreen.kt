package com.example.tykka.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tykka.data.network.ApiState
import com.example.tykka.viewmodel.ReceiptViewModel

@Composable
fun AddReceiptScreen(
    viewModel: ReceiptViewModel,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var storeName by remember { mutableStateOf("") }
    var priceText by remember { mutableStateOf("") }
    var warrantyMonthsText by remember { mutableStateOf("") }
    var emisorIdText by remember { mutableStateOf("") }

    val emisorState by viewModel.emisorState.collectAsState()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Agregar Recibo",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Validar Datos del Emisor", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            OutlinedTextField(
                                value = emisorIdText,
                                onValueChange = { emisorIdText = it },
                                label = { Text("ID Emisor (1-10)") },
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = {
                                val id = emisorIdText.toIntOrNull()
                                if (id != null) viewModel.searchEmisor(id)
                            }) {
                                Text("Buscar")
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        when (val state = emisorState) {
                            is ApiState.Loading -> {
                                Text("Ingrese un ID para consultar datos...", style = MaterialTheme.typography.bodySmall)
                            }
                            is ApiState.Success -> {
                                val emisor = state.data
                                Text("Empresa: ${emisor.nombreEmpresa}", color = MaterialTheme.colorScheme.primary)
                                Text("RUC/ID: ${emisor.rucOIdentificacion}", style = MaterialTheme.typography.bodySmall)

                                TextButton(onClick = { storeName = emisor.nombreEmpresa }) {
                                    Text("Usar este nombre en el recibo")
                                }
                            }
                            is ApiState.Error -> {
                                Text(" ${state.message}", color = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // CAMPOS DE REGISTRO DEL RECIBO
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título del recibo (ej. Compra de mes)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = storeName,
                    onValueChange = { storeName = it },
                    label = { Text("Tienda / Empresa emisor") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = priceText,
                    onValueChange = { priceText = it },
                    label = { Text("Monto / Precio") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = warrantyMonthsText,
                    onValueChange = { warrantyMonthsText = it },
                    label = { Text("Meses de garantía") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            Button(
                onClick = {
                    val price = priceText.toDoubleOrNull() ?: 0.0
                    val months = warrantyMonthsText.toIntOrNull() ?: 0

                    if (title.isNotEmpty() && storeName.isNotEmpty()) {
                        viewModel.addReceipt(
                            title = title,
                            store = storeName,
                            months = months,
                            price = price,
                            imageUri = ""
                        )
                        onBack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text("Guardar Recibo")
            }
        }
    }
}