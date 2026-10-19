package dev.iln.cobblemongo.ui.screen.settings.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import dev.iln.cobblemongo.data.config.LocalConfig
import dev.iln.cobblemongo.resources.*
import dev.iln.cobblemongo.ui.components.AppText
import dev.iln.cobblemongo.ui.screen.settings.components.SettingsCard
import dev.iln.cobblemongo.ui.theme.AppTheme.colors
import dev.iln.cobblemongo.ui.theme.AppTheme.shapes
import dev.iln.cobblemongo.ui.theme.AppTheme.typography
import org.jetbrains.compose.resources.stringResource

@Composable
fun MemorySection(
    ram: Int,
    maxMemoryGb: Int,
    recommendedMemoryGb: Int,
    totalMemoryGb: Int?,
    onMemoryChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val recommendedLabel = when (totalMemoryGb) {
        null -> stringResource(Res.string.settings_memory_recommended, recommendedMemoryGb)
        else -> stringResource(Res.string.settings_memory_recommended_of_total, recommendedMemoryGb, totalMemoryGb)
    }


    SettingsCard(
        title = stringResource(Res.string.settings_memory),
        modifier = modifier,
        spacing = 10.dp,
        titleTrailing = {
            AppText(
                text = stringResource(Res.string.memory_gb, ram),
                style = typography.displayLarge,
                color = colors.yellow100
            )
        }
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            for (value in LocalConfig.MIN_MEMORY_GB..maxMemoryGb) {
                MemoryBlock(
                    value = value,
                    selected = value == ram,
                    filled = value <= ram,
                    aboveRecommended = value > recommendedMemoryGb,
                    onClick = { onMemoryChange(value) },
                    modifier = Modifier.weight(1f)
                )
            }
        }


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AppText(
                text = stringResource(Res.string.memory_gb, LocalConfig.MIN_MEMORY_GB),
                style = typography.caption,
                color = colors.purple100
            )

            AppText(
                text = recommendedLabel,
                style = typography.caption,
                color = colors.purple100
            )

            AppText(
                text = stringResource(Res.string.memory_gb, maxMemoryGb),
                style = typography.caption,
                color = colors.purple100
            )
        }
    }
}


@Composable
private fun MemoryBlock(
    value: Int,
    selected: Boolean,
    filled: Boolean,
    aboveRecommended: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = shapes.extraSmall

    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    val description = stringResource(Res.string.memory_gb, value)

    val fillColor = when {
        !filled -> colors.purple800
        aboveRecommended -> colors.orange200
        else -> colors.teal400
    }

    val border = when {
        selected -> colors.yellow100
        filled -> fillColor
        else -> colors.purple400
    }


    Box(
        modifier = modifier
            .height(40.dp)
            .background(if (hovered) lerp(fillColor, colors.white, 0.15f) else fillColor, shape)
            .border(2.dp, border, shape)
            .hoverable(interactionSource)
            .selectable(selected, interactionSource, indication = null, role = Role.RadioButton, onClick = onClick)
            .pointerHoverIcon(PointerIcon.Hand)
            .semantics { contentDescription = description }
    )
}