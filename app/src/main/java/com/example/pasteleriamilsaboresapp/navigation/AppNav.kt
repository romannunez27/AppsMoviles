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
import com.example.pasteleriamilsaboresapp.ui.contacto.ContactScreen
import com.example.pasteleriamilsaboresapp.ui.home.HomeUserScreen
import com.example.pasteleriamilsaboresapp.view.ProductoFormScreen
import com.example.pasteleriamilsaboresapp.ui.theme.PasteleriaMilSaboresTheme

@Composable
fun AppNav() {
    val navController = rememberNavController()

    PasteleriaMilSaboresTheme {
        NavHost(
            navController = navController,
            startDestination = "splash" // 🌸 inicia en splash
        ) {
            // 🩷 Pantalla de inicio animada
            composable("splash") {
                SplashScreen(navController = navController)
            }

            // 🔐 Login
            composable("login") {
                LoginScreen(navController = navController)
            }

            // 🏠 Home principal con Drawer
            composable("home") {
                HomeUserScreen(navController = navController)
            }

            // 📦 Catálogo
            composable("catalogo") {
                CatalogoScreen(navController = navController)
            }

            //Blog
            composable("blogs"){
                BlogPage(navController = navController)
            }

            //contacto
            composable("contacto"){
                ContactScreen(navController = navController)
            }

            // ℹ️ Nosotros
            composable("nosotros") {
                NosotrosScreen(navController = navController)
            }

            // 🧁 Formulario de producto (detalle)
            composable(
                route = "productoForm/{nombre}/{precio}",
                arguments = listOf(
                    navArgument("nombre") { type = NavType.StringType },
                    navArgument("precio") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val nombre = Uri.decode(backStackEntry.arguments?.getString("nombre") ?: "")
                val precio = backStackEntry.arguments?.getString("precio") ?: ""
                ProductoFormScreen(
                    navController = navController,
                    nombre = nombre,
                    precio = precio
                )
            }
        }
    }
}
