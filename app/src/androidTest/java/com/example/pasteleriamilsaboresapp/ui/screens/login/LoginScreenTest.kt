package com.example.pasteleriamilsaboresapp.ui.login

import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    /**
     * Verifica que los elementos principales de la pantalla se muestren:
     * - Título
     * - Campos de correo y contraseña
     * - Botones de Iniciar y Registrar
     */
    @Test
    fun loginScreen_muestraElementosIniciales() {
        composeRule.setContent {
            val navController = rememberNavController()
            LoginScreen(navController = navController)
        }

        // Título
        composeRule.onNodeWithText("Bienvenido/a").assertIsDisplayed()

        // Campos (por texto del label)
        composeRule.onNodeWithText("Correo electrónico").assertIsDisplayed()
        composeRule.onNodeWithText("Contraseña").assertIsDisplayed()

        // Botones
        composeRule.onNodeWithText("Iniciar").assertIsDisplayed()
        composeRule.onNodeWithText("Registrar").assertIsDisplayed()
    }

    /**
     * Escribe en los campos de correo y contraseña y verifica que
     * el mismo texto quede reflejado en los TextField (vía state).
     */
    @Test
    fun loginScreen_escrituraEnCamposActualizaUi() {
        composeRule.setContent {
            val navController = rememberNavController()
            LoginScreen(navController = navController)
        }

        val correo = "usuario@correo.cl"
        val password = "123456"

        // Escribimos en el campo correo
        composeRule.onNodeWithTag("login_email")
            .performTextInput(correo)

        // Escribimos en el campo contraseña
        composeRule.onNodeWithTag("login_password")
            .performTextInput(password)

        // El texto del correo queda visible en la UI
        composeRule.onNodeWithText(correo).assertIsDisplayed()
        // Para la contraseña, al usar PasswordVisualTransformation,
        // no veremos el texto plano, así que nos basta con que el nodo exista:
        composeRule.onNodeWithTag("login_password").assertIsDisplayed()
    }

    /**
     * Verifica la navegación al presionar el botón "Registrar".
     * Se arma un NavHost de prueba:
     *  - login -> LoginScreen
     *  - registro -> Text("Pantalla Registro")
     *
     * Si al hacer click en "Registrar" aparece "Pantalla Registro",
     * la navegación funciona.
     */
    @Test
    fun loginScreen_navegaARegistroAlPresionarRegistrar() {
        composeRule.setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "login"
            ) {
                composable("login") {
                    LoginScreen(navController = navController)
                }
                composable("registro") {
                    Text("Pantalla Registro")
                }
            }
        }

        // Click en el botón "Registrar"
        composeRule.onNodeWithTag("register_button")
            .performClick()

        // Ahora debería estar visible el contenido de la ruta "registro"
        composeRule.onNodeWithText("Pantalla Registro")
            .assertIsDisplayed()
    }
}
