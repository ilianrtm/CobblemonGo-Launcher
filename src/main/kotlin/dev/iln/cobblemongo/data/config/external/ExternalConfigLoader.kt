package dev.iln.cobblemongo.data.config.external

import dev.iln.cobblemongo.core.log.Logger
import dev.iln.cobblemongo.core.log.Notifier
import dev.iln.cobblemongo.data.service.WebService
import dev.iln.cobblemongo.resources.Res
import dev.iln.cobblemongo.resources.notif_external_config_failed_message
import dev.iln.cobblemongo.resources.notif_external_config_failed_title
import dev.nucleusframework.core.runtime.ExecutableRuntime
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.future.await
import kotlinx.serialization.json.Json
import java.net.http.HttpResponse
import kotlin.io.path.Path
import kotlin.io.path.absolutePathString
import kotlin.io.path.exists
import kotlin.io.path.readText

sealed interface ExternalConfigState {
    data object Loading : ExternalConfigState
    data class Ready(val config: ExternalConfig) : ExternalConfigState
    data object Failed : ExternalConfigState
}


object ExternalConfigLoader {

    private val json = Json { ignoreUnknownKeys = true }
    private val localConfigFile = Path("external/config.json")

    private val _state = MutableStateFlow<ExternalConfigState>(ExternalConfigState.Loading)
    val state = _state.asStateFlow()


    suspend fun load(url: String) {
        _state.value = ExternalConfigState.Loading

        try {
            val config = fetch(url)

            Logger.info("External config loaded")
            _state.value = ExternalConfigState.Ready(config)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            _state.value = ExternalConfigState.Failed

            Logger.error("Failed to load external configuration", e)
            Notifier.error(Res.string.notif_external_config_failed_title, Res.string.notif_external_config_failed_message)
        }
    }


    private suspend fun fetch(url: String): ExternalConfig {
        if (ExecutableRuntime.isDev() && localConfigFile.exists()) {
            Logger.info("Using local external config: ${localConfigFile.absolutePathString()}")
            return json.decodeFromString<ExternalConfig>(localConfigFile.readText())
        }

        val response = WebService.requestAsync(url, HttpResponse.BodyHandlers.ofString()).await()

        check(response.statusCode() == 200) {
            "HTTP ${response.statusCode()} for $url"
        }

        return json.decodeFromString<ExternalConfig>(response.body())
    }
}