package com.example.pasteleriamilsaboresapp.ui.contacto

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pasteleriamilsaboresapp.ui.components.CommonFooter
import com.example.pasteleriamilsaboresapp.ui.components.CommonTopBar
import com.example.pasteleriamilsaboresapp.ui.theme.BeigeSuave
import com.example.pasteleriamilsaboresapp.ui.theme.CafeSuave
import kotlinx.coroutines.launch

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
fun ContactScreen(navController: NavController) {

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    Scaffold(
        topBar = {
            CommonTopBar(
                onMenuClick = { scope.launch { drawerState.open() } },
                onCartClick = { navController.navigate("carrito") },
                onProfileClick = { navController.navigate("perfil") }
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = BeigeSuave
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ContactForm()

                Spacer(modifier = Modifier.height(32.dp)) // separador opcional

                CommonFooter(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BeigeSuave)
                )
            }

        }
    }
}



@Composable
fun ContactForm() {

    val validator = ContactValidator()

    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    var showConfirmation by remember { mutableStateOf(false) }
    var nombreError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var messageError by remember { mutableStateOf(false) }


    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Formulario de contacto", style = MaterialTheme.typography.titleLarge)

            // Nombre
            Column(
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                Text("Nombre completo", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(bottom = 1.dp))

                OutlinedTextField(
                    value = nombre,
                    onValueChange = {
                        nombre = it
                        nombreError = false
                    },
                    label = { Text("Ej: Antonia Palma") },
                    isError = nombreError,
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                if (nombreError) {
                    Text(
                        text = "Por favor ingresa tu nombre",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }



            // Correo

            Column(
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                Text("Correo electrónico", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(bottom = 1.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = false
                    },
                    label = { Text("Ej: antopalma@duoc.cl") },
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
            }



            // Mensaje

            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text("Mensaje", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(bottom = 1.dp))

                OutlinedTextField(
                    value = message,
                    onValueChange = {
                        message = it
                        messageError = false
                    },
                    label = { Text("") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )
                if (messageError) {
                    Text(
                        text = "El mensaje no puede estar vacío",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }



            // Botón
            Button(
                onClick = {
                    val validNombre = nombre.isNotBlank()
                    val validEmail = validator.isValidEmail(email)
                    val validMessage = message.isNotBlank()

                    nombreError = !validNombre
                    emailError = !validEmail
                    messageError = !validMessage

                    if (validNombre && validEmail && validMessage) {
                        showConfirmation = true
                    }
                },
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CafeSuave,
                    contentColor = Color.White
                )
            ) {
                Text("Enviar")
            }

            // Confirmación
            if (showConfirmation) {
                Text(
                    text = "¡Gracias por tu mensaje!",
                    color = Color(0xFF4CAF50),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

}



@Preview(showBackground = true) // Genera la vista
@Composable // Genera Interfz Garfica
fun ContactScreenreview(){
    MaterialTheme {
        ContactScreen(navController = rememberNavController())
    }
}// Fin HomeScreen
