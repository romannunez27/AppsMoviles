package com.example.pasteleriamilsaboresapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val codigo: String,
    val nombre: String,
    val precioUnitario: Int,
    val cantidad: Int,

    // 📝 Mensaje en la torta
    val dedicatoria: String? = null,

    // 🕯️ Velas numéricas (por torta)
    val velasCantidad: Int = 0,         // 0–3
    val velasNumeros: String? = null,   // ej: "2 y 5"

    val img: String? = null
) {
    // 💰 Subtotal = tortas + velas (2000 c/u por torta)
    val subtotal: Int
        get() = (precioUnitario * cantidad) + (velasCantidad * 2000 * cantidad)
}
