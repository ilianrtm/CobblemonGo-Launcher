package dev.iln.cobblemongo.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import dev.iln.cobblemongo.ui.screen.home.HomeScreen
import dev.iln.cobblemongo.ui.screen.login.LoginScreen
import dev.iln.cobblemongo.ui.screen.settings.SettingsScreen

private val fade = fadeIn(
    tween(300)
) togetherWith fadeOut(
    tween(300)
)


@Composable
fun NavHost(
    navigator: Navigator,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(LocalNavigator provides navigator) {
        NavDisplay(
            backStack = navigator.backStack,
            modifier = modifier,
            onBack = { navigator.back() },
            transitionSpec = { fade },
            popTransitionSpec = { fade },
            predictivePopTransitionSpec = { fade }
        ) { screen ->
            NavEntry(screen) {
                when (screen) {
                    Screen.Home -> HomeScreen()
                    Screen.Login -> LoginScreen()
                    Screen.Settings -> SettingsScreen()
                }
            }
        }
    }
}