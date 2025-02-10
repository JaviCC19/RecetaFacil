package com.myproject.JavierCifuentes.Presentation.Navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.myproject.JavierCifuentes.Presentation.Screens.RecetaDetailRoute
import kotlinx.serialization.Serializable

@Serializable
data class RecetaDetailDestination(
    val id: Int
) {

}

 fun NavController.navigateToRecetaDetailScreen(
    destination: RecetaDetailDestination,
    navOptions: NavOptions? = null
 ){
     this.navigate(destination, navOptions)
 }

fun NavGraphBuilder.recetaDetailScreen(onNavigateBack: () -> Unit){
    composable<RecetaDetailDestination> { backStackEntry ->
        val destination: RecetaDetailDestination = backStackEntry.arguments?.getSerializable("destination") as RecetaDetailDestination
        RecetaDetailRoute(
            id = destination.id,
            onNavigateBack = onNavigateBack
        )
    }
}