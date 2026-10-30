package dev.iln.cobblemongo.data.service

import dev.iln.cobblemongo.core.log.Logger
import dev.iln.cobblemongo.data.config.external.ServerAddress
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.future.await
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.net.http.HttpResponse

data class ServerStatus(
    val isOnline: Boolean,
    val onlinePlayers: Int,
    val maxPlayers: Int,
)


object StatusService {

    private val json = Json { ignoreUnknownKeys = true }

    private val _status = MutableStateFlow<ServerStatus?>(null)
    val status = _status.asStateFlow()


    suspend fun refresh(address: ServerAddress) {
        try {
            val url = "https://api.mcsrvstat.us/3/$address"
            val response = WebService.requestAsync(url, HttpResponse.BodyHandlers.ofString()).await()

            check(response.statusCode() == 200) {
                "HTTP ${response.statusCode()} for $url"
            }

            val serverResponse = json.decodeFromString<StatusResponse>(response.body())

            _status.value = ServerStatus(
                serverResponse.online,
                serverResponse.players?.online ?: 0,
                serverResponse.players?.max ?: 0,
            )
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Logger.error("Server status unavailable", e)
        }
    }


    @Serializable
    private data class StatusResponse(
        val online: Boolean,
        val players: Players? = null,
    )


    @Serializable
    private data class Players(
        val online: Int,
        val max: Int
    )
}