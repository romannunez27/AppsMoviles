package com.example.pasteleriamilsaboresapp.ui.view

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pasteleriamilsaboresapp.R
import com.example.pasteleriamilsaboresapp.ui.theme.*

@Composable
fun DrawerMenu(
    navController: NavController,
    drawerState: DrawerState,
    closeDrawer: () -> Unit
) {
    val context = LocalContext.current

    // 🌈 Gradiente de fondo pastel
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(RosaPastel, BeigeSuave.copy(alpha = 0.95f))
    )

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(brush = gradientBrush)
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // 🧁 Encabezado con logo y nombre
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_sin_fondo),
                contentDescription = "Logo Pastelería Mil Sabores",
                modifier = Modifier
                    .size(100.dp)
                    .padding(bottom = 8.dp)
            )
            Text(
                text = "Pastelería Mil Sabores",
                fontSize = 20.sp,
                color = MarronOscuro,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            Divider(
                color = CafeSuave.copy(alpha = 0.3f),
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        // 🧭 Opciones del menú
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            val opciones = listOf(
                Triple("Inicio", Icons.Default.Home, "home"),
                Triple("Catálogo", Icons.Default.ShoppingBag, "catalogo"),
                Triple("Blog", Icons.Default.Article, "blogs"),
                Triple("Nosotros", Icons.Default.Info, "nosotros"),
                Triple("Contáctanos", Icons.Default.Email, "contacto")
            )

            opciones.forEach { (titulo, icono, ruta) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            navController.navigate(ruta)
                            closeDrawer()
                        }
                        .background(Color.White.copy(alpha = 0.25f))
                        .padding(vertical = 10.dp, horizontal = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = icono,
                        contentDescription = titulo,
                        tint = CafeSuave,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = titulo,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium,
                        color = MarronOscuro
                    )
                }
            }

            // 🚪 NUEVO: Botón “Salir”
            Spacer(modifier = Modifier.height(10.dp))
            Divider(color = CafeSuave.copy(alpha = 0.3f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {
                        (context as? Activity)?.finishAffinity() // Cierra la app
                    }
                    .background(Color.White.copy(alpha = 0.25f))
                    .padding(vertical = 10.dp, horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = "Salir",
                    tint = RosaIntenso,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Salir",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MarronOscuro
                )
            }
        }

        // 📞 Pie con redes o info
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Divider(
                color = CafeSuave.copy(alpha = 0.3f),
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = "Síguenos",
                fontSize = 15.sp,
                color = MarronOscuro,
                fontWeight = FontWeight.SemiBold
            )
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Icon(
                    imageVector = Icons.Default.Facebook,
                    contentDescription = "Facebook",
                    tint = MarronOscuro,
                    modifier = Modifier.size(22.dp)
                )
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Instagram",
                    tint = MarronOscuro,
                    modifier = Modifier.size(22.dp)
                )
                Icon(
                    imageVector = Icons.Default.Public,
                    contentDescription = "Twitter",
                    tint = MarronOscuro,
                    modifier = Modifier.size(22.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "© 2025 Mil Sabores",
                fontSize = 13.sp,
                color = MarronOscuro.copy(alpha = 0.8f)
            )
        }
    }
}
