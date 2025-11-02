package com.example.pasteleriamilsaboresapp.data.repository

import com.example.pasteleriamilsaboresapp.data.dao.ProductoDao
import com.example.pasteleriamilsaboresapp.data.model.Producto
import kotlinx.coroutines.flow.Flow

class ProductRepository (private val productoDao: ProductoDao){

    suspend fun insertarProducto(producto: Producto){
        productoDao.insertarProducto(producto)
    }

    fun obtenerProductos(): Flow<List<Producto>>{
        return productoDao.obtenerProductos()
    }
}