package dev.iln.cobblemongo.ui.components.extention

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.hardShadow(
    color: Color,
    shape: Shape = RectangleShape,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp
) = drawBehind {
    val outline = shape.createOutline(size, layoutDirection, this)

    translate(offsetX.toPx(), offsetY.toPx()) {
        drawOutline(outline, color)
    }
}