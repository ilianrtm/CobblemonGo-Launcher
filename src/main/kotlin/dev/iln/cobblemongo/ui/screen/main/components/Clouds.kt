package dev.iln.cobblemongo.ui.screen.main.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.iln.cobblemongo.ui.theme.AppTheme.colors

private data class Cloud(
    val x: Float,
    val y: Dp,
    val width: Dp,
    val height: Dp,
    val rotation: Float,
    val riseDistance: Dp,
)


private val CloudShapes = listOf(
    Cloud(x = 0.06f, y = 120.dp, width = 180.dp, height = 120.dp, rotation = -7f, riseDistance = 26.dp),
    Cloud(x = 0.40f, y = 80.dp, width = 130.dp, height = 90.dp, rotation = 8f, riseDistance = 18.dp),
    Cloud(x = 0.68f, y = 330.dp, width = 110.dp, height = 80.dp, rotation = -5f, riseDistance = 34.dp)
)


@Composable
fun Clouds(
    raised: Boolean,
    modifier: Modifier = Modifier
) {
    val color = colors.teal200
    val progress by animateFloatAsState(
        targetValue = if (raised) 1f else 0f,
        animationSpec = tween(560, delayMillis = 80, easing = SceneEasing)
    )


    Canvas(modifier) {
        CloudShapes.forEach { cloud ->
            val topLeft = Offset(size.width * cloud.x, (cloud.y - cloud.riseDistance * progress).toPx())
            val cloudSize = Size(cloud.width.toPx(), cloud.height.toPx())

            rotate(cloud.rotation, pivot = topLeft + cloudSize.center) {
                drawRect(color, topLeft, cloudSize, 0.55f)
            }
        }
    }
}