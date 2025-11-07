package com.example.pasteleriamilsaboresapp.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasteleriamilsaboresapp.data.database.UsuarioDataBase
import com.example.pasteleriamilsaboresapp.data.model.Usuario
import com.example.pasteleriamilsaboresapp.data.repository.UsuarioRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RegistroViewModel(application: Application) : AndroidViewModel(application) {

    private val usuarioDao = UsuarioDataBase.getDatabase(application).usuarioDao()
    private val repository = UsuarioRepository(usuarioDao)

    /**
     * Función principal de registro usada por tu RegistroScreen
     */
    fun registrarUsuario(
        nombre: String,
        correo: String,
        password: String,
        fechaNacimiento: String,
        codigoPromocional: String?,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            // ✅ Validaciones básicas
            if (nombre.isBlank() || correo.isBlank() || password.isBlank() || fechaNacimiento.isBlank()) {
                onResult(false, "Todos los campos son obligatorios.")
                return@launch
            }

            // ✅ Validar formato de fecha
            val fechaValida = try {
                LocalDate.parse(fechaNacimiento, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                true
            } catch (e: Exception) {
                false
            }

            if (!fechaValida) {
                onResult(false, "La fecha debe tener formato yyyy-MM-dd.")
                return@launch
            }

            // ✅ Crear usuario temporal
            val usuarioTemporal = Usuario(
                nombre = nombre,
                correo = correo,
                password = password,
                fechaNacimiento = fechaNacimiento,
                codigoPromocional = codigoPromocional
            )

            // 🎁 Calcular beneficio y descuento
            val (beneficio, descuento) = usuarioTemporal.calcularBeneficio()
            val usuarioFinal = usuarioTemporal.copy(
                beneficio = beneficio,
                descuento = descuento
            )

            // 💾 Intentar registrar
            val exito = repository.registrarUsuario(usuarioFinal)

            if (exito) {
                onResult(true, "Registro exitoso. $beneficio")
            } else {
                onResult(false, "El correo ya está registrado o el dominio no es válido.")
            }
        }
    }
}
