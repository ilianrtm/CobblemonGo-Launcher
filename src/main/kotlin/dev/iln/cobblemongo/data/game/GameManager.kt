package dev.iln.cobblemongo.data.game

import dev.iln.cobblemongo.core.Paths
import dev.iln.cobblemongo.core.log.Logger
import dev.iln.cobblemongo.core.log.Notifier
import dev.iln.cobblemongo.data.config.AfterLaunchAction
import dev.iln.cobblemongo.data.config.LocalConfig
import dev.iln.cobblemongo.data.config.external.ExternalConfig
import dev.iln.cobblemongo.data.service.Device
import dev.iln.cobblemongo.data.service.OperatingSystem
import dev.iln.cobblemongo.data.game.updater.UpdaterCallback
import dev.iln.cobblemongo.data.game.updater.label
import dev.iln.cobblemongo.resources.*
import dev.iln.cobblemongo.ui.window.AppWindow
import fr.flowarg.flowupdater.FlowUpdater.FlowUpdaterBuilder
import fr.flowarg.flowupdater.utils.ModFileDeleter
import fr.flowarg.flowupdater.utils.UpdaterOptions
import fr.flowarg.flowupdater.versions.VanillaVersion.VanillaVersionBuilder
import fr.flowarg.flowupdater.versions.fabric.FabricVersionBuilder
import fr.flowarg.openlauncherlib.NoFramework
import fr.theshark34.openlauncherlib.JavaUtil
import fr.theshark34.openlauncherlib.minecraft.AuthInfos
import fr.theshark34.openlauncherlib.minecraft.GameFolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import java.util.*
import kotlin.io.path.exists
import kotlin.io.path.readLines
import kotlin.io.path.writeText
import kotlin.system.exitProcess

sealed interface GameState {
    data object Idle : GameState
    data class Preparing(
        val step: StringResource,
        val progress: Float,
        val downloadedBytes: Long = 0,
        val totalBytes: Long = 0
    ) : GameState
    data object Running : GameState
}


object GameManager {

    private val gameDir = Paths.root
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _state = MutableStateFlow<GameState>(GameState.Idle)
    val state = _state.asStateFlow()


    fun play(external: ExternalConfig, local: LocalConfig, username: String) {
        if (_state.value != GameState.Idle) return

        _state.value = GameState.Preparing(Res.string.game_step_java, 0f)

        scope.launch { run(external, local, username) }
    }


    private fun run(external: ExternalConfig, local: LocalConfig, username: String) {
        try {
            update(external)

            _state.value = GameState.Preparing(Res.string.home_launching, 1f)
            val process = launch(external, local, username)

            _state.value = GameState.Running
            Logger.info("Minecraft ${external.minecraftVersion} started")

            when (local.afterLaunch) {
                AfterLaunchAction.Keep -> Unit
                AfterLaunchAction.Minimize -> AppWindow.isVisible = false
                AfterLaunchAction.Close -> exitProcess(0)
            }

            val exitCode = process.waitFor()
            Logger.info("Minecraft exited with code $exitCode")
        } catch (e: Exception) {
            Logger.error("Failed to launch the game", e)
            Notifier.error(Res.string.notif_game_failed_title, Res.string.notif_game_failed_message)
        } finally {
            AppWindow.isVisible = true
            _state.value = GameState.Idle
        }
    }


    private fun update(external: ExternalConfig) {
        var step = Res.string.game_step_java

        JavaChecker(external.javaVersion).checkJava()

        val vanilla = VanillaVersionBuilder()
            .withName(external.minecraftVersion)
            .build()
        val fabric = FabricVersionBuilder()
            .withFabricVersion(external.fabricVersion)
            .withFileDeleter(ModFileDeleter(true))
            //.withMods(external.resources.modsURL)
            .build()
        val updaterOptions = UpdaterOptions.UpdaterOptionsBuilder()
            .withJavaPath(JavaUtil.getJavaCommand())
            .withVersionChecker(false)
            .build()

        val callback = UpdaterCallback(
            onUpdate = { info ->
                val progress = if (info.totalToDownloadBytes > 0) {
                    info.downloadedBytes.toFloat() / info.totalToDownloadBytes
                } else {
                    0f
                }

                _state.value = GameState.Preparing(step, progress, info.downloadedBytes, info.totalToDownloadBytes)
            },
            onStepChange = {
                step = it.label

                val previous = _state.value as? GameState.Preparing
                _state.value = GameState.Preparing(step, 0f, previous?.downloadedBytes ?: 0, previous?.totalBytes ?: 0)
            },
        )

        FlowUpdaterBuilder()
            .withUpdaterOptions(updaterOptions)
            .withVanillaVersion(vanilla)
            .withModLoaderVersion(fabric)
            //.withExternalFiles(external.resources.externalFilesURL)
            .withProgressCallback(callback)
            .build()
            .update(gameDir)

        enableResourcePacks(external.resourcePacks)
    }


    private fun launch(external: ExternalConfig, local: LocalConfig, username: String): Process {
        val gameArgs = mutableListOf<String>()
        val vmArgs = mutableListOf("-Xms1G", "-Xmx${local.ram}G")
        val auth = AuthInfos(username, "0", offlineUuid(username))

        if (Device.os == OperatingSystem.MacOS) {
            vmArgs += "-XstartOnFirstThread"
            vmArgs += "-Dorg.lwjgl.librarypath=${gameDir.resolve("natives")}"
        }

        if (local.window.fullscreen) {
            gameArgs += "--fullscreen"
        } else {
            gameArgs += listOf("--width", local.window.width.toString(), "--height", local.window.height.toString())
        }

        if (local.autoJoin) {
            gameArgs += listOf("--quickPlayMultiplayer", external.server.toString())
        }


        return NoFramework(
            gameDir,
            auth,
            GameFolder.FLOW_UPDATER_1_19_SUP,
            vmArgs,
            gameArgs
        ).launch(external.minecraftVersion, external.fabricVersion, NoFramework.ModLoader.FABRIC)
    }


    private fun enableResourcePacks(resourcePacks: List<String>) {
        if (resourcePacks.isEmpty()) return

        val optionsFile = gameDir.resolve("options.txt")
        val lines = if (optionsFile.exists()) optionsFile.readLines() else emptyList()
        val currentPacksLine = lines.firstOrNull { it.startsWith("resourcePacks:") }

        val packs = currentPacksLine
            ?.removePrefix("resourcePacks:")
            ?.removeSurrounding("[", "]")
            ?.split(",")
            ?.map { it.trim().removeSurrounding("\"") }
            ?.filter { it.isNotEmpty() }
            .orEmpty()
            .toMutableList()

        resourcePacks
            .map { "file/$it" }
            .filterNot { it in packs }
            .forEach { packs += it }

        val packsLine = "resourcePacks:[${packs.joinToString(",") { "\"$it\"" }}]"
        val updatedLines = if (currentPacksLine != null) lines.map { if (it == currentPacksLine) packsLine else it } else lines + packsLine

        optionsFile.writeText(updatedLines.joinToString("\n"))
    }


    private fun offlineUuid(username: String): String =
        UUID.nameUUIDFromBytes("OfflinePlayer:$username".toByteArray()).toString()
}