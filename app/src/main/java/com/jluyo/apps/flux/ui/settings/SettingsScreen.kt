package com.jluyo.apps.flux.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val currentName by viewModel.userName.collectAsState()
    var showResetDialog by remember { mutableStateOf(false) }
    var nameInput by remember(currentName) { mutableStateOf(currentName) }

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("¿Borrar todos los datos?") },
            text = { Text("Esta acción no se puede deshacer. Se borrarán todas las transacciones y tu información.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.resetAllData()
                        showResetDialog = false
                        // Ideally navigate to Onboarding or exit app, but UserPreferencesRepository clears isSetupComplete,
                        // so MainActivity logic might handle it if we restart or observe properly.
                        // For now we just stay here or maybe go back.
                    }
                ) {
                    Text("Borrar", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ajustes") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Perfil", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = nameInput ?: "",
                onValueChange = { nameInput = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    nameInput?.let { viewModel.updateName(it) }
                },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                enabled = nameInput != currentName
            ) {
                Text("Actualizar Nombre")
            }

            Spacer(modifier = Modifier.height(32.dp))
            Text("Zona de Peligro", style = MaterialTheme.typography.titleMedium, color = Color.Red)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { showResetDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Borrar Todos los Datos")
            }
        }
    }
}
