package dev.iln.cobblemongo.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.iln.cobblemongo.resources.Res
import dev.iln.cobblemongo.resources.default_avatar_icon
import org.jetbrains.compose.resources.painterResource

@Composable
fun PlayerHead(
    username: String,
    size: Int,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    val defaultAvatar = painterResource(Res.drawable.default_avatar_icon)


    AsyncImage(
        model = username.takeIf { it.isNotBlank() }?.let { "https://mc-heads.net/avatar/$it/$size" },
        contentDescription = contentDescription,
        modifier = modifier.size(size.dp),
        placeholder = defaultAvatar,
        error = defaultAvatar,
        fallback = defaultAvatar,
        filterQuality = FilterQuality.None
    )
}