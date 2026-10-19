package dev.iln.cobblemongo.ui.screen.login.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.iln.cobblemongo.resources.*
import dev.iln.cobblemongo.ui.components.AppText
import dev.iln.cobblemongo.ui.components.PixelTextField
import dev.iln.cobblemongo.ui.screen.login.LoginModel
import dev.iln.cobblemongo.ui.screen.login.UsernameStatus
import dev.iln.cobblemongo.ui.theme.AppTheme.colors
import dev.iln.cobblemongo.ui.theme.AppTheme.typography
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun UsernameField(
    value: String,
    onValueChange: (String) -> Unit,
    status: UsernameStatus,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (help, helpColor) = when (status) {
        UsernameStatus.Empty -> stringResource(Res.string.login_username_hint, LoginModel.MIN_LENGTH, LoginModel.MAX_LENGTH) to colors.purple100
        is UsernameStatus.TooShort -> pluralStringResource(Res.plurals.login_username_too_short, status.missingCharacters, status.missingCharacters) to colors.orange100
        UsernameStatus.InvalidCharacters -> stringResource(Res.string.login_username_invalid) to colors.orange100
        UsernameStatus.Valid -> stringResource(Res.string.login_username_valid) to colors.teal300
    }


    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            AppText(
                text = stringResource(Res.string.login_username_label),
                style = typography.bodyMedium
            )

            AppText(
                text = "${value.length}/${LoginModel.MAX_LENGTH}",
                style = typography.caption,
                color = colors.purple100
            )
        }


        PixelTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = typography.input,
            modifier = Modifier.fillMaxWidth().height(46.dp),
            placeholder = stringResource(Res.string.login_username_placeholder),
            horizontalPadding = 14.dp,
            onSubmit = onSubmit
        )

        AppText(
            text = help,
            style = typography.caption,
            color = helpColor
        )
    }
}