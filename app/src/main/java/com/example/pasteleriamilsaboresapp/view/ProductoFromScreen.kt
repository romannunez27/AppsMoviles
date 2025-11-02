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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.pasteleriamilsaboresapp.data.model.Producto
import com.example.pasteleriamilsaboresapp.ui.theme.PasteleriaMilSaboresTheme
import com.example.pasteleriamilsaboresapp.viewmodel.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoFormScreen(
    navController: NavController,
    nombre: String,
    precio: String,
    productoViewModel: ProductoViewModel = viewModel()
) {
    // Estados locales del formulario
    var cantidad by remember { mutableStateOf(TextFieldValue("")) }
    var direccion by remember { mutableStateOf(TextFieldValue("")) }
    var mensajeDedicatoria by remember { mutableStateOf(false) }
    var agregarVela by remember { mutableStateOf(false) }
    var mensajeExito by remember { mutableStateOf(false) }

    // Fondo principal del formulario
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 🖼️ Imagen del producto
            Image(
                painter = rememberAsyncImagePainter(model = "file:///android_asset/${nombre.replace(" ", "_").lowercase()}.jpg"),
                contentDescription = "Imagen del producto",
                modifier = Modifier
                    .size(220.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 🧁 Título del producto
            Text(
                text = nombre,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )

            // 💵 Precio
            Text(
                text = "Precio: $$precio",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 🔢 Campo cantidad (solo números)
            OutlinedTextField(
                value = cantidad,
                onValueChange = {
                    // Filtramos para permitir solo dígitos
                    if (it.text.all { char -> char.isDigit() }) cantidad = it
                },
                label = { Text("Cantidad") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(0.9f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    focusedLabelColor = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 📍 Campo dirección
            OutlinedTextField(
                value = direccion,
                onValueChange = { direccion = it },
                label = { Text("Dirección de entrega") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.9f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    focusedLabelColor = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 💌 Switch: mensaje dedicatoria
            Row(
                modifier = Modifier.fillMaxWidth(0.9f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("¿Agregar mensaje dedicatoria?", color = MaterialTheme.colorScheme.onSurface)
                Switch(
                    checked = mensajeDedicatoria,
                    onCheckedChange = { mensajeDedicatoria = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.primary
                    )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 🕯️ Switch: agregar vela
            Row(
                modifier = Modifier.fillMaxWidth(0.9f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("¿Agregar vela?", color = MaterialTheme.colorScheme.onSurface)
                Switch(
                    checked = agregarVela,
                    onCheckedChange = { agregarVela = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.primary
                    )
                )
            }


            Spacer(modifier = Modifier.height(24.dp))

            // Botón de compra
            Button(
                onClick = {
                    if (cantidad.text.isNotBlank() && direccion.text.isNotBlank()) {
                        val nuevoProducto = Producto(
                            nombre = nombre,
                            precio = precio,
                            cantidad = cantidad.text,
                            direccion = direccion.text,
                            mensajeDedicatoria = mensajeDedicatoria,
                            agregarVela = agregarVela
                        )
                        productoViewModel.guardarProducto(nuevoProducto)
                        mensajeExito = true
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Comprar")
            }

            // Mensaje de confirmación
            if (mensajeExito) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "¡Compra registrada con éxito!",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Volver al catálogo
            TextButton(onClick = { navController.popBackStack() }) {
                Text("Volver al catálogo", color = MaterialTheme.colorScheme.onSurface)
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewProductoFormScreen() {
    // ✅ Creamos un repositorio real con un DAO simulado
    val fakeRepository = com.example.pasteleriamilsaboresapp.data.repository.ProductRepository(
        productoDao = object : com.example.pasteleriamilsaboresapp.data.dao.ProductoDao {
            override suspend fun insertarProducto(producto: com.example.pasteleriamilsaboresapp.data.model.Producto) {
                // No hace nada, solo simula
            }

            override fun obtenerProductos(): kotlinx.coroutines.flow.Flow<List<com.example.pasteleriamilsaboresapp.data.model.Producto>> {
                // Devuelve una lista vacía simulada
                return kotlinx.coroutines.flow.flowOf(emptyList())
            }
        }
    )

    val fakeViewModel = remember {
        com.example.pasteleriamilsaboresapp.viewmodel.ProductoViewModel(repository = fakeRepository)
    }

    com.example.pasteleriamilsaboresapp.ui.theme.PasteleriaMilSaboresTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            com.example.pasteleriamilsaboresapp.view.ProductoFormScreen(
                navController = rememberNavController(),
                nombre = "Torta Cuadrada de Chocolate",
                precio = "45000",
                productoViewModel = fakeViewModel
            )
        }
    }
}
