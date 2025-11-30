package com.example.pasteleriamilsaboresapp.view

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.rememberNavController
import com.example.pasteleriamilsaboresapp.data.dao.CartDao
import com.example.pasteleriamilsaboresapp.data.dao.ProductoDao
import com.example.pasteleriamilsaboresapp.data.model.CartItem
import com.example.pasteleriamilsaboresapp.data.model.Producto
import com.example.pasteleriamilsaboresapp.data.repository.CartRepository
import com.example.pasteleriamilsaboresapp.data.repository.ProductRepository
import com.example.pasteleriamilsaboresapp.viewmodel.CartViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

// ==== Fakes en memoria para el ViewModel ====

class FakeCartDao : CartDao {
    private val itemsFlow = MutableStateFlow<List<CartItem>>(emptyList())

    fun setItems(items: List<CartItem>) {
        itemsFlow.value = items
    }

    override fun obtenerCarrito(): Flow<List<CartItem>> = itemsFlow

    override suspend fun insertarItem(item: CartItem) {
        itemsFlow.value = itemsFlow.value + item
    }

    override suspend fun actualizarItem(item: CartItem) {
        itemsFlow.value = itemsFlow.value.map { if (it.id == item.id) item else it }
    }

    override suspend fun eliminarItem(item: CartItem) {
        itemsFlow.value = itemsFlow.value.filterNot { it.id == item.id }
    }

    override suspend fun vaciarCarrito() {
        itemsFlow.value = emptyList()
    }

    override suspend fun actualizarCantidad(id: Int, delta: Int) {
        itemsFlow.value = itemsFlow.value.map {
            if (it.id == id) it.copy(cantidad = it.cantidad + delta) else it
        }
    }
}

class FakeProductoDao : ProductoDao {
    private val productosFlow = MutableStateFlow<List<Producto>>(emptyList())

    override suspend fun insertarProducto(producto: Producto) {
        productosFlow.value = productosFlow.value + producto
    }

    override fun obtenerProductos(): Flow<List<Producto>> = productosFlow
}

class CarritoScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    // Helper para crear un CartViewModel real con DAOs falsos en memoria
    private fun createViewModelWithItems(items: List<CartItem>): CartViewModel {
        val fakeCartDao = FakeCartDao().apply { setItems(items) }
        val cartRepo = CartRepository(fakeCartDao)
        val productRepo = ProductRepository(FakeProductoDao())
        return CartViewModel(cartRepo, productRepo)
    }

    // ---------- TESTS ----------

    @Test
    fun carritoScreen_muestraItemsCorrectamente() {
        val vm = createViewModelWithItems(
            listOf(
                CartItem(1, "COD1", "Torta Chocolate", 15000, 2),
                CartItem(2, "COD2", "Pie Limón", 8000, 1)
            )
        )

        composeRule.setContent {
            val nav = rememberNavController()
            CarritoScreen(navController = nav, cartViewModel = vm)
        }

        composeRule.onNodeWithTag("cart_list").assertIsDisplayed()
        composeRule.onNodeWithText("Torta Chocolate").assertIsDisplayed()
        composeRule.onNodeWithText("Pie Limón").assertIsDisplayed()
    }

    @Test
    fun carritoScreen_noConfirmaSiDireccionVacia() {
        val vm = createViewModelWithItems(
            listOf(CartItem(1, "COD1", "Torta Chocolate", 15000, 1))
        )

        composeRule.setContent {
            val nav = rememberNavController()
            CarritoScreen(navController = nav, cartViewModel = vm)
        }

        // Botón habilitado pero sin dirección -> no hay boleta
        composeRule.onNodeWithTag("cart_confirm_button")
            .assertIsEnabled()
            .performClick()

        composeRule.onNodeWithTag("cart_success_title")
            .assertDoesNotExist()
    }

    @Test
    fun carritoScreen_confirmaCompraYDespliegaPopup() {
        val vm = createViewModelWithItems(
            listOf(CartItem(1, "COD1", "Torta Chocolate", 15000, 1))
        )

        composeRule.setContent {
            val nav = rememberNavController()
            CarritoScreen(navController = nav, cartViewModel = vm)
        }

        composeRule.onNodeWithTag("cart_direccion_field")
            .performTextInput("Mi Casa 123")

        composeRule.onNodeWithTag("cart_confirm_button")
            .performClick()

        composeRule.onNodeWithTag("cart_success_title")
            .assertIsDisplayed()
    }

    @Test
    fun carritoScreen_incrementaCantidad_actualizaSubtotal() {
        val vm = createViewModelWithItems(
            listOf(CartItem(1, "COD1", "Torta Chocolate", 15000, 1))
        )

        composeRule.setContent {
            val nav = rememberNavController()
            CarritoScreen(navController = nav, cartViewModel = vm)
        }

        // Subtotal inicial: 1 * 15000
        composeRule.onNodeWithText("Subtotal: $15000")
            .assertIsDisplayed()

        // Click en "Aumentar"
        composeRule.onAllNodesWithContentDescription("Aumentar")
            .onFirst()
            .performClick()

        // Ahora el subtotal debe ser 30000
        composeRule.onNodeWithText("Subtotal: $30000")
            .assertIsDisplayed()
    }

    @Test
    fun carritoScreen_eliminaItem() {
        val vm = createViewModelWithItems(
            listOf(CartItem(1, "COD1", "Torta Chocolate", 15000, 1))
        )

        composeRule.setContent {
            val nav = rememberNavController()
            CarritoScreen(navController = nav, cartViewModel = vm)
        }

        composeRule.onAllNodesWithContentDescription("Eliminar")
            .onFirst()
            .performClick()

        composeRule.onNodeWithText("Torta Chocolate")
            .assertDoesNotExist()
    }

    @Test
    fun carritoScreen_boletaCompletaSeMuestraCorrectamente() {
        val vm = createViewModelWithItems(
            listOf(
                CartItem(
                    id = 1,
                    codigo = "COD1",
                    nombre = "Torta Chocolate",
                    precioUnitario = 15000,
                    cantidad = 2,
                    dedicatoria = "Feliz Cumpleaños",
                    velasCantidad = 3,
                    velasNumeros = "1,2,3"
                )
            )
        )

        composeRule.setContent {
            val nav = rememberNavController()
            CarritoScreen(navController = nav, cartViewModel = vm)
        }

        // Dirección de entrega
        composeRule.onNodeWithTag("cart_direccion_field")
            .performTextInput("Av. Siempre Viva 742")

        // Confirmar compra → boleta en popup
        composeRule.onNodeWithTag("cart_confirm_button")
            .performClick()

        // Título del popup
        composeRule.onNodeWithTag("cart_success_title")
            .assertIsDisplayed()

        // --- Detalle de producto (sin forzar el subtotal de la línea) ---
        composeRule.onNodeWithText("Torta Chocolate", substring = true)
            .assertIsDisplayed()
        composeRule.onNodeWithText("x2", substring = true)
            .assertIsDisplayed()

        // Dedicatoria
        composeRule.onNodeWithText("Feliz Cumpleaños", substring = true)
            .assertIsDisplayed()

        // Velas
        composeRule.onNodeWithText("Velas: 3", substring = true)
            .assertIsDisplayed()
        composeRule.onNodeWithText("(1,2,3)", substring = true)
            .assertIsDisplayed()

        // Dirección en la boleta
        composeRule.onNodeWithText("Dirección de entrega: Av. Siempre Viva 742")
            .assertIsDisplayed()

        // Total pagado (esto sí lo mantenemos)
        composeRule.onNodeWithText("Total pagado: $42000")
            .assertIsDisplayed()

        // Fecha estimada de entrega: comprobamos la etiqueta
        composeRule.onNodeWithText("Fecha estimada de entrega:", substring = true)
            .assertIsDisplayed()
    }

}
