package com.example.tykka.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceiptDao {

    // Obtener todos los recibos ordenados por ID descendente
    @Query("SELECT * FROM receipts ORDER BY id DESC")
    fun getAllReceipts(): Flow<List<ReceiptEntity>>

    // Insertar un nuevo recibo o reemplazarlo si existe
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReceipt(receipt: ReceiptEntity)

    // Eliminar un recibo
    @Delete
    suspend fun deleteReceipt(receipt: ReceiptEntity)
}