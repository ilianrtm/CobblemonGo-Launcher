package dev.iln.cobblemongo.ui.screen.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.iln.cobblemongo.ui.components.AppText
import dev.iln.cobblemongo.ui.components.PixelTextField
import dev.iln.cobblemongo.ui.theme.AppTheme.colors
import dev.iln.cobblemongo.ui.theme.AppTheme.typography

private const val MAX_DIGITS = 5


@Composable
fun NumberField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        AppText(
            text = label,
            style = typography.caption,
            color = if (enabled) colors.teal100 else colors.purple200
        )

        PixelTextField(
            value = value,
            onValueChange = { onValueChange(it.filter(Char::isDigit).take(MAX_DIGITS)) },
            textStyle = typography.titleSmall,
            modifier = Modifier.fillMaxWidth().height(40.dp),
            enabled = enabled
        )
    }
}