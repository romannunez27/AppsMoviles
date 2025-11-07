package com.example.pasteleriamilsaboresapp.data.repository

import com.example.pasteleriamilsaboresapp.data.dao.UsuarioDao
import com.example.pasteleriamilsaboresapp.data.model.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsuarioRepository(private val dao: UsuarioDao) {

    /**
     * Registra un nuevo usuario si cumple condiciones y no existe el correo.
     * Retorna true si fue exitoso, false si el correo ya existe o el dominio no es válido.
     */
    suspend fun registrarUsuario(usuario: Usuario): Boolean = withContext(Dispatchers.IO) {
        try {
            val existente = dao.obtenerUsuarioPorCorreo(usuario.correo)
            if (existente != null) return@withContext false
            if (!usuario.dominioValido()) return@withContext false

            dao.insertarUsuario(usuario)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Autenticación por correo y contraseña.
     */
    suspend fun login(correo: String, password: String): Usuario? = withContext(Dispatchers.IO) {
        try {
            dao.login(correo, password)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Obtiene un usuario por correo (para aplicar beneficios o validaciones).
     */
    suspend fun obtenerUsuarioPorCorreo(correo: String): Usuario? = withContext(Dispatchers.IO) {
        try {
            dao.obtenerUsuarioPorCorreo(correo)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
