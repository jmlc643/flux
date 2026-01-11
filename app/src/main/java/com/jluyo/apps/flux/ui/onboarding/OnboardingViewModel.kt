package com.jluyo.apps.flux.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jluyo.apps.flux.data.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    fun completeOnboarding(name: String) {
        viewModelScope.launch {
            userPreferencesRepository.setUserName(name)
            userPreferencesRepository.setSetupComplete(true)
        }
    }
}
