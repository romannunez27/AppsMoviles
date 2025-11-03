package com.example.pasteleriamilsaboresapp.data.repository

import com.example.pasteleriamilsaboresapp.data.model.Compra
import com.example.pasteleriamilsaboresapp.data.model.Producto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ProductRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val productosCollection = firestore.collection("producto")
    private val comprasCollection = firestore.collection("compras")

    suspend fun obtenerProductos(limite: Int = 20): ResultadoProductos {
        return try {
            val snapshot = productosCollection.limit(limite.toLong()).get().await()
            val productos = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Producto::class.java)?.copy(id = doc.id)
            }
            ResultadoProductos(productos)
        } catch (e: Exception) {
            e.printStackTrace()
            ResultadoProductos(emptyList())
        }
    }

    suspend fun obtenerProductoPorId(id: String): Producto? {
        return try {
            val doc = productosCollection.document(id).get().await()
            doc.toObject(Producto::class.java)?.copy(id = doc.id)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun registrarCompra(compra: Compra) {
        try {
            comprasCollection.add(compra).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun actualizarStock(id: String, nuevoStock: Int) {
        try {
            productosCollection.document(id).update("stock", nuevoStock).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

data class ResultadoProductos(val productos: List<Producto>)
