package dev.iln.cobblemongo.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

object AppTheme {
    val typography: AppTypography
        @ReadOnlyComposable
        @Composable
        get() = LocalAppTypography.current
    val colors: AppColors
        @ReadOnlyComposable
        @Composable
        get() = LocalAppColors.current
    val shapes: AppShapes
        @ReadOnlyComposable
        @Composable
        get() = LocalAppShapes.current
}


@Composable
fun AppTheme(
    colors: AppColors = DefaultAppColors,
    typography: AppTypography = appTypography(),
    shapes: AppShapes = DefaultAppShapes,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalAppTypography provides typography,
        LocalAppShapes provides shapes,
        content = content
    )
}