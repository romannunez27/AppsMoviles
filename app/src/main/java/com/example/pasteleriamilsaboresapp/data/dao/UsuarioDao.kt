package com.example.pasteleriamilsaboresapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pasteleriamilsaboresapp.data.model.Usuario

@Dao
interface UsuarioDao {

    // 🔹 Insertar usuario nuevo (ABORT lanza excepción si ya existe)
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertarUsuario(usuario: Usuario)

    // 🔹 Buscar usuario por correo (para verificar duplicados o login)
    @Query("SELECT * FROM usuario WHERE correo = :correo LIMIT 1")
    suspend fun obtenerUsuarioPorCorreo(correo: String): Usuario?

    // 🔹 Login simple
    @Query("SELECT * FROM usuario WHERE correo = :correo AND password = :password LIMIT 1")
    suspend fun login(correo: String, password: String): Usuario?
}
