package com.example.pasteleriamilsaboresapp.ui.Blog

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun BlogNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = "list") {
        composable("list") {
            BlogScreen(onPostClick = { postId ->
                navController.navigate("detail/$postId")
            })
        }
        composable("detail/{postId}") { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId")?.toIntOrNull()
            val post = samplePosts.find { it.id == postId }
            post?.let {
                DetalleBlog(
                    post = it,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
