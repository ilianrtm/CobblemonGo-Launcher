package dev.iln.cobblemongo.ui.screen.login.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.iln.cobblemongo.resources.Res
import dev.iln.cobblemongo.resources.login_button
import dev.iln.cobblemongo.resources.login_subtitle
import dev.iln.cobblemongo.resources.login_title
import dev.iln.cobblemongo.ui.components.AppText
import dev.iln.cobblemongo.ui.components.PixelButton
import dev.iln.cobblemongo.ui.components.extention.pixelPanel
import dev.iln.cobblemongo.ui.screen.login.LoginModel
import dev.iln.cobblemongo.ui.theme.AppTheme.colors
import dev.iln.cobblemongo.ui.theme.AppTheme.shapes
import dev.iln.cobblemongo.ui.theme.AppTheme.typography
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoginCard(
    model: LoginModel,
    onLogin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val submit = { if (model.canLogin) onLogin() }


    Row(
        modifier = modifier
            .width(600.dp)
            .pixelPanel(shapes.extraLarge)
            .padding(18.dp)
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(22.dp)
    ) {
        AvatarPanel(
            username = model.username,
            modifier = Modifier.width(170.dp).fillMaxHeight()
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                AppText(
                    text = stringResource(Res.string.login_title),
                    style = typography.displaySmall
                )

                AppText(
                    text = stringResource(Res.string.login_subtitle),
                    color = colors.purple100
                )
            }


            UsernameField(
                value = model.username,
                status = model.status,
                onValueChange = model::onUsernameChange,
                onSubmit = submit
            )

            RememberCheckbox(
                checked = model.remember,
                onCheckedChange = model::onRememberChange
            )

            PixelButton(
                text = stringResource(Res.string.login_button),
                onClick = submit,
                enabled = model.canLogin,
                modifier = Modifier.fillMaxWidth().height(52.dp)
            )
        }
    }
}