package com.example.pasteleriamilsaboresapp.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.platform.testTag


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    navController: NavController,
    qrContent: String = "",
    vm: RegistroViewModel = viewModel()
) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var fechaNacimientoState by remember { mutableStateOf(TextFieldValue("")) }
    var codigo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf<String?>(null) }
    var showPass by remember { mutableStateOf(false) }
    var showConfirmPass by remember { mutableStateOf(false) }

    // Si se escaneó un QR, lo cargamos automáticamente
    LaunchedEffect(qrContent) {
        if (qrContent.isNotBlank()) codigo = qrContent
    }

    PasteleriaMilSaboresTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // ✅ Scroll para evitar que se oculte contenido
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 🧁 Encabezado
                Text(
                    "Registro de Usuario",
                    style = MaterialTheme.typography.titleLarge,
                    color = CafeSuave
                )

                Spacer(Modifier.height(10.dp))

                Image(
                    painter = painterResource(id = R.drawable.logo_fndo_blanco),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(0.55f)
                        .height(100.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(Modifier.height(20.dp))

                // 🟤 Campos de texto
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre completo") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.95f).testTag("registro_nombre")
                )

                OutlinedTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    label = { Text("Correo electrónico") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.95f).testTag("registro_correo")
                )

                var fechaNacimientoState by remember { mutableStateOf(TextFieldValue("")) }

                OutlinedTextField(
                    value = fechaNacimientoState,
                    onValueChange = { newValue ->
                        val input = newValue.text.filter { it.isDigit() }
                        if (input.length > 8) return@OutlinedTextField

                        val formatted = buildString {
                            for (i in input.indices) {
                                append(input[i])
                                if (i == 1 || i == 3) append('-')
                            }
                        }

                        fechaNacimientoState = newValue.copy(
                            text = formatted,
                            selection = androidx.compose.ui.text.TextRange(formatted.length)
                        )
                    },
                    label = { Text("Fecha de nacimiento (día-mes-año)") },
                    singleLine = true,
                    placeholder = { Text("00-00-0000") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = RosaIntenso,
                        unfocusedBorderColor = CafeSuave,
                        focusedLabelColor = RosaIntenso
                    ),
                    modifier = Modifier.fillMaxWidth(0.95f).testTag("registro_fecha")
                )

                OutlinedTextField(
                    value = codigo,
                    onValueChange = { codigo = it },
                    label = { Text("Código promocional (opcional)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.95f).testTag("registro_codigo")
                )

                // 🔸 Sección QR
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .padding(top = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Escanear QR",
                        tint = RosaIntenso
                    )

                    Text(
                        text = "¿Tienes un código QR promocional?",
                        color = CafeSuave,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedButton(
                        onClick = { navController.navigate("qrscanner") },
                        border = BorderStroke(1.dp, RosaIntenso),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = RosaIntenso),
                        modifier = Modifier.height(38.dp)
                    ) {
                        Text("Escanear", style = MaterialTheme.typography.labelSmall)
                    }
                }

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
                    modifier = Modifier.fillMaxWidth(0.95f).testTag("registro_password")
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
                    modifier = Modifier.fillMaxWidth(0.95f).testTag("registro_confirm_password")
                )

                // 🟢 Mensaje dinámico
                mensaje?.let {
                    Text(
                        it,
                        color = if (it.contains("exitoso")) Color(0xFF4CAF50) else Color.Red,
                        modifier = Modifier.padding(top = 6.dp).testTag("registro_mensaje")
                    )
                }

                Spacer(Modifier.height(18.dp))

                // 🩷 Botones
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth(0.95f)
                ) {
                    Button(
                        onClick = {
                            if (nombre.isBlank() || correo.isBlank() || fechaNacimientoState.text.isBlank() ||
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
                                    fechaNacimientoState.text,
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
                            .border(2.dp, CafeSuave, MaterialTheme.shapes.medium).testTag("registro_boton_registrar"),
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

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistroScreenPreview() {
    val navController = rememberNavController()
    PasteleriaMilSaboresTheme {
        RegistroScreen(navController = navController)
    }
}
