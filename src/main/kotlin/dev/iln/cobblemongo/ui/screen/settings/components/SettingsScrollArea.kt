package dev.iln.cobblemongo.ui.screen.settings.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import dev.iln.cobblemongo.resources.Res
import dev.iln.cobblemongo.resources.arrow_down_icon
import dev.iln.cobblemongo.resources.settings_more
import dev.iln.cobblemongo.ui.components.AppText
import dev.iln.cobblemongo.ui.components.extention.hardShadow
import dev.iln.cobblemongo.ui.components.extention.pixelClickable
import dev.iln.cobblemongo.ui.theme.AppTheme.colors
import dev.iln.cobblemongo.ui.theme.AppTheme.shapes
import dev.iln.cobblemongo.ui.theme.AppTheme.typography
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SettingsScrollArea(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val atBottom by remember { derivedStateOf { scrollState.value >= scrollState.maxValue - 12 } }


    Row(modifier) {
        Box(Modifier.weight(1f).fillMaxHeight()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(end = 10.dp, bottom = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                content = content
            )

            if (!atBottom) {
                MoreSettingsHint(
                    onClick = { scope.launch { scrollState.animateScrollBy(scrollState.viewportSize * 0.8f) } },
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        }


        VerticalScrollbar(
            adapter = rememberScrollbarAdapter(scrollState),
            modifier = Modifier
                .fillMaxHeight()
                .background(colors.purple800, shapes.extraSmall),
            style = ScrollbarStyle(
                minimalHeight = 32.dp,
                thickness = 10.dp,
                shape = shapes.extraSmall,
                hoverDurationMillis = 150,
                unhoverColor = colors.teal400,
                hoverColor = colors.teal300
            )
        )
    }
}


@Composable
private fun MoreSettingsHint(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = shapes.medium

    val transition = rememberInfiniteTransition()
    val arrowOffset by transition.animateFloat(
        initialValue = -2f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1100
                -2f at 549
                2f at 550
            },
            repeatMode = RepeatMode.Restart
        ),
    )


    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(84.dp)
            .background(Brush.verticalGradient(0f to colors.purple600.copy(0f), 0.7f to colors.purple600))
            .padding(bottom = 8.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier
                .height(40.dp)
                .hardShadow(colors.teal600, shape, offsetY = 4.dp)
                .background(colors.purple800, shape)
                .border(2.dp, colors.teal400, shape)
                .pixelClickable(onClick)
                .padding(start = 12.dp, end = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(Res.drawable.arrow_down_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp)
                    .graphicsLayer { translationY = arrowOffset.dp.toPx() }
            )

            AppText(
                text = stringResource(Res.string.settings_more),
                style = typography.titleSmall,
                color = colors.teal300
            )
        }
    }
}