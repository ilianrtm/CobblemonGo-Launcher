package dev.iln.cobblemongo.ui.screen.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.iln.cobblemongo.resources.*
import dev.iln.cobblemongo.ui.components.AppText
import dev.iln.cobblemongo.ui.components.PixelButton
import dev.iln.cobblemongo.ui.components.PixelTile
import dev.iln.cobblemongo.ui.components.PlayerHead
import dev.iln.cobblemongo.ui.theme.AppTheme.colors
import dev.iln.cobblemongo.ui.theme.AppTheme.shapes
import dev.iln.cobblemongo.ui.theme.AppTheme.typography
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun Hotbar(
    username: String,
    ram: Int,
    launchingText: String?,
    canPlay: Boolean,
    onAccount: () -> Unit,
    onMemory: () -> Unit,
    onPlay: () -> Unit,
    onDiscord: () -> Unit,
    onSettings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(colors.purple800, shapes.medium)
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HotbarSlot(
            onClick = onAccount,
            onClickLabel = stringResource(Res.string.home_change_username, username)
        ) {
            PlayerHead(
                username = username,
                size = 40
            )
        }


        HotbarSlot(
            onClick = onMemory,
            spacing = 2.dp
        ) {
            AppText(
                text = stringResource(Res.string.memory_gb, ram),
                style = typography.stat,
                color = colors.teal300
            )

            SlotLabel(
                text = stringResource(Res.string.home_memory),
                muted = true
            )
        }


        PixelButton(
            text = launchingText ?: stringResource(Res.string.home_play),
            onClick = onPlay,
            enabled = canPlay,
            textStyle = typography.buttonLarge,
            shape = shapes.small,
            modifier = Modifier.size(256.dp, 72.dp),
            leadingIcon = if (launchingText == null) {
                {
                    Image(
                        painter = painterResource(Res.drawable.play_icon),
                        contentDescription = null,
                        modifier = Modifier.size(22.dp, 24.dp)
                    )
                }
            } else {
                null
            }
        )

        HotbarSlot(onClick = onDiscord) {
            Image(
                painter = painterResource(Res.drawable.discord_icon),
                contentDescription = null,
                modifier = Modifier.size(28.dp, 22.dp)
            )

            SlotLabel(stringResource(Res.string.home_discord))
        }


        HotbarSlot(onClick = onSettings) {
            Image(
                painter = painterResource(Res.drawable.settings_icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )

            SlotLabel(stringResource(Res.string.home_settings))
        }
    }
}


@Composable
private fun HotbarSlot(
    onClick: () -> Unit,
    onClickLabel: String? = null,
    spacing: Dp = 4.dp,
    content: @Composable ColumnScope.() -> Unit,
) {
    PixelTile(
        onClick = onClick,
        modifier = Modifier.size(72.dp),
        onClickLabel = onClickLabel,
        spacing = spacing,
        contentPadding = PaddingValues(horizontal = 6.dp),
        content = content
    )
}


@Composable
private fun SlotLabel(
    text: String,
    muted: Boolean = false,
) {
    AppText(
        text = text,
        style = typography.caption,
        color = if (muted) colors.purple100 else colors.teal100,
        maxLines = 1
    )
}