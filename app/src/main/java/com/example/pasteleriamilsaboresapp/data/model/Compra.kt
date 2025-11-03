package com.example.pasteleriamilsaboresapp.data.model

data class Compra(
    val productoId: String = "",
    val nombreProducto: String = "",
    val cantidad: Int = 1,
    val direccion: String = "",
    val mensajeDedicatoria: Boolean = false,
    val agregarVela: Boolean = false,
    val precioTotal: Double = 0.0
)