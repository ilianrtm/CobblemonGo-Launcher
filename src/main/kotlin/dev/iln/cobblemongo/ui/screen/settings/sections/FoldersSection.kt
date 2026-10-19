package dev.iln.cobblemongo.ui.screen.settings.sections

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import dev.iln.cobblemongo.resources.*
import dev.iln.cobblemongo.ui.components.AppText
import dev.iln.cobblemongo.ui.components.PixelTextField
import dev.iln.cobblemongo.ui.components.extention.pixelClickable
import dev.iln.cobblemongo.ui.screen.settings.components.SettingsCard
import dev.iln.cobblemongo.ui.theme.AppTheme.colors
import dev.iln.cobblemongo.ui.theme.AppTheme.shapes
import dev.iln.cobblemongo.ui.theme.AppTheme.typography
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun FoldersSection(
    gameDirectory: String,
    onOpenGameDirectory: () -> Unit,
    onOpenLogs: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = shapes.medium
    val openGameFolderLabel = stringResource(Res.string.settings_open_game_folder)


    SettingsCard(
        title = stringResource(Res.string.settings_folders),
        modifier = modifier,
        spacing = 10.dp
    ) {
        AppText(
            text = stringResource(Res.string.settings_game_folder),
            style = typography.caption
        )

        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            PixelTextField(
                value = gameDirectory,
                onValueChange = {},
                textStyle = typography.caption,
                modifier = Modifier.weight(1f).height(40.dp),
                readOnly = true
            )

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(colors.purple400, shape)
                    .pixelClickable(onOpenGameDirectory)
                    .semantics { contentDescription = openGameFolderLabel },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(Res.drawable.folder_icon),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
        }


        Row(
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth()
                .height(40.dp)
                .border(2.dp, colors.teal400, shape)
                .pixelClickable(onOpenLogs),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(Res.drawable.logs_icon),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )

            AppText(
                text = stringResource(Res.string.settings_open_logs_folder),
                style = typography.captionMedium,
                color = colors.teal300
            )
        }


        AppText(
            text = stringResource(Res.string.settings_logs_hint),
            style = typography.caption,
            color = colors.purple100
        )
    }
}