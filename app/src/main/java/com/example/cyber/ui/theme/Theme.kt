package com.example.cyber.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Typography



// Cores claras
val LightPrimary = Color(0xFF061233)
val LightSecondary = Color(0xFF1D2B53)
val LightBackground = Color(0xFFCBD6E2)
val LightSurface = Color(0xFFCBD6E2)
val LightOnPrimary = Color(0xFFCBD6E2)
val LightOnSecondary = Color(0xFFCBD6E2)
val LightOnBackground = Color(0xFF000000)

// Cores escuras
val DarkPrimary = Color(0xFF1D1E33)
val DarkSecondary = Color(0xFF2A2B4F)
val DarkBackground = Color(0xFF121212)
val DarkSurface = Color(0xFF1E1E1E)
val DarkOnPrimary = Color(0xFFFFFFFF)
val DarkOnSecondary = Color(0xFFFFFFFF)
val DarkOnBackground = Color(0xFFCBD6E2)


// Tema claro
private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    secondary = LightSecondary,
    background = LightBackground,
    surface = LightSurface,
    onPrimary = LightOnPrimary,
    onSecondary = LightOnSecondary,
    onBackground = LightOnBackground,
    onSurface = LightOnBackground
)

// Tema escuro
private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    secondary = DarkSecondary,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = DarkOnPrimary,
    onSecondary = DarkOnSecondary,
    onBackground = DarkOnBackground,
    onSurface = DarkOnBackground
)

@Composable
fun CyberTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Alterna automaticamente com o sistema
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography(),
        content = content
    )
}
