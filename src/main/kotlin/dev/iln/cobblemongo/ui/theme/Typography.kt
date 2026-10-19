package dev.iln.cobblemongo.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import dev.iln.cobblemongo.resources.*
import org.jetbrains.compose.resources.Font

@Composable
fun LexendFont() = FontFamily(
    Font(Res.font.Lexend_Regular, weight = FontWeight.Normal),
    Font(Res.font.Lexend_Medium, weight = FontWeight.Medium),
    Font(Res.font.Lexend_SemiBold, weight = FontWeight.SemiBold)
)


@Composable
fun JerseyFont() = FontFamily(Font(Res.font.Jersey_Regular))


@Immutable
data class AppTypography(
    val displayLarge: TextStyle,
    val displayMedium: TextStyle,
    val displaySmall: TextStyle,
    val headline: TextStyle,
    val title: TextStyle,
    val titleSmall: TextStyle,
    val buttonLarge: TextStyle,
    val button: TextStyle,
    val input: TextStyle,
    val stat: TextStyle,
    val label: TextStyle,

    val body: TextStyle,
    val bodyMedium: TextStyle,
    val bodySemiBold: TextStyle,
    val caption: TextStyle,
    val captionMedium: TextStyle,
)


@Composable
internal fun appTypography(): AppTypography {
    val jersey = JerseyFont()
    val lexend = LexendFont()

    fun pixel(size: TextUnit) = TextStyle(
        fontFamily = jersey,
        fontWeight = FontWeight.Normal,
        fontSize = size,
        lineHeight = 1.em,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.Both
        )
    )

    fun text(size: TextUnit, weight: FontWeight, lineHeight: Float) = TextStyle(
        fontFamily = lexend,
        fontWeight = weight,
        fontSize = size,
        lineHeight = lineHeight.em
    )

    return AppTypography(
        displayLarge = pixel(50.sp),
        displayMedium = pixel(42.sp),
        displaySmall = pixel(36.sp),
        headline = pixel(28.sp),
        title = pixel(26.sp),
        titleSmall = pixel(22.sp),
        buttonLarge = pixel(38.sp),
        button = pixel(32.sp),
        input = pixel(24.sp),
        stat = pixel(26.sp),
        label = pixel(20.sp),

        body = text(14.sp, FontWeight.Normal, 1.5f),
        bodyMedium = text(14.sp, FontWeight.Medium, 1.5f),
        bodySemiBold = text(14.sp, FontWeight.SemiBold, 1.4f),
        caption = text(12.sp, FontWeight.Normal, 1.4f),
        captionMedium = text(12.sp, FontWeight.Medium, 1.4f),
    )
}


val LocalAppTypography = staticCompositionLocalOf<AppTypography> {
    error("AppTypography not provided: wrap the content in AppTheme { }")
}