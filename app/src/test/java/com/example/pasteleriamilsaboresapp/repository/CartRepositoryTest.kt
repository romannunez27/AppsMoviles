package com.example.pasteleriamilsaboresapp.data.repository

import com.example.pasteleriamilsaboresapp.data.dao.CartDao
import com.example.pasteleriamilsaboresapp.data.model.CartItem
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
class CartRepositoryTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var cartDao: CartDao
    private lateinit var repository: CartRepository

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        cartDao = mockk(relaxed = true)
        repository = CartRepository(cartDao)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // ---------------------------------------------------------
    // TEST 1: obtenerCarrito()
    // ---------------------------------------------------------
    @Test
    fun `obtenerCarrito delega en el dao y retorna el mismo flow`() = runTest(testDispatcher) {
        val fakeFlow = flowOf(listOf<CartItem>())

        every { cartDao.obtenerCarrito() } returns fakeFlow

        val result = repository.obtenerCarrito()

        assertEquals(fakeFlow, result)
        coVerify { cartDao.obtenerCarrito() }
    }

    // ---------------------------------------------------------
    // TEST 2: insertarItem()
    // ---------------------------------------------------------
    @Test
    fun `insertarItem delega correctamente en el dao`() = runTest(testDispatcher) {
        val item = CartItem(
            id = 1,
            codigo = "COD123",
            nombre = "Torta Chocolate",
            precioUnitario = 15000,
            cantidad = 2
        )

        coEvery { cartDao.insertarItem(item) } returns Unit

        repository.agregarAlCarrito(item)

        coVerify { cartDao.insertarItem(item) }
    }

    // ---------------------------------------------------------
    // TEST 3: actualizarItem()
    // ---------------------------------------------------------
    @Test
    fun `actualizarItem delega correctamente en el dao`() = runTest(testDispatcher) {
        val item = CartItem(
            id = 10,
            codigo = "X1",
            nombre = "Torta Fresa",
            precioUnitario = 12000,
            cantidad = 1
        )

        coEvery { cartDao.actualizarItem(item) } returns Unit

        repository.actualizarItem(item)

        coVerify { cartDao.actualizarItem(item) }
    }

    // ---------------------------------------------------------
    // TEST 4: eliminarItem()
    // ---------------------------------------------------------
    @Test
    fun `eliminarItem delega correctamente en el dao`() = runTest(testDispatcher) {
        val item = CartItem(
            id = 5,
            codigo = "C222",
            nombre = "Pie de Limon",
            precioUnitario = 8000,
            cantidad = 3
        )

        coEvery { cartDao.eliminarItem(item) } returns Unit

        repository.eliminarItem(item)

        coVerify { cartDao.eliminarItem(item) }
    }

    // ---------------------------------------------------------
    // TEST 5: actualizarCantidad()
    // ---------------------------------------------------------
    @Test
    fun `actualizarCantidad delega correctamente en el dao`() = runTest(testDispatcher) {
        coEvery { cartDao.actualizarCantidad(10, 3) } returns Unit

        repository.cambiarCantidad(10,3)

        coVerify { cartDao.actualizarCantidad(10, 3) }
    }

    // ---------------------------------------------------------
    // TEST 6: vaciarCarrito()
    // ---------------------------------------------------------
    @Test
    fun `vaciarCarrito delega correctamente en el dao`() = runTest(testDispatcher) {
        coEvery { cartDao.vaciarCarrito() } returns Unit

        repository.vaciarCarrito()

        coVerify { cartDao.vaciarCarrito() }
    }
}
