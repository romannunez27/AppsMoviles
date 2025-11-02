package com.example.pasteleriamilsaboresapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Facebook
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import com.example.pasteleriamilsaboresapp.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pasteleriamilsaboresapp.ui.theme.CafeSuave


@Composable

fun CommonFooter() {
    Surface(

        //colores
        color = CafeSuave,
        contentColor = Color.White

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //logo
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_sin_fondo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Pastelería Mil Sabores",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )

            }

            //webpay
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Medio de pago", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(15.dp))
                Image(
                    painter = painterResource(id = R.drawable.webpay),
                    contentDescription = "Logo Webpay",
                    modifier = Modifier.size(100.dp)
                )
            }

            //redes sociales con texto
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Síguenos", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(15.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Icon(Icons.Default.Facebook, contentDescription = "Facebook")
                    Icon(Icons.Default.Share, contentDescription = "Instagram")
                    Icon(Icons.Default.Public, contentDescription = "Twitter")

                }
            }

        } //column

    } //bottom

}//funcion