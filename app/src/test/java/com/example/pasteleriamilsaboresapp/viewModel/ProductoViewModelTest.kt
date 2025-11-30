package com.example.pasteleriamilsaboresapp.viewmodel

import com.example.pasteleriamilsaboresapp.data.model.Producto
import com.example.pasteleriamilsaboresapp.data.repository.ProductRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

@OptIn(ExperimentalCoroutinesApi::class)
class ProductoViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var repository: ProductRepository
    private lateinit var viewModel: ProductoViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk(relaxed = true)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // ---------------------------------------------------------
    // TEST 1 — init: productos se cargan desde el repositorio
    // ---------------------------------------------------------
    @Test
    fun `al inicializar, productos se cargan desde el repositorio`() = runTest(testDispatcher) {
        val lista = listOf(
            Producto(
                id = 1,
                nombre = "Torta Chocolate",
                precio = "15000",
                cantidad = 1,
                direccion = "Av Siempreviva 123",
                mensajeDedicatoria = true,
                agregarVela = true
            ),
            Producto(
                id = 2,
                nombre = "Pie Limón",
                precio = "10000",
                cantidad = 2,
                direccion = "Calle Falsa 456",
                mensajeDedicatoria = false,
                agregarVela = false
            )
        )

        // El repo entrega un flow con esa lista
        every { repository.obtenerProductos() } returns flowOf(lista)

        // Cuando se crea el ViewModel, se suscribe al flow
        viewModel = ProductoViewModel(repository)

        // Con UnconfinedTestDispatcher, la corrutina corre de inmediato
        assertEquals(lista, viewModel.productos.value)
    }

    // ---------------------------------------------------------
    // TEST 2 — guardarProducto delega en el repositorio
    // ---------------------------------------------------------
    @Test
    fun `guardarProducto delega en el repositorio`() = runTest(testDispatcher) {
        val producto = Producto(
            id = 0,
            nombre = "Cheesecake",
            precio = "12000",
            cantidad = 1,
            direccion = "Mi Casa 555",
            mensajeDedicatoria = false,
            agregarVela = true
        )

        coEvery { repository.insertarProducto(producto) } returns Unit

        viewModel = ProductoViewModel(repository)

        viewModel.guardarProducto(producto)

        coVerify { repository.insertarProducto(producto) }
    }
}
