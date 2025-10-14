package com.woonish.winwin.ui.theme

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
    primary = Orange,
    onPrimary = Black,
    secondary = OrangeDark,
    onSecondary = Black,
    background = DarkSurface,
    surface = DarkSurface,
    onBackground = LightSurface,
    onSurface = LightSurface
)

private val LightColorScheme = lightColorScheme(
    primary = Orange,
    onPrimary = Black,
    secondary = OrangeDark,
    onSecondary = Black,
    background = LightSurface,
    surface = LightSurface,
    onBackground = Black,
    onSurface = Black
)

@Composable
fun WinwinTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Disable dynamic color to enforce brand palette
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}