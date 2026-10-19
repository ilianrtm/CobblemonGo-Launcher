package dev.iln.cobblemongo.ui.screen.login.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import dev.iln.cobblemongo.resources.Res
import dev.iln.cobblemongo.resources.check_icon
import dev.iln.cobblemongo.resources.login_remember_me
import dev.iln.cobblemongo.ui.components.AppText
import dev.iln.cobblemongo.ui.theme.AppTheme.colors
import dev.iln.cobblemongo.ui.theme.AppTheme.shapes
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun RememberCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = shapes.extraSmall


    Row(
        modifier = modifier
            .height(40.dp)
            .toggleable(
                value = checked,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                role = Role.Checkbox,
                onValueChange = onCheckedChange,
            )
            .pointerHoverIcon(PointerIcon.Hand),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .background(if (checked) colors.teal400 else colors.purple800, shape)
                .border(2.dp, if (checked) colors.teal400 else colors.purple300, shape),
            contentAlignment = Alignment.Center
        ) {
            if (checked) {
                Image(
                    painter = painterResource(Res.drawable.check_icon),
                    contentDescription = null,
                    modifier = Modifier.size(14.dp)
                )
            }
        }


        AppText(stringResource(Res.string.login_remember_me))
    }
}