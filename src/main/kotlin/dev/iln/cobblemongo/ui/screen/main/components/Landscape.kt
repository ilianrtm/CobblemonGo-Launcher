package dev.iln.cobblemongo.ui.screen.main.components

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.iln.cobblemongo.ui.theme.AppTheme.colors

enum class LandscapeLevel(val hillScale: Float, val groundHeight: Dp) {
    Hidden(hillScale = 0f, groundHeight = 0.dp),
    Low(hillScale = 0.38f, groundHeight = 34.dp),
    High(hillScale = 0.68f, groundHeight = 150.dp),
}


val LandscapeLevel.hillsHeight: Dp
    get() = (MAX_HILL_HEIGHT * hillScale).dp


val SceneEasing = CubicBezierEasing(0.2f, 0.8f, 0.2f, 1f)

private const val MAX_HILL_HEIGHT = 80
private val BackHills = intArrayOf(52, 64, 64, 72, 80, 80, 68, 60, 52, 56, 64, 72, 80, 80, 72, 60, 52, 60, 72, 80, 72, 64, 52, 52)
private val FrontHills = intArrayOf(36, 44, 44, 52, 60, 60, 52, 44, 36, 36, 44, 52, 60, 60, 52, 44, 36, 44, 52, 60, 52, 44, 36, 36)


@Composable
fun Landscape(
    level: LandscapeLevel,
    modifier: Modifier = Modifier,
) {
    val hillScale by animateFloatAsState(level.hillScale, tween(360, delayMillis = 120, easing = SceneEasing))
    val groundHeight by animateDpAsState(level.groundHeight, tween(360, delayMillis = 120, easing = SceneEasing))
    val hillColor = colors.teal500
    val groundColor = colors.purple600


    Canvas(modifier.height(groundHeight + (MAX_HILL_HEIGHT * hillScale).dp)) {
        val groundTop = size.height - groundHeight.toPx()
        val columnWidth = size.width / BackHills.size

        drawHills(BackHills, hillScale, groundTop, columnWidth, hillColor)
        drawHills(FrontHills, hillScale, groundTop, columnWidth, groundColor)

        if (groundHeight > 0.dp) {
            val groundOverlap = 4.dp.toPx()
            drawRect(groundColor, Offset(0f, groundTop - groundOverlap), Size(size.width, size.height - groundTop + groundOverlap))
        }
    }
}


private fun DrawScope.drawHills(profile: IntArray, scale: Float, groundTop: Float, columnWidth: Float, color: Color) {
    profile.forEachIndexed { index, height ->
        val hillHeight = (height * scale).dp.toPx()
        drawRect(color, Offset(index * columnWidth, groundTop - hillHeight), Size(columnWidth + 1f, hillHeight))
    }
}