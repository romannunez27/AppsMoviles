package com.example.pasteleriamilsaboresapp.ui.catalogo

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.pasteleriamilsaboresapp.ui.theme.PasteleriaMilSaboresTheme
import com.example.pasteleriamilsaboresapp.ui.view.DrawerMenu
import kotlinx.coroutines.launch
import com.example.pasteleriamilsaboresapp.ui.theme.CafeSuave
import com.example.pasteleriamilsaboresapp.ui.theme.MarronOscuro
import com.example.pasteleriamilsaboresapp.ui.theme.RosaPastel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

data class ProductoCatalogo(
    val codigo: String,
    val nombre: String,
    val precio: Int,
    val img: Int
)

// 🔹 Lista de productos estáticos
val productosCatalogo = listOf(
    ProductoCatalogo("TC001", "Torta Cuadrada de Chocolate", 45000, R.drawable.tc001),
    ProductoCatalogo("TC002", "Torta Cuadrada de Frutas", 50000, R.drawable.tc002),
    ProductoCatalogo("TT001", "Torta Circular de Vainilla", 40000, R.drawable.tt001),
    ProductoCatalogo("TT002", "Torta Circular de Manjar", 42000, R.drawable.tt002),
    ProductoCatalogo("PI001", "Mousse de Chocolate", 5000, R.drawable.pi001),
    ProductoCatalogo("PI002", "Tiramisú Clásico", 5500, R.drawable.pi002),
    ProductoCatalogo("PSA001", "Torta Sin Azúcar de Naranja", 48000, R.drawable.psa001),
    ProductoCatalogo("PSA002", "Cheesecake Sin Azúcar", 47000, R.drawable.psa002),
    ProductoCatalogo("PT001", "Empanada de Manzana", 3000, R.drawable.pt001),
    ProductoCatalogo("PT002", "Tarta de Santiago", 6000, R.drawable.pt002),
    ProductoCatalogo("PG001", "Brownie Sin Gluten", 4000, R.drawable.pg001),
    ProductoCatalogo("PG002", "Pan Sin Gluten", 3500, R.drawable.pg002),
    ProductoCatalogo("PV001", "Torta Vegana de Chocolate", 50000, R.drawable.pv001),
    ProductoCatalogo("PV002", "Galletas Veganas de Avena", 4500, R.drawable.pv002),
    ProductoCatalogo("TE001", "Torta Especial de Cumpleaños", 55000, R.drawable.te001),
    ProductoCatalogo("TE002", "Torta Especial de Boda", 60000, R.drawable.te002)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(navController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerMenu(
                navController = navController,
                drawerState = drawerState,
                closeDrawer = { scope.launch { drawerState.close() } }
            )
        }
    )
    {
        Scaffold(
            topBar = {
                CommonTopBar(
                    onMenuClick = { scope.launch { drawerState.open() } },
                    onCartClick = { navController.navigate("carrito") },
                    onProfileClick = { navController.navigate("perfil") }
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(productosCatalogo) { producto ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Imagen con tamaño estandarizado
                            Image(
                                painter = painterResource(id = producto.img),
                                contentDescription = producto.nombre,
                                modifier = Modifier
                                    .height(180.dp)
                                    .width(220.dp)
                                    .padding(6.dp),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = producto.nombre,
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Text(
                                text = "$${producto.precio}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // Botón "Ver detalles" con borde visible
                            OutlinedButton(
                                onClick = {
                                    navController.navigate("productoForm/${producto.nombre}/${producto.precio}")
                                },
                                border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = RosaPastel
                                )
                            ) {
                                Text("Ver detalles", color = MarronOscuro)
                            }


                        }
                    }
                }

                item {
                    CommonFooter(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                    )

                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCatalogoScreen() {
    PasteleriaMilSaboresTheme {
        CatalogoScreen(
            navController = rememberNavController()
        )
    }
}
