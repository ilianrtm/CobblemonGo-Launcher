package dev.iln.cobblemongo.ui.screen.main.components.toast

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import dev.iln.cobblemongo.core.log.AppToast
import dev.iln.cobblemongo.core.log.ToastLevel
import dev.iln.cobblemongo.resources.*
import dev.iln.cobblemongo.ui.components.AppText
import dev.iln.cobblemongo.ui.components.extention.hardShadow
import dev.iln.cobblemongo.ui.components.extention.pixelClickable
import dev.iln.cobblemongo.ui.theme.AppTheme.colors
import dev.iln.cobblemongo.ui.theme.AppTheme.shapes
import dev.iln.cobblemongo.ui.theme.AppTheme.typography
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ToastCard(
    toast: AppToast,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = shapes.medium
    val style = toast.level.style


    Row(
        modifier = modifier
            .width(272.dp)
            .hardShadow(colors.purple800, shape, offsetY = 4.dp)
            .background(colors.purple500, shape)
            .border(2.dp, style.background, shape)
            .pixelClickable(onDismiss, onClickLabel = stringResource(Res.string.notif_dismiss))
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(style.background, shapes.extraSmall)
        ) {
            Image(
                painter = painterResource(style.icon),
                contentDescription = null,
                colorFilter = ColorFilter.tint(style.iconColor),
                modifier = Modifier.size(18.dp).align(Alignment.Center)
            )
        }


        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            AppText(
                text = stringResource(toast.title),
                style = typography.label,
                maxLines = 1
            )

            AppText(
                text = stringResource(toast.message, *toast.args.toTypedArray()),
                style = typography.caption,
                color = colors.purple100,
                maxLines = 3
            )
        }
    }
}


private class LevelStyle(val icon: DrawableResource, val background: Color, val iconColor: Color)


private val ToastLevel.style: LevelStyle
    @Composable
    get() {

        return when (this) {
            ToastLevel.Info -> LevelStyle(Res.drawable.info_icon, colors.yellow100, colors.purple600)
            ToastLevel.Success -> LevelStyle(Res.drawable.check_icon, colors.green100, colors.purple600)
            ToastLevel.Error -> LevelStyle(Res.drawable.error_icon, colors.orange300, colors.white)
        }
    }