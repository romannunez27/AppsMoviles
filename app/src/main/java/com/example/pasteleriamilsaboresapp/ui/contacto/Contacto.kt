package com.example.pasteleriamilsaboresapp.ui.contacto

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pasteleriamilsaboresapp.ui.components.CommonFooter
import com.example.pasteleriamilsaboresapp.ui.components.CommonTopBar

//validar correos con dominios específicos
class ContactValidator {
    private val allowedDomains = listOf("gmail.com", "duoc.cl", "profesor.duoc.cl")

    fun isValidEmail(email: String): Boolean {
        val parts = email.trim().split("@")
        if (parts.size != 2) return false
        val domain = parts[1].lowercase()
        return allowedDomains.contains(domain)
    }
}


@Composable
fun ContactForm() {

    val validator = ContactValidator()

    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var showConfirmation by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var messageError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Contáctanos 💌", style = MaterialTheme.typography.titleLarge)

        // 📧 Campo de correo
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = false
            },
            label = { Text("Correo electrónico") },
            isError = emailError,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        if (emailError) {
            Text(
                text = "Correo inválido. Solo se aceptan Gmail, Duoc.cl o profesor.duoc.cl",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // 💬 Campo de mensaje
        OutlinedTextField(
            value = message,
            onValueChange = {
                message = it
                messageError = false
            },
            label = { Text("Mensaje") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )
        if (messageError) {
            Text(
                text = "El mensaje no puede estar vacío",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // 🧁 Botón de envío
        Button(
            onClick = {
                val validEmail = validator.isValidEmail(email)
                val validMessage = message.isNotBlank()

                emailError = !validEmail
                messageError = !validMessage

                if (validEmail && validMessage) {
                    showConfirmation = true
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Enviar")
        }

        // 🎉 Confirmación
        if (showConfirmation) {
            Text(
                text = "¡Gracias por tu mensaje!",
                color = Color(0xFF4CAF50),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


//pantalla que llama al formulario
@Composable
fun ContactScreen() {
    Scaffold(
        //topBar
        topBar =
            {
                CommonTopBar(
                    //title = "Cntacto",
                    onMenuClick = { /* abrir menú lateral */ },
                    onCartClick = { /* ir al carrito */ },
                    onProfileClick = { /* ir al perfil */ }

                )
            }, // fin topBar

        //Footer
        bottomBar = { CommonFooter() }

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            ContactForm()
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}//contactScreen



@Preview(showBackground = true) // Genera la vista
@Composable // Genera Interfz Garfica
fun ContactScreenreview(){
    MaterialTheme {
        ContactScreen()
    }
}// Fin HomeScreen
