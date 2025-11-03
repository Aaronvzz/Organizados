package com.organizados.ajustes.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = ButtonColor,
    onPrimary = TextColor,
    secondary = PurpleGrey80,
    onSecondary = TextColor,
    tertiary = Pink80,
    onTertiary = TextColor,
    background = BackgroundColor,
    onBackground = TextColor,
    surface = BackgroundColor,
    onSurface = TextColor,
    error = ErrorRed,
    onError = TextColor
)

private val LightColorScheme = lightColorScheme(
    primary = ButtonColor,
    onPrimary = TextColor,
    secondary = PurpleGrey40,
    onSecondary = TextColor,
    tertiary = Pink40,
    onTertiary = TextColor,
    background = BackgroundColor,
    onBackground = TextColor,
    surface = BackgroundColor,
    onSurface = TextColor,
    error = ErrorRed,
    onError = TextColor
)

@Composable
fun AjustesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Deshabilitado para usar colores personalizados
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

