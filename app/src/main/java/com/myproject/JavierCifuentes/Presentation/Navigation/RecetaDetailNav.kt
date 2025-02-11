package com.myproject.JavierCifuentes.Presentation.Navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.myproject.JavierCifuentes.Presentation.Screens.RecetaDetailRoute
import kotlinx.serialization.Serializable

@Serializable
data class RecetaDetailDestination(val id: Int)

fun NavGraphBuilder.recetaDetailScreen(
    onNavigateBack: () -> Unit
) {
    composable<RecetaDetailDestination> { backStackEntry ->
        val id = backStackEntry.arguments?.getInt("id")
        if (id != null) {
            RecetaDetailRoute(
                id = id,
                onNavigateBack = onNavigateBack
            )
        }
    }
}


fun NavController.navigateToRecetaDetailScreen(id: Int, navOptions: NavOptions? = null) {
    this.navigate(RecetaDetailDestination(id), navOptions)
}

