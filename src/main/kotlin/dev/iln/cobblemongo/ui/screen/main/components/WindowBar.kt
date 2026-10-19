package dev.iln.cobblemongo.ui.screen.main.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import dev.iln.cobblemongo.resources.*
import dev.iln.cobblemongo.ui.components.AppText
import dev.iln.cobblemongo.ui.components.extention.pixelClickable
import dev.iln.cobblemongo.ui.theme.AppTheme.colors
import dev.iln.cobblemongo.ui.theme.AppTheme.shapes
import dev.iln.cobblemongo.ui.theme.AppTheme.typography
import dev.nucleusframework.core.runtime.NucleusApp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun WindowBar(onMinimize: () -> Unit, onClose: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 14.dp)
    ) {
        Image(
            painter = painterResource(Res.drawable.icon),
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )

        Spacer(Modifier.width(10.dp))

        AppText(
            text = NucleusApp.appName ?: "CobblemonGo",
            style = typography.title,
            color = colors.purple600
        )

        Spacer(Modifier.weight(1f))

        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            WindowButton(
                icon = Res.drawable.window_minimize_icon,
                label = stringResource(Res.string.window_minimize),
                onClick = onMinimize
            )

            WindowButton(
                icon = Res.drawable.window_close_icon,
                label = stringResource(Res.string.window_close),
                onClick = onClose
            )
        }
    }
}


@Composable
private fun WindowButton(
    icon: DrawableResource,
    label: String,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    val hoverAlpha by animateFloatAsState(if (hovered) 0.12f else 0f)


    Box(
        modifier = Modifier
            .size(34.dp)
            .background(
                color = colors.purple600.copy(hoverAlpha),
                shape = shapes.medium
            )
            .pixelClickable(onClick, interactionSource = interactionSource)
            .semantics { contentDescription = label },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(14.dp)
        )
    }
}