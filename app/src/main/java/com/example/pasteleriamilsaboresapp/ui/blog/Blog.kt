package com.example.pasteleriamilsaboresapp.ui.blog


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pasteleriamilsaboresapp.R
import com.example.pasteleriamilsaboresapp.ui.components.CommonFooter
import com.example.pasteleriamilsaboresapp.ui.components.CommonTopBar
import com.example.pasteleriamilsaboresapp.ui.theme.BeigeSuave
import com.example.pasteleriamilsaboresapp.ui.theme.FondoCrema
import com.example.pasteleriamilsaboresapp.ui.theme.PasteleriaMilSaboresTheme
import com.example.pasteleriamilsaboresapp.ui.view.DrawerMenu
import kotlinx.coroutines.launch


//Modelo de datos
data class BlogPost(
    val id: Int,
    val titulo: String,
    val categoria: String,
    val imageId: Int,
    val content: String
)

// Lista de posts de ejemplo
val samplePosts = listOf(
    BlogPost(
        id = 1,
        titulo = "Tartaletas con crema pastelera y frutas",
        categoria = "Receta",
        imageId = R.drawable.tartaleta,
        content = "Mini tartaletas con base crujiente, crema pastelera suave y frutas frescas: un postre equilibrado, colorido y personalizable, perfecto para disfrutar o compartir."
    ),
    BlogPost(
        id = 2,
        titulo = "5 Datos curiosos que seguro no sabías sobre la repostería",
        categoria = "Artículo",
        imageId = R.drawable.articulo,
        content = "Cinco curiosidades revelan el lado más fascinante de la repostería: sus orígenes, ingredientes valiosos y los postres más famosos del mundo, en una lectura dulce y reveladora."
    ),
    BlogPost(
        id = 3,
        titulo = "Usa merengue suizo para tortas que necesitan firmeza y elegancia",
        categoria = "Recomendación",
        imageId = R.drawable.merengue,
        content = "El merengue suizo, suave y brillante, se logra al batir claras y azúcar a baño maría, ofreciendo estabilidad, elegancia y sabor ideal para decorar o cubrir tortas."
    )
)

//Pantalla principal del blog
@Composable
fun BlogScreen(onPostClick: (Int) -> Unit) {
    LazyColumn {
        items(samplePosts) { post ->
            BlogCard(post = post, onClick = {
                onPostClick(post.id)
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogPage(navController: NavController) {
    var selectedPost by remember { mutableStateOf<BlogPost?>(null) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerMenu(
                navController = navController,
                drawerState = drawerState,
                closeDrawer = { scope.launch { drawerState.close() } }
            )
        }
    ) {
        Scaffold(
            topBar = {
                CommonTopBar(
                    onMenuClick = { scope.launch { drawerState.open() } },
                    onCartClick = { navController.navigate("catalogo") },
                    onProfileClick = { navController.navigate("nosotros") }
                )
            },
            bottomBar = { CommonFooter() }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(FondoCrema)
            ) {
                if (selectedPost == null) {
                    BlogScreen(onPostClick = { postId ->
                        selectedPost = samplePosts.find { it.id == postId }
                    })
                } else {
                    DetalleBlog(
                        post = selectedPost!!,
                        onBack = { selectedPost = null }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BlogPageScreenPreview() {
    val fakeNavController = rememberNavController() // 💡 simulació
    PasteleriaMilSaboresTheme {
        BlogPage(navController = fakeNavController)
    }
}



