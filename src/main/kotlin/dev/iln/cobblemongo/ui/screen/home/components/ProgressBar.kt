package dev.iln.cobblemongo.ui.screen.home.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import dev.iln.cobblemongo.resources.Res
import dev.iln.cobblemongo.resources.home_preparing_game
import dev.iln.cobblemongo.ui.components.AppText
import dev.iln.cobblemongo.ui.theme.AppTheme.colors
import dev.iln.cobblemongo.ui.theme.AppTheme.typography
import org.jetbrains.compose.resources.stringResource
import kotlin.math.abs
import kotlin.math.roundToInt

private const val SEGMENTS = 32
private const val LIGHT_WIDTH = 3


@Composable
fun ProgressBar(
    label: String,
    progress: Float,
    modifier: Modifier = Modifier,
    active: Boolean = false,
) {
    val preparingLabel = stringResource(Res.string.home_preparing_game)
    val transition = rememberInfiniteTransition()

    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(600)
    )

    val filled = (SEGMENTS * animatedProgress).roundToInt()

    val blink by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val lightPosition by transition.animateFloat(
        initialValue = -LIGHT_WIDTH.toFloat(),
        targetValue = (SEGMENTS + LIGHT_WIDTH).toFloat(),
        animationSpec = infiniteRepeatable(tween(2200, easing = LinearEasing))
    )


    Column(
        modifier = modifier.width(684.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        AppText(
            text = label,
            style = typography.label,
            color = colors.yellow100
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .background(colors.purple800)
                .padding(2.dp)
                .semantics {
                    contentDescription = preparingLabel
                    progressBarRangeInfo = ProgressBarRangeInfo(progress, 0f..1f)
                },
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            repeat(SEGMENTS) { index ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(
                            when {
                                index < filled -> colors.yellow100

                                !active -> colors.purple400

                                filled == 0 -> lerp(
                                    start = colors.purple400,
                                    stop = colors.yellow100,
                                    fraction = (1f - abs(index - lightPosition) / LIGHT_WIDTH).coerceIn(0f, 1f)
                                )

                                index == filled -> lerp(
                                    start = colors.purple400,
                                    stop = colors.yellow100,
                                    fraction = blink
                                )
                                
                                else -> colors.purple400
                            }
                        )
                )
            }
        }
    }
}