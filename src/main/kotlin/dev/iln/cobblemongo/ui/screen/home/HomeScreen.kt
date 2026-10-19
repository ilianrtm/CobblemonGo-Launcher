package dev.iln.cobblemongo.ui.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.iln.cobblemongo.core.AutoUpdater
import dev.iln.cobblemongo.data.config.ConfigStore.config
import dev.iln.cobblemongo.data.config.external.ExternalConfigLoader
import dev.iln.cobblemongo.data.config.external.ExternalConfigState
import dev.iln.cobblemongo.data.game.GameManager
import dev.iln.cobblemongo.data.service.StatusService
import dev.iln.cobblemongo.data.service.Session
import dev.iln.cobblemongo.resources.*
import dev.iln.cobblemongo.ui.navigation.LocalNavigator
import dev.iln.cobblemongo.ui.navigation.Screen
import dev.iln.cobblemongo.ui.screen.home.components.Hotbar
import dev.iln.cobblemongo.ui.screen.home.components.ProgressBar
import dev.iln.cobblemongo.ui.screen.home.components.NewsSection
import dev.iln.cobblemongo.ui.screen.home.components.ServerOverview
import dev.iln.cobblemongo.ui.screen.main.components.LandscapeLevel
import dev.iln.cobblemongo.ui.screen.main.components.hillsHeight
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeScreen() {
    val navigator = LocalNavigator.current
    val uriHandler = LocalUriHandler.current

    val discordUrl = stringResource(Res.string.discord_url)
    val model = remember { HomeModel() }

    val updateState by AutoUpdater.state.collectAsStateWithLifecycle()
    val username by Session.username.collectAsStateWithLifecycle()
    val localConfig by config.collectAsStateWithLifecycle()
    val externalConfig by ExternalConfigLoader.state.collectAsStateWithLifecycle()
    val serverStatus by StatusService.status.collectAsStateWithLifecycle()
    val gameState by GameManager.state.collectAsStateWithLifecycle()

    val serverConfig = (externalConfig as? ExternalConfigState.Ready)?.config
    val progressBar = model.progressBar(updateState, externalConfig, gameState)


    LaunchedEffect(serverConfig?.server) {
        serverConfig?.server?.let { model.watchServerStatus(it) }
    }


    Column(Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(start = 36.dp, top = 14.dp, end = 36.dp, bottom = LandscapeLevel.High.hillsHeight),
            horizontalArrangement = Arrangement.spacedBy(28.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ServerOverview(
                isServerOnline = serverStatus?.isOnline,
                onlinePlayers = serverStatus?.onlinePlayers,
                modifier = Modifier.weight(1f)
            )

            NewsSection(
                news = serverConfig?.news.orEmpty(),
                modifier = Modifier.width(300.dp)
            )
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 22.dp, top = 4.dp, end = 22.dp, bottom = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ProgressBar(
                label = progressBar.label.resolve(),
                progress = progressBar.progress,
                active = progressBar.active
            )

            Hotbar(
                username = username.orEmpty(),
                ram = localConfig.ram,
                launchingText = model.launchingText(gameState)?.resolve(),
                canPlay = model.canPlay(updateState, externalConfig, gameState),
                onAccount = { navigator.navigate(Screen.Login) },
                onMemory = { navigator.navigate(Screen.Settings) },
                onPlay = { model.play(externalConfig, localConfig, username) },
                onDiscord = { uriHandler.openUri(discordUrl) },
                onSettings = { navigator.navigate(Screen.Settings) }
            )
        }
    }
}
