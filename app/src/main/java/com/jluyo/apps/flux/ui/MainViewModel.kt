package com.jluyo.apps.flux.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jluyo.apps.flux.data.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    val isSetupComplete = userPreferencesRepository.isSetupComplete
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
}
