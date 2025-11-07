package com.example.pasteleriamilsaboresapp.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasteleriamilsaboresapp.data.database.UsuarioDataBase
import com.example.pasteleriamilsaboresapp.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val usuarioDao = UsuarioDataBase.getDatabase(application).usuarioDao()
    private val repository = UsuarioRepository(usuarioDao)

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onCorreoChange(value: String) {
        _uiState.value = _uiState.value.copy(correo = value)
    }

    fun onPasswordChange(value: String) {
        _uiState.value = _uiState.value.copy(password = value)
    }

    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val user = repository.login(
                correo = _uiState.value.correo,
                password = _uiState.value.password
            )

            if (user != null) {
                onSuccess()
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Credenciales incorrectas o usuario no registrado."
                )
            }
        }
    }
}
