package com.example.pasteleriamilsaboresapp.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasteleriamilsaboresapp.data.database.UsuarioDataBase
import com.example.pasteleriamilsaboresapp.data.model.Usuario
import com.example.pasteleriamilsaboresapp.data.repository.UsuarioRepository
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.Period
import org.threeten.bp.format.DateTimeFormatter
import java.util.Locale

class RegistroViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UsuarioRepository

    init {
        val dao = UsuarioDataBase.getDatabase(application).usuarioDao()
        repository = UsuarioRepository(dao)
    }

    fun registrarUsuario(
        nombre: String,
        correo: String,
        password: String,
        fechaNacimiento: String,
        codigo: String?,
        onResultado: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                // ✅ Forzamos Locale y patrón compatible con ThreeTenABP
                val inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale("es", "CL"))
                val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale("es", "CL"))

                println("DEBUG 👉 fechaNacimiento recibida: '$fechaNacimiento'")

                // 🧭 Parsear la fecha de nacimiento ingresada
                val birthDate = LocalDate.parse(fechaNacimiento.trim(), inputFormatter)
                val fechaFormateada = birthDate.format(outputFormatter)

                // 🎂 Calcular edad
                val edad = Period.between(birthDate, LocalDate.now()).years

                // ✅ Validar dominio permitido
                val dominioValido = correo.endsWith("gmail.com") ||
                        correo.endsWith("duocuc.cl") ||
                        correo.endsWith("duoc.profesor.cl")

                if (!dominioValido) {
                    onResultado(false, "Dominio de correo no permitido. Usa gmail.com, duocuc.cl o duoc.profesor.cl")
                    return@launch
                }

                // 🧮 Calcular beneficios
                var descuento = 0.0
                var beneficio = "Sin beneficios"

                when {
                    edad >= 50 -> {
                        descuento = 0.5
                        beneficio = "Descuento del 50% por edad"
                    }

                    codigo.equals("FELICES50", ignoreCase = true) -> {
                        descuento = 0.1
                        beneficio = "Descuento del 10% por código FELICES50"
                    }

                    correo.endsWith("duocuc.cl") && esCumpleHoy(birthDate) -> {
                        beneficio = "Torta gratis en cumpleaños (correo institucional Duoc)"
                    }
                }

                // 💾 Crear usuario y guardar en BD
                val nuevoUsuario = Usuario(
                    nombre = nombre,
                    correo = correo,
                    password = password,
                    fechaNacimiento = fechaFormateada, // guardamos yyyy-MM-dd
                    descuento = descuento,
                    beneficio = beneficio
                )

                val exito = repository.registrarUsuario(nuevoUsuario)
                if (exito) {
                    onResultado(true, "Registro exitoso. $beneficio aplicado.")
                } else {
                    onResultado(false, "El usuario ya existe con este correo.")
                }

            } catch (e: Exception) {
                e.printStackTrace()
                onResultado(false, "Fecha de nacimiento inválida. Usa formato DD-MM-AAAA (ej: 27-05-1991).")
            }
        }
    }

    // 🎂 Compara si hoy es el cumpleaños del usuario
    private fun esCumpleHoy(fecha: LocalDate): Boolean {
        val hoy = LocalDate.now()
        return hoy.dayOfMonth == fecha.dayOfMonth && hoy.month == fecha.month
    }
}
