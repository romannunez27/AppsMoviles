package com.example.pasteleriamilsaboresapp.data.repository

import com.example.pasteleriamilsaboresapp.data.model.Producto
import com.example.pasteleriamilsaboresapp.data.model.Compra
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

data class ResultadoProductos(
    val productos: List<Producto>,
    val ultimoDocumento: DocumentSnapshot? = null
)

class ProductoRepository {

    private val db = FirebaseFirestore.getInstance()

    // 🔹 Obtener productos con stock (primer lote)
    suspend fun obtenerProductos(limite: Int = 10): ResultadoProductos {
        return try {
            val query = db.collection("producto")
                .whereGreaterThan("stock", 0)
                .orderBy("stock", Query.Direction.DESCENDING)
                .limit(limite.toLong())

            val snapshot = query.get().await()
            val productos = snapshot.documents.mapNotNull { it.toProducto() }

            ResultadoProductos(productos, snapshot.documents.lastOrNull())
        } catch (e: Exception) {
            e.printStackTrace()
            ResultadoProductos(emptyList(), null)
        }
    }

    // 🔹 Obtener más productos (paginación)
    suspend fun obtenerMasProductos(limite: Int = 10, ultimoDocumento: DocumentSnapshot?): ResultadoProductos {
        if (ultimoDocumento == null) return ResultadoProductos(emptyList(), null)

        return try {
            val query = db.collection("producto")
                .whereGreaterThan("stock", 0)
                .orderBy("stock", Query.Direction.DESCENDING)
                .startAfter(ultimoDocumento)
                .limit(limite.toLong())

            val snapshot = query.get().await()
            val productos = snapshot.documents.mapNotNull { it.toProducto() }

            ResultadoProductos(productos, snapshot.documents.lastOrNull())
        } catch (e: Exception) {
            e.printStackTrace()
            ResultadoProductos(emptyList(), null)
        }
    }

    // 🔹 Actualizar stock
    suspend fun actualizarStock(productoId: String, nuevoStock: Int): Boolean {
        return try {
            db.collection("producto")
                .document(productoId)
                .update("stock", nuevoStock)
                .await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // 🔹 Obtener producto por ID
    suspend fun obtenerProductoPorId(productoId: String): Producto? {
        return try {
            val document = db.collection("producto")
                .document(productoId)
                .get()
                .await()
            document.toProducto()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // 🔹 Registrar compra en Firestore
    suspend fun registrarCompra(compra: Compra): Boolean {
        return try {
            db.collection("compras")
                .add(compra)
                .await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // 🔸 Conversión segura DocumentSnapshot → Producto
    private fun DocumentSnapshot.toProducto(): Producto? {
        return try {
            Producto(
                id = id,
                nombre = getString("nombre") ?: "",
                precio = getDouble("precio") ?: 0.0,
                imagen = getString("imagen") ?: "",
                stock = getLong("stock")?.toInt() ?: 0,
                mensajeDedicatoria = getBoolean("mensajeDedicatoria") ?: false,
                agregarVela = getBoolean("agregarVela") ?: false
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
