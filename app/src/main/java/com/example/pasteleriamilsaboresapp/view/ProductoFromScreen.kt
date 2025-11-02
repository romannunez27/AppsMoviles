package com.example.pasteleriamilsaboresapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pasteleriamilsaboresapp.R
import com.example.pasteleriamilsaboresapp.data.model.Producto
import com.example.pasteleriamilsaboresapp.ui.theme.*
import com.example.pasteleriamilsaboresapp.viewmodel.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoFormScreen(
    navController: NavController,
    nombre: String,
    precio: String
) {
    var cantidad by remember { mutableStateOf(TextFieldValue("")) }
    var direccion by remember { mutableStateOf(TextFieldValue("")) }
    var mensajeDedicatoria by remember { mutableStateOf(false) }
    var agregarVela by remember { mutableStateOf(false) }

    // ⚙️ ViewModel (aún debes adaptarlo a tu versión pastelera)
    val viewModel: ProductoViewModel = viewModel()
    val productos: List<Producto> by viewModel.productos.collectAsState(initial = emptyList())

    PasteleriaMilSaboresTheme {
        Scaffold(
            bottomBar = {
                BottomAppBar(
                    containerColor = RosaPastel,
                    content = {}
                )
            }
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // 🧁 Imagen del producto
                Image(
                    painter = painterResource(id = R.drawable.logo_fndo_blanco),
                    contentDescription = "Imagen del producto",
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth()
                )

                Spacer(Modifier.height(16.dp))

                // 🧁 Datos base del producto
                Text(
                    text = nombre,
                    style = MaterialTheme.typography.titleLarge.copy(color = MarronOscuro)
                )
                Text(
                    text = "Precio: $precio",
                    style = MaterialTheme.typography.bodyMedium.copy(color = CafeSuave)
                )

                Spacer(Modifier.height(16.dp))

                // 🧁 Campos de entrada
                OutlinedTextField(
                    value = cantidad,
                    onValueChange = { cantidad = it },
                    label = { Text("Cantidad") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = RosaIntenso,
                        unfocusedBorderColor = CafeSuave,
                        focusedLabelColor = RosaIntenso
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = direccion,
                    onValueChange = { direccion = it },
                    label = { Text("Dirección de entrega") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = RosaIntenso,
                        unfocusedBorderColor = CafeSuave,
                        focusedLabelColor = RosaIntenso
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                // 🧁 Opciones adicionales
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = mensajeDedicatoria,
                        onCheckedChange = { mensajeDedicatoria = it },
                        colors = CheckboxDefaults.colors(checkedColor = RosaPastel)
                    )
                    Text("Agregar mensaje dedicatoria", color = MarronOscuro)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = agregarVela,
                        onCheckedChange = { agregarVela = it },
                        colors = CheckboxDefaults.colors(checkedColor = RosaPastel)
                    )
                    Text("Agregar vela decorativa", color = MarronOscuro)
                }

                Spacer(Modifier.height(20.dp))

                // 🧁 Botón guardar
                Button(
                    onClick = {
                        val producto = Producto(
                            nombre = nombre,
                            precio = precio,
                            cantidad = cantidad.text,
                            direccion = direccion.text,
                            mensajeDedicatoria = mensajeDedicatoria,
                            agregarVela = agregarVela
                        )
                        viewModel.guardarProducto(producto)
                    },
                    enabled = cantidad.text.isNotBlank() && direccion.text.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RosaPastel,
                        contentColor = CafeSuave
                    ),
                    modifier = Modifier.fillMaxWidth(0.7f)
                ) {
                    Text("Confirmar Pedido", style = MaterialTheme.typography.labelLarge)
                }

                Spacer(Modifier.height(16.dp))

                // 🧾 Lista de productos guardados
                Text(
                    "Pedidos realizados:",
                    style = MaterialTheme.typography.titleLarge.copy(color = MarronOscuro)
                )

                if (productos.isNotEmpty()) {
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(productos) { producto ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = FondoCrema)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = "${producto.nombre} - ${producto.precio}",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = MarronOscuro
                                        )
                                    )
                                    Text(
                                        text = "Cantidad: ${producto.cantidad}",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    Text(
                                        text = "Dirección: ${producto.direccion}",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    if (producto.mensajeDedicatoria)
                                        Text("💌 Incluye dedicatoria", style = MaterialTheme.typography.bodySmall)
                                    if (producto.agregarVela)
                                        Text("🕯️ Incluye vela decorativa", style = MaterialTheme.typography.bodySmall)
                                }
                            }
                        }
                    }
                } else {
                    Text(
                        text = "No hay pedidos realizados",
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyMedium.copy(color = MarronOscuro)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProductoFormScreen() {
    ProductoFormScreen(
        navController = rememberNavController(),
        nombre = "Torta Tres Leches",
        precio = "$15.000"
    )
}
