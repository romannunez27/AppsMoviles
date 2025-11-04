package com.example.pasteleriamilsaboresapp.ui.components

import android.location.Geocoder
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.maplibre.android.MapLibre
import org.maplibre.android.WellKnownTileServer
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.camera.CameraUpdateFactory
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.maps.MapView
import org.maplibre.android.maps.Style
import java.util.Locale

@Composable
fun MapaInteractivo(
    modifier: Modifier = Modifier,
    direccionTexto: String, // 🆕 recibe la dirección actual desde la vista principal
    onLocationSelected: (String) -> Unit
) {
    val context = LocalContext.current
    var mapLibreMap by remember { mutableStateOf<MapLibreMap?>(null) }

    // 🧠 Efecto: cuando cambia la dirección en el TextField, el mapa se actualiza
    LaunchedEffect(direccionTexto) {
        if (direccionTexto.isNotBlank() && mapLibreMap != null) {
            val coords = buscarCoordenadasPorDireccion(context, direccionTexto)
            coords?.let {
                mapLibreMap!!.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(it, 13.0)
                )
            }
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            // Inicializa MapLibre una sola vez
            try {
                MapLibre.getInstance(ctx, "test-api-key", WellKnownTileServer.MapTiler)
            } catch (_: Exception) {}

            MapView(ctx).apply {
                getMapAsync { map ->
                    mapLibreMap = map
                    map.setStyle(
                        Style.Builder().fromUri("https://tiles.openfreemap.org/styles/liberty")
                    ) {
                        // 📍 Posición inicial
                        val initial = LatLng(-33.4489, -70.6693)
                        map.cameraPosition = CameraPosition.Builder()
                            .target(initial)
                            .zoom(11.0)
                            .build()

                        // 🎯 Detectar clics en el mapa
                        map.addOnMapClickListener { latLng ->
                            val coords = "${latLng.latitude}, ${latLng.longitude}"
                            Toast.makeText(ctx, "Ubicación seleccionada: $coords", Toast.LENGTH_SHORT).show()
                            onLocationSelected(coords)
                            true
                        }
                    }
                }
            }
        }
    )
}

// 🌍 Función auxiliar para convertir dirección → coordenadas usando Geocoder (sin API externa)
suspend fun buscarCoordenadasPorDireccion(context: android.content.Context, direccion: String): LatLng? {
    return withContext(Dispatchers.IO) {
        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val resultados = geocoder.getFromLocationName(direccion, 1)
            if (!resultados.isNullOrEmpty()) {
                val ubicacion = resultados[0]
                LatLng(ubicacion.latitude, ubicacion.longitude)
            } else null
        } catch (e: Exception) {
            null
        }
    }
}
