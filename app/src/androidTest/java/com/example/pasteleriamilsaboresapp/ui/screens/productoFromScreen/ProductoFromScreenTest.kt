package com.example.pasteleriamilsaboresapp.view

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import androidx.navigation.compose.rememberNavController
import org.junit.Rule
import org.junit.Test

class ProductoFormScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    /**
     * Verifica que la pantalla muestre los datos básicos del producto:
     * - Nombre
     * - Precio base
     * - Label de cantidad
     */
    @Test
    fun productoFormScreen_muestraDatosBasicosDeProducto() {
        composeRule.setContent {
            val navController = rememberNavController()
            ProductoFormScreen(
                navController = navController,
                codigo = "TC001",
                nombre = "Torta Cuadrada de Chocolate",
                precio = "45000",
                cartViewModel = null
            )
        }

        // Nombre de la torta
        composeRule.onNodeWithText("Torta Cuadrada de Chocolate")
            .assertIsDisplayed()

        // Precio base
        composeRule.onNodeWithText("Precio base: $45000")
            .assertIsDisplayed()

        // Label de cantidad
        composeRule.onNodeWithText("Cantidad de tortas")
            .assertIsDisplayed()
    }

    /**
     * Verifica que el subtotal se actualiza cuando cambia la cantidad.
     * Caso simple: precio 45000, cantidad 2 → subtotal 90000.
     */
    @Test
    fun productoFormScreen_actualizaSubtotalAlCambiarCantidad() {
        composeRule.setContent {
            val navController = rememberNavController()
            ProductoFormScreen(
                navController = navController,
                codigo = "TC001",
                nombre = "Torta Cuadrada de Chocolate",
                precio = "45000",
                cartViewModel = null
            )
        }

        // Subtotal inicial (cantidad 1, sin velas) → 45000
        composeRule.onNodeWithTag("form_subtotal")
            .assertIsDisplayed()
        composeRule.onNodeWithText("Subtotal: $45000")
            .assertIsDisplayed()

        // Cambiamos la cantidad a 2
        composeRule.onNodeWithTag("form_quantity")
            .performTextReplacement("2")

        // Ahora el subtotal debería ser 90000 (2 * 45000)
        composeRule.onNodeWithText("Subtotal: $90000")
            .assertIsDisplayed()
    }

    /**
     * Verifica la validación de velas:
     * - Activamos las velas
     * - Ponemos 2 velas pero solo rellenamos 1
     *  => Aparece mensaje de error y el botón de agregar queda deshabilitado.
     */
    @Test
    fun productoFormScreen_muestraErrorCuandoVelasIncompletasYDeshabilitaBoton() {
        composeRule.setContent {
            val navController = rememberNavController()
            ProductoFormScreen(
                navController = navController,
                codigo = "TC001",
                nombre = "Torta Cuadrada de Chocolate",
                precio = "45000",
                cartViewModel = null
            )
        }

        // Activamos el switch de velas
        composeRule.onNodeWithTag("form_switch_velas")
            .performClick()

        // Aumentamos a 2 velas (usa el IconButton con contentDescription "Más velas")
        composeRule.onNodeWithContentDescription("Más velas")
            .performClick()

        // Rellenamos solo la vela 1, dejamos la 2 vacía
        composeRule.onNodeWithText("Número de la vela 1")
            .performTextInput("1")

        // Debe aparecer el mensaje de error
        composeRule.onNodeWithText("Completa todos los números de las velas (0 a 9).")
            .assertIsDisplayed()

        // Y el botón "Agregar al carrito" debe estar deshabilitado
        composeRule.onNodeWithTag("form_add_to_cart")
            .assertIsNotEnabled()
    }
}
