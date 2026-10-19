package dev.iln.cobblemongo.data.game.updater

import dev.iln.cobblemongo.resources.*
import fr.flowarg.flowupdater.download.Step
import org.jetbrains.compose.resources.StringResource

val Step.label: StringResource
    get() = when (this) {
        Step.INTEGRATION -> Res.string.game_step_preparing
        Step.READ -> Res.string.game_step_reading
        Step.DL_LIBS -> Res.string.game_step_libraries
        Step.DL_ASSETS -> Res.string.game_step_assets
        Step.EXTRACT_NATIVES -> Res.string.game_step_natives
        Step.MOD_LOADER -> Res.string.game_step_mod_loader
        Step.MOD_PACK -> Res.string.game_step_modpack
        Step.MODS -> Res.string.game_step_mods
        Step.EXTERNAL_FILES -> Res.string.game_step_external_files
        Step.POST_EXECUTIONS -> Res.string.game_step_finishing
        Step.END -> Res.string.home_launching
    }