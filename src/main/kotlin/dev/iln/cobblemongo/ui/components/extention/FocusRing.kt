package dev.iln.cobblemongo.ui.components.extention

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.focusRing(
    visible: Boolean,
    color: Color,
    shape: Shape,
    width: Dp = 4.dp,
) = drawBehind {
    if (!visible) return@drawBehind

    val ring = width.toPx()
    val outline = shape.createOutline(Size(size.width + ring * 2, size.height + ring * 2), layoutDirection, this)

    translate(-ring, -ring) {
        drawOutline(outline, color)
    }
}