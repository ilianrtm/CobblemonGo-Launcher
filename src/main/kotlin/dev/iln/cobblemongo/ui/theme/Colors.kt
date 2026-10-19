package dev.iln.cobblemongo.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class AppColors(
    val teal100: Color,
    val teal200: Color,
    val teal300: Color,
    val teal400: Color,
    val teal500: Color,
    val teal600: Color,
    val teal700: Color,

    val purple100: Color,
    val purple200: Color,
    val purple300: Color,
    val purple400: Color,
    val purple500: Color,
    val purple600: Color,
    val purple700: Color,
    val purple800: Color,

    val orange100: Color,
    val orange200: Color,
    val orange300: Color,
    val orange400: Color,

    val yellow100: Color,

    val green100: Color,
    val green200: Color,

    val red100: Color,
    val red200: Color,

    val white: Color,
)


val DefaultAppColors = AppColors(
    teal100 = Color(0xFFEFFBF9),
    teal200 = Color(0xFF9BF3EA),
    teal300 = Color(0xFF8AF0E6),
    teal400 = Color(0xFF3FD6CE),
    teal500 = Color(0xFF2BC0C0),
    teal600 = Color(0xFF1F9FA6),
    teal700 = Color(0xFF1F4F55),

    purple100 = Color(0xFFBFA8C6),
    purple200 = Color(0xFF8E7896),
    purple300 = Color(0xFF5E2464),
    purple400 = Color(0xFF3D1A47),
    purple500 = Color(0xFF2C1233),
    purple600 = Color(0xFF24102B),
    purple700 = Color(0xFF1B0A20),
    purple800 = Color(0xFF13061A),

    orange100 = Color(0xFFFF9A7A),
    orange200 = Color(0xFFFF7A45),
    orange300 = Color(0xFFF2491A),
    orange400 = Color(0xFFA82A0B),

    yellow100 = Color(0xFFFFC93C),

    green100 = Color(0xFF4ADE6B),
    green200 = Color(0xFF1E4A2C),

    red100 = Color(0xFFFF5A5A),
    red200 = Color(0xFF5A1A22),

    white = Color.White,
)


val LocalAppColors = staticCompositionLocalOf<AppColors> {
    error("AppColors not provided: wrap the content in AppTheme { }")
}