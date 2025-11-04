package com.example.pasteleriamilsaboresapp.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pasteleriamilsaboresapp.R
import com.example.pasteleriamilsaboresapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    vm: LoginViewModel = viewModel()
) {
    val state = vm.uiState
    var showPass by remember { mutableStateOf(false) }

    PasteleriaMilSaboresTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 🧁 Título
                Text(
                    text = "Bienvenido/a",
                    style = MaterialTheme.typography.titleLarge,
                    color = CafeSuave
                )

                // 🧁 Logo
                Image(
                    painter = painterResource(id = R.drawable.logo_fndo_blanco),
                    contentDescription = "Logo Pastelería",
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(160.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(Modifier.height(24.dp))

                // ✉️ Usuario
                OutlinedTextField(
                    value = state.username,
                    onValueChange = vm::onUsernameChange,
                    label = { Text("Correo o Usuario") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = RosaIntenso,
                        unfocusedBorderColor = CafeSuave,
                        focusedLabelColor = RosaIntenso
                    ),
                    modifier = Modifier.fillMaxWidth(0.9f)
                )

                // 🔒 Contraseña
                OutlinedTextField(
                    value = state.password,
                    onValueChange = vm::onPasswordChange,
                    label = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        TextButton(onClick = { showPass = !showPass }) {
                            Text(if (showPass) "Ocultar" else "Ver", color = RosaIntenso)
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = RosaIntenso,
                        unfocusedBorderColor = CafeSuave,
                        focusedLabelColor = RosaIntenso
                    ),
                    modifier = Modifier.fillMaxWidth(0.9f)
                )

                // ⚠️ Mensaje de error
                if (state.error != null) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = state.error ?: "",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.height(30.dp))

                // 🎀 Botones Login + Registro
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            vm.submit { user ->
                                navController.navigate("home") {
                                    popUpTo("login") { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        },
                        enabled = !state.isLoading,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .border(2.dp, CafeSuave, MaterialTheme.shapes.medium),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RosaPastel,
                            contentColor = CafeSuave
                        )
                    ) {
                        Text(
                            if (state.isLoading) "Validando..." else "Iniciar",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    OutlinedButton(
                        onClick = { navController.navigate("registro") },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        border = BorderStroke(2.dp, CafeSuave),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = CafeSuave
                        )
                    ) {
                        Text("Registrar")
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    val vm = LoginViewModel()

    PasteleriaMilSaboresTheme {
        LoginScreen(navController = navController, vm = vm)
    }
}
