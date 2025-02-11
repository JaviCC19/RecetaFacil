package com.myproject.JavierCifuentes.Presentation.Navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.myproject.JavierCifuentes.Presentation.Screens.CrearRecetaRoute
import com.myproject.JavierCifuentes.Presentation.Screens.CrearRecetaScreen
import com.myproject.JavierCifuentes.Presentation.Screens.RecetaDetailRoute
import kotlinx.serialization.Serializable

@Serializable
data object NewRecetaDestination

fun NavGraphBuilder.newReceta(
    onNavigateBack: () -> Unit
) {
    composable<NewRecetaDestination> {
        CrearRecetaRoute(
            onNavigateBack = onNavigateBack
        )
    }
}