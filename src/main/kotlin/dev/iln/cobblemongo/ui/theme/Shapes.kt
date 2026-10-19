package dev.iln.cobblemongo.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Immutable
data class AppShapes(
    val extraSmall: Shape,
    val small: Shape,
    val normal: Shape,
    val medium: Shape,
    val large: Shape,
    val extraLarge: Shape,
)


val DefaultAppShapes = AppShapes(
    extraSmall = RoundedCornerShape(size = 4.dp),
    small = RoundedCornerShape(size = 6.dp),
    normal = RoundedCornerShape(size = 12.dp),
    medium = RoundedCornerShape(size = 8.dp),
    large = RoundedCornerShape(size = 16.dp),
    extraLarge = RoundedCornerShape(size = 20.dp),
)


val LocalAppShapes = staticCompositionLocalOf<AppShapes> {
    error("AppShapes not provided: wrap the content in AppTheme { }")
}