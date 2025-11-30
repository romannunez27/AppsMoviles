package com.example.pasteleriamilsaboresapp.data.repository

import android.util.Log
import com.example.pasteleriamilsaboresapp.data.dao.ProductoDao
import com.example.pasteleriamilsaboresapp.data.model.Producto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
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
class ProductRepositoryTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var productoDao: ProductoDao
    private lateinit var repository: ProductRepository

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        // 👇 NECESARIO para evitar el error "Method d in Log not mocked"
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0

        productoDao = mockk(relaxed = true)
        repository = ProductRepository(productoDao)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkStatic(Log::class)
    }

    // ---------------------------------------------------------
    // TEST 1: obtenerProductos()
    // ---------------------------------------------------------
    @Test
    fun `obtenerProductos retorna el mismo flow del dao`() = runTest(testDispatcher) {

        val fakeFlow = flowOf(
            listOf(
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
                    nombre = "Pie de Limón",
                    precio = "10000",
                    cantidad = 2,
                    direccion = "Calle Falsa 123",
                    mensajeDedicatoria = false,
                    agregarVela = false
                )
            )
        )

        every { productoDao.obtenerProductos() } returns fakeFlow

        val result = repository.obtenerProductos()

        assertEquals(fakeFlow, result)
        coVerify { productoDao.obtenerProductos() }
    }

    // ---------------------------------------------------------
    // TEST 2: insertarProducto()
    // ---------------------------------------------------------
    @Test
    fun `insertarProducto delega correctamente en el dao`() = runTest(testDispatcher) {

        val producto = Producto(
            id = 0,
            nombre = "Cheesecake",
            precio = "12000",
            cantidad = 1,
            direccion = "Mi Casa 555",
            mensajeDedicatoria = false,
            agregarVela = true
        )

        coEvery { productoDao.insertarProducto(producto) } returns Unit

        repository.insertarProducto(producto)

        coVerify { productoDao.insertarProducto(producto) }
    }
}
