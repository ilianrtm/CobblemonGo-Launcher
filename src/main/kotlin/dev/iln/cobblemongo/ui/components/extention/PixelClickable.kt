package dev.iln.cobblemongo.ui.components.extention

import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.semantics.Role

fun Modifier.pixelClickable(
    onClick: () -> Unit,
    enabled: Boolean = true,
    role: Role = Role.Button,
    onClickLabel: String? = null,
    interactionSource: MutableInteractionSource? = null,
) = this
    .then(if (interactionSource != null) Modifier.hoverable(interactionSource, enabled) else Modifier)
    .pointerHoverIcon(if (enabled) PointerIcon.Hand else PointerIcon.Default)
    .clickable(
        interactionSource = interactionSource,
        indication = null,
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
        onClick = onClick,
    )