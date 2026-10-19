package dev.iln.cobblemongo.core

import dev.iln.cobblemongo.core.log.Logger
import dev.iln.cobblemongo.core.log.Notifier
import dev.iln.cobblemongo.resources.Res
import dev.iln.cobblemongo.resources.notif_update_failed_message
import dev.iln.cobblemongo.resources.notif_update_failed_title
import dev.nucleusframework.updater.NucleusUpdater
import dev.nucleusframework.updater.UpdateResult
import dev.nucleusframework.updater.provider.GenericProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed interface UpdateState {
    data object Idle : UpdateState
    data object Checking : UpdateState
    data class Downloading(val percent: Int) : UpdateState
    data object Installing : UpdateState
}


object AutoUpdater {

    private val _state = MutableStateFlow<UpdateState>(UpdateState.Idle)
    val state = _state.asStateFlow()

    private val _isUpToDate = MutableStateFlow(false)
    val isUpToDate = _isUpToDate.asStateFlow()


    suspend fun checkAndInstall(baseUrl: String) {
        val updater = runCatching { NucleusUpdater { provider = GenericProvider(baseUrl) } }
            .getOrElse {
                Logger.error("Invalid update URL: $baseUrl", it)
                return
            }

        try {
            _state.value = UpdateState.Checking

            when (val result = updater.checkForUpdates()) {
                is UpdateResult.NotAvailable -> {
                    _isUpToDate.value = true
                    Logger.info("Launcher up to date (${updater.currentVersion})")
                }

                is UpdateResult.Error -> fail(result.exception)

                is UpdateResult.Available -> {
                    Logger.info("Updating ${updater.currentVersion} → ${result.info.version}")

                    updater.downloadUpdate(result.info).collect { progress ->
                        _state.value = UpdateState.Downloading(progress.percent.toInt())

                        progress.file?.let {
                            _state.value = UpdateState.Installing
                            updater.installAndRestart(it)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            fail(e)
        } finally {
            if (_state.value != UpdateState.Installing) _state.value = UpdateState.Idle
        }
    }


    private fun fail(cause: Throwable) {
        Logger.error("Cannot update the launcher", cause)
        Notifier.error(Res.string.notif_update_failed_title, Res.string.notif_update_failed_message)
    }
}