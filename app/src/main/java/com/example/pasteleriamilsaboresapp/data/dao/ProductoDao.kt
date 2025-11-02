package com.example.pasteleriamilsaboresapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pasteleriamilsaboresapp.data.model.Producto
import kotlinx.coroutines.flow.Flow

@Dao

interface ProductoDao {

    @Insert
    suspend fun insertarProducto(producto: Producto)

    @Query(value = "SELECT * FROM productos")
    fun  obtenerProductos(): Flow<List<Producto>>
}