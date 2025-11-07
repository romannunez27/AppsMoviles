package com.example.pasteleriamilsaboresapp.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

@Entity(
    tableName = "usuario",
    indices = [Index(value = ["correo"], unique = true)]
)
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombre: String,
    val correo: String,
    val password: String,
    val fechaNacimiento: String, // formato: yyyy-MM-dd
    val codigoPromocional: String? = null,

    // 👇 nuevos campos para uso futuro en el carrito
    val beneficio: String? = null,
    val descuento: Double? = 0.0
) {

    /** Calcula edad del usuario **/
    fun calcularEdad(): Int {
        return try {
            val formato = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val nacimiento = LocalDate.parse(fechaNacimiento, formato)
            Period.between(nacimiento, LocalDate.now()).years
        } catch (e: Exception) {
            0
        }
    }

    /** Aplica beneficios según edad, código o dominio **/
    fun calcularBeneficio(): Pair<String, Double> {
        val edad = calcularEdad()
        val dominio = correo.substringAfterLast("@")

        return when {
            dominio.equals("duocuc.cl", ignoreCase = true) ->
                "🎂 Torta gratis en tu cumpleaños" to 0.0
            dominio.equals("profesor.duoc.cl", ignoreCase = true) ->
                "🎁 Descuento 10% permanente (profesor DUOC)" to 0.10
            codigoPromocional.equals("FELICES50", ignoreCase = true) ->
                "🎉 Descuento 10% de por vida (código FELICES50)" to 0.10
            edad >= 50 ->
                "🎁 Descuento 50% por ser mayor de 50 años" to 0.50
            else ->
                "Sin beneficios especiales" to 0.0
        }
    }

    /** Valida dominio permitido **/
    fun dominioValido(): Boolean {
        val dominiosPermitidos = listOf("gmail.com", "duocuc.cl", "profesor.duoc.cl")
        val dominio = correo.substringAfterLast("@")
        return dominiosPermitidos.contains(dominio.lowercase())
    }
}
