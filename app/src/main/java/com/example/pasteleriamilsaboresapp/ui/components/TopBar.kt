package com.example.pasteleriamilsaboresapp.ui.components

import android.media.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import com.example.pasteleriamilsaboresapp.R
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.DEFAULT_ARGS_KEY
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopBar( //estas funciones son para que se hagan "algo" cuando las aprieten

    onMenuClick: () -> Unit = {}, // Acción al hacer clic en el ícono de menú
    onCartClick: () -> Unit = {}, // Acción al hacer clic en el ícono de carrito
    onProfileClick: () -> Unit = {}
)

{
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            )
            {
                //logo
                Image(
                    painter = painterResource(id = R.drawable.logo_sin_fondo ),
                    contentDescription = "Logo",
                    modifier = Modifier.size(65.dp)
                )
            } // fin row
        }, //title

        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Default.Menu, contentDescription = "Menú")
            }
        }, //navigation

        actions = {
            IconButton(onClick = onCartClick) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
            }
            IconButton(onClick = onProfileClick) {
                Icon(Icons.Default.AccountCircle, contentDescription = "Perfil")
            }
        }, //actions

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFFFC107), // Fondo dorado pastel
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White

        )
    )
}
