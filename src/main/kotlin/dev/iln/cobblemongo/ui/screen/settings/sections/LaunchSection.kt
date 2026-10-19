 package dev.iln.cobblemongo.ui.screen.settings.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.iln.cobblemongo.data.config.AfterLaunchAction
import dev.iln.cobblemongo.resources.*
import dev.iln.cobblemongo.ui.components.AppText
import dev.iln.cobblemongo.ui.screen.settings.components.SettingOption
import dev.iln.cobblemongo.ui.screen.settings.components.SettingSwitch
import dev.iln.cobblemongo.ui.screen.settings.components.SettingsCard
import dev.iln.cobblemongo.ui.theme.AppTheme.colors
import dev.iln.cobblemongo.ui.theme.AppTheme.typography
import org.jetbrains.compose.resources.stringResource

@Composable
fun LaunchSection(
    autoJoin: Boolean,
    afterLaunch: AfterLaunchAction,
    onAutoJoinChange: (Boolean) -> Unit,
    onAfterLaunchChange: (AfterLaunchAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    SettingsCard(
        title = stringResource(Res.string.settings_launch),
        modifier = modifier
    ) {
        SettingSwitch(
            label = stringResource(Res.string.settings_auto_join),
            checked = autoJoin,
            onCheckedChange = onAutoJoinChange
        )

        AppText(
            text = stringResource(Res.string.settings_after_launch),
            style = typography.caption,
            color = colors.purple100
        )

        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            AfterLaunchAction.entries.forEach { action ->
                SettingOption(
                    label = action.label,
                    selected = action == afterLaunch,
                    onClick = { onAfterLaunchChange(action) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}


private val AfterLaunchAction.label: String
    @Composable get() = when (this) {
        AfterLaunchAction.Keep -> stringResource(Res.string.settings_after_launch_keep)
        AfterLaunchAction.Minimize -> stringResource(Res.string.settings_after_launch_minimize)
        AfterLaunchAction.Close -> stringResource(Res.string.settings_after_launch_close)
    }