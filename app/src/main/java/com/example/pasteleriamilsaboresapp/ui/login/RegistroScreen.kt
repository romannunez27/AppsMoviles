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
fun RegistroScreen(
    navController: NavController,
    vm: RegistroViewModel = viewModel()
) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var codigo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf<String?>(null) }
    var showPass by remember { mutableStateOf(false) }
    var showConfirmPass by remember { mutableStateOf(false) }

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
                Text("Registro de Usuario", style = MaterialTheme.typography.titleLarge, color = CafeSuave)

                Image(
                    painter = painterResource(id = R.drawable.logo_fndo_blanco),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(130.dp),
                    contentScale = ContentScale.Fit
                )

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre completo") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.9f)
                )

                OutlinedTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    label = { Text("Correo electrónico") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.9f)
                )

                OutlinedTextField(
                    value = fechaNacimiento,
                    onValueChange = { fechaNacimiento = it },
                    label = { Text("Fecha de nacimiento (AAAA-MM-DD)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.9f)
                )

                OutlinedTextField(
                    value = codigo,
                    onValueChange = { codigo = it },
                    label = { Text("Código promocional (opcional)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.9f)
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        TextButton(onClick = { showPass = !showPass }) {
                            Text(if (showPass) "Ocultar" else "Ver", color = RosaIntenso)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(0.9f)
                )

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirmar contraseña") },
                    singleLine = true,
                    visualTransformation = if (showConfirmPass) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        TextButton(onClick = { showConfirmPass = !showConfirmPass }) {
                            Text(if (showConfirmPass) "Ocultar" else "Ver", color = RosaIntenso)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(0.9f)
                )

                mensaje?.let {
                    Text(it, color = if (it.contains("exitoso")) Color(0xFF4CAF50) else Color.Red)
                }

                Spacer(Modifier.height(20.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(
                        onClick = {
                            if (nombre.isBlank() || correo.isBlank() || fechaNacimiento.isBlank() ||
                                password.isBlank() || confirmPassword.isBlank()
                            ) {
                                mensaje = "Completa todos los campos obligatorios"
                            } else if (password != confirmPassword) {
                                mensaje = "Las contraseñas no coinciden"
                            } else {
                                vm.registrarUsuario(
                                    nombre,
                                    correo,
                                    password,
                                    fechaNacimiento,
                                    codigo.ifBlank { null }
                                ) { exito, msg ->
                                    mensaje = msg
                                    if (exito) navController.navigate("login")
                                }
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .border(2.dp, CafeSuave, MaterialTheme.shapes.medium),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RosaPastel,
                            contentColor = CafeSuave
                        )
                    ) {
                        Text("Registrar", fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = { navController.navigate("login") },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        border = BorderStroke(2.dp, CafeSuave),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = CafeSuave)
                    ) {
                        Text("Volver")
                    }
                }
            }
        }
    }
}
