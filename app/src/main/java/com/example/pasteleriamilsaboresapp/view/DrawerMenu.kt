package com.example.pasteleriamilsaboresapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pasteleriamilsaboresapp.ui.theme.*

@Composable
fun DrawerMenu(onNavigateTo: (String) -> Unit) {
    Column(
        Modifier
            .fillMaxHeight()
            .background(RosaPastel)
            .padding(16.dp)
    ) {
        Text("Menú", color = CafeSuave, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(Modifier.height(12.dp))

        val opciones = listOf(
            "Inicio" to "homeUser",
            "Catálogo" to "catalogo",
            "Blogs" to "blogs",
            "Nosotros" to "nosotros"
        )

        opciones.forEach { (titulo, ruta) ->
            TextButton(onClick = { onNavigateTo(ruta) }) {
                Text(titulo, color = MarronOscuro, fontSize = 16.sp)
            }
        }
    }
}
