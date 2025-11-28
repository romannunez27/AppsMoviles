package com.example.pasteleriamilsaboresapp.data.repository

import com.example.pasteleriamilsaboresapp.data.dao.CartDao
import com.example.pasteleriamilsaboresapp.data.model.CartItem
import kotlinx.coroutines.flow.Flow

class CartRepository(private val cartDao: CartDao) {

    fun obtenerCarrito(): Flow<List<CartItem>> = cartDao.obtenerCarrito()

    suspend fun agregarAlCarrito(item: CartItem) {
        cartDao.insertarItem(item)
    }

    suspend fun actualizarItem(item: CartItem) {
        cartDao.actualizarItem(item)
    }

    suspend fun eliminarItem(item: CartItem) {
        cartDao.eliminarItem(item)
    }

    suspend fun vaciarCarrito() {
        cartDao.vaciarCarrito()
    }

    suspend fun cambiarCantidad(id: Int, delta: Int) {
        cartDao.actualizarCantidad(id, delta)
    }
}
