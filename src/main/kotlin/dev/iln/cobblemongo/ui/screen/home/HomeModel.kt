package dev.iln.cobblemongo.ui.screen.home

import androidx.compose.runtime.Composable
import dev.iln.cobblemongo.core.UpdateState
import dev.iln.cobblemongo.data.config.LocalConfig
import dev.iln.cobblemongo.data.config.external.ExternalConfigState
import dev.iln.cobblemongo.data.config.external.ServerAddress
import dev.iln.cobblemongo.data.game.GameManager
import dev.iln.cobblemongo.data.game.GameState
import dev.iln.cobblemongo.data.service.StatusService
import dev.iln.cobblemongo.resources.*
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import kotlin.math.roundToInt
import kotlin.time.Duration.Companion.minutes

data class UiText(
    val resource: StringResource,
    val args: List<Any> = emptyList()
) {

    @Composable
    fun resolve() = stringResource(resource, *args.toTypedArray())
}


data class ProgressBarState(
    val label: UiText,
    val progress: Float,
    val active: Boolean = false
)


class HomeModel {

    private val megabyte = 1024.0 * 1024.0
    private val gigabyte = 1024.0 * megabyte


    fun canPlay(update: UpdateState, external: ExternalConfigState, game: GameState) =
        update == UpdateState.Idle && external is ExternalConfigState.Ready && game == GameState.Idle


    fun play(external: ExternalConfigState, local: LocalConfig, username: String?) {
        val config = (external as? ExternalConfigState.Ready)?.config ?: return
        GameManager.play(config, local, username.orEmpty())
    }


    suspend fun watchServerStatus(address: ServerAddress) {
        while (true) {
            StatusService.refresh(address)
            delay(5.minutes)
        }
    }


    fun progressBar(update: UpdateState, external: ExternalConfigState, game: GameState) = when (update) {
            UpdateState.Checking -> ProgressBarState(
                label = UiText(Res.string.update_checking),
                progress = 0f,
                active = true
            )

            is UpdateState.Downloading -> ProgressBarState(
                label = UiText(
                    resource = Res.string.update_downloading,
                    args = listOf(update.percent)
                ),
                progress = update.percent / 100f,
                active = true
            )

            UpdateState.Installing -> ProgressBarState(
                label = UiText(Res.string.update_installing),
                progress = 1f,
                active = true
            )

            UpdateState.Idle -> when (external) {
                ExternalConfigState.Loading -> ProgressBarState(
                    label = UiText(Res.string.home_connecting),
                    progress = 0f,
                    active = true
                )

                ExternalConfigState.Failed -> ProgressBarState(
                    label = UiText(Res.string.home_server_unreachable),
                    progress = 0f
                )

                is ExternalConfigState.Ready -> when (game) {
                    GameState.Idle -> ProgressBarState(
                        label = UiText(Res.string.home_ready),
                        progress = 1f
                    )

                    is GameState.Preparing -> ProgressBarState(
                        label = UiText(game.step),
                        progress = game.progress,
                        active = true
                    )

                    GameState.Running -> ProgressBarState(
                        label = UiText(Res.string.game_running),
                        progress = 1f
                    )
                }
            }
        }


    fun launchingText(game: GameState) = when (game) {
        GameState.Idle -> null

        is GameState.Preparing -> if (game.totalBytes > 0) {
            downloadText(
                downloaded = game.downloadedBytes,
                total = game.totalBytes
            )
        } else {
            UiText(Res.string.home_launching)
        }

        GameState.Running -> UiText(Res.string.home_launching)
    }


    private fun downloadText(downloaded: Long, total: Long) = if (total >= gigabyte) {
        UiText(
            resource = Res.string.home_download_gb,
            args = listOf("%.1f".format(downloaded / gigabyte), "%.1f".format(total / gigabyte))
        )
    } else {
        UiText(
            resource = Res.string.home_download_mb,
            args = listOf((downloaded / megabyte).roundToInt(), (total / megabyte).roundToInt())
        )
    }
}