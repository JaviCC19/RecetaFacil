package com.myproject.JavierCifuentes.Presentation.Navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.myproject.JavierCifuentes.Presentation.Screens.RecetasRoute
import kotlinx.serialization.Serializable

@Serializable

data object RecetasListDestination

fun NavGraphBuilder.recetasListScreen(
    onRecetaClick: (Int) -> Unit,
    onBackToLogin: () -> Unit,
    onCrearRecetaClick: () -> Unit
) {
    composable<RecetasListDestination> {
        RecetasRoute(
            onRecetaClick = onRecetaClick,
            modifier = Modifier.fillMaxWidth(),
            onCrearRecetaClick = onCrearRecetaClick
        )
        BackHandler {
            onBackToLogin()
        }
    }
}