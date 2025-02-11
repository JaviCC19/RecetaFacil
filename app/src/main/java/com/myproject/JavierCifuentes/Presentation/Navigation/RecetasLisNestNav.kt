package com.myproject.JavierCifuentes.Presentation.Navigation

import androidx.activity.compose.BackHandler
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.myproject.JavierCifuentes.Presentation.Screens.RecetasScreen
import kotlinx.serialization.Serializable

@Serializable
data object RecetasListDestiantion2

fun NavGraphBuilder.recetasListScreen2(
    navController: NavController,
    onBackToLogin: () -> Unit
) {
    navigation<RecetasListDestiantion2>(startDestination = RecetasListDestination) {
        recetasListScreen(
            onRecetaClick = { id ->
                navController.navigateToRecetaDetailScreen(id)
            },
            onBackToLogin = onBackToLogin,
            onCrearRecetaClick = {
                navController.navigateNewReceta()
            }
        )

        recetaDetailScreen(
            onNavigateBack = {
                navController.navigateUp()
            }
        )

        newReceta(
            onNavigateBack = {
                navController.navigateUp()
            }
        )
    }
}
