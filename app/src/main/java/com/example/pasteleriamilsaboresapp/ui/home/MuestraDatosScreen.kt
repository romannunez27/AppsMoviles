package com.example.pasteleriamilsaboresapp.ui.home


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.example.pasteleriamilsaboresapp.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pasteleriamilsaboresapp.ui.components.CommonFooter
import com.example.pasteleriamilsaboresapp.ui.components.CommonTopBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable


fun HomeScreen(){

    val ColorScheme = darkColorScheme(
        primary= Color(0xFF98222E),
        onPrimary = Color.White,
        onSurface = Color(0xFF333333), //Gris
    ) // fin dark

    MaterialTheme(
        colorScheme = ColorScheme
    ){ // inicio Aplicar Material

        Scaffold (

            //topBar
            topBar =
                {
                    CommonTopBar(
                        //title = "Catálogo",
                        onMenuClick = { /* abrir menú lateral */ },
                        onCartClick = { /* ir al carrito */ },
                        onProfileClick = { /* ir al perfil */ }

                    )
                }, // fin topBar


            //Footer
            bottomBar = { CommonFooter() }

        ) // fin Scaff





        {// Inicio Inner
                innerPadding ->
// Representa el espacio interno para que no choque con el topBar
            Column ( // Colaca los elementos de la Ui
                modifier = Modifier
                    .padding( innerPadding)
// Evita que quede oculto
                    .fillMaxSize() // Hace que la columnna tome el todo el tamaño
                    .padding(16.dp)
                    .background(Color(0xFFF0F0F0)), // gris Claro
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally // Centra horizontalmente
//Define que elementos dentro la columna estaran separados por 20.dp
            )// fin column
            {// inicio Contenido
                Text(text="Bienvenido !",
                    style= MaterialTheme.typography.headlineMedium,
                    color=MaterialTheme.colorScheme.primary
                ) // Muestra un texto simple en la pantalla
                Image( // insertar una imagen en la interfaz
                    painter= painterResource(id = R.drawable.logo_sin_fondo),
                    contentDescription = "Logo App",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.Fit
// Ajusta la imagen para que encaje dentro del espacio
                ) // Fin Image
// agregar un espacio entre la imagen y el boton
                Spacer(modifier = Modifier.height(66.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                )// Fin Row
                {// Aplica row
                    Text("texto uno",
                        style =MaterialTheme.typography.bodyLarge.copy(
                            color=MaterialTheme.colorScheme.onSurface.copy(alpha=0.8f),
                            fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .padding(end=8.dp)
                    )// fin texto 1
                    Text("texto dos",
                        style =MaterialTheme.typography.bodyLarge.copy(
                            color=MaterialTheme.colorScheme.onSurface.copy(alpha=0.8f),
                            fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .padding(end=8.dp)
                    )// fin texto 1
                } // fin Aplica row
// agregar un espacio entre la imagen y el boton
                Spacer(modifier = Modifier.height(66.dp))
                Button(onClick = {/* accion futura*/}){
                    Text("Presioname")
                } // fin boton
            }// fin Contenido
        } // Fin inner
    } // fin Aplicar Material
}// Fin HomeScreen
@Preview(showBackground = true) // Genera la vista
@Composable // Genera Interfz Garfica
fun HomeScreenPreview(){
    HomeScreen()
}// Fin HomeScreen