package com.example.pasteleriamilsaboresapp.ui.catalogo

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.pasteleriamilsaboresapp.ui.theme.PasteleriaMilSaboresTheme
import com.example.pasteleriamilsaboresapp.ui.view.DrawerMenu
import com.example.pasteleriamilsaboresapp.ui.theme.MarronOscuro
import com.example.pasteleriamilsaboresapp.ui.theme.RosaPastel
import com.example.pasteleriamilsaboresapp.viewmodel.CartViewModel
import kotlinx.coroutines.launch

data class ProductoCatalogo(
    val codigo: String,
    val nombre: String,
    val precio: Int,
    val img: Int,
    val stock: Int,
    val descripcion: String
)

// 🔹 Lista de productos estáticos con stock
val productosCatalogo = listOf(
    ProductoCatalogo(
        "TC001",
        "Torta Cuadrada de Chocolate",
        45000,
        R.drawable.tc001,
        stock = 3,
        descripcion = "Bizcocho de chocolate húmedo con relleno de manjar casero y cobertura de ganache de cacao, decorada con virutas de chocolate."
    ),
    ProductoCatalogo(
        "TC002",
        "Torta Cuadrada de Frutas",
        50000,
        R.drawable.tc002,
        stock = 2,
        descripcion = "Esponjoso bizcocho de vainilla relleno con crema pastelera y mezcla de frutas frescas de temporada, ideal para celebraciones familiares."
    ),
    ProductoCatalogo(
        "TT001",
        "Torta Circular de Vainilla",
        40000,
        R.drawable.tt001,
        stock = 4,
        descripcion = "Clásica torta de vainilla con relleno de crema suave y una ligera capa de almíbar cítrico, perfecta para quienes prefieren sabores tradicionales."
    ),
    ProductoCatalogo(
        "TT002",
        "Torta Circular de Manjar",
        42000,
        R.drawable.tt002,
        stock = 5,
        descripcion = "Torta húmeda rellena y cubierta completamente de manjar, con un toque de nueces picadas para aportar textura y sabor."
    ),
    ProductoCatalogo(
        "PI001",
        "Mousse de Chocolate",
        5000,
        R.drawable.pi001,
        stock = 10,
        descripcion = "Postre individual de mousse de chocolate semiamargo, de textura aireada y suave, servido sobre una base de galleta crocante."
    ),
    ProductoCatalogo(
        "PI002",
        "Tiramisú Clásico",
        5500,
        R.drawable.pi002,
        stock = 8,
        descripcion = "Clásico tiramisú italiano con bizcochos remojados en café, crema de mascarpone y suave toque de cacao en polvo."
    ),
    ProductoCatalogo(
        "PSA001",
        "Torta Sin Azúcar de Naranja",
        48000,
        R.drawable.psa001,
        stock = 2,
        descripcion = "Torta elaborada sin azúcar añadida, con bizcocho de naranja y endulzantes naturales, ideal para quienes buscan opciones más livianas."
    ),
    ProductoCatalogo(
        "PSA002",
        "Cheesecake Sin Azúcar",
        47000,
        R.drawable.psa002,
        stock = 3,
        descripcion = "Cheesecake cremoso sin azúcar añadida sobre base de galleta integral, con topping suave de frutos rojos."
    ),
    ProductoCatalogo(
        "PT001",
        "Empanada de Manzana",
        3000,
        R.drawable.pt001,
        stock = 12,
        descripcion = "Masa crujiente rellena de manzana caramelizada con canela, perfecta para acompañar un café o té de la tarde."
    ),
    ProductoCatalogo(
        "PT002",
        "Tarta de Santiago",
        6000,
        R.drawable.pt002,
        stock = 6,
        descripcion = "Tradicional tarta de almendras de estilo español, con textura suave y aroma intenso, espolvoreada con azúcar flor."
    ),
    ProductoCatalogo(
        "PG001",
        "Brownie Sin Gluten",
        4000,
        R.drawable.pg001,
        stock = 7,
        descripcion = "Brownie intenso de chocolate elaborado sin gluten, con interior húmedo y trozos de chocolate derretido."
    ),
    ProductoCatalogo(
        "PG002",
        "Pan Sin Gluten",
        3500,
        R.drawable.pg002,
        stock = 9,
        descripcion = "Pan artesanal sin gluten, de miga suave y corteza ligera, ideal para acompañar comidas o preparar tostadas."
    ),
    ProductoCatalogo(
        "PV001",
        "Torta Vegana de Chocolate",
        50000,
        R.drawable.pv001,
        stock = 2,
        descripcion = "Torta de chocolate 100% vegana, sin lácteos ni huevo, con relleno de crema vegetal y cobertura de cacao."
    ),
    ProductoCatalogo(
        "PV002",
        "Galletas Veganas de Avena",
        4500,
        R.drawable.pv002,
        stock = 10,
        descripcion = "Galletas crocantes de avena y frutos secos, libres de productos de origen animal, perfectas para snack o desayuno."
    ),
    ProductoCatalogo(
        "TE001",
        "Torta Especial de Cumpleaños",
        55000,
        R.drawable.te001,
        stock = 3,
        descripcion = "Torta personalizada de cumpleaños, con capas de bizcocho a elección, relleno doble y decoración temática para la ocasión."
    ),
    ProductoCatalogo(
        "TE002",
        "Torta Especial de Boda",
        60000,
        R.drawable.te002,
        stock = 1,
        descripcion = "Elegante torta de boda de varios niveles, decorada con detalles finos y rellenos a elección, ideal para eventos formales."
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(
    navController: NavController,
    cartViewModel: CartViewModel? = null
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

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
                    cartCount = cartCount
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

                    val enCarrito = itemsCarrito
                        .filter { it.codigo == producto.codigo }
                        .sumOf { it.cantidad }

                    val stockDisponible = (producto.stock - enCarrito).coerceAtLeast(0)
                    val sinStock = stockDisponible <= 0

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

                            Text(
                                text = if (sinStock)
                                    "Sin stock disponible"
                                else
                                    "Stock disponible: $stockDisponible",
                                style = MaterialTheme.typography.bodySmall,
                                color = if (sinStock) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // 🛒 Agregar al carrito (simple, sin dedicatoria)
                            Button(
                                onClick = {
                                    if (cartViewModel != null && !sinStock) {
                                        cartViewModel.agregarProductoAlCarrito(
                                            codigo = producto.codigo,
                                            nombre = producto.nombre,
                                            precioUnitario = producto.precio,
                                            cantidad = 1
                                        )
                                    }
                                },
                                enabled = !sinStock && cartViewModel != null,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = RosaPastel,
                                    contentColor = MarronOscuro
                                )
                            ) {
                                Text("Agregar al carrito")
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            // 🔍 Ver detalles (personalización)
                            OutlinedButton(
                                onClick = {
                                    navController.navigate(
                                        "productoForm/${producto.codigo}/${producto.nombre}/${producto.precio}"
                                    )
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