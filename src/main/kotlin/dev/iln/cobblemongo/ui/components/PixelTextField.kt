package dev.iln.cobblemongo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.iln.cobblemongo.ui.components.extention.focusRing
import dev.iln.cobblemongo.ui.theme.AppTheme.colors
import dev.iln.cobblemongo.ui.theme.AppTheme.shapes

@Composable
fun PixelTextField(
    value: String,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    placeholder: String? = null,
    horizontalPadding: Dp = 12.dp,
    onSubmit: (() -> Unit)? = null,
) {
    val shape = shapes.medium
    val interactionSource = remember { MutableInteractionSource() }
    val focused by interactionSource.collectIsFocusedAsState()


    CompositionLocalProvider(
        LocalTextSelectionColors provides TextSelectionColors(colors.teal400, colors.teal400.copy(0.4f))
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = readOnly,
            singleLine = true,
            textStyle = textStyle.copy(color = if (enabled) colors.teal100 else colors.purple200),
            cursorBrush = SolidColor(colors.teal400),
            interactionSource = interactionSource,
            modifier = modifier
                .focusRing(focused, colors.teal700, shape)
                .background(if (enabled) colors.purple800 else colors.purple600, shape)
                .border(
                    width = 2.dp,
                    shape = shape,
                    color = when {
                        !enabled -> colors.purple400
                        focused -> colors.teal400
                        else -> colors.purple300
                    }
                )
                .then(
                    if (onSubmit != null) {
                        Modifier.onPreviewKeyEvent { event ->
                            val isEnter = event.key == Key.Enter || event.key == Key.NumPadEnter

                            if (isEnter && event.type == KeyEventType.KeyDown) {
                                onSubmit()
                                true
                            } else {
                                false
                            }
                        }
                    } else {
                        Modifier
                    }
                )
                .padding(horizontal = horizontalPadding),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxHeight(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (placeholder != null && value.isEmpty()) {
                        AppText(
                            text = placeholder,
                            style = textStyle,
                            color = colors.purple200
                        )
                    }

                    innerTextField()
                }
            }
        )
    }
}