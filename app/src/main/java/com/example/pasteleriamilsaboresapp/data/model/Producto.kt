package com.example.pasteleriamilsaboresapp.data.model

import com.google.firebase.firestore.PropertyName


data class Producto(
    val id: String = "",
    val nombre: String = "",
    val precio: Double = 0.0,
    @get:PropertyName("image") @set:PropertyName("image")
    var imagen:String = "",
    val stock: Int = 0,
    val mensajeDedicatoria: Boolean = false,
    val agregarVela:Boolean = false
)