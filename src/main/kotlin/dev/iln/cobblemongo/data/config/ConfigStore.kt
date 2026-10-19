package dev.iln.cobblemongo.data.config

import dev.iln.cobblemongo.core.Paths
import dev.iln.cobblemongo.core.log.Logger
import dev.iln.cobblemongo.core.log.Notifier
import dev.iln.cobblemongo.resources.Res
import dev.iln.cobblemongo.resources.notif_config_save_failed_message
import dev.iln.cobblemongo.resources.notif_config_save_failed_title
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json
import java.nio.file.StandardCopyOption
import kotlin.io.path.*

object ConfigStore {

    private val file = Paths.config

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        prettyPrint = true
        coerceInputValues = true
    }

    private val _config = MutableStateFlow(load())
    val config = _config.asStateFlow()


    fun update(transform: (LocalConfig) -> LocalConfig): Boolean {
        val updated = transform(_config.value).coercedToLimits()

        return runCatching { write(updated) }
            .onSuccess { _config.value = updated }
            .onFailure {
                Logger.error("Cannot update the config", it)

                Notifier.error(
                    Res.string.notif_config_save_failed_title,
                    Res.string.notif_config_save_failed_message
                )
            }
            .isSuccess
    }


    private fun load(): LocalConfig {
        if (file.notExists()) return LocalConfig()

        return runCatching { json.decodeFromString<LocalConfig>(file.readText()) }
            .getOrDefault(LocalConfig())
            .coercedToLimits()
    }


    private fun write(config: LocalConfig) {
        file.createParentDirectories()

        val temp = file.resolveSibling("${file.fileName}.tmp")
        temp.writeText(json.encodeToString(config))

        temp.moveTo(file, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING)
    }
}