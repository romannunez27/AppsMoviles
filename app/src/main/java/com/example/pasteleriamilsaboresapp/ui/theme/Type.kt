// ui/theme/Type.kt
package com.example.pasteleriamilsaboresapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.pasteleriamilsaboresapp.R

// ✅ Carga las fuentes desde res/font (debes agregarlas allí)
val Lato = FontFamily(
    Font(R.font.lato_regular, FontWeight.Normal),
    Font(R.font.lato_bold, FontWeight.Bold)
)

val Pacifico = FontFamily(
    Font(R.font.pacifico_regular, FontWeight.Normal)
)

val typography = Typography(
    titleLarge = TextStyle(
        fontFamily = Pacifico,
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Lato,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Lato,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    )
)
