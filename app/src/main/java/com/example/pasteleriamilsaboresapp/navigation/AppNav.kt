package com.example.pasteleriamilsaboresapp.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pasteleriamilsaboresapp.ui.login.LoginScreen
import com.example.pasteleriamilsaboresapp.ui.nosotros.NosotrosScreen
import com.example.pasteleriamilsaboresapp.ui.splash.SplashScreen
import com.example.pasteleriamilsaboresapp.view.DrawerMenu
import com.example.pasteleriamilsaboresapp.view.ProductoFormScreen
import com.example.pasteleriamilsaboresapp.ui.theme.PasteleriaMilSaboresTheme

@Composable
fun AppNav() {
    val navController = rememberNavController()

    // 🍰 Envolvemos todo con tu tema pastel
    PasteleriaMilSaboresTheme {
        NavHost(
            navController = navController,
            startDestination = "splash" // 👈 ahora parte desde el splash
        ) {
            composable("splash") {
                SplashScreen(navController = navController)
            }

            composable("login") {
                LoginScreen(navController = navController)
            }

            composable("nosotros") {
                NosotrosScreen()
            }

            // 🧁 Pantalla principal con Drawer (pasando el usuario)
            composable(
                route = "DrawerMenu/{username}",
                arguments = listOf(
                    navArgument("username") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val username = backStackEntry.arguments?.getString("username").orEmpty()
                DrawerMenu(username = username, navController = navController)
            }

            // 🧁 Pantalla de formulario de producto
            composable(
                route = "ProductoFormScreen/{nombre}/{precio}",
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
