package dev.iln.cobblemongo.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import dev.iln.cobblemongo.ui.components.extention.hardShadow
import dev.iln.cobblemongo.ui.components.extention.pixelClickable
import dev.iln.cobblemongo.ui.theme.AppTheme.colors
import dev.iln.cobblemongo.ui.theme.AppTheme.shapes
import dev.iln.cobblemongo.ui.theme.AppTheme.typography

@Composable
fun PixelButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textStyle: TextStyle = typography.button,
    shape: Shape = shapes.medium,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    leadingIcon: (@Composable () -> Unit)? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val hovered by interactionSource.collectIsHoveredAsState()

    val offset by animateDpAsState(
        targetValue = when {
            !enabled -> 0.dp
            pressed -> 4.dp
            hovered -> (-2).dp
            else -> 0.dp
        },
        animationSpec = tween(if (pressed) 80 else 140)
    )

    val background by animateColorAsState(
        targetValue = when {
            !enabled -> colors.purple500
            hovered && !pressed -> lerp(colors.orange300, colors.white, 0.08f)
            else -> colors.orange300
        },
        animationSpec = tween(140)
    )


    Row(
        modifier = modifier
            .graphicsLayer { translationY = offset.toPx() }
            .then(if (enabled) Modifier.hardShadow(colors.orange400, shape, offsetY = 4.dp - offset) else Modifier)
            .background(background, shape)
            .border(4.dp, if (enabled) colors.yellow100 else colors.purple400, shape)
            .pixelClickable(onClick, enabled = enabled, interactionSource = interactionSource)
            .padding(contentPadding),
        horizontalArrangement = Arrangement.spacedBy(14.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingIcon?.invoke()

        AppText(
            text = text,
            style = textStyle,
            color = if (enabled) colors.white else colors.purple200
        )
    }
}