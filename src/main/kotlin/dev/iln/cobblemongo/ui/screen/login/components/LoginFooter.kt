package dev.iln.cobblemongo.ui.screen.login.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.iln.cobblemongo.core.AutoUpdater
import dev.iln.cobblemongo.core.UpdateState
import dev.iln.cobblemongo.resources.*
import dev.iln.cobblemongo.ui.components.AppText
import dev.iln.cobblemongo.ui.theme.AppTheme.colors
import dev.iln.cobblemongo.ui.theme.AppTheme.typography
import dev.nucleusframework.core.runtime.NucleusApp
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoginFooter(modifier: Modifier = Modifier) {
    val updateState by AutoUpdater.state.collectAsStateWithLifecycle()

    val helpText = buildAnnotatedString {
        append(stringResource(Res.string.login_help))

        withLink(
            LinkAnnotation.Url(
                url = stringResource(Res.string.discord_url),
                styles = TextLinkStyles(
                    style = SpanStyle(color = colors.teal300, textDecoration = TextDecoration.Underline),
                    hoveredStyle = SpanStyle(color = colors.teal100)
                ),
            )
        ) {
            append(stringResource(Res.string.login_help_link))
        }
    }


    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp, top = 4.dp, end = 24.dp, bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AppText(
            text = when (val update = updateState) {
                UpdateState.Checking -> stringResource(Res.string.update_checking)
                is UpdateState.Downloading -> stringResource(Res.string.update_downloading, update.percent)
                UpdateState.Installing -> stringResource(Res.string.update_installing)
                UpdateState.Idle -> stringResource(Res.string.login_launcher_version, NucleusApp.version ?: "dev")
            },
            style = typography.caption,
            color = colors.purple100
        )

        BasicText(
            text = helpText,
            style = typography.caption.copy(color = colors.purple100)
        )
    }
}