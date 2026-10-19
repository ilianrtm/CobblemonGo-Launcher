package dev.iln.cobblemongo.ui.navigation

sealed interface Screen {

    data object Home : Screen
    data object Login : Screen
    data object Settings : Screen
}