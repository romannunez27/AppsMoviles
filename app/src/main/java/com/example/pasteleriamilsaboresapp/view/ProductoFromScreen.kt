package com.example.pasteleriamilsaboresapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pasteleriamilsaboresapp.ui.catalogo.productosCatalogo
import com.example.pasteleriamilsaboresapp.ui.components.CommonTopBar
import com.example.pasteleriamilsaboresapp.ui.theme.*
import com.example.pasteleriamilsaboresapp.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoFormScreen(
    navController: NavController,
    codigo: String,
    nombre: String,
    precio: String,
    cartViewModel: CartViewModel? = null
) {
    val precioUnitario = precio.toIntOrNull() ?: 0

    val itemsCarrito = cartViewModel?.items?.collectAsState()?.value ?: emptyList()
    val cartCount = itemsCarrito.sumOf { it.cantidad }

    val productoBase = productosCatalogo.firstOrNull { it.codigo == codigo }
    val stockTotal = productoBase?.stock ?: Int.MAX_VALUE
    val descripcion = productoBase?.descripcion
        ?: "Deliciosa torta artesanal, perfecta para celebrar momentos especiales."

    val enCarrito = itemsCarrito
        .filter { it.codigo == codigo }
        .sumOf { it.cantidad }

    val stockDisponible = (stockTotal - enCarrito).coerceAtLeast(0)

    var cantidad by remember { mutableStateOf(TextFieldValue(if (stockDisponible > 0) "1" else "0")) }
    var dedicatoria by remember { mutableStateOf(TextFieldValue("")) }

    var usarVelas by remember { mutableStateOf(false) }
    var velasCantidad by remember { mutableStateOf(1) }

    // 🔢 hasta 3 velas, como strings (más fácil de editar/borrar)
    var velasNumeros by remember { mutableStateOf(listOf("", "", "")) }

    var mensajeExito by remember { mutableStateOf(false) }

    val cantidadInt = cantidad.text.toIntOrNull() ?: 0

    // ✅ Validación de velas:
    // si están activas, todas las usadas (hasta velasCantidad) deben tener 1 dígito
    val camposVelasCompletos =
        !usarVelas || velasNumeros
            .take(velasCantidad)
            .all { txt -> txt.length == 1 && txt[0].isDigit() }

    val velasExtra = if (usarVelas && cantidadInt > 0) velasCantidad * 2000 * cantidadInt else 0
    val subtotal = (precioUnitario * cantidadInt) + velasExtra

    Scaffold(
        topBar = {
            CommonTopBar(
                showBackButton = true,
                onMenuClick = { navController.popBackStack() },
                onCartClick = { navController.navigate("carrito") },
                onProfileClick = { navController.navigate("perfil") },
                cartCount = cartCount
            )
        },
        containerColor = FondoCrema
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(FondoCrema)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = nombre,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center,
                    color = MarronOscuro
                )

                Text(
                    text = "Precio base: $$precio",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MarronOscuro
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify,
                    color = MarronOscuro
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = if (stockDisponible <= 0)
                        "Sin stock disponible para este producto."
                    else
                        "Stock disponible: $stockDisponible",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (stockDisponible <= 0) MaterialTheme.colorScheme.error else MarronOscuro
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 🔢 Cantidad
                OutlinedTextField(
                    value = cantidad,
                    onValueChange = {
                        val value = it.text.toIntOrNull() ?: 0
                        when {
                            it.text.isEmpty() -> cantidad = TextFieldValue("")
                            value in 1..stockDisponible -> cantidad = TextFieldValue(value.toString())
                        }
                    },
                    label = { Text("Cantidad de tortas") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(0.9f),
                    enabled = stockDisponible > 0
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 📝 Mensaje en la torta
                OutlinedTextField(
                    value = dedicatoria,
                    onValueChange = { dedicatoria = it },
                    label = { Text("Mensaje en la torta (ej: Feliz cumpleaños Juan)") },
                    singleLine = false,
                    modifier = Modifier.fillMaxWidth(0.9f),
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 🕯️ Configuración de velas numéricas
                Row(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("¿Agregar velas numéricas?")
                    Switch(
                        checked = usarVelas,
                        onCheckedChange = { usarVelas = it }
                    )
                }

                if (usarVelas) {
                    Spacer(modifier = Modifier.height(8.dp))

                    // Cantidad de velas 1–3
                    Row(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Cantidad de velas (máx. 3)")
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = {
                                    if (velasCantidad > 1) velasCantidad--
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Remove,
                                    contentDescription = "Menos velas"
                                )
                            }
                            Text(
                                text = velasCantidad.toString(),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.width(24.dp),
                                textAlign = TextAlign.Center
                            )
                            IconButton(
                                onClick = {
                                    if (velasCantidad < 3) velasCantidad++
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = "Más velas"
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Un campo por vela (0–9) – se puede borrar/cambiar, pero no se puede comprar si alguno está vacío
                    Column(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for (i in 0 until velasCantidad) {
                            OutlinedTextField(
                                value = velasNumeros[i],
                                onValueChange = { nuevo ->
                                    val texto = nuevo
                                    // vacío o 1 dígito → permitido; más de 1 → ignoramos
                                    if (texto.isEmpty() || (texto.length == 1 && texto[0].isDigit())) {
                                        velasNumeros = velasNumeros.toMutableList().also { lista ->
                                            lista[i] = texto
                                        }
                                    }
                                },
                                label = { Text("Número de la vela ${i + 1}") },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        if (!camposVelasCompletos) {
                            Text(
                                text = "Completa todos los números de las velas (0 a 9).",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 💰 Subtotal
                Card(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Subtotal: $$subtotal",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MarronOscuro
                        )
                        if (usarVelas && cantidadInt > 0) {
                            Text(
                                text = "Incluye ${velasCantidad * cantidadInt} vela(s) adicionales ($${velasCantidad * 2000 * cantidadInt})",
                                style = MaterialTheme.typography.bodySmall,
                                color = MarronOscuro
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                val puedeAgregar =
                    cantidadInt in 1..stockDisponible &&
                            cartViewModel != null &&
                            camposVelasCompletos

                // 🛒 Agregar al carrito
                Button(
                    onClick = {
                        if (puedeAgregar && cartViewModel != null) {
                            val numeros = if (usarVelas) {
                                velasNumeros
                                    .take(velasCantidad)
                                    .joinToString(" y ") { it }
                            } else null

                            cartViewModel.agregarProductoAlCarrito(
                                codigo = codigo,
                                nombre = nombre,
                                precioUnitario = precioUnitario,
                                cantidad = cantidadInt,
                                dedicatoria = dedicatoria.text.ifBlank { null },
                                velasCantidad = if (usarVelas) velasCantidad else 0,
                                velasNumeros = numeros
                            )
                            mensajeExito = true
                        }
                    },
                    enabled = puedeAgregar,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RosaPastel,
                        contentColor = MarronOscuro
                    )
                ) {
                    Text("Agregar al carrito")
                }

                if (mensajeExito) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Producto agregado al carrito 🧺",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                    TextButton(onClick = { navController.navigate("carrito") }) {
                        Text("Ir al carrito", color = MarronOscuro)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = { navController.popBackStack() }) {
                    Text("Volver al catálogo", color = MarronOscuro)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewProductoFormScreen() {
    PasteleriaMilSaboresTheme {
        ProductoFormScreen(
            navController = rememberNavController(),
            codigo = "TC001",
            nombre = "Torta Cuadrada de Chocolate",
            precio = "45000",
            cartViewModel = null
        )
    }
}
