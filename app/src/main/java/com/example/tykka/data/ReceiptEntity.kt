package com.example.tykka.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "receipts")
data class ReceiptEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val storeName: String,
    val purchaseDate: Long,
    val warrantyMonths: Int,
    val price: Double,
    val imageUri: String,
    val notes: String = ""
)