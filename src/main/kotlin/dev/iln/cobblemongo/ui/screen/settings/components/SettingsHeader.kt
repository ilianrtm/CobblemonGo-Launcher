package dev.iln.cobblemongo.ui.screen.settings.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.iln.cobblemongo.resources.*
import dev.iln.cobblemongo.ui.components.AppText
import dev.iln.cobblemongo.ui.components.PixelButton
import dev.iln.cobblemongo.ui.components.PixelTile
import dev.iln.cobblemongo.ui.theme.AppTheme.colors
import dev.iln.cobblemongo.ui.theme.AppTheme.typography
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SettingsHeader(
    isSaved: Boolean,
    onBack: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BackButton(onBack)

            AppText(
                text = stringResource(Res.string.settings_title),
                style = typography.displayMedium
            )
        }


        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isSaved) {
                AppText(
                    text = stringResource(Res.string.settings_saved),
                    style = typography.caption,
                    color = colors.teal300
                )
            }

            PixelButton(
                text = stringResource(Res.string.settings_save),
                onClick = onSave,
                textStyle = typography.title,
                contentPadding = PaddingValues(horizontal = 24.dp),
                modifier = Modifier.height(46.dp)
            )
        }
    }
}


@Composable
private fun BackButton(onClick: () -> Unit) {
    PixelTile(
        onClick = onClick,
        modifier = Modifier.size(44.dp),
        contentDescription = stringResource(Res.string.settings_back)
    ) {
        Image(
            painter = painterResource(Res.drawable.back_icon),
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
    }
}