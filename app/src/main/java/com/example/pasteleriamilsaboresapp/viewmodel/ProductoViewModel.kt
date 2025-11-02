package com.example.pasteleriamilsaboresapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasteleriamilsaboresapp.data.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductoViewModel : ViewModel() {

    // Lista observable de productos (inicialmente vacía)
    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    // Agregar un nuevo producto a la lista (en memoria)
    fun guardarProducto(producto: Producto) {
        viewModelScope.launch {
            val nuevaLista = _productos.value + producto
            _productos.value = nuevaLista
        }
    }
}
