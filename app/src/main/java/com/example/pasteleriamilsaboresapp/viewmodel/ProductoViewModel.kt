package com.example.pasteleriamilsaboresapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasteleriamilsaboresapp.data.model.Producto
import com.example.pasteleriamilsaboresapp.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProductoViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    init {
        // 🔄 Cargar productos desde Room automáticamente
        viewModelScope.launch {
            repository.obtenerProductos().collectLatest { lista ->
                _productos.value = lista
            }
        }
    }

    fun guardarProducto(producto: Producto) {
        viewModelScope.launch {
            repository.insertarProducto(producto)
        }
    }
}
