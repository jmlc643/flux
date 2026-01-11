package com.jluyo.apps.flux.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jluyo.apps.flux.data.local.TransactionEntity
import com.jluyo.apps.flux.data.local.TransactionType
import com.jluyo.apps.flux.data.repository.TransactionRepository
import com.jluyo.apps.flux.data.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

import java.util.Calendar

data class HomeUiState(
    val userName: String = "",
    val transactions: List<TransactionEntity> = emptyList(),
    val monthlyIncome: Double = 0.0,
    val monthlyExpense: Double = 0.0,
    val totalBalance: Double = 0.0
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    transactionRepository: TransactionRepository,
    userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = combine(
        transactionRepository.getAllTransactions(),
        userPreferencesRepository.userName
    ) { transactions, name ->
        val totalBalance = transactions.sumOf {
            if (it.type == TransactionType.INCOME) it.amount else -it.amount
        }

        // Filter for current month
        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentYear = calendar.get(Calendar.YEAR)

        val monthlyTransactions = transactions.filter {
            val date = Calendar.getInstance().apply { timeInMillis = it.date }
            date.get(Calendar.MONTH) == currentMonth && date.get(Calendar.YEAR) == currentYear
        }

        val monthlyIncome = monthlyTransactions.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
        val monthlyExpense = monthlyTransactions.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }

        HomeUiState(
            userName = name ?: "Usuario",
            transactions = monthlyTransactions,
            monthlyIncome = monthlyIncome,
            monthlyExpense = monthlyExpense,
            totalBalance = totalBalance
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState())
}
