package dev.iln.cobblemongo.ui.screen.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.iln.cobblemongo.resources.*
import dev.iln.cobblemongo.ui.components.AppText
import dev.iln.cobblemongo.ui.theme.AppTheme.colors
import dev.iln.cobblemongo.ui.theme.AppTheme.shapes
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ServerOverview(
    isServerOnline: Boolean?,
    onlinePlayers: Int?,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = painterResource(Res.drawable.logo),
            contentDescription = "CobblemonGo",
            modifier = Modifier.size(380.dp, 234.dp)
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            InfoPill {
                StatusDot(
                    color = when (isServerOnline) {
                        true -> colors.green100
                        false -> colors.red100
                        null -> colors.purple200
                    },
                    ring = when (isServerOnline) {
                        true -> colors.green200
                        false -> colors.red200
                        null -> colors.purple400
                    }
                )

                AppText(
                    text = when (isServerOnline) {
                        true -> stringResource(Res.string.home_server_online)
                        false -> stringResource(Res.string.home_server_offline)
                        null -> stringResource(Res.string.home_server_searching)
                    }
                )
            }

            if (isServerOnline == true && onlinePlayers != null) {
                InfoPill {
                    AppText(onlinePlayersLabel(onlinePlayers))
                }
            }
        }
    }
}


@Composable
private fun InfoPill(content: @Composable () -> Unit) {
    Row(
        modifier = Modifier
            .height(36.dp)
            .background(colors.purple600, shapes.medium)
            .padding(horizontal = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}


@Composable
private fun StatusDot(color: Color, ring: Color) {
    Box(
        modifier = Modifier.size(16.dp).background(ring),
        contentAlignment = Alignment.Center
    ) {
        Box(Modifier.size(10.dp).background(color))
    }
}


@Composable
private fun onlinePlayersLabel(count: Int): String =
    pluralStringResource(Res.plurals.home_players_online, count, count)