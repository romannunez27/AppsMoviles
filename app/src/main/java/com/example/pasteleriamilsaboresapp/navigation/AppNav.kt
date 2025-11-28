package com.example.pasteleriamilsaboresapp.navigation

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pasteleriamilsaboresapp.data.database.ProductoDataBase
import com.example.pasteleriamilsaboresapp.data.repository.CartRepository
import com.example.pasteleriamilsaboresapp.data.repository.ProductRepository
import com.example.pasteleriamilsaboresapp.ui.blog.BlogPage
import com.example.pasteleriamilsaboresapp.ui.catalogo.CatalogoScreen
import com.example.pasteleriamilsaboresapp.ui.contacto.ContactScreen
import com.example.pasteleriamilsaboresapp.ui.home.HomeUserScreen
import com.example.pasteleriamilsaboresapp.ui.login.LoginScreen
import com.example.pasteleriamilsaboresapp.ui.login.RegistroScreen
import com.example.pasteleriamilsaboresapp.ui.nosotros.NosotrosScreen
import com.example.pasteleriamilsaboresapp.ui.splash.SplashScreen
import com.example.pasteleriamilsaboresapp.view.CarritoScreen
import com.example.pasteleriamilsaboresapp.view.ProductoFormScreen
import com.example.pasteleriamilsaboresapp.view.QrScannerScreen
import com.example.pasteleriamilsaboresapp.viewmodel.CartViewModel
import com.example.pasteleriamilsaboresapp.viewmodel.CartViewModelFactory
import com.example.pasteleriamilsaboresapp.viewmodel.QrViewModel
import com.example.pasteleriamilsaboresapp.utils.CameraPermissionHelper
import com.example.pasteleriamilsaboresapp.ui.theme.PasteleriaMilSaboresTheme

@Composable
fun AppNav() {
    val navController = rememberNavController()
    val context = LocalContext.current

    // 🧁 Inicializamos Room y repositorios UNA sola vez
    val db = ProductoDataBase.getDatabase(context)
    val cartRepository = CartRepository(db.cartDao())
    val productRepository = ProductRepository(db.productoDao())

    val cartViewModel: CartViewModel = viewModel(
        factory = CartViewModelFactory(cartRepository, productRepository)
    )

    PasteleriaMilSaboresTheme {
        NavHost(
            navController = navController,
            startDestination = "splash"
        ) {
            composable("splash") { SplashScreen(navController) }

            composable("login") { LoginScreen(navController) }

            composable("home") {
                HomeUserScreen(
                    navController = navController,
                    cartViewModel = cartViewModel         // 👈 pásalo donde uses CommonTopBar
                )
            }

            composable("catalogo") {
                CatalogoScreen(
                    navController = navController,
                    cartViewModel = cartViewModel          // 👈 para agregar productos al carrito
                )
            }

            composable("blogs") { BlogPage(navController) }

            composable("contacto") { ContactScreen(navController) }

            composable("nosotros") { NosotrosScreen(navController) }

            // 🧁 Detalle de producto / compra inmediata
            composable(
                route = "productoForm/{codigo}/{nombre}/{precio}",
                arguments = listOf(
                    navArgument("codigo") { type = NavType.StringType },
                    navArgument("nombre") { type = NavType.StringType },
                    navArgument("precio") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val codigo = backStackEntry.arguments?.getString("codigo") ?: ""
                val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
                val precio = backStackEntry.arguments?.getString("precio") ?: ""
                ProductoFormScreen(
                    navController = navController,
                    codigo = codigo,
                    nombre = nombre,
                    precio = precio,
                    cartViewModel = cartViewModel
                )
            }

            composable(
                route = "registro?qrContent={qrContent}",
                arguments = listOf(
                    navArgument("qrContent") {
                        type = NavType.StringType
                        defaultValue = ""
                        nullable = true
                    }
                )
            ) { backStackEntry ->
                val qrContent = backStackEntry.arguments?.getString("qrContent") ?: ""
                RegistroScreen(navController = navController, qrContent = qrContent)
            }

            // 📷 Escáner de QR
            composable("qrscanner") {
                val ctx = LocalContext.current
                val viewModel = QrViewModel()
                val hasPermission = CameraPermissionHelper.hasCameraPermission(ctx)
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission()
                ) { granted ->
                    Toast.makeText(
                        ctx,
                        if (granted) "Permiso concedido ✅" else "Permiso denegado ❌",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                QrScannerScreen(
                    navController = navController,
                    viewModel = viewModel,
                    hasCameraPermission = hasPermission,
                    onRequestPermission = {
                        launcher.launch(android.Manifest.permission.CAMERA)
                    }
                )
            }

            // 🛒 RUTA CARRITO
            composable("carrito") {
                CarritoScreen(
                    navController = navController,
                    cartViewModel = cartViewModel
                )
            }
        }
    }
}
