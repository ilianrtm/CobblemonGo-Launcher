package dev.iln.cobblemongo.ui.screen.settings.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.iln.cobblemongo.resources.*
import dev.iln.cobblemongo.ui.components.AppText
import dev.iln.cobblemongo.ui.screen.settings.components.NumberField
import dev.iln.cobblemongo.ui.screen.settings.components.SettingSwitch
import dev.iln.cobblemongo.ui.screen.settings.components.SettingsCard
import dev.iln.cobblemongo.ui.theme.AppTheme.colors
import dev.iln.cobblemongo.ui.theme.AppTheme.typography
import org.jetbrains.compose.resources.stringResource

@Composable
fun DisplaySection(
    width: String,
    height: String,
    fullscreen: Boolean,
    onWidthChange: (String) -> Unit,
    onHeightChange: (String) -> Unit,
    onFullscreenChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    SettingsCard(
        title = stringResource(Res.string.settings_display),
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            NumberField(
                label = stringResource(Res.string.settings_width),
                value = width,
                onValueChange = onWidthChange,
                modifier = Modifier.weight(1f),
                enabled = !fullscreen
            )

            AppText(
                text = "x",
                modifier = Modifier.padding(bottom = 8.dp),
                style = typography.titleSmall,
                color = if (fullscreen) colors.purple200 else colors.purple100
            )

            NumberField(
                label = stringResource(Res.string.settings_height),
                value = height,
                onValueChange = onHeightChange,
                modifier = Modifier.weight(1f),
                enabled = !fullscreen
            )
        }


        SettingSwitch(
            label = stringResource(Res.string.settings_fullscreen),
            checked = fullscreen,
            onCheckedChange = onFullscreenChange
        )
    }
}