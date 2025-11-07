package com.example.pasteleriamilsaboresapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.rememberNavController
import com.example.pasteleriamilsaboresapp.navigation.AppNav
import com.example.pasteleriamilsaboresapp.ui.theme.PasteleriaMilSaboresTheme
import com.jakewharton.threetenabp.AndroidThreeTen
import org.maplibre.android.MapLibre
import org.maplibre.android.WellKnownTileServer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidThreeTen.init(this) // 👈 Inicializa compatibilidad con java.time

        // 🧭 Inicializar MapLibre solo una vez (antes de usar MapView)
        try {
            MapLibre.getInstance(this, "test-api-key", WellKnownTileServer.MapTiler)
        } catch (_: Exception) {
            // Ignorar si ya está inicializado
        }

        setContent {
            PasteleriaMilSaboresTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    AppNav()
                }
            }
        }
    }
}
