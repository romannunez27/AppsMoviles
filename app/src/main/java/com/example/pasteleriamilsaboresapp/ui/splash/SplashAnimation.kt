package com.example.pasteleriamilsaboresapp.ui.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.example.pasteleriamilsaboresapp.R

@Composable
fun SplashLogoAnimation() {
    val scale = remember { Animatable(0.5f) }
    val easing = CubicBezierEasing(0.390f, 0.575f, 0.565f, 1.000f)

    LaunchedEffect(Unit) {
        delay(200)
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 400, easing = easing)
        )
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_fndo_blanco),
            contentDescription = "Logo Pastelería Mil Sabores",
            modifier = Modifier
                .size(180.dp)
                .scale(scale.value)
        )
    }
}
