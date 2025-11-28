package com.example.pasteleriamilsaboresapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pasteleriamilsaboresapp.R
import com.example.pasteleriamilsaboresapp.ui.components.CommonFooter
import com.example.pasteleriamilsaboresapp.ui.components.CommonTopBar
import com.example.pasteleriamilsaboresapp.ui.theme.*
import com.example.pasteleriamilsaboresapp.ui.view.DrawerMenu
import com.example.pasteleriamilsaboresapp.viewmodel.CartViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeUserScreen(
    navController: NavController,
    cartViewModel: CartViewModel? = null      // 👈 ahora acepta cartViewModel opcional
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // 🛒 Cantidad de productos en el carrito (0 si no hay viewModel, ej. en Preview)
    val itemsCarrito = cartViewModel?.items?.collectAsState()?.value ?: emptyList()
    val cartCount = itemsCarrito.sumOf { it.cantidad }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerMenu(
                navController = navController,
                drawerState = drawerState,
                closeDrawer = { scope.launch { drawerState.close() } }
            )
        }
    ) {
        Scaffold(
            topBar = {
                CommonTopBar(
                    onMenuClick = { scope.launch { drawerState.open() } },
                    onCartClick = { navController.navigate("carrito") },
                    onProfileClick = { navController.navigate("perfil") },
                    cartCount = cartCount                      // 👈 se muestra el badge aquí
                )
            },
            containerColor = FondoCrema
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(FondoCrema),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 🧁 Sección de bienvenida
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
                            text = "Nuestra pastelería es un lugar tradicional, donde cada postre refleja la dedicación y el amor por los sabores de antaño...",
                            color = MarronOscuro,
                            textAlign = TextAlign.Justify
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { navController.navigate("catalogo") },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = RosaPastel,
                                contentColor = CafeSuave
                            )
                        ) {
                            Text("Ver productos 🍰")
                        }
                    }
                }

                // 🎂 Carrusel promocional
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
                        items(images) { img: Int ->
                            Card(
                                modifier = Modifier
                                    .width(280.dp)
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

                // 🍰 Productos destacados
                val productos = listOf(
                    Triple("Torta Especial de Cumpleaños", "$55.000", R.drawable.te001),
                    Triple("Torta Circular de Manjar", "$42.000", R.drawable.tt002),
                    Triple("Torta Cuadrada de Chocolate", "$45.000", R.drawable.tc001),
                    Triple("Torta Sin Azúcar de Naranja", "$48.000", R.drawable.psa001)
                )

                items(productos) { (nombre, precio, imagen) ->
                    Card(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(0.9f)
                            .border(2.dp, CafeSuave),
                        colors = CardDefaults.cardColors(containerColor = BeigeSuave)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Image(
                                painter = painterResource(id = imagen),
                                contentDescription = nombre,
                                modifier = Modifier
                                    .size(100.dp)
                                    .border(1.dp, CafeSuave),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(Modifier.width(12.dp))
                            Column {
                                Text(nombre, color = CafeSuave, fontWeight = FontWeight.Bold)
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    "Deliciosa opción artesanal elaborada con amor y tradición.",
                                    color = MarronOscuro
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(precio, color = MarronOscuro, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                // 🍮 Footer scrolleable
                item {
                    Spacer(modifier = Modifier.height(32.dp))
                    CommonFooter(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(FondoCrema)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewHomeUserScreen() {
    val navController = rememberNavController()
    PasteleriaMilSaboresTheme {
        // 👇 En preview no pasamos cartViewModel, usa el default = null
        HomeUserScreen(navController = navController)
    }
}
