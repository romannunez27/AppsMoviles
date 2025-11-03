package com.example.pasteleriamilsaboresapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasteleriamilsaboresapp.data.model.Compra
import com.example.pasteleriamilsaboresapp.data.model.Producto
import com.example.pasteleriamilsaboresapp.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductoViewModel(
    private val repository: ProductRepository = ProductRepository()
) : ViewModel() {

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    private val _productoSeleccionado = MutableStateFlow<Producto?>(null)
    val productoSeleccionado: StateFlow<Producto?> = _productoSeleccionado.asStateFlow()

    init {
        cargarProductosDesdeFirebase()
    }

    // ☁️ Cargar todos los productos desde Firebase
    fun cargarProductosDesdeFirebase() {
        viewModelScope.launch {
            val resultado = repository.obtenerProductos(limite = 20)
            _productos.value = resultado.productos
        }
    }

    // ☁️ Cargar un producto específico
    fun cargarProductoDesdeFirestore(id: String) {
        viewModelScope.launch {
            val producto = repository.obtenerProductoPorId(id)
            _productoSeleccionado.value = producto
        }
    }

    // ☁️ Registrar compra y reducir stock
    fun guardarCompra(
        producto: Producto,
        cantidad: Int,
        direccion: String,
        mensajeDedicatoria: Boolean,
        agregarVela: Boolean
    ) {
        viewModelScope.launch {
            val compra = Compra(
                productoId = producto.id,
                nombreProducto = producto.nombre,
                cantidad = cantidad,
                direccion = direccion,
                mensajeDedicatoria = mensajeDedicatoria,
                agregarVela = agregarVela,
                precioTotal = producto.precio * cantidad
            )

            repository.registrarCompra(compra)
            repository.actualizarStock(producto.id, producto.stock - cantidad)
        }
    }
}
