package com.jluyo.apps.flux.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class TransactionType {
    INCOME, EXPENSE
}

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amount: Double,
    val type: TransactionType,
    val category: String,
    val description: String?,
    val date: Long // Timestamp
)
