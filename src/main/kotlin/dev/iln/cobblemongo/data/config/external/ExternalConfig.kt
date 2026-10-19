package dev.iln.cobblemongo.data.config.external

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExternalConfig(
    val minecraftVersion: String,
    val fabricVersion: String,
    val javaVersion: Int,
    val server: ServerAddress,
    val resources: Resources,
    val resourcePacks: List<String> = emptyList(),
    val news: List<NewsItem> = emptyList(),
)


@Serializable
data class Resources(
    val externalFilesURL: String,
    val modsURL: String,
)


@Serializable
data class ServerAddress(
    val ip: String,
    val port: Int = 25565,
) {
    override fun toString() = if (port == 25565) ip else "$ip:$port"
}


@Serializable
data class NewsItem(
    val category: NewsCategory,
    val date: String,
    val title: String,
    val description: String,
)


@Serializable
enum class NewsCategory {
    @SerialName("modpack") Modpack,
    @SerialName("event") Event,
    @SerialName("server") Server
}