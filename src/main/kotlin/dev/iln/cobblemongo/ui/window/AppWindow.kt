package dev.iln.cobblemongo.ui.window

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.CachePolicy
import coil3.request.crossfade
import dev.iln.cobblemongo.resources.Res
import dev.iln.cobblemongo.resources.icon
import dev.iln.cobblemongo.ui.screen.main.MainScreen
import dev.iln.cobblemongo.ui.theme.AppTheme
import dev.nucleusframework.application.DecoratedWindow
import dev.nucleusframework.application.NucleusBackend
import dev.nucleusframework.application.aotTraining
import dev.nucleusframework.application.NucleusWindow
import dev.nucleusframework.application.nucleusApplication
import dev.nucleusframework.core.runtime.NucleusApp
import dev.nucleusframework.window.WindowDoubleClickAction
import dev.nucleusframework.window.windowDragArea
import org.jetbrains.compose.resources.painterResource
import kotlin.time.Duration.Companion.seconds

class AppWindow(private val onInit: () -> Unit = {}) {

    companion object {
        var window: NucleusWindow? = null
            private set

        var isVisible by mutableStateOf(true)
    }


    fun createWindow() = nucleusApplication(backend = NucleusBackend.Tao) {
        onInit()
        aotTraining(duration = 30.seconds)

        DecoratedWindow(
            onCloseRequest = ::exitApplication,
            title = NucleusApp.appName ?: "CobblemonGo",
            icon = painterResource(Res.drawable.icon),
            visible = isVisible,
            state = rememberWindowState(
                size = DpSize(1152.dp, 648.dp),
                position = WindowPosition.Aligned(Alignment.Center)
            ),
            resizable = false,
            transparent = true,
            undecorated = true
        ) {
            window = nucleusWindow
            AppContent(onClose = ::exitApplication)
        }
    }


    @Composable
    private fun AppContent(onClose: () -> Unit) = Box(
        modifier = Modifier
            .fillMaxSize()
            .windowDragArea(doubleClickAction = WindowDoubleClickAction.None)
    ) {
        setSingletonImageLoaderFactory { context ->
            ImageLoader.Builder(context)
                .diskCachePolicy(CachePolicy.DISABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .crossfade(false)
                .build()
        }

        AppTheme {
            MainScreen(
                onMinimize = { window?.setMinimized(true) },
                onClose = onClose
            )
        }
    }
}