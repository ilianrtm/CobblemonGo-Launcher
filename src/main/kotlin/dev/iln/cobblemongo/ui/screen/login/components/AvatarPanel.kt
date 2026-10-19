package dev.iln.cobblemongo.ui.screen.login.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.iln.cobblemongo.resources.Res
import dev.iln.cobblemongo.resources.default_avatar_icon
import dev.iln.cobblemongo.ui.components.AppText
import dev.iln.cobblemongo.ui.components.extention.hardShadow
import dev.iln.cobblemongo.ui.theme.AppTheme.colors
import dev.iln.cobblemongo.ui.theme.AppTheme.shapes
import dev.iln.cobblemongo.ui.theme.AppTheme.typography
import org.jetbrains.compose.resources.painterResource

@Composable
fun AvatarPanel(
    username: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(colors.purple700, shapes.large)
            .padding(horizontal = 12.dp, vertical = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)
    ) {
        AppText(
            text = username.ifEmpty { "???" },
            modifier = Modifier
                .widthIn(min = 72.dp)
                .background(colors.purple800)
                .padding(start = 10.dp, top = 2.dp, end = 10.dp, bottom = 4.dp),
            style = typography.titleSmall.copy(textAlign = TextAlign.Center),
            color = colors.white,
            maxLines = 1
        )

        Image(
            painter = painterResource(Res.drawable.default_avatar_icon),
            contentDescription = null,
            modifier = Modifier
                .size(76.dp)
                .hardShadow(colors.purple300, offsetY = 4.dp)
        )
    }
}