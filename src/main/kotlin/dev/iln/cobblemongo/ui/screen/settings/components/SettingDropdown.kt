package dev.iln.cobblemongo.ui.screen.settings.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import dev.iln.cobblemongo.resources.Res
import dev.iln.cobblemongo.resources.arrow_down_icon
import dev.iln.cobblemongo.ui.components.AppText
import dev.iln.cobblemongo.ui.components.extention.pixelClickable
import dev.iln.cobblemongo.ui.theme.AppTheme.colors
import dev.iln.cobblemongo.ui.theme.AppTheme.shapes
import dev.iln.cobblemongo.ui.theme.AppTheme.typography
import org.jetbrains.compose.resources.painterResource

@Suppress("ContentSlotReused")
@Composable
fun <T> SettingDropdown(
    selected: T,
    options: List<T>,
    onSelect: (T) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (T) -> String
) {
    val shape = shapes.medium
    val style = typography.caption

    var expanded by remember { mutableStateOf(false) }
    var fieldWidth by remember { mutableIntStateOf(0) }
    var fieldHeight by remember { mutableIntStateOf(0) }


    Column(modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .onSizeChanged {
                    fieldWidth = it.width
                    fieldHeight = it.height
                }
                .background(colors.purple800, shape)
                .border(2.dp, if (expanded) colors.teal400 else colors.purple300, shape)
                .pixelClickable(onClick = { expanded = !expanded }, role = Role.DropdownList)
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppText(
                text = label(selected),
                style = style
            )

            Image(
                painter = painterResource(Res.drawable.arrow_down_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(12.dp)
                    .rotate(if (expanded) 180f else 0f)
            )
        }

        if (expanded) {
            Popup(
                offset = IntOffset(0, fieldHeight + with(LocalDensity.current) { 4.dp.roundToPx() }),
                onDismissRequest = { expanded = false },
                properties = PopupProperties(focusable = true)
            ) {
                Column(
                    modifier = Modifier
                        .width(with(LocalDensity.current) { fieldWidth.toDp() })
                        .background(colors.purple800, shape)
                        .border(2.dp, colors.teal400, shape)
                        .padding(4.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    options.forEach { option ->
                        val isSelected = option == selected


                        AppText(
                            text = label(option),
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(if (isSelected) colors.teal400 else colors.purple800, shapes.small)
                                .pixelClickable(
                                    onClick = {
                                        onSelect(option)
                                        expanded = false
                                    },
                                    role = Role.RadioButton,
                                )
                                .padding(8.dp),
                            style = if (isSelected) style.copy(fontWeight = FontWeight.SemiBold) else style,
                            color = if (isSelected) colors.purple600 else colors.teal100
                        )
                    }
                }
            }
        }
    }
}