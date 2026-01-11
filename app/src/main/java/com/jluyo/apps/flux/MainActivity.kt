package com.jluyo.apps.flux

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jluyo.apps.flux.ui.MainViewModel
import com.jluyo.apps.flux.ui.assistant.AssistantScreen
import com.jluyo.apps.flux.ui.home.HomeScreen
import com.jluyo.apps.flux.ui.navigation.Screen
import com.jluyo.apps.flux.ui.onboarding.OnboardingScreen
import com.jluyo.apps.flux.ui.settings.SettingsScreen
import com.jluyo.apps.flux.ui.transaction.AddTransactionScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            viewModel.isSetupComplete.value == null
        }

        setContent {
            val isSetupComplete by viewModel.isSetupComplete.collectAsState()

            if (isSetupComplete != null) {
                FluxApp(startDestination = if (isSetupComplete == true) Screen.Home.route else Screen.Onboarding.route)
            }
        }
    }
}

@Composable
fun FluxApp(startDestination: String) {
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(navController = navController, startDestination = startDestination) {
            composable(Screen.Onboarding.route) {
                OnboardingScreen(
                    onOnboardingComplete = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Onboarding.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(Screen.Home.route) {
                HomeScreen(
                    onAddTransactionClick = { navController.navigate(Screen.AddTransaction.route) },
                    onSettingsClick = { navController.navigate(Screen.Settings.route) },
                    onAssistantClick = { navController.navigate(Screen.Assistant.route) }
                )
            }
            composable(Screen.AddTransaction.route) {
                AddTransactionScreen(
                    onTransactionSaved = { navController.popBackStack() }
                )
            }
            composable(Screen.Settings.route) {
                SettingsScreen(
                    onBack = { navController.popBackStack() }
                )
            }
            composable(Screen.Assistant.route) {
                AssistantScreen(
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
