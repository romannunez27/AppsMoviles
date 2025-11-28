package com.example.pasteleriamilsaboresapp.data.dao

import androidx.room.*
import com.example.pasteleriamilsaboresapp.data.model.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM cart_items")
    fun obtenerCarrito(): Flow<List<CartItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarItem(item: CartItem)

    @Update
    suspend fun actualizarItem(item: CartItem)

    @Delete
    suspend fun eliminarItem(item: CartItem)

    @Query("DELETE FROM cart_items")
    suspend fun vaciarCarrito()

    // 🔼 Incrementar / decrementar cantidad
    @Query("UPDATE cart_items SET cantidad = cantidad + :delta WHERE id = :id")
    suspend fun actualizarCantidad(id: Int, delta: Int)
}
