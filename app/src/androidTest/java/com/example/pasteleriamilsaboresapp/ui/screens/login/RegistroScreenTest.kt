package com.example.pasteleriamilsaboresapp.ui.login

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import org.junit.Rule
import org.junit.Test
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasText

class RegistroScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    /**
     * Si los campos obligatorios están vacíos y se presiona "Registrar",
     * se muestra el mensaje "Completa todos los campos obligatorios".
     */
    @Test
    fun registroScreen_muestraErrorCuandoCamposVacios() {
        composeRule.setContent {
            val nav = rememberNavController()
            RegistroScreen(navController = nav)
        }

        // Click en botón Registrar sin llenar nada
        composeRule.onNodeWithTag("registro_boton_registrar")
            .performClick()

        // Mensaje de error correspondiente
        composeRule.onNodeWithTag("registro_mensaje")
            .assertIsDisplayed()
            .assertTextEquals("Completa todos los campos obligatorios")
    }

    /**
     * Si las contraseñas no coinciden, se muestra el mensaje
     * "Las contraseñas no coinciden".
     */
    @Test
    fun registroScreen_muestraErrorCuandoPasswordsNoCoinciden() {
        composeRule.setContent {
            val nav = rememberNavController()
            RegistroScreen(navController = nav)
        }

        // Rellenamos los campos obligatorios con datos válidos
        composeRule.onNodeWithTag("registro_nombre")
            .performTextInput("Sebastián Tester")

        composeRule.onNodeWithTag("registro_correo")
            .performTextInput("test@duocuc.cl")

        composeRule.onNodeWithTag("registro_fecha")
            .performTextInput("01-01-2000")

        composeRule.onNodeWithTag("registro_password")
            .performTextInput("123456")

        composeRule.onNodeWithTag("registro_confirm_password")
            .performTextInput("654321")

        // Click en Registrar
        composeRule.onNodeWithTag("registro_boton_registrar")
            .performClick()

        composeRule.onNodeWithTag("registro_mensaje")
            .assertIsDisplayed()
            .assertTextEquals("Las contraseñas no coinciden")
    }

    /**
     * Si se pasa un qrContent, el código promocional se rellena automáticamente.
     */
    @Test
    fun registroScreen_cargaCodigoDesdeQrContent() {
        composeRule.setContent {
            val nav = rememberNavController()
            RegistroScreen(
                navController = nav,
                qrContent = "PROMO123"
            )
        }

        // Buscamos un TextField (tiene setTextAction) cuyo texto sea PROMO123
        composeRule.onNode(
            hasText("PROMO123") and hasSetTextAction()
        ).assertIsDisplayed()
    }
}
