package com.gauscan.app.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = GreenPrimary,
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFB8F5B1),
    onPrimaryContainer = GreenPrimaryDark,
    secondary = AmberSecondary,
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFE0B2),
    onSecondaryContainer = AmberDark,
    tertiary = Color(0xFF795548),
    background = SurfaceLight,
    onBackground = TextPrimary,
    surface = CardLight,
    onSurface = TextPrimary,
    surfaceVariant = Color(0xFFE8F5E9),
    error = ErrorRed,
    outline = Color(0xFFBBBBBB)
)

private val DarkColorScheme = darkColorScheme(
    primary = GreenPrimaryLight,
    onPrimary = GreenPrimaryDark,
    primaryContainer = GreenPrimary,
    onPrimaryContainer = Color(0xFFB8F5B1),
    secondary = AmberLight,
    onSecondary = Color(0xFF3E2000),
    secondaryContainer = AmberDark,
    onSecondaryContainer = Color(0xFFFFDEB3),
    tertiary = Color(0xFFBCAAA4),
    background = SurfaceDark,
    onBackground = Color(0xFFE0E0E0),
    surface = CardDark,
    onSurface = Color(0xFFE0E0E0),
    surfaceVariant = Color(0xFF1A2E1A),
    error = Color(0xFFEF9A9A),
    outline = Color(0xFF555555)
)

@Composable
fun GauScanTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = GauScanTypography,
        content = content
    )
}