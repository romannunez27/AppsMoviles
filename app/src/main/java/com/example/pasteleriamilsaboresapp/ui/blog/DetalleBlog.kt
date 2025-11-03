package com.example.pasteleriamilsaboresapp.ui.blog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pasteleriamilsaboresapp.ui.theme.CafeSuave

@Composable
fun DetalleBlog(post: BlogPost, onBack: () -> Unit) {
    Column(modifier = Modifier.padding(24.dp)) {
        Image(
            painter = painterResource(id = post.imageId),
            contentDescription = post.titulo,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = post.titulo, style = MaterialTheme.typography.titleLarge)
        Text(
            text = post.categoria,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = post.content, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(
                containerColor = CafeSuave,
                contentColor = Color.White
            )
        ) {
            Text("Volver al blog")
        }
    }
}
