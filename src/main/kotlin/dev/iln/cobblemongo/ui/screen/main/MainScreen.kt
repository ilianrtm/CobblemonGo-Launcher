package dev.iln.cobblemongo.ui.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.iln.cobblemongo.core.AutoUpdater
import dev.iln.cobblemongo.data.config.external.ExternalConfigLoader
import dev.iln.cobblemongo.data.service.Session
import dev.nucleusframework.aot.runtime.AotRuntime
import dev.iln.cobblemongo.resources.Res
import dev.iln.cobblemongo.resources.config_url
import dev.iln.cobblemongo.resources.update_url
import dev.iln.cobblemongo.ui.navigation.NavHost
import dev.iln.cobblemongo.ui.navigation.Screen
import dev.iln.cobblemongo.ui.navigation.rememberNavigator
import dev.iln.cobblemongo.ui.screen.main.components.Clouds
import dev.iln.cobblemongo.ui.screen.main.components.Landscape
import dev.iln.cobblemongo.ui.screen.main.components.LandscapeLevel
import dev.iln.cobblemongo.ui.screen.main.components.WindowBar
import dev.iln.cobblemongo.ui.screen.main.components.toast.ToastSection
import dev.iln.cobblemongo.ui.theme.AppTheme.colors
import dev.iln.cobblemongo.ui.theme.AppTheme.shapes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Duration.Companion.seconds

@Composable
fun MainScreen(onMinimize: () -> Unit, onClose: () -> Unit) {
    val navigator = rememberNavigator(if (Session.isLoggedIn) Screen.Home else Screen.Login)
    val landscapeLevel = navigator.current.landscapeLevel
    val updateUrl = stringResource(Res.string.update_url)
    val configUrl = stringResource(Res.string.config_url)

    
    LaunchedEffect(Unit) {
        launch { ExternalConfigLoader.load(configUrl) }

        if (!AotRuntime.isTraining()) {
            launch { AutoUpdater.checkAndInstall(updateUrl) }
            return@LaunchedEffect
        }

        while (true) {
            listOf(Screen.Login, Screen.Home, Screen.Settings).forEach { screen ->
                navigator.replace(screen)
                delay(5.seconds)
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(shapes.normal)
            .background(colors.teal400)
    ) {
        Clouds(
            raised = landscapeLevel == LandscapeLevel.High,
            modifier = Modifier.fillMaxSize()
        )

        Landscape(
            level = landscapeLevel,
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth()
        )

        Column(Modifier.fillMaxSize()) {
            WindowBar(
                onMinimize = onMinimize,
                onClose = onClose
            )

            NavHost(
                navigator = navigator,
                modifier = Modifier.weight(1f).fillMaxWidth()
            )
        }


        ToastSection(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 18.dp, bottom = 18.dp)
        )
    }
}


private val Screen.landscapeLevel: LandscapeLevel
    get() = when (this) {
        Screen.Login -> LandscapeLevel.Low
        Screen.Home -> LandscapeLevel.High
        Screen.Settings -> LandscapeLevel.Hidden
    }