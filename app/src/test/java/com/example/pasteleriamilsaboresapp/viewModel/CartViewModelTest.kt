package com.example.pasteleriamilsaboresapp.viewmodel

import android.util.Log
import com.example.pasteleriamilsaboresapp.data.dao.CartDao
import com.example.pasteleriamilsaboresapp.data.dao.ProductoDao
import com.example.pasteleriamilsaboresapp.data.model.CartItem
import com.example.pasteleriamilsaboresapp.data.repository.CartRepository
import com.example.pasteleriamilsaboresapp.data.repository.ProductRepository
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var cartDao: CartDao
    private lateinit var productDao: ProductoDao
    private lateinit var cartRepository: CartRepository
    private lateinit var productRepository: ProductRepository
    private lateinit var viewModel: CartViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        // Mock de Android Log
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0

        // DAO mocks
        cartDao = mockk(relaxed = true)
        productDao = mockk(relaxed = true)

        cartRepository = CartRepository(cartDao)
        productRepository = ProductRepository(productDao)

        // Carrito inicial simulado
        every { cartDao.obtenerCarrito() } returns flowOf(
            listOf(
                CartItem(
                    id = 1,
                    codigo = "C1",
                    nombre = "Torta Chocolate",
                    precioUnitario = 15000,
                    cantidad = 2,
                    dedicatoria = "Feliz día",
                    velasCantidad = 1,
                    velasNumeros = "5",
                    img = null
                ),
                CartItem(
                    id = 2,
                    codigo = "C2",
                    nombre = "Pie Limón",
                    precioUnitario = 10000,
                    cantidad = 1,
                    dedicatoria = null,
                    velasCantidad = 0,
                    velasNumeros = null,
                    img = null
                )
            )
        )

        viewModel = CartViewModel(cartRepository, productRepository)
        // 👇 Avanzamos el scheduler del dispatcher de test
        testDispatcher.scheduler.advanceUntilIdle()
    }

    @AfterEach
    fun tearDown() {
        unmockkStatic(Log::class)
        Dispatchers.resetMain()
    }

    // ---------------------------------------------------------
    // TEST 1 — Inicialización de items y total
    // ---------------------------------------------------------
    @Test
    fun `al inicializar, items y total se cargan correctamente`() = runTest(testDispatcher) {
        val items = viewModel.items.value
        val total = viewModel.total.value

        assertEquals(2, items.size)
        val esperado = items.sumOf { it.subtotal }
        assertEquals(esperado, total)
    }

    // ---------------------------------------------------------
    // TEST 2 — incrementarCantidad() delega en cambiarCantidad()
    // ---------------------------------------------------------
    @Test
    fun `incrementarCantidad llama cambiarCantidad con delta 1`() = runTest(testDispatcher) {
        val item = viewModel.items.value.first()

        coEvery { cartDao.actualizarCantidad(item.id, 1) } returns Unit

        viewModel.incrementarCantidad(item)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { cartDao.actualizarCantidad(item.id, 1) }
    }

    // ---------------------------------------------------------
    // TEST 3A — decrementarCantidad elimina cuando cantidad = 1
    // ---------------------------------------------------------
    @Test
    fun `decrementarCantidad elimina el item si cantidad es 1`() = runTest(testDispatcher) {
        val item = viewModel.items.value[1] // cantidad = 1

        coEvery { cartDao.eliminarItem(item) } returns Unit

        viewModel.decrementarCantidad(item)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { cartDao.eliminarItem(item) }
    }

    // ---------------------------------------------------------
    // TEST 3B — decrementarCantidad resta 1 cuando cantidad mayor a 1
    // ---------------------------------------------------------
    @Test
    fun `decrementarCantidad resta 1 si cantidad mayor a 1`() = runTest(testDispatcher) {
        val item = viewModel.items.value[0] // cantidad = 2

        coEvery { cartDao.actualizarCantidad(item.id, -1) } returns Unit

        viewModel.decrementarCantidad(item)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { cartDao.actualizarCantidad(item.id, -1) }
    }

    // ---------------------------------------------------------
    // TEST 4 — eliminarItem llama al dao
    // ---------------------------------------------------------
    @Test
    fun `eliminarItem delega correctamente en el dao`() = runTest(testDispatcher) {
        val item = viewModel.items.value.first()

        coEvery { cartDao.eliminarItem(item) } returns Unit

        viewModel.eliminarItem(item)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { cartDao.eliminarItem(item) }
    }

    // ---------------------------------------------------------
    // TEST 5 — confirmarCompra genera Productos y vacía el carrito
    // ---------------------------------------------------------
    @Test
    fun `confirmarCompra guarda productos y vacia el carrito`() = runTest(testDispatcher) {

        coEvery { productDao.insertarProducto(any()) } returns Unit
        coEvery { cartDao.vaciarCarrito() } returns Unit

        viewModel.confirmarCompra(
            direccion = "Mi Casa 555",
            mensajeDedicatoria = true,
            agregarVela = true
        )

        testDispatcher.scheduler.advanceUntilIdle()

        // Verificar inserción de productos
        coVerify(exactly = 2) { productDao.insertarProducto(any()) }

        // Verificar vaciar carrito
        coVerify { cartDao.vaciarCarrito() }
    }
}
