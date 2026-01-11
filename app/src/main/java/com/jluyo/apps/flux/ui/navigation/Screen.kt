package com.jluyo.apps.flux.ui.navigation

sealed class Screen(val route: String) {
    data object Onboarding : Screen("onboarding")
    data object Home : Screen("home")
    data object AddTransaction : Screen("add_transaction")
    data object Settings : Screen("settings")
    data object Assistant : Screen("assistant")
}
