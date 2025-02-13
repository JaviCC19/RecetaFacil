package com.myproject.JavierCifuentes.Presentation.Navigation

import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import com.myproject.JavierCifuentes.Presentation.Screens.LoginRoute


@Serializable
data object LoginDestination

fun NavGraphBuilder.loginScreen(
    onLoginClick: () -> Unit
){
    composable<LoginDestination>{
        LoginRoute(
            onLoginClick = onLoginClick,


        )
    }
}