package com.example.pasteleriamilsaboresapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.pasteleriamilsaboresapp.viewmodel.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoFromScreen(
    navController: NavController,
    productoId: String,
    productoViewModel: ProductoViewModel = viewModel()
) {
    val producto by productoViewModel.productoSeleccionado.collectAsState()
    var cantidad by remember { mutableStateOf(TextFieldValue("")) }
    var direccion by remember { mutableStateOf(TextFieldValue("")) }
    var mensajeDedicatoria by remember { mutableStateOf(false) }
    var agregarVela by remember { mutableStateOf(false) }
    var mensajeExito by remember { mutableStateOf(false) }

    LaunchedEffect(productoId) {
        productoViewModel.cargarProductoDesdeFirestore(productoId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        if (producto == null) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 🖼️ Imagen
                Image(
                    painter = rememberAsyncImagePainter(model = producto!!.imagen),
                    contentDescription = "Imagen del producto",
                    modifier = Modifier
                        .size(220.dp)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 🧁 Nombre
                Text(
                    text = producto!!.nombre,
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center
                )

                // 💵 Precio
                Text(
                    text = "Precio: $${producto!!.precio}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 🔢 Cantidad
                OutlinedTextField(
                    value = cantidad,
                    onValueChange = { if (it.text.all { c -> c.isDigit() }) cantidad = it },
                    label = { Text("Cantidad") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(0.9f)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // 📍 Dirección
                OutlinedTextField(
                    value = direccion,
                    onValueChange = { direccion = it },
                    label = { Text("Dirección de entrega") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.9f)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // 💌 Switches
                Row(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("¿Agregar mensaje dedicatoria?")
                    Switch(checked = mensajeDedicatoria, onCheckedChange = { mensajeDedicatoria = it })
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("¿Agregar vela?")
                    Switch(checked = agregarVela, onCheckedChange = { agregarVela = it })
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 🛒 Botón Comprar
                Button(
                    onClick = {
                        if (cantidad.text.isNotBlank() && direccion.text.isNotBlank()) {
                            productoViewModel.guardarCompra(
                                producto = producto!!,
                                cantidad = cantidad.text.toInt(),
                                direccion = direccion.text,
                                mensajeDedicatoria = mensajeDedicatoria,
                                agregarVela = agregarVela
                            )
                            mensajeExito = true
                        }
                    }
                ) {
                    Text("Comprar")
                }

                if (mensajeExito) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "¡Compra registrada con éxito!",
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
                TextButton(onClick = { navController.popBackStack() }) {
                    Text("Volver al catálogo")
                }
            }
        }
    }
}
