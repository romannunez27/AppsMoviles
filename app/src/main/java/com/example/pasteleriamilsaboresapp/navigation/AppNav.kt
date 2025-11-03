package com.example.pasteleriamilsaboresapp.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pasteleriamilsaboresapp.ui.blog.BlogPage
import com.example.pasteleriamilsaboresapp.ui.login.LoginScreen
import com.example.pasteleriamilsaboresapp.ui.nosotros.NosotrosScreen
import com.example.pasteleriamilsaboresapp.ui.splash.SplashScreen
import com.example.pasteleriamilsaboresapp.ui.catalogo.CatalogoScreen
import com.example.pasteleriamilsaboresapp.ui.home.HomeUserScreen
import com.example.pasteleriamilsaboresapp.view.ProductoFromScreen
import com.example.pasteleriamilsaboresapp.ui.theme.PasteleriaMilSaboresTheme

@Composable
fun AppNav() {
    val navController = rememberNavController()

    PasteleriaMilSaboresTheme {
        NavHost(
            navController = navController,
            startDestination = "splash"
        ) {
            // 🌸 Pantalla inicial
            composable("splash") {
                SplashScreen(navController = navController)
            }

            // 🔐 Login
            composable("login") {
                LoginScreen(navController = navController)
            }

            // 🏠 Home principal
            composable("home") {
                HomeUserScreen(navController = navController)
            }

            // 📦 Catálogo
            composable("catalogo") {
                CatalogoScreen(navController = navController)
            }

            // 🧁 Detalle del producto (ruta coherente con tu naming)
            composable(
                route = "productFrom/{productoId}", // ✅ corregido
                arguments = listOf(
                    navArgument("productoId") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val productoId = backStackEntry.arguments?.getString("productoId") ?: ""
                ProductoFromScreen( // ✅ coincide con tu clase real
                    navController = navController,
                    productoId = productoId
                )
            }

            // 📰 Blog
            composable("blogs") {
                BlogPage(navController = navController)
            }

            // ℹ️ Nosotros
            composable("nosotros") {
                NosotrosScreen(navController = navController)
            }
        }
    }
}
