package com.jluyo.apps.flux.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jluyo.apps.flux.data.local.TransactionType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onAddTransactionClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onAssistantClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Flux") },
                actions = {
                    IconButton(onClick = onAssistantClick) {
                        Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = "Asistente")
                    }
                    IconButton(onClick = onSettingsClick) {
                        Icon(Icons.Default.Settings, contentDescription = "Configuración")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTransactionClick) {
                Icon(Icons.Default.Add, contentDescription = "Add Transaction")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("Hola, ${uiState.userName}", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Balance Total")
                    Text("$${uiState.totalBalance}", style = MaterialTheme.typography.displaySmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Mes Actual", style = MaterialTheme.typography.labelLarge)
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Ingresos: $${uiState.monthlyIncome}", color = Color.Green)
                        Text("Gastos: $${uiState.monthlyExpense}", color = Color.Red)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Movimientos del Mes", style = MaterialTheme.typography.titleMedium)
            LazyColumn {
                items(uiState.transactions) { transaction ->
                    ListItem(
                        headlineContent = { Text(transaction.category) },
                        supportingContent = { Text(transaction.description ?: "") },
                        trailingContent = {
                            Text(
                                "$${transaction.amount}",
                                color = if (transaction.type == TransactionType.INCOME) Color.Green else Color.Red
                            )
                        }
                    )
                }
            }
        }
    }
}
