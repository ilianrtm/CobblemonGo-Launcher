package dev.iln.cobblemongo.ui.navigation

import androidx.compose.runtime.*

val LocalNavigator = staticCompositionLocalOf<Navigator> { error("No Navigator provided") }


@Stable
class Navigator(start: Screen) {

    val backStack = mutableStateListOf(start)

    val current: Screen
        get() = backStack.last()

    val canGoBack: Boolean
        get() = backStack.size > 1


    fun navigate(screen: Screen) {
        if (screen != current) backStack += screen
    }


    fun replace(screen: Screen) {
        backStack.clear()
        backStack += screen
    }


    fun back(): Boolean {
        if (!canGoBack) return false

        backStack.removeAt(backStack.lastIndex)
        return true
    }
}


@Composable
fun rememberNavigator(start: Screen): Navigator = remember { Navigator(start) }