package com.jluyo.apps.flux.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jluyo.apps.flux.data.repository.TransactionRepository
import com.jluyo.apps.flux.data.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    val userName = userPreferencesRepository.userName
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    fun updateName(newName: String) {
        viewModelScope.launch {
            userPreferencesRepository.setUserName(newName)
        }
    }

    fun resetAllData() {
        viewModelScope.launch {
            transactionRepository.deleteAll()
            userPreferencesRepository.clearAll()
        }
    }
}
