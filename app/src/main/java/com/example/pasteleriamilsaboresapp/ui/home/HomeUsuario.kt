package com.example.pasteleriamilsaboresapp.ui.home

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pasteleriamilsaboresapp.R
import com.example.pasteleriamilsaboresapp.ui.theme.*
import com.example.pasteleriamilsaboresapp.view.DrawerMenu
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeUserScreen(
    onNavigateTo: (String) -> Unit,
    onCartClick: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { DrawerMenu(onNavigateTo) }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Pastelería Mil Sabores",
                            color = CafeSuave,
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú", tint = CafeSuave)
                        }
                    },
                    actions = {
                        IconButton(onClick = onCartClick) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito", tint = CafeSuave)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = RosaPastel)
                )
            },
            containerColor = FondoCrema
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(FondoCrema)
            ) {
                // 🩷 Descripción principal
                item {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Bienvenidos a Pastelería Mil Sabores",
                            style = MaterialTheme.typography.titleLarge,
                            color = CafeSuave
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Nuestra pastelería es un lugar tradicional, donde cada postre refleja la dedicación y el amor por los sabores de antaño. Conservamos un aire nostálgico que transporta a nuestros clientes a recuerdos dulces de la infancia, pero con la frescura y calidad que esperan hoy en cada bocado.",
                            color = MarronOscuro,
                            textAlign = TextAlign.Justify
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { onNavigateTo("catalogo") },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = RosaPastel,
                                contentColor = CafeSuave
                            ),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text("Ver Productos 🍰")
                        }
                    }
                }

                // 🍰 Carrusel de imágenes (simplificado)
                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        val images = listOf(
                            R.drawable.cuadro_final,
                            R.drawable.imagen_promocional_p,
                            R.drawable.blog
                        )
                        items(images) { img ->
                            Card(
                                modifier = Modifier
                                    .width(300.dp)
                                    .height(180.dp),
                                colors = CardDefaults.cardColors(containerColor = FondoCrema),
                                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = img),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }

                // 🎂 Productos destacados
                val productos = listOf(
                    "Torta Especial de Cumpleaños" to "$55.000",
                    "Torta Circular de Manjar" to "$42.000",
                    "Torta Cuadrada de Chocolate" to "$45.000",
                    "Torta Sin Azúcar de Naranja" to "$48.000"
                )

                items(productos) { (nombre, precio) ->
                    Card(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth()
                            .border(2.dp, CafeSuave),
                        colors = CardDefaults.cardColors(containerColor = BeigeSuave)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(nombre, color = CafeSuave, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(4.dp))
                            Text("Deliciosa opción artesanal elaborada con amor y tradición.")
                            Spacer(Modifier.height(8.dp))
                            Text(precio, color = MarronOscuro, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
@Preview(
    showBackground = true)
@Composable
fun PreviewHomeUserScreen() {
    com.example.pasteleriamilsaboresapp.ui.theme.PasteleriaMilSaboresTheme {
        HomeUserScreen(
            onNavigateTo = {},
            onCartClick = {}
        )
    }
}