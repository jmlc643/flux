package com.jluyo.apps.flux.ui.transaction

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jluyo.apps.flux.data.local.TransactionEntity
import com.jluyo.apps.flux.data.local.TransactionType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    onTransactionSaved: () -> Unit,
    viewModel: TransactionViewModel = hiltViewModel()
) {
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isIncome by remember { mutableStateOf(false) }

    val incomeCategories = listOf("Sueldo", "Pago de préstamo", "Pago de trabajo freelance", "Envío de otra cuenta", "Otros")
    val expenseCategories = listOf("Compra de articulo", "Prestamo a un amigo", "Compra de comida", "Envio a otra cuenta", "Otros")

    val currentCategories = if (isIncome) incomeCategories else expenseCategories
    var category by remember(isIncome) { mutableStateOf(currentCategories.first()) }
    var expanded by remember { mutableStateOf(false) }

    // Date Picker state
    var selectedDate by remember { mutableLongStateOf(System.currentTimeMillis()) }
    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = selectedDate)
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { selectedDate = it }
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Nuevo Movimiento", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Row {
            FilterChip(
                selected = !isIncome,
                onClick = { isIncome = false },
                label = { Text("Gasto") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            FilterChip(
                selected = isIncome,
                onClick = { isIncome = true },
                label = { Text("Ingreso") }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Date Selector
        OutlinedTextField(
            value = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(selectedDate)),
            onValueChange = {},
            readOnly = true,
            label = { Text("Fecha") },
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Seleccionar fecha")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Monto") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = category,
                onValueChange = {},
                readOnly = true,
                label = { Text("Categoría") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                modifier = Modifier.fillMaxWidth().menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                currentCategories.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            category = selectionOption
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descripción / Nota") },
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val amountVal = amount.toDoubleOrNull()
                if (amountVal != null) {
                    val transaction = TransactionEntity(
                        amount = amountVal,
                        type = if (isIncome) TransactionType.INCOME else TransactionType.EXPENSE,
                        category = category,
                        description = description,
                        date = selectedDate
                    )
                    viewModel.saveTransaction(transaction)
                    onTransactionSaved()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }
    }
}
