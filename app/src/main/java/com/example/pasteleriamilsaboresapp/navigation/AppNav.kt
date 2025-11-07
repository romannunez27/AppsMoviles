package com.example.pasteleriamilsaboresapp.navigation

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pasteleriamilsaboresapp.ui.blog.BlogPage
import com.example.pasteleriamilsaboresapp.ui.contacto.ContactScreen
import com.example.pasteleriamilsaboresapp.ui.home.HomeUserScreen
import com.example.pasteleriamilsaboresapp.ui.login.LoginScreen
import com.example.pasteleriamilsaboresapp.ui.login.RegistroScreen
import com.example.pasteleriamilsaboresapp.ui.nosotros.NosotrosScreen
import com.example.pasteleriamilsaboresapp.ui.splash.SplashScreen
import com.example.pasteleriamilsaboresapp.ui.catalogo.CatalogoScreen
import com.example.pasteleriamilsaboresapp.view.QrScannerScreen
import com.example.pasteleriamilsaboresapp.viewmodel.QrViewModel
import com.example.pasteleriamilsaboresapp.utils.CameraPermissionHelper
import com.example.pasteleriamilsaboresapp.ui.theme.PasteleriaMilSaboresTheme

@Composable
fun AppNav() {
    val navController = rememberNavController()

    PasteleriaMilSaboresTheme {
        NavHost(navController = navController, startDestination = "splash") {
            composable("splash") { SplashScreen(navController) }
            composable("login") { LoginScreen(navController) }
            composable("home") { HomeUserScreen(navController) }
            composable("catalogo") { CatalogoScreen(navController) }
            composable("blogs") { BlogPage(navController) }
            composable("contacto") { ContactScreen(navController) }
            composable("nosotros") { NosotrosScreen(navController) }

            // 🧁 Registro con parámetro opcional QR
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
                val context = LocalContext.current
                val viewModel = QrViewModel()
                val hasPermission = CameraPermissionHelper.hasCameraPermission(context)
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission()
                ) { granted ->
                    Toast.makeText(
                        context,
                        if (granted) "Permiso concedido ✅" else "Permiso denegado ❌",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                QrScannerScreen(
                    viewModel = viewModel,
                    hasCameraPermission = hasPermission,
                    onRequestPermission = {
                        launcher.launch(android.Manifest.permission.CAMERA)
                    }
                )
            }
        }
    }
}
