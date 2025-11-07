package com.example.pasteleriamilsaboresapp.view

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pasteleriamilsaboresapp.utils.QrScanner
import com.example.pasteleriamilsaboresapp.viewmodel.QrViewModel
import com.example.pasteleriamilsaboresapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrScannerScreen(
    navController: NavController, // 👈 agregado para usar popBackStack()
    viewModel: QrViewModel,
    hasCameraPermission: Boolean,
    onRequestPermission: () -> Unit
) {
    val qrResult by viewModel.qrResult.observeAsState()
    val context = LocalContext.current
    var isScanning by remember { mutableStateOf(true) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(BeigeSuave)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // 🧁 Título
            Text(
                "Escáner de Código QR",
                style = MaterialTheme.typography.titleLarge,
                color = CafeSuave,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            when {
                // 🚫 Sin permiso
                !hasCameraPermission -> {
                    Text(
                        "Permiso de cámara requerido",
                        style = MaterialTheme.typography.titleMedium,
                        color = RosaIntenso
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Para escanear códigos QR, necesitamos acceso a la cámara.",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = CafeSuave
                    )
                    Spacer(Modifier.height(24.dp))
                    Button(
                        onClick = onRequestPermission,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RosaPastel,
                            contentColor = CafeSuave
                        ),
                        border = BorderStroke(1.dp, CafeSuave)
                    ) {
                        Text("Conceder permiso")
                    }
                }

                // 🎥 Escaneando
                qrResult == null && isScanning -> {
                    Text(
                        "Apunta tu cámara al código QR",
                        style = MaterialTheme.typography.bodyLarge,
                        color = CafeSuave,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .background(Color.Transparent),
                        contentAlignment = Alignment.Center
                    ) {
                        QrScanner(
                            onQrCodeScanned = { qrContent ->
                                val isUrl = qrContent.startsWith("http://") || qrContent.startsWith("https://")

                                if (isUrl) {
                                    try {
                                        val intent = Intent(Intent.ACTION_VIEW)
                                        intent.data = Uri.parse(qrContent)
                                        context.startActivity(intent)
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "Error al abrir el enlace", Toast.LENGTH_SHORT).show()
                                    }
                                } else {
                                    viewModel.onQrDetected(qrContent)
                                    isScanning = false
                                    Toast.makeText(context, "Código QR detectado", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier.fillMaxSize()
                        )

                        // 🎯 Marco visual
                        Surface(
                            modifier = Modifier
                                .size(260.dp)
                                .align(Alignment.Center),
                            color = Color.Transparent,
                            shape = MaterialTheme.shapes.medium,
                            border = BorderStroke(3.dp, RosaIntenso)
                        ) {}
                    }

                    Spacer(Modifier.height(16.dp))
                    Text(
                        "El contenido del código se abrirá automáticamente si es un enlace.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }

                // ✅ Resultado detectado
                qrResult != null -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "✅ Código detectado:",
                            style = MaterialTheme.typography.titleMedium,
                            color = CafeSuave,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFFFF5E1)
                            ),
                            border = BorderStroke(1.dp, CafeSuave)
                        ) {
                            Text(
                                qrResult!!.content,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(16.dp),
                                color = CafeSuave
                            )
                        }
                        Spacer(Modifier.height(20.dp))
                        Button(
                            onClick = {
                                viewModel.clearResult()
                                isScanning = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = RosaPastel,
                                contentColor = CafeSuave
                            )
                        ) {
                            Text("Escanear otro QR", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // 🔙 Botón volver
            OutlinedButton(
                onClick = { navController.popBackStack() },
                border = BorderStroke(2.dp, CafeSuave),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = CafeSuave)
            ) {
                Text("Volver", fontWeight = FontWeight.Bold)
            }
        }
    }
}
