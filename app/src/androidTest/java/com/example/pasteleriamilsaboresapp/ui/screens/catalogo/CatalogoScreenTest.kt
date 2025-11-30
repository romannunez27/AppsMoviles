package com.example.pasteleriamilsaboresapp.ui.catalogo

import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.junit.Rule
import org.junit.Test
import androidx.compose.ui.test.onFirst

class CatalogoScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    /**
     * Verifica que el primer producto del catálogo está visible
     * (nombre y precio).
     */
    @Test
    fun catalogoScreen_muestraPrimerProductoDelCatalogo() {
        val primerProducto = productosCatalogo.first()

        composeRule.setContent {
            val navController = rememberNavController()
            CatalogoScreen(
                navController = navController,
                cartViewModel = null      // sin carrito para este test
            )
        }

        // Nombre del producto
        composeRule.onNodeWithText(primerProducto.nombre)
            .assertIsDisplayed()

        // Precio con formato usado en la UI: "$<precio>"
        composeRule.onNodeWithText("$${primerProducto.precio}")
            .assertIsDisplayed()
    }

    /**
     * Verifica que el botón "Agregar al carrito" esté deshabilitado
     * cuando no se pasa un CartViewModel (cartViewModel = null).
     */
    @Test
    fun catalogoScreen_botonAgregarDeshabilitadoSinCartViewModel() {
        composeRule.setContent {
            val navController = rememberNavController()
            CatalogoScreen(
                navController = navController,
                cartViewModel = null
            )
        }

        // Toma el primer botón "Agregar al carrito" y verifica que esté deshabilitado
        composeRule.onAllNodesWithText("Agregar al carrito")
            .onFirst()
            .assertIsNotEnabled()
    }

    /**
     * Verifica que al pulsar "Ver detalles" se navega a la ruta
     * productoForm/{codigo}/{nombre}/{precio}.
     *
     * Para eso armamos un NavHost de prueba donde la pantalla destino
     * muestra un Text con el nombre del producto.
     */
    @Test
    fun catalogoScreen_navegaAFormularioProductoAlVerDetalles() {
        val primerProducto = productosCatalogo.first()

        composeRule.setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "catalogo"
            ) {
                composable("catalogo") {
                    CatalogoScreen(
                        navController = navController,
                        cartViewModel = null
                    )
                }
                composable(
                    route = "productoForm/{codigo}/{nombre}/{precio}"
                ) { backStackEntry ->
                    val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
                    Text(text = "Detalle de: $nombre")
                }
            }
        }

        // Click en el primer botón "Ver detalles"
        composeRule.onAllNodesWithText("Ver detalles")
            .onFirst()
            .performClick()

        // Debería mostrarse el texto de la pantalla destino
        composeRule.onNodeWithText("Detalle de: ${primerProducto.nombre}")
            .assertIsDisplayed()
    }
}
