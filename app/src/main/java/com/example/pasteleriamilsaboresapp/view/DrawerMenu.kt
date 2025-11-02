package com.example.pasteleriamilsaboresapp.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pasteleriamilsaboresapp.ui.theme.*

@Composable
fun DrawerMenu(
    navController: NavController,
    closeDrawer: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(RosaPastel)
            .padding(16.dp)
    ) {
        Text(
            "Menú principal",
            color = CafeSuave,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )
        Spacer(Modifier.height(16.dp))

        val opciones = listOf(
            "Inicio" to "home",
            "Catálogo" to "catalogo",
            "Blogs" to "blogs",
            "Nosotros" to "nosotros"
        )

        opciones.forEach { (titulo, ruta) ->
            TextButton(onClick = {
                navController.navigate(ruta)
                closeDrawer()
            }) {
                Text(titulo, color = MarronOscuro, fontSize = 18.sp)
            }
        }
    }
}
