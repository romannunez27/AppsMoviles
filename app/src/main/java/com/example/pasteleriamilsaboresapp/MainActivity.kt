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

/*package com.example.camara

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.camara.ui.view.QrScannerScreen
import com.example.camara.ui.viewmodel.QrViewModel
import com.example.camara.utils.CameraPermissionHelper

class MainActivity : ComponentActivity() {

    private val qrViewModel: QrViewModel by viewModels()

    // Estado para controlar si tenemos permiso
    private var hasCameraPermission by mutableStateOf(false)

    // Registro para solicitar permisos
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
        if (isGranted) {
            Toast.makeText(this, "Permiso de cámara concedido", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Se necesita permiso de cámara para escanear QR", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Verificar permiso inicial
        hasCameraPermission = CameraPermissionHelper.hasCameraPermission(this)

        setContent {
            // Use MaterialTheme directly as a quick fix
            MaterialTheme {
                Surface {
                    QrScannerScreen(
                        viewModel = qrViewModel,
                        hasCameraPermission = hasCameraPermission,
                        onRequestPermission = {
                            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    )
                }
            }
        }

        // Observa los resultados del QR
        qrViewModel.qrResult.observe(this) { qrResult ->
            qrResult?.let { result ->
                Toast.makeText(this, "Código QR Detectado: ${result.content}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Actualizar estado del permiso cuando la app se reanuda
        hasCameraPermission = CameraPermissionHelper.hasCameraPermission(this)
    }
}*/
