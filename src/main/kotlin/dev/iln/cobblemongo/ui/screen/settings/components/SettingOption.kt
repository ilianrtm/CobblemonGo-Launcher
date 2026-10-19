package dev.iln.cobblemongo.ui.screen.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.iln.cobblemongo.ui.components.AppText
import dev.iln.cobblemongo.ui.theme.AppTheme.colors
import dev.iln.cobblemongo.ui.theme.AppTheme.shapes
import dev.iln.cobblemongo.ui.theme.AppTheme.typography

@Composable
fun SettingOption(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = shapes.medium
    val style = typography.caption


    Box(
        modifier = modifier
            .height(40.dp)
            .background(if (selected) colors.teal400 else Color.Transparent, shape)
            .border(2.dp, if (selected) colors.teal400 else colors.purple300, shape)
            .selectable(selected, role = Role.RadioButton, onClick = onClick)
            .pointerHoverIcon(PointerIcon.Hand),
        contentAlignment = Alignment.Center
    ) {
        AppText(
            text = label,
            style = if (selected) style.copy(fontWeight = FontWeight.SemiBold) else style,
            color = if (selected) colors.purple600 else colors.teal100
        )
    }
}