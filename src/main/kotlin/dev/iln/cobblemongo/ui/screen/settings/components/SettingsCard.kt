package dev.iln.cobblemongo.ui.screen.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.iln.cobblemongo.ui.components.AppText
import dev.iln.cobblemongo.ui.theme.AppTheme.colors
import dev.iln.cobblemongo.ui.theme.AppTheme.shapes
import dev.iln.cobblemongo.ui.theme.AppTheme.typography

@Composable
fun SettingsCard(
    title: String,
    modifier: Modifier = Modifier,
    spacing: Dp = 12.dp,
    titleTrailing: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .settingsCardSurface()
            .padding(horizontal = 18.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(spacing)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            AppText(
                text = title,
                style = typography.title
            )

            titleTrailing?.invoke()
        }

        content()
    }
}


@Composable
fun Modifier.settingsCardSurface(): Modifier {
    val shape = shapes.medium
    return background(colors.purple500, shape).border(4.dp, colors.purple400, shape)
}