package com.example.pasteleriamilsaboresapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import typography

// 🎀 Paleta de colores pastel clara
private val PasteleriaColorScheme = lightColorScheme(
    primary = RosaPastel,
    onPrimary = FondoCrema,
    secondary = CafeSuave,
    onSecondary = FondoCrema,
    background = FondoCrema,
    surface = FondoCrema,
    onSurface = MarronOscuro,
    tertiary = RosaIntenso
)

// 🎂 Tema principal para toda la app
@Composable
fun PasteleriaMilSaboresTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = PasteleriaColorScheme,
        typography = typography,
        content = content
    )
}
