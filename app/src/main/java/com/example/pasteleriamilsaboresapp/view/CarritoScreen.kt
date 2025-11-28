package com.example.pasteleriamilsaboresapp.view

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pasteleriamilsaboresapp.ui.catalogo.productosCatalogo
import com.example.pasteleriamilsaboresapp.ui.components.CommonTopBar
import com.example.pasteleriamilsaboresapp.ui.components.MapaInteractivo
import com.example.pasteleriamilsaboresapp.ui.theme.FondoCrema
import com.example.pasteleriamilsaboresapp.ui.theme.MarronOscuro
import com.example.pasteleriamilsaboresapp.ui.theme.RosaPastel
import com.example.pasteleriamilsaboresapp.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    navController: NavController,
    cartViewModel: CartViewModel
) {
    val context = LocalContext.current

    val items by cartViewModel.items.collectAsState()
    val total by cartViewModel.total.collectAsState()

    var direccion by remember { mutableStateOf(TextFieldValue("")) }
    var mensajeDedicatoriaGeneral by remember { mutableStateOf(false) }
    var mensajeExito by remember { mutableStateOf(false) }

    val cartCount = items.sumOf { it.cantidad }

    Scaffold(
        topBar = {
            CommonTopBar(
                showBackButton = true,
                onMenuClick = { navController.popBackStack() },
                onCartClick = { /* ya estamos en el carrito */ },
                onProfileClick = { navController.navigate("perfil") },
                cartCount = cartCount
            )
        },
        containerColor = FondoCrema
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(FondoCrema)
        ) {
            if (items.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Tu carrito está vacío 🧁",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = MarronOscuro,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Agrega productos desde el catálogo para verlos aquí.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MarronOscuro,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = { navController.navigate("catalogo") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RosaPastel,
                            contentColor = MarronOscuro
                        )
                    ) {
                        Text("Ir al catálogo")
                    }
                }
                return@Column
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items) { item ->
                    val base = productosCatalogo.firstOrNull { it.codigo == item.codigo }
                    val stockTotal = base?.stock ?: Int.MAX_VALUE
                    val cantidadTotalMismoCodigo = items
                        .filter { it.codigo == item.codigo }
                        .sumOf { it.cantidad }

                    val puedeIncrementar = cantidadTotalMismoCodigo < stockTotal

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = item.nombre,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.SemiBold
                                    ),
                                    color = MarronOscuro
                                )
                                Text(
                                    text = "Precio unitario: $${item.precioUnitario}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "Subtotal: $${item.subtotal}",
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                if (!item.dedicatoria.isNullOrBlank()) {
                                    Text(
                                        text = "Mensaje: \"${item.dedicatoria}\"",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                                if (item.velasCantidad > 0) {
                                    Text(
                                        text = "Velas: ${item.velasCantidad} por torta (${item.velasNumeros ?: "sin detalle de número"})",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }

                                if (base != null) {
                                    Text(
                                        text = "Stock total: ${base.stock} | En carrito: $cantidadTotalMismoCodigo",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = if (puedeIncrementar) MarronOscuro else MaterialTheme.colorScheme.error
                                    )
                                }
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = { cartViewModel.decrementarCantidad(item) }) {
                                    Icon(
                                        imageVector = Icons.Filled.Remove,
                                        contentDescription = "Disminuir"
                                    )
                                }
                                Text(
                                    text = item.cantidad.toString(),
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.width(24.dp),
                                    textAlign = TextAlign.Center
                                )
                                IconButton(
                                    onClick = { cartViewModel.incrementarCantidad(item) },
                                    enabled = puedeIncrementar
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Add,
                                        contentDescription = "Aumentar"
                                    )
                                }
                            }

                            IconButton(onClick = { cartViewModel.eliminarItem(item) }) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Eliminar"
                                )
                            }
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Datos de entrega",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MarronOscuro,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp)
                )

                OutlinedTextField(
                    value = direccion,
                    onValueChange = { direccion = it },
                    label = { Text("Dirección de entrega") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                MapaInteractivo(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    direccionTexto = direccion.text,
                    onLocationSelected = { nuevaDireccion ->
                        direccion = TextFieldValue(nuevaDireccion)
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("¿Agregar dedicatoria general?")
                    Switch(
                        checked = mensajeDedicatoriaGeneral,
                        onCheckedChange = { mensajeDedicatoriaGeneral = it }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Total: $$total",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MarronOscuro
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (direccion.text.isNotBlank() && items.isNotEmpty()) {
                            cartViewModel.confirmarCompra(
                                direccion = direccion.text,
                                mensajeDedicatoria = mensajeDedicatoriaGeneral,
                                agregarVela = false
                            )
                            mensajeExito = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RosaPastel,
                        contentColor = MarronOscuro
                    )
                ) {
                    Text("Confirmar compra")
                }

                TextButton(
                    onClick = { navController.navigate("catalogo") },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Seguir comprando", color = MarronOscuro)
                }
            }
        }

        // 🎉 Popup de compra exitosa con contraste
        if (mensajeExito) {
            AlertDialog(
                onDismissRequest = { /* obligamos a elegir opción */ },
                containerColor = FondoCrema,
                title = {
                    Text(
                        text = "Compra realizada",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MarronOscuro
                    )
                },
                text = {
                    Text(
                        text = "¡Felicidades! Tu pedido ha sido registrado con éxito.\n\nPronto recibirás tu pedido en la dirección indicada.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MarronOscuro
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            mensajeExito = false
                            navController.navigate("home") {
                                popUpTo("home") { inclusive = true }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RosaPastel,
                            contentColor = MarronOscuro
                        )
                    ) {
                        Text("Volver al inicio")
                    }
                },
                dismissButton = {
                    OutlinedButton(
                        onClick = {
                            mensajeExito = false
                            (context as? Activity)?.finishAffinity()
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MarronOscuro
                        ),
                        border = ButtonDefaults.outlinedButtonBorder.copy(
                            width = 1.dp,
                            brush = androidx.compose.ui.graphics.SolidColor(RosaPastel)
                        )
                    ) {
                        Text("Salir")
                    }
                }
            )
        }
    }
}
