package com.jluyo.apps.flux.ui.onboarding

import androidx.lifecycle.ViewModel
import com.jluyo.apps.flux.data.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    suspend fun completeOnboarding(name: String) {
        userPreferencesRepository.setUserName(name)
        userPreferencesRepository.setSetupComplete(true)
    }
}
