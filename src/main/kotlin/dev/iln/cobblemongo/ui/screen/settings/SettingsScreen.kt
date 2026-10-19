package dev.iln.cobblemongo.ui.screen.settings

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.iln.cobblemongo.core.AutoUpdater
import dev.iln.cobblemongo.core.Paths
import dev.iln.cobblemongo.ui.components.extention.pixelPanel
import dev.iln.cobblemongo.ui.navigation.LocalNavigator
import dev.iln.cobblemongo.ui.navigation.Screen
import dev.iln.cobblemongo.ui.screen.settings.components.SettingsHeader
import dev.iln.cobblemongo.ui.screen.settings.components.SettingsScrollArea
import dev.iln.cobblemongo.ui.screen.settings.sections.DisplaySection
import dev.iln.cobblemongo.ui.screen.settings.sections.FoldersSection
import dev.iln.cobblemongo.ui.screen.settings.sections.LanguageSection
import dev.iln.cobblemongo.ui.screen.settings.sections.LaunchSection
import dev.iln.cobblemongo.ui.screen.settings.sections.LauncherSection
import dev.iln.cobblemongo.ui.screen.settings.sections.MemorySection
import dev.iln.cobblemongo.ui.theme.AppTheme.shapes
import dev.nucleusframework.core.runtime.NucleusApp
import java.awt.Desktop

@Composable
fun SettingsScreen() {
    val navigator = LocalNavigator.current
    val model = remember { SettingsModel() }
    val isUpToDate by AutoUpdater.isUpToDate.collectAsStateWithLifecycle()

    val goHome = {
        model.onLeave()
        if (!navigator.back()) navigator.replace(Screen.Home)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, top = 16.dp, end = 24.dp, bottom = 22.dp)
            .pixelPanel(shapes.extraLarge)
            .padding(22.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SettingsHeader(
            isSaved = model.isSaved,
            onBack = goHome,
            onSave = model::onSave
        )

        SettingsScrollArea(Modifier.weight(1f).fillMaxWidth()) {
            BoxWithConstraints(Modifier.fillMaxWidth()) {
                val columnWidth = (maxWidth - 24.dp) / 3


                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.height(IntrinsicSize.Min),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        MemorySection(
                            ram = model.ram,
                            maxMemoryGb = model.maxMemoryGb,
                            recommendedMemoryGb = model.recommendedMemoryGb,
                            totalMemoryGb = model.totalMemoryGb,
                            onMemoryChange = model::onMemoryChange,
                            modifier = Modifier.width(columnWidth * 2 + 12.dp).fillMaxHeight()
                        )

                        DisplaySection(
                            width = model.windowWidth,
                            height = model.windowHeight,
                            fullscreen = model.fullscreen,
                            onWidthChange = model::onWindowWidthChange,
                            onHeightChange = model::onWindowHeightChange,
                            onFullscreenChange = model::onFullscreenChange,
                            modifier = Modifier.width(columnWidth).fillMaxHeight()
                        )
                    }


                    Row(
                        modifier = Modifier.height(IntrinsicSize.Min),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        LaunchSection(
                            autoJoin = model.autoJoin,
                            afterLaunch = model.afterLaunch,
                            onAutoJoinChange = model::onAutoJoinChange,
                            onAfterLaunchChange = model::onAfterLaunchChange,
                            modifier = Modifier.width(columnWidth).fillMaxHeight()
                        )

                        FoldersSection(
                            gameDirectory = model.gameDirectory,
                            onOpenGameDirectory = { Desktop.getDesktop().open(Paths.root.toFile()) },
                            onOpenLogs = { Desktop.getDesktop().open(Paths.logs.toFile()) },
                            modifier = Modifier.width(columnWidth).fillMaxHeight()
                        )

                        LanguageSection(
                            language = model.language,
                            onLanguageChange = model::onLanguageChange,
                            modifier = Modifier.width(columnWidth).fillMaxHeight()
                        )
                    }
                }
            }


            LauncherSection(
                version = NucleusApp.version ?: "dev",
                isUpToDate = isUpToDate
            )
        }
    }
}