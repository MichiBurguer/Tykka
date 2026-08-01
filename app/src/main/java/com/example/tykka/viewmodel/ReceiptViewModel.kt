package com.example.tykka.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tykka.data.network.ApiState
import com.example.tykka.data.network.EmisorDto
import com.example.tykka.data.ReceiptEntity
import com.example.tykka.data.ReceiptRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ReceiptViewModel(private val repository: ReceiptRepository) : ViewModel() {

    // Lista de recibos
    val receipts: StateFlow<List<ReceiptEntity>> = repository.allReceipts
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Ajuste de moneda
    val currencySymbol: StateFlow<String> = repository.currencySymbol
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = "$"
        )
    val daysAlert: StateFlow<Int> = repository.daysAlert
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 7
        )
    val isDarkMode: StateFlow<Boolean> = repository.isDarkMode
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )


    private val _emisorState = MutableStateFlow<ApiState<EmisorDto>>(ApiState.Loading)
    val emisorState: StateFlow<ApiState<EmisorDto>> = _emisorState.asStateFlow()

    // Función para buscar un emisor en la API
    fun searchEmisor(id: Int) {
        viewModelScope.launch {
            _emisorState.value = ApiState.Loading
            try {
                val resultado = repository.fetchEmisor(id)
                _emisorState.value = ApiState.Success(resultado)
            } catch (e: Exception) {
                _emisorState.value = ApiState.Error(
                    e.localizedMessage ?: "Error al conectar con la API"
                )
            }
        }
    }

    fun addReceipt(title: String, store: String, months: Int, price: Double, imageUri: String) {
        viewModelScope.launch {
            val newReceipt = ReceiptEntity(
                title = title,
                storeName = store,
                purchaseDate = System.currentTimeMillis(),
                warrantyMonths = months,
                price = price,
                imageUri = imageUri
            )
            repository.insertReceipt(newReceipt)
        }
    }

    fun deleteReceipt(receipt: ReceiptEntity) {
        viewModelScope.launch {
            repository.deleteReceipt(receipt)
        }
    }

    fun setCurrency(symbol: String) {
        viewModelScope.launch {
            repository.updateCurrency(symbol)
        }
    }
    fun setDaysAlert(days: Int) {
        viewModelScope.launch {
            repository.updateDaysAlert(days)
        }
    }
    fun setDarkMode(enable: Boolean) {
        viewModelScope.launch {
            repository.updateDarkMode(enable)
        }
    }
}