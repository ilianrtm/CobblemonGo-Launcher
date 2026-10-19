package dev.iln.cobblemongo.ui.screen.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.iln.cobblemongo.core.Paths
import dev.iln.cobblemongo.core.log.Notifier
import dev.iln.cobblemongo.data.config.AfterLaunchAction
import dev.iln.cobblemongo.data.config.ConfigStore
import dev.iln.cobblemongo.data.config.ConfigStore.config
import dev.iln.cobblemongo.data.config.Language
import dev.iln.cobblemongo.data.config.LocalConfig
import dev.iln.cobblemongo.data.config.Window
import dev.iln.cobblemongo.data.service.Device
import dev.iln.cobblemongo.resources.Res
import dev.iln.cobblemongo.resources.notif_changes_discarded_message
import dev.iln.cobblemongo.resources.notif_changes_discarded_title

class SettingsModel(initial: LocalConfig = config.value) {

    val maxMemoryGb = Device.maxMemoryGb
    val recommendedMemoryGb = Device.recommendedMemoryGb
    val totalMemoryGb = Device.totalMemoryGb
    var ram by mutableStateOf(initial.ram)
        private set

    var windowWidth by mutableStateOf(initial.window.width.toString())
        private set
    var windowHeight by mutableStateOf(initial.window.height.toString())
        private set
    var fullscreen by mutableStateOf(initial.window.fullscreen)
        private set

    var autoJoin by mutableStateOf(initial.autoJoin)
        private set

    var afterLaunch by mutableStateOf(initial.afterLaunch)
        private set

    var language by mutableStateOf(initial.language)
        private set

    val gameDirectory: String = Paths.root.toString()

    var isSaved by mutableStateOf(false)
        private set


    val hasUnsavedChanges: Boolean
        get() = draft(config.value).coercedToLimits() != config.value


    fun onMemoryChange(value: Int) = edit {
        ram = value.coerceIn(LocalConfig.MIN_MEMORY_GB, maxMemoryGb)
    }


    fun onWindowWidthChange(value: String) = edit {
        windowWidth = value
    }


    fun onWindowHeightChange(value: String) = edit {
        windowHeight = value
    }


    fun onFullscreenChange(value: Boolean) = edit {
        fullscreen = value
    }


    fun onAutoJoinChange(value: Boolean) = edit {
        autoJoin = value
    }


    fun onAfterLaunchChange(value: AfterLaunchAction) = edit {
        afterLaunch = value
    }


    fun onLanguageChange(value: Language) = edit {
        language = value
    }


    fun onSave() {
        isSaved = ConfigStore.update(::draft)

        if (isSaved) {
            val saved = config.value

            windowWidth = saved.window.width.toString()
            windowHeight = saved.window.height.toString()
        }
    }


    fun onLeave() {
        if (hasUnsavedChanges) {
            Notifier.info(Res.string.notif_changes_discarded_title, Res.string.notif_changes_discarded_message)
        }
    }


    private fun draft(current: LocalConfig) = current.copy(
        ram = ram,
        window = Window(
            width = windowWidth.toIntOrNull() ?: current.window.width,
            height = windowHeight.toIntOrNull() ?: current.window.height,
            fullscreen = fullscreen
        ),
        autoJoin = autoJoin,
        afterLaunch = afterLaunch,
        language = language,
    )


    private fun edit(block: () -> Unit) {
        block()
        isSaved = false
    }
}