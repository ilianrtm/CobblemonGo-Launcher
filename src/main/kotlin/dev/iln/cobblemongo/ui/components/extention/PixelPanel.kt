package dev.iln.cobblemongo.ui.components.extention

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.iln.cobblemongo.ui.theme.AppTheme.colors
import dev.iln.cobblemongo.ui.theme.AppTheme.shapes

@Composable
fun Modifier.pixelPanel(
    shape: Shape = shapes.extraLarge,
    shadowColor: Color = colors.teal600,
    shadowOffset: Dp = 8.dp,
) = this
    .hardShadow(shadowColor, shape, offsetX = shadowOffset, offsetY = shadowOffset)
    .background(colors.purple600, shape)