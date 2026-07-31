package com.example.tykka.data

import kotlinx.coroutines.flow.Flow
import com.example.tykka.data.network.EmisorApiService
import com.example.tykka.data.network.EmisorDto

class ReceiptRepository(
    private val receiptDao: ReceiptDao,
    private val dataStoreManager: DataStoreManager,
    private val apiService:EmisorApiService = EmisorApiService.create() // API
) {
    // Flow con la lista de recibos desde Room
    val allReceipts: Flow<List<ReceiptEntity>> = receiptDao.getAllReceipts()

    // Operaciones para Room
    suspend fun insertReceipt(receipt: ReceiptEntity) {
        receiptDao.insertReceipt(receipt)
    }

    suspend fun deleteReceipt(receipt: ReceiptEntity) {
        receiptDao.deleteReceipt(receipt)
    }

    // Preferencias desde DataStore
    val currencySymbol: Flow<String> = dataStoreManager.currencySymbol
    val daysAlert: Flow<Int> = dataStoreManager.daysAlert

    suspend fun updateCurrency(symbol: String) {
        dataStoreManager.saveCurrencySymbol(symbol)
    }

    suspend fun updateDaysAlert(days: Int) {
        dataStoreManager.saveDaysAlert(days)
    }

    suspend fun fetchEmisor(id: Int): EmisorDto {
        return apiService.getEmisorPorId(id)
    }
}