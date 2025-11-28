package com.example.pasteleriamilsaboresapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasteleriamilsaboresapp.data.model.CartItem
import com.example.pasteleriamilsaboresapp.data.model.Producto
import com.example.pasteleriamilsaboresapp.data.repository.CartRepository
import com.example.pasteleriamilsaboresapp.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _items = MutableStateFlow<List<CartItem>>(emptyList())
    val items: StateFlow<List<CartItem>> = _items.asStateFlow()

    val total: StateFlow<Int> = MutableStateFlow(0)

    init {
        viewModelScope.launch {
            cartRepository.obtenerCarrito().collectLatest { lista ->
                _items.value = lista
                (total as MutableStateFlow).value = lista.sumOf { it.subtotal }
            }
        }
    }

    fun agregarProductoAlCarrito(
        codigo: String,
        nombre: String,
        precioUnitario: Int,
        cantidad: Int = 1,
        dedicatoria: String? = null,
        velasCantidad: Int = 0,
        velasNumeros: String? = null,
        img: String? = null
    ) {
        viewModelScope.launch {
            val nuevo = CartItem(
                codigo = codigo,
                nombre = nombre,
                precioUnitario = precioUnitario,
                cantidad = cantidad,
                dedicatoria = dedicatoria,
                velasCantidad = velasCantidad,
                velasNumeros = velasNumeros,
                img = img
            )
            cartRepository.agregarAlCarrito(nuevo)
        }
    }

    fun incrementarCantidad(item: CartItem) {
        viewModelScope.launch {
            cartRepository.cambiarCantidad(item.id, 1)
        }
    }

    fun decrementarCantidad(item: CartItem) {
        viewModelScope.launch {
            if (item.cantidad <= 1) {
                cartRepository.eliminarItem(item)
            } else {
                cartRepository.cambiarCantidad(item.id, -1)
            }
        }
    }

    fun eliminarItem(item: CartItem) {
        viewModelScope.launch {
            cartRepository.eliminarItem(item)
        }
    }

    fun vaciarCarrito() {
        viewModelScope.launch {
            cartRepository.vaciarCarrito()
        }
    }

    /**
     * 🔄 Confirma la compra:
     * - Convierte los CartItem a Producto (tu entidad actual)
     * - Los guarda con ProductRepository (Room)
     * - Vacía el carrito
     */
    fun confirmarCompra(
        direccion: String,
        mensajeDedicatoria: Boolean,
        agregarVela: Boolean
    ) {
        viewModelScope.launch {
            val listaActual = _items.value
            if (listaActual.isEmpty()) return@launch

            listaActual.forEach { item ->
                val producto = Producto(
                    nombre = item.nombre,
                    precio = item.subtotal.toString(),
                    cantidad = item.cantidad,
                    direccion = direccion,
                    mensajeDedicatoria = mensajeDedicatoria || !item.dedicatoria.isNullOrBlank(),
                    agregarVela = agregarVela || item.velasCantidad > 0
                )
                productRepository.insertarProducto(producto)
            }

            cartRepository.vaciarCarrito()
        }
    }
}
