package com.example.pasteleriamilsaboresapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class Producto(
    @PrimaryKey(autoGenerate = true)
    val id:Int =0,
    val nombre: String,
    val precio:String,
    val cantidad:Int,
    val direccion: String,
    val mensajeDedicatoria: Boolean,
    val agregarVela:Boolean
)