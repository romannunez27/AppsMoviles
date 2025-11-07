package com.example.pasteleriamilsaboresapp.ui.login

data class LoginUiState(
    val correo: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
