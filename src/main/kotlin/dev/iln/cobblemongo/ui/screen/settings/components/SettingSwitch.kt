package dev.iln.cobblemongo.ui.screen.settings.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import dev.iln.cobblemongo.ui.components.AppText
import dev.iln.cobblemongo.ui.theme.AppTheme.colors
import dev.iln.cobblemongo.ui.theme.AppTheme.shapes

@Composable
fun SettingSwitch(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = shapes.small
    val knobOffset by animateDpAsState(if (checked) 24.dp else 0.dp, tween(120))


    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .pointerHoverIcon(PointerIcon.Hand)
            .toggleable(
                value = checked,
                onValueChange = onCheckedChange,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                role = Role.Switch,
            ),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppText(
            text = label,
            modifier = Modifier.weight(1f)
        )

        Box(
            modifier = Modifier
                .size(50.dp, 26.dp)
                .background(if (checked) colors.teal400 else colors.purple800, shape)
                .border(2.dp, if (checked) colors.teal400 else colors.purple300, shape)
                .padding(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .offset { IntOffset((knobOffset).roundToPx(), 0) }
                    .size(14.dp)
                    .background(if (checked) colors.purple600 else colors.purple200, shapes.extraSmall)
            )
        }
    }
}