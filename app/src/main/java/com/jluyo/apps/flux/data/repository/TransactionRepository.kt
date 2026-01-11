package com.jluyo.apps.flux.data.repository

import com.jluyo.apps.flux.data.local.TransactionDao
import com.jluyo.apps.flux.data.local.TransactionEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao
) {
    fun getAllTransactions(): Flow<List<TransactionEntity>> = transactionDao.getAllTransactions()

    fun getTransactionsByDateRange(start: Long, end: Long): Flow<List<TransactionEntity>> =
        transactionDao.getTransactionsByDateRange(start, end)

    suspend fun insertTransaction(transaction: TransactionEntity) =
        transactionDao.insertTransaction(transaction)

    suspend fun updateTransaction(transaction: TransactionEntity) =
        transactionDao.updateTransaction(transaction)

    suspend fun deleteTransaction(transaction: TransactionEntity) =
        transactionDao.deleteTransaction(transaction)

    suspend fun deleteAll() = transactionDao.deleteAll()
}
