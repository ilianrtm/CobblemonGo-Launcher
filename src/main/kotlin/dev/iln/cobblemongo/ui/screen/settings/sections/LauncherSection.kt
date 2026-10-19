package dev.iln.cobblemongo.ui.screen.settings.sections

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import dev.iln.cobblemongo.resources.*
import dev.iln.cobblemongo.ui.components.AppText
import dev.iln.cobblemongo.ui.screen.settings.components.settingsCardSurface
import dev.iln.cobblemongo.ui.theme.AppTheme.colors
import dev.iln.cobblemongo.ui.theme.AppTheme.shapes
import dev.iln.cobblemongo.ui.theme.AppTheme.typography
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun LauncherSection(
    version: String,
    isUpToDate: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .settingsCardSurface()
            .padding(horizontal = 18.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(22.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(Res.drawable.icon),
            contentDescription = null,
            modifier = Modifier.size(40.dp, 42.dp)
        )

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            AppText(
                text = stringResource(Res.string.settings_launcher_name),
                style = typography.title
            )

            AppText(
                text = stringResource(Res.string.settings_launcher_auto_update),
                style = typography.caption,
                color = colors.purple100
            )
        }


        Spacer(Modifier.weight(1f))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppText(
                text = stringResource(Res.string.settings_launcher_version, version),
                style = typography.displaySmall,
                color = colors.yellow100
            )

            if (isUpToDate) {
                Row(
                    modifier = Modifier
                        .height(30.dp)
                        .background(colors.teal700, shapes.small)
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(Res.drawable.check_icon),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(colors.teal300),
                        modifier = Modifier.size(12.dp)
                    )

                    AppText(
                        text = stringResource(Res.string.settings_launcher_up_to_date),
                        style = typography.captionMedium,
                        color = colors.teal300
                    )
                }
            }
        }
    }
}