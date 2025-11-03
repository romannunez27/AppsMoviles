package com.example.pasteleriamilsaboresapp.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pasteleriamilsaboresapp.R
import com.example.pasteleriamilsaboresapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopBar(
    onMenuClick: () -> Unit = {},
    onCartClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    showBackButton: Boolean = false
) {
    // 🌈 Animación de color suave para mantener el dinamismo visual
    val topBarColor by animateColorAsState(targetValue = RosaIntenso)

    // ✨ Gradiente pastel moderno
    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(RosaIntenso, RosaPastel, BeigeSuave)
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 8.dp, spotColor = RosaPastel) // sombra sutil
            .background(brush = gradientBrush),
        color = Color.Transparent
    ) {
        CenterAlignedTopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 68.dp, max = 100.dp)
                .background(brush = gradientBrush)
                .windowInsetsPadding(WindowInsets.safeDrawing),
            title = {
                Image(
                    painter = painterResource(id = R.drawable.logo_sin_fondo),
                    contentDescription = "Logo Pastelería Mil Sabores",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .heightIn(min = 45.dp, max = 70.dp)
                        .widthIn(min = 110.dp, max = 170.dp)
                )
            },
            navigationIcon = {
                IconButton(onClick = onMenuClick) {
                    Icon(
                        imageVector = if (showBackButton) Icons.Default.ArrowBack else Icons.Default.Menu,
                        contentDescription = if (showBackButton) "Volver" else "Menú",
                        tint = FondoCrema
                    )
                }
            },
            actions = {
                IconButton(onClick = onCartClick) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Carrito",
                        tint = FondoCrema
                    )
                }
                IconButton(onClick = onProfileClick) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Perfil",
                        tint = FondoCrema
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.Transparent,
                titleContentColor = FondoCrema,
                navigationIconContentColor = FondoCrema,
                actionIconContentColor = FondoCrema
            )
        )
    }
}
